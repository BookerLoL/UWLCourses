import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ExtHash {
	public static final String BUCKET_FILE_SUFFIX = "buckets";
	public static final String DIRECTORY_FILE_SUFFIX = "dir";
	private static final String FILE_MODE = "rw";
	private static final int MIN_DIRECTORY_BITS = 1;
	private static final long NOT_FOUND = 0;

	RandomAccessFile buckets;
	RandomAccessFile directory;
	int bucketSize;
	int directoryBits;

	private class Bucket {
		private long bucketAddress;
		private int nBits;
		private int nKeys;
		private int[] keys;
		private long[] rowAddrs;

		public Bucket() throws IOException {
			bucketAddress = buckets.length();
			keys = new int[bucketSize];
			rowAddrs = new long[bucketSize];
			nBits = directoryBits;
		}

		public Bucket(long address) throws IOException {
			bucketAddress = address;
			buckets.seek(bucketAddress);
			nBits = buckets.readInt();
			nKeys = buckets.readInt();

			keys = new int[bucketSize];
			for (int i = 0; i < bucketSize; i++) {
				keys[i] = buckets.readInt();
			}

			rowAddrs = new long[bucketSize];
			for (int i = 0; i < bucketSize; i++) {
				rowAddrs[i] = buckets.readLong();
			}
		}

		public int indexOfKey(int key) {
			for (int i = 0; i < nKeys; i++) {
				if (keys[i] == key) {
					return i;
				}
			}
			return -1;
		}

		public void write() throws IOException {
			buckets.seek(bucketAddress);
			buckets.writeInt(nBits);
			buckets.writeInt(nKeys);

			for (int key : keys) {
				buckets.writeInt(key);
			}
			for (long rowAddr : rowAddrs) {
				buckets.writeLong(rowAddr);
			}
		}

		public boolean isEmpty() {
			return nKeys == 0;
		}

		public boolean isFull() {
			return nKeys == bucketSize;
		}

		public boolean isDirectoryExpandable() {
			return nBits == directoryBits;
		}

		public boolean insert(int key, long address) {
			if (isFull()) {
				return false;
			}

			keys[nKeys] = key;
			rowAddrs[nKeys] = address;
			nKeys++;
			return true;
		}

		public long remove(int key) {
			int index = this.indexOfKey(key);
			if (index == -1) {
				return NOT_FOUND;
			}

			long addr = this.rowAddrs[index];

			// shift array content over
			System.arraycopy(keys, index + 1, keys, index, nKeys - index - 1);
			System.arraycopy(rowAddrs, index + 1, rowAddrs, index, nKeys - index - 1);
			nKeys--;

			return addr;
		}

		public List<Pair<Integer, Long>> getKeyAndAddresses() {
			List<Pair<Integer, Long>> keyAndAddr = new LinkedList<>();
			for (int key = 0; key < nKeys; key++) {
				keyAndAddr.add(new Pair<>(keys[key], rowAddrs[key]));
			}
			return keyAndAddr;
		}

		public String toString() {
			return String.format("Bucket Address: %d, Bits: %d, Total Keys: %d, Keys: %s, Key Addresses: %s",
					this.bucketAddress, this.nBits, this.nKeys, Arrays.toString(this.keys),
					Arrays.toString(this.rowAddrs));
		}
	}

	private class Pair<K, V> {
		K key;
		V value;

		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public ExtHash(String filename, int bsize) throws IOException {
		File bucketsFile = createNewFile(filename + BUCKET_FILE_SUFFIX);
		File directoryFile = createNewFile(filename + DIRECTORY_FILE_SUFFIX);
		buckets = new RandomAccessFile(bucketsFile, FILE_MODE);
		directory = new RandomAccessFile(directoryFile, FILE_MODE);
		bucketSize = bsize > 0 ? bsize : 4;
		directoryBits = MIN_DIRECTORY_BITS;

		buckets.writeInt(bucketSize);
		directory.writeInt(directoryBits);
		initDirectoryAndBinAddresses();
	}

	private void initDirectoryAndBinAddresses() throws IOException {
		directory.seek(directory.length());

		int totalDirectories = totalDirectories();
		for (int directoryBin = 0; directoryBin < totalDirectories; directoryBin++) {
			Bucket bucket = new Bucket(); // buckets seek end of the file by default
			bucket.write();
			directory.writeLong(bucket.bucketAddress);
			// System.out.println(directoryBin + "\t" + bucket.bucketAddress);
		}
	}

	private File createNewFile(String filename) throws IOException {
		File file = new File(filename);

		if (file.exists()) {
			file.delete();
		}

		file.createNewFile();
		return file;
	}

	public ExtHash(String filename) throws IOException {
		buckets = new RandomAccessFile(filename + BUCKET_FILE_SUFFIX, FILE_MODE);
		directory = new RandomAccessFile(filename + DIRECTORY_FILE_SUFFIX, FILE_MODE);

		bucketSize = buckets.readInt();
		directoryBits = directory.readInt();
	}

	public boolean insert(int key, long addr) throws IOException {
		/*
		 * If key is not a duplicate add key to the hash index addr is the address of
		 * the row that contains the key return true if the key is added return false if
		 * the key is a duplicate This method might need to extend the directory or
		 * create new buckets
		 */
		return insert(key, addr, false);
	}

	private boolean insert(int key, long addr, boolean skipSearch) throws IOException {
		if (!skipSearch && search(key) != NOT_FOUND) { // key already exists
			return false;
		}

		System.out.println("Trying to insert: " + key + "\t reinsert? " + skipSearch);

		Bucket keyBucket = getBucketOn(key);

		System.out.println("BUCKET: " + keyBucket);

		if (keyBucket.insert(key, addr)) {
			keyBucket.write();
			return true;
		}

		if (keyBucket.isDirectoryExpandable()) {
			System.out.println("EXPANDING DIRECTORY");
			expandDirectory();
		}

		// SPLIT
		// Grab all the bucket, split due to differing bits of the bucket's next nBits
		final int nextBucketNBits = keyBucket.nBits + 1;
		final int updatedBitsToMatch = getLeastSigBitsValue(hash(key), nextBucketNBits);

		System.out.println(nextBucketNBits);
		System.out.println(hash(key));
		System.out.println(updatedBitsToMatch);
		// Need to handle case where multiple directories point to the same bucket, rare
		// case that arises when buckets are small
		Pair<List<Integer>, List<Integer>> splitLists = getSplitDirectoryBins(keyBucket, updatedBitsToMatch,
				nextBucketNBits);

		System.out.println("SPLIT LIST ONE: " + splitLists.key);
		System.out.println("SPLIT LIST TWO: " + splitLists.value);
		// Grabs the directory with a higher number
		List<Integer> newSplitDirectoryBins = getMaxHeadList(splitLists);

		Bucket newSplitBucket = new Bucket();
		for (int splitDirectoryBin : newSplitDirectoryBins) {
			directory.seek(getDirectoryAddress(splitDirectoryBin));
			directory.writeLong(newSplitBucket.bucketAddress);
		}

		// grab all the items to rearrange
		// would be more efficient to just split the keys between the two buckets.
		List<Pair<Integer, Long>> rearrangeItems = keyBucket.getKeyAndAddresses();
		rearrangeItems.add(new Pair<>(key, addr));

		// Will reinsert all the keys in case of rare propagated splits
		keyBucket.nKeys = 0;
		keyBucket.nBits = nextBucketNBits;
		newSplitBucket.nBits = nextBucketNBits;
		keyBucket.write();
		newSplitBucket.write();

		// Insert all the items
		for (Pair<Integer, Long> item : rearrangeItems) {
			insert(item.key, item.value, true);
		}

		return true;
	}

	private List<Integer> getPointedDirectoriesOn(Bucket b) throws IOException {
		List<Integer> sameDirectoryBucket = new LinkedList<>();
		List<Long> bucketAddresses = getAllDirectoryBucketAddresses();
		for (int directoryBin = 0; directoryBin < bucketAddresses.size(); directoryBin++) {
			if (bucketAddresses.get(directoryBin) == b.bucketAddress) {
				sameDirectoryBucket.add(directoryBin);
			}
		}
		return sameDirectoryBucket;
	}

	private Pair<List<Integer>, List<Integer>> getSplitDirectoryBins(Bucket keyBucket, final int updatedBitsToMatch,
			final int nextBucketNBits) throws IOException {
		List<Integer> directoryBins = getPointedDirectoriesOn(keyBucket);
		List<Integer> splitBinOne = new LinkedList<>();
		List<Integer> splitBinTwo = new LinkedList<>();

		for (int directoryBin : directoryBins) {
			if (getLeastSigBitsValue(directoryBin, nextBucketNBits) != updatedBitsToMatch) {
				splitBinOne.add(directoryBin);
			} else {
				splitBinTwo.add(directoryBin);
			}
		}
		return new Pair<>(splitBinOne, splitBinTwo);
	}

	private List<Integer> getMaxHeadList(Pair<List<Integer>, List<Integer>> listPair) {
		if (listPair.key.isEmpty()) {
			return listPair.key;
		} else if (listPair.value.isEmpty()) {
			return listPair.value;
		}
		return listPair.key.get(0) > listPair.value.get(0) ? listPair.key : listPair.value;
	}

	private void expandDirectory() throws IOException {
		// double directory using the current directory bucket addresses for the new
		// directories
		System.out.println("Before Directory Length: " + (directory.length() - 4));

		List<Long> oldAddresses = getAllDirectoryBucketAddresses();
		directory.seek(directory.length());
		for (long copyOldAddress : oldAddresses) {
			directory.writeLong(copyOldAddress);
		}
		System.out.println("After Directory Length: " + (directory.length() - 4));
		this.directoryBits++;
		directory.seek(0);
		directory.writeInt(directoryBits);
	}

	private List<Long> getAllDirectoryBucketAddresses() throws IOException {
		final int totalDirectories = totalDirectories();
		List<Long> directoryBucketAddresses = new ArrayList<>(totalDirectories);
		for (int directoryBin = 0; directoryBin < totalDirectories; directoryBin++) {
			directory.seek(getDirectoryAddress(directoryBin));
			directoryBucketAddresses.add(directory.readLong());
		}
		return directoryBucketAddresses;
	}

	private int totalDirectories() {
		return 1 << directoryBits; // (int) Math.pow(2, directoryBits)
	}

	public long remove(int key) throws IOException {
		/*
		 * If the key is in the hash index, remove the key and return the address of the
		 * row. return 0 if the key is not found in the hash index This method might
		 * have to recover space for the buckets or decrease the size of the directory
		 */
		Bucket keyBucket = getBucketOn(key);
		long removeAddr = keyBucket.remove(key);

		if (removeAddr == NOT_FOUND) {
			return removeAddr;
		}

		keyBucket.write();

		// Can attempt to borrow or combine
		if (keyBucket.isEmpty() && directoryBits > MIN_DIRECTORY_BITS) {
			List<Integer> samePointingDirectories = getPointedDirectoriesOn(keyBucket);

			int keyDirectory = getLeastSigBitsValue(hash(key), directoryBits);
			int firstLaterHalfDirectory = totalDirectories() / 2;

			System.out.println(keyDirectory + "\t" + samePointingDirectories);
			keyDirectory = samePointingDirectories.get(0); // always has the min at the start

			// Check if can borrow from parent
			if (keyDirectory < firstLaterHalfDirectory) {
				int parentDirectoryBin = firstLaterHalfDirectory + keyDirectory;
				System.out.println("KEY DIRECTORY VS LATER HALF: " + keyDirectory + "\t" + parentDirectoryBin + "\t"
						+ totalDirectories());

				Bucket parentBucket = getBucketFromDirectory(parentDirectoryBin);

				System.out.println("BORROWING BITS: PARENT VS KEY\t" + parentBucket.nBits + " vs " + keyBucket.nBits);

				keyBucket.nKeys = Math.max(keyBucket.nKeys, parentBucket.nKeys);
				keyBucket.keys = parentBucket.keys;
				keyBucket.rowAddrs = parentBucket.rowAddrs;
				if (keyBucket.nBits > MIN_DIRECTORY_BITS) {
					keyBucket.nBits--;
				} else {
					// CAN HAPPEN IF BORROWING AT 0 OR 1 BUCKET
					System.out.println("BITS WAS ALREADY 1 \t" + keyBucket);
				}

				keyBucket.write();

				if (parentBucket.nBits > MIN_DIRECTORY_BITS) {
					parentBucket.nKeys = 0;
					parentBucket.nBits--;
				}
				parentBucket.write();

				samePointingDirectories = getPointedDirectoriesOn(parentBucket);
				updateDirectoryBinAddrs(keyBucket, samePointingDirectories);

			}

			// Collapse pointers to lower bin if possible
			// Probably can get rid of correspondingLowerHalfBin check since keyDirectory protects against those issues
			int correspondingLowerHalfBin = keyDirectory - (getNearestUnderPowerOfTwo(keyDirectory) / 2);
			System.out.println("LOWER HALF BIN: " + correspondingLowerHalfBin + "\tKEY DIRECTORY: " + keyDirectory);
			if (keyDirectory > 1 && correspondingLowerHalfBin >= 0
					&& !samePointingDirectories.contains(correspondingLowerHalfBin)) {
				Bucket lowerHalfBinBucket = this.getBucketFromDirectory(correspondingLowerHalfBin);

				if (lowerHalfBinBucket.nBits > MIN_DIRECTORY_BITS) {
					lowerHalfBinBucket.nBits--;
				}

				System.out.println("COLLASPING");
				lowerHalfBinBucket.write();
				updateDirectoryBinAddrs(lowerHalfBinBucket, samePointingDirectories);
			}

			boolean removedHalfDirectory = false;
			while (canRemoveHalfDirectory()) {
				directoryBits--;
				directory.seek(0);
				directory.writeInt(directoryBits);
				directory.setLength(getDirectoryAddress(totalDirectories()));
				removedHalfDirectory = true;
			}

			// clean up bucket file as much as possible
			if (removedHalfDirectory) {
				long endOfLastValidBucket = addrsMapBin().keySet().stream().max(Comparator.naturalOrder()).get()
						+ bucketSize();
				buckets.setLength(endOfLastValidBucket);
			}
		}

		return removeAddr;
	}

	private void updateDirectoryBinAddrs(Bucket newBin, List<Integer> directories) throws IOException {
		for (int samePointingDirectory : directories) {
			directory.seek(getDirectoryAddress(samePointingDirectory));
			directory.writeLong(newBin.bucketAddress);
		}
	}

	private boolean canRemoveHalfDirectory() throws IOException {
		if (directoryBits == MIN_DIRECTORY_BITS) {
			return false;
		}

		boolean canRemoveHalf = true;
		final int totalDirectories = totalDirectories();
		for (int bin = totalDirectories / 2; bin < totalDirectories; bin++) {
			directory.seek(getDirectoryAddress(bin));
			Bucket b = new Bucket(directory.readLong());

			if (b.isEmpty() || b.nBits < this.directoryBits) {
				continue;
			}

			canRemoveHalf = false;
			break;
		}
		return canRemoveHalf;
	}

	private HashMap<Long, Integer> addrsMapBin() throws IOException {
		HashMap<Long, Integer> addrToBin = new HashMap<>();
		for (int i = 0; i < totalDirectories(); i++) {
			Bucket b = getBucketFromDirectory(i);
			addrToBin.putIfAbsent(b.bucketAddress, i);
		}
		return addrToBin;
	}

	public long search(int k) throws IOException {
		/*
		 * If the key is found return the address of the row with the key otherwise
		 * return 0
		 */
		Bucket keyBucket = getBucketOn(k);
		int keyIndex = keyBucket.indexOfKey(k);
		return keyIndex != -1 ? keyBucket.rowAddrs[keyIndex] : NOT_FOUND;
	}

	private int getDirectoryBinOn(int key) throws IOException {
		int hashKey = hash(key);
		return getLeastSigBitsValue(hashKey, directoryBits);
	}

	private Bucket getBucketOn(int key) throws IOException {
		int directoryBin = getDirectoryBinOn(key);
		return getBucketFromDirectory(directoryBin);
	}

	private Bucket getBucketFromDirectory(int directoryBin) throws IOException {
		directory.seek(getDirectoryAddress(directoryBin));
		return new Bucket(directory.readLong());
	}

	private long getDirectoryAddress(int directoryBin) {
		return directoryBin * 8 + 4; // directory * long byte size + offset
	}

	public int hash(int key) {
		return key;
	}

	public void close() throws IOException {
		buckets.close();
		directory.close();
	}

	private static int getLeastSigBitsValue(int value, int nBits) {
		StringBuilder binarySB = new StringBuilder(nBits);
		nBits = Math.min(nBits, 32); // int is only 32 bits at most
		for (int i = nBits - 1; i >= 0; i--) {
			int mask = 1 << i;
			char bit = (value & mask) == 0 ? '0' : '1';
			binarySB.append(bit);
		}

		return Integer.parseInt(binarySB.toString(), 2);
	}

	private long bucketSize() {
		return 8 + (4 * bucketSize) + (8 * bucketSize);
	}

	private static int getNearestUnderPowerOfTwo(int number) {
		int powerOfTwo = 2;
		while (number >= powerOfTwo) {
			powerOfTwo = powerOfTwo << 1;
		}
		return powerOfTwo;
	}

	public void printDirectory() throws IOException {
		directory.seek(0);
		int bits = directory.readInt();
		System.out.println("Directory Bits: " + bits);
		List<Long> bucketAddress = new ArrayList<>(this.totalDirectories());
		for (int i = 0; i < totalDirectories(); i++) {
			bucketAddress.add(directory.readLong());
		}

		for (int i = 0; i < totalDirectories(); i++) {
			System.out.println("Bin " + i + ", address=" + bucketAddress.get(i));
		}
	}

	public void printBuckets() throws IOException {
		HashMap<Long, Integer> addrToBin = addrsMapBin();

		this.buckets.seek(0);
		int bucketSize = buckets.readInt();
		System.out.println("Bucket size: " + bucketSize);
		System.out.println("Total buckets: " + addrToBin.size());

		for (long addr : new ArrayList<>(addrToBin.keySet()).stream().sorted(Comparator.naturalOrder())
				.collect(Collectors.toList())) {
			int bin = addrToBin.get(addr);
			Bucket b = new Bucket(addr);
			System.out.println("Bin: " + bin + ", " + b);
		}
	}
}
