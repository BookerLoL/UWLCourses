import java.io.*;
import java.util.LinkedList;
public class DBTable {
    RandomAccessFile rows; //the file that stores the rows in the table
    long free; //head of the free list space for rows
    int numOtherFields;
    int otherFieldLengths[];
    static final String BTREE_EXTENSION = "BTreeFile";
    //add other instance variables as needed 
    BTree tree;
    private class Row {
        private int keyField;
        private char otherFields[][];
        /*
         * Each row consists of unique key and one or more character array fields.
         * Each character array field is a fixed length field (for example 10
         * characters).
         * Each field can have a different length.
         * Fields are padded with null characters so a field with a length of
         * of x characters always uses space for x characters.
         */
        //Constructors and other Row methods
        
        /**
         * 
         */
        public Row(long address) throws IOException {
            rows.seek(address);
            keyField = rows.readInt();
            
            otherFields = new char[otherFieldLengths.length][];
            for(int i = 0; i < otherFieldLengths.length; i++) {
                otherFields[i] = new char[otherFieldLengths[i]];
            }
            
            int counter = 0; 
            int counter2 = 0;
            while(counter != otherFields.length) {
                while(counter2 != otherFields[counter].length) {
                    otherFields[counter][counter2] = rows.readChar();
                    counter2++;
                }
                counter2 = 0;
                counter++;
            }
        }
        
        
        public Row(int key, char otherFields[][]) throws IOException {
            keyField = key;
            
            this.otherFields = new char[otherFieldLengths.length][];
            for(int i = 0; i < otherFieldLengths.length; i++) {
                this.otherFields[i] = new char[otherFieldLengths[i]];
            }
            
            int counter = 0; 
            int counter2 = 0;
            //System.out.println("OtherFieldLengths length: " + otherFieldLengths.length);
            while(counter != otherFields.length && counter != otherFieldLengths.length) {
                //System.out.println("OtherFieldLengths[counter]: " + otherFieldLengths[counter]);
                while(counter2 != otherFields[counter].length && counter2 != otherFieldLengths[counter]) {
                    this.otherFields[counter][counter2] = otherFields[counter][counter2];
                    counter2++;
                }
                counter2 = 0; 
                counter++;
            }
        }
        
        /**
         * 
         */
        public void writeRow(long address) throws IOException {
            rows.seek(address);
            rows.writeInt(keyField);
            
            int counter = 0; 
            int counter2 = 0;
            while(counter != otherFields.length) {
                while(counter2 != otherFields[counter].length) {
                    rows.writeChar(otherFields[counter][counter2]);
                    counter2++;
                }
                counter2 = 0;
                counter++;
            }
        }
    }
    
    /**
     * DBTable constructor
     * @param String filename for DBTable data
     * @param int[] the lengths of the otherFields
     * @param int the block size
     * @throws IOException 
     */
    public DBTable(String filename, int fL[], int bsize ) throws IOException {
        /*
         * Use this constructor to create a new DBTable.
         * filename is the name of the file used to store the table
         * fL is the lengths of the otherFields
         * fL.length indicates how many other fields are part of the row
         * bsize is the block size. It is used to calculate the order of the B+Tree
         * A B+Tree must be created for the key field in the table
         * If a file with name filename exists, the file should be deleted before the
         * new file is created.
         */
        File path = new File(filename);
        rows = new RandomAccessFile(path, "rw");
        tree = new BTree(createUniqueFileName(filename), bsize);
        //Reset the files
        if(path.exists()) {
            //reset length
            rows.setLength(0);
        } 
        
        numOtherFields = fL.length;
        otherFieldLengths = fL;
        
        //numOtherFields
        rows.seek(0);
        rows.writeInt(numOtherFields);
        //Length of each other field 
        for(int i = 0; i < numOtherFields; i++) {
            rows.writeInt(otherFieldLengths[i]);
        }
        
        //Free
        rows.writeLong(0);
    }
    
    /**
     * Helper method to create a unique name
     * Can be changed to create more of a unique name
     * @param String filename of dbtable 
     * @param String filename for BTree 
     */
    private String createUniqueFileName(String filename) {
        return filename + BTREE_EXTENSION; 
    }
 
    public DBTable(String filename) throws IOException{
        //Use this constructor to open an existing DBTable
        File path = new File(filename);
        rows = new RandomAccessFile(path, "rw");
        tree = new BTree(createUniqueFileName(filename));
            
        //Update data in DBTable
        rows.seek(0);
        numOtherFields = rows.readInt();
        otherFieldLengths = new int[numOtherFields];
        for(int i = 0; i < numOtherFields; i++) {
            int otherFields = rows.readInt();
            otherFieldLengths[i] = otherFields; 
        }
        free = rows.readLong();
    }
    
    private void addFree(long addr) throws IOException{
        rows.seek(addr);
        rows.writeLong(free);
        free = addr;
    }
    
    private long getFree() throws IOException{
        if(free == 0 ) {
            return rows.length();
        }
        long curFree = free;
        rows.seek(free);
        free = rows.readLong();
        return curFree;
    }
    
    private long peekFree() throws IOException {
        if(free != 0) {
            return free;
        } 
        return rows.length();
    }
    
    public boolean insert(int key, char fields[][]) throws IOException {
        //PRE: the length of each row is fields matches the expected length
        /*
         * If a row with the key is not in the table, the row is added and the method
         * returns true otherwise the row is not added and the method returns false.
         * The method must use the B+tree to determine if a row with the key exists.
         * If the row is added the key is also added into the B+tree.
           */
          
        
        if(tree.insert(key, peekFree())) {
            //System.out.println("Inserted the value: " + key);
            Row insertedRow = new Row(key, fields);
            insertedRow.writeRow(getFree());
            return true;
        } else {
            //System.out.println("Key is already in the tree: " + key);
            return false;
        }
    } 
 
    public boolean remove(int key) throws IOException{
        /*
         * If a row with the key is in the table it is removed and true is returned
         * otherwise false is returned.
         * The method must use the B+Tree to determine if a row with the key exists.
         * If the row is deleted the key must be deleted from the B+Tree
           */
        long removeAddr = tree.remove(key);
        
        //Not in table
        if(removeAddr == 0) {
            return false;
        } 
        
        //Found in table, free up address 
        addFree(removeAddr);
        return true; 
    }
 
    public LinkedList<String> search(int key) throws IOException {
        /*
         * If a row with the key is found in the table return a list of the other fields in
         * the row.
         * The string values in the list should not include the null characters.
         * If a row with the key is not found return an empty list
         * The method must use the equality search in B+Tree
           */
        long addr = tree.search(key);
        LinkedList<String> rowContent = new LinkedList<>();
        if(addr == 0) {
            //System.out.println("Didn't find anything");
            return rowContent;
        }else {
            //System.out.println("Found something");
            StringBuilder tempString = new StringBuilder();
            Row tempRow = new Row(addr);
            int counter = 0; 
            int counter2 = 0;
            while(counter != otherFieldLengths.length) {
                while(counter2 != tempRow.otherFields[counter].length  && tempRow.otherFields[counter][counter2] != '\0') {
                    tempString.append(tempRow.otherFields[counter][counter2]);
                    counter2++;
                }
                rowContent.add(tempString.toString());
                tempString.setLength(0);
                counter2 = 0;
                counter++;
            }
            
            return rowContent;
        }
    }
    
    public LinkedList<LinkedList<String>> rangeSearch(int low, int high) throws IOException {
        //PRE: low <= high
        /*
         * For each row with a key that is in the range low to high inclusive a list
         * of the fields (including the key) in the row is added to the list
         * returned by the call.
         * If there are no rows with a key in the range return an empty list
         * The method must use the range search in B+Tree
         */
        LinkedList<Long> addresses = tree.rangeSearch(low, high);
        LinkedList<LinkedList<String>> multipleContent = new LinkedList<>();
        if(addresses.size() == 0) {
            //System.out.println("Didn't find anything");
            return multipleContent;
        }else {
            //System.out.println("Found something");
            //Row temp = new Row(addr);
            StringBuilder tempString = new StringBuilder();
            while(addresses.size() != 0) {
                LinkedList<String> rowContent = new LinkedList<>();
                Row tempRow = new Row(addresses.remove());
                rowContent.add(Integer.toString(tempRow.keyField));
                int counter = 0; 
                int counter2 = 0;
                
                while(counter != tempRow.otherFields.length) {
                    while(counter2 != tempRow.otherFields[counter].length  && tempRow.otherFields[counter][counter2] != '\0') {
                        tempString.append(tempRow.otherFields[counter][counter2]);
                        counter2++;
                    }
                    rowContent.add(tempString.toString());
                    tempString.setLength(0);
                    counter2 = 0;
                    counter++;
                }
                
                multipleContent.add(rowContent);
            }
            
            return multipleContent;
        }
    }
    
    public void print() throws IOException{
        //Print the rows to standard output is ascending order (based on the keys)
        //One row per line
        System.out.println("-----------------");
        System.out.println("-----------------");
        tree.print();     
        System.out.println("-----------------");
        System.out.println("-----------------");
        System.out.println("DBTable");
        System.out.println("Current free: " + free);
        System.out.println("Number of Otherfields:" + numOtherFields);
        for(int temp: otherFieldLengths) {
            System.out.println("Other field: " + temp);
        }
    }
    
    public void close() throws IOException {
        //close the DBTable. The table should not be used after it is closed
        tree.close();
        //Update the class fields 
        int pos = (numOtherFields+1)*4;
        rows.seek(pos);
        rows.writeLong(free);
        rows.close();
    }
}
