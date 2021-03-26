import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExtHash {
	private static final String FILE_MODE = "rw";
	private static final long NOT_FOUND = 0;
	public static final int INITIAL_HASH_BITS = 1;
	public static final String BUCKET_FILE_SUFFIX = "buckets";
	public static final String DIRECTORY_FILE_SUFFIX = "dir";

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
			// Bucket in order of file structure
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
		bucketSize = bsize;
		directoryBits = INITIAL_HASH_BITS;

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

		Bucket keyBucket = getBucketOn(key);

		if (keyBucket.insert(key, addr)) {
			keyBucket.write();
			return true;
		}

		if (keyBucket.isDirectoryExpandable()) {
			expandDirectory();
		}

		// SPLIT

		// Grab all the buckets that need to be split due to differing bits of the
		// bucket's next nBits
		final int nextBucketNBits = keyBucket.nBits + 1;
		final int updatedBitsToMatch = getLeastSigBitsValue(hash(key), nextBucketNBits);

		List<Integer> newSplitDirectoryBins = getPointedDirectoriesOn(keyBucket).stream()
				.filter(directoryBin -> updatedBitsToMatch == getLeastSigBitsValue(directoryBin, nextBucketNBits))
				.collect(Collectors.toList());

		// update directories that need to split to the new bucket, there could be more
		// than 1
		Bucket newSplitBucket = new Bucket();
		// System.out.println(newSplitDirectoryBins);
		for (int splitDirectoryBin : newSplitDirectoryBins) {
			directory.seek(getDirectoryAddress(splitDirectoryBin));
			directory.writeLong(newSplitBucket.bucketAddress);
		}

		// grab all the items to rearrange
		List<Pair<Integer, Long>> rearrangeItems = keyBucket.getKeyAndAddresses();
		rearrangeItems.add(new Pair<>(key, addr));

		// Update the nbits and update the key as if removed and to handle potential
		// future splits.

		keyBucket.nBits = nextBucketNBits;
		newSplitBucket.nBits = nextBucketNBits;
		keyBucket.nKeys = 0;
		keyBucket.write();
		newSplitBucket.write();

		// System.out.println(keyBucket);
		// System.out.println(newSplitBucket);
		// System.out.println();

		// Insert all the items
		for (Pair<Integer, Long> item : rearrangeItems) {
			insert(item.key, item.value, true);
		}

		return true;
	}

	private void expandDirectory() throws IOException {
		// double directory using the current bucket addresses for the new directories
		List<Long> oldAddresses = getAllDirectoryBucketAddresses();
		directory.seek(directory.length());

		for (long oldAddress : oldAddresses) {
			directory.writeLong(oldAddress);
		}

		this.directoryBits++;
		directory.seek(0); // update directory bits in file
		directory.writeInt(directoryBits);
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

	private List<Long> getAllDirectoryBucketAddresses() throws IOException {
		final int totalDirectories = totalDirectories();
		List<Long> directoryBucketAddresses = new ArrayList<>();
		for (int directoryBin = 0; directoryBin < totalDirectories; directoryBin++) {
			directory.seek(getDirectoryAddress(directoryBin));
			directoryBucketAddresses.add(directory.readLong());
		}
		return directoryBucketAddresses;
	}

	private int totalDirectories() {
		return (int) Math.pow(2, directoryBits);
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
		int keyDirectory = getLeastSigBitsValue(hash(key), directoryBits);

		if (keyBucket.isEmpty() && this.directoryBits > 1) {
			List<Integer> samePointingDirectories = this.getPointedDirectoriesOn(keyBucket);
			int correspondingLowerHalfBin = keyDirectory - (getNearestUnderPowerOfTwo(keyDirectory) / 2);
			int firstLaterHalfDirectory = totalDirectories() / 2;

			keyDirectory = samePointingDirectories.stream().min(Integer::compare).get();

			// Check if can borrow from parent
			if (keyDirectory < firstLaterHalfDirectory) {
				int parentDirectoryBin = firstLaterHalfDirectory + keyDirectory;

				Bucket parentBucket = this.getBucketFromDirectory(parentDirectoryBin);

				keyBucket.nKeys = parentBucket.nKeys;
				keyBucket.keys = parentBucket.keys;
				keyBucket.rowAddrs = parentBucket.rowAddrs;
				keyBucket.nBits = Math.min(parentBucket.nBits, keyBucket.nBits);
				if (keyBucket.nBits > 1) {
					keyBucket.nBits--;
				}

				keyBucket.write();

				parentBucket.nBits--;
				parentBucket.write();

				samePointingDirectories = getPointedDirectoriesOn(parentBucket);
				updateDirectoryBinAddrs(keyBucket, samePointingDirectories);

			}
			if (correspondingLowerHalfBin >= 0 && !samePointingDirectories.contains(correspondingLowerHalfBin)) {
				// Check if need to shift pointers.
				Bucket lowerHalfBinBucket = this.getBucketFromDirectory(correspondingLowerHalfBin);

				if (lowerHalfBinBucket.nBits > 1) {
					lowerHalfBinBucket.nBits--;
				}

				lowerHalfBinBucket.write();
				updateDirectoryBinAddrs(lowerHalfBinBucket, samePointingDirectories);
			}

			boolean removedHalfDirectory = false;
			while (canRemoveHalfDirectory()) {
				this.directoryBits--;
				this.directory.seek(0);
				this.directory.writeInt(directoryBits);
				this.directory.setLength(getDirectoryAddress(totalDirectories()));
				removedHalfDirectory = true;
			}

			if (removedHalfDirectory) { // clean up bucket file as much as possible
				long bucketAddr = addrsMapBin().keySet().stream().max(Comparator.naturalOrder()).get()
						+ this.bucketSize();
				this.buckets.setLength(bucketAddr);
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
		if (directoryBits == 1) {
			return false;
		}

		boolean canRemoveHalf = true;
		final int totalDirectories = totalDirectories();
		for (int bin = totalDirectories / 2; bin < totalDirectories; bin++) {
			directory.seek(getDirectoryAddress(bin));
			Bucket b = new Bucket(directory.readLong());

			if (b.isEmpty() || b.nBits < this.directoryBits) {
				continue;
			} else {
				canRemoveHalf = false;
				break;
			}

		}

		return canRemoveHalf;
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
		// directory * long byte size + offset
		return directoryBin * 8 + 4;
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

		for (int i = nBits - 1; i >= 0; i--) {
			int mask = 1 << i;
			char bit = (value & mask) == 0 ? '0' : '1';
			binarySB.append(bit);
		}

		return Integer.parseInt(binarySB.toString(), 2);
	}

	private long bucketSize() {
		return 8 + (4 * this.bucketSize) + (8 * this.bucketSize);
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

	private HashMap<Long, Integer> addrsMapBin() throws IOException {
		HashMap<Long, Integer> addrToBin = new HashMap<>();
		for (int i = 0; i < this.totalDirectories(); i++) {
			Bucket b = this.getBucketFromDirectory(i);
			addrToBin.putIfAbsent(b.bucketAddress, i);
		}
		return addrToBin;
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
