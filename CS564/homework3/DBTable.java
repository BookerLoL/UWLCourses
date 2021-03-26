import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class DBTable {
	private static long EMPTY_ADDR = 0;
	RandomAccessFile rows; // the file that stores the rows in the table
	int numOtherFields;
	int otherFieldsLengths[];
	int bucketSize;
	ExtHash hashIndexer;

	private class Row {
		private long rowAddr;
		private int keyField;
		private char otherFields[][];

		/*
		 * Each row consists of a unique key and one or more character array fields
		 * 
		 * Each character field is a fixed length
		 * 
		 * Each field can have different length (values)
		 * 
		 * Fields are padding with null characters
		 */
		public Row(int keyField, char fields[][]) {
			this.keyField = keyField;
			initOtherFields();

			for (int field = 0; field < fields.length; field++) {
				System.arraycopy(fields[field], 0, otherFields[field], 0, fields[field].length);
			}
		}

		public Row(long addr) throws IOException {
			rowAddr = addr;
			rows.seek(rowAddr);
			this.keyField = rows.readInt();
			initOtherFields();

			for (int field = 0; field < otherFields.length; field++) {
				for (int i = 0; i < otherFields[field].length; i++) {
					otherFields[field][i] = rows.readChar();
				}
			}
		}

		private void initOtherFields() {
			otherFields = new char[otherFieldsLengths.length][];
			for (int field = 0; field < otherFieldsLengths.length; field++) {
				int length = otherFieldsLengths[field];
				otherFields[field] = new char[length];
			}
		}

		public void write() throws IOException {
			rows.seek(rowAddr);
			rows.writeInt(keyField);

			for (char[] field : otherFields) {
				for (char c : field) {
					rows.writeChar(c);
				}
			}
		}

		public String getField(int field, boolean includeNullChars) {
			StringBuilder sb = new StringBuilder(otherFields[field].length);

			if (includeNullChars) {
				for (char ch : otherFields[field]) {
					sb.append(ch);
				}
			} else {
				for (char ch : otherFields[field]) {
					if (ch == '\0') {
						break;
					}
					sb.append(ch);
				}
			}
			return sb.toString();
		}

		public String toString() {
			String output = String.format("Row at addr: %d, key=%d, ", this.rowAddr, this.keyField);
			for (int i = 0; i < numOtherFields; i++) {
				output += "field " + (i + 1) + "=" + this.getField(i, true) + ", ";
			}
			return output;
		}
	}

	public DBTable(String filename, int fL[], int bsize) throws IOException {
		/*
		 * Use this constructor to create a new DBTable. filename is the name of the
		 * file used to store the table fL is the lengths of the otherFields fL.length
		 * indicates how many other fields are part of the row bsize is the bucket size
		 * used by the hash index A ExtHash object must be created for the key field in
		 * the table
		 * 
		 * If a file with name filename exists, the file should be deleted before the
		 * new file is created.
		 */
		rows = new RandomAccessFile(createNewFile(filename), "rw");
		bucketSize = bsize;
		numOtherFields = fL.length;
		otherFieldsLengths = new int[numOtherFields];
		System.arraycopy(fL, 0, otherFieldsLengths, 0, numOtherFields);

		hashIndexer = new ExtHash(filename, bsize);

		rows.writeInt(numOtherFields);
		for (int fieldLength : otherFieldsLengths) {
			rows.writeInt(fieldLength);
		}
		rows.writeLong(EMPTY_ADDR); // free list
	}

	public DBTable(String filename) throws IOException {
		// Use this constructor to open an existing DBTable
		this.rows = new RandomAccessFile(filename, "rw");
		this.numOtherFields = rows.readInt();
		this.otherFieldsLengths = new int[numOtherFields];
		for (int field = 0; field < otherFieldsLengths.length; field++) {
			this.otherFieldsLengths[field] = this.rows.readInt();
		}
		this.hashIndexer = new ExtHash(filename);
	}

	private File createNewFile(String filename) throws IOException {
		File file = new File(filename);

		if (file.exists()) {
			file.delete();
		}

		file.createNewFile();
		return file;
	}

	public boolean insert(int key, char fields[][]) throws IOException {
		// PRE: the length of each row in fields matches the expected length
		/*
		 * If a row with the key is not in the table, the row is added and the method
		 * returns true otherwise the row is not added and the method returns false. The
		 * method must use the hash index to determine if a row with the key exists. If
		 * the row is added the key is also added into the hash index.
		 */
		boolean usedFreeList = !isFreeListEmpty();
		long addr = usedFreeList ? getFreeListHead() : this.rows.length();

		if (!hashIndexer.insert(key, addr)) {
			return false;
		}

		if (usedFreeList) {
			popFreeListHead();
		}

		Row row = new Row(key, fields);
		row.rowAddr = addr;
		row.write();

		return true;
	}

	public boolean remove(int key) throws IOException {
		/*
		 * If a row with the key is in the table it is removed and true is returned
		 * otherwise false is returned. The method must use the hash index to determine
		 * if a row with the key exists.
		 * 
		 * If the row is deleted the key must be deleted from the hash index
		 */
		long addr = hashIndexer.remove(key);

		if (addr == EMPTY_ADDR) {
			return false;
		}

		pushFreeListHead(addr);
		
		//System.out.println(this.hashIndexer.buckets.length());
		return true;
	}

	public LinkedList<String> search(int key) throws IOException {
		/*
		 * If a row with the key is found in the table return a list of the other fields
		 * in the row. The string values in the list should not include the null
		 * characters used for padding. If a row with the key is not found return an
		 * empty list The method must use the hash index index to determine if a row
		 * with the key exists
		 */
		LinkedList<String> items = new LinkedList<>();
		long addr = hashIndexer.search(key);

		if (addr == EMPTY_ADDR) {
			return items;
		}

		Row row = new Row(addr);
		for (int field = 0; field < row.otherFields.length; field++) {
			items.add(row.getField(field, false)); // stops at null characters
		}

		return items;
	}

	private void pushFreeListHead(long newAddr) throws IOException {
		long previousHead = getFreeListHead();
		rows.seek(newAddr);
		rows.writeLong(previousHead);

		rows.seek(getFreeListAddress());
		rows.writeLong(newAddr);
	}

	private long getFreeListHead() throws IOException {
		this.rows.seek(getFreeListAddress());
		return rows.readLong();
	}

	private void popFreeListHead() throws IOException {
		rows.seek(getFreeListHead());
		long afterHead = rows.readLong();

		rows.seek(getFreeListAddress());
		rows.writeLong(afterHead);
	}

	private boolean isFreeListEmpty() throws IOException {
		long freeSpaceAddr = getFreeListHead();
		return freeSpaceAddr == EMPTY_ADDR;
	}

	private long getFreeListAddress() {
		return 4 + numOtherFields * 4; // numOtherFields + otherLengths
	}

	public void close() throws IOException {
		rows.close();
		hashIndexer.close();
	}

	public void printDBTable() throws IOException {
		this.rows.seek(0);

		// Other fields
		System.out.println("numOtherFields=" + rows.readInt());
		for (int i = 0; i < this.numOtherFields; i++) {
			System.out.println("Field " + (i + 1) + " size=" + rows.readInt());
		}

		// Print free list
		List<Long> freeList = new LinkedList<>();
		long addr = this.getFreeListAddress();
		do {
			this.rows.seek(addr);
			addr = rows.readLong();
			freeList.add(addr);
		} while (addr != 0);
		System.out.println("Free list: " + freeList);

		long rowSize = this.rowSize();
		rows.seek(getFreeListAddress());
		rows.readLong();
		long currentAddress = rows.getFilePointer();
		while (currentAddress < rows.length()) {
			if (!freeList.contains(currentAddress)) {
				Row r = new Row(currentAddress);
				System.out.println(r);
			}
			currentAddress += rowSize;
		}
	}

	private long rowSize() {
		int totalSize = 4; // 4 for int key
		for (int fieldLength : this.otherFieldsLengths) {
			totalSize += (fieldLength * 2); // each char is 2
		}
		return totalSize;
	}

	public void printDirectory() throws IOException {
		this.hashIndexer.printDirectory();
	}

	public void printBuckets() throws IOException {
		this.hashIndexer.printBuckets();
	}
}
