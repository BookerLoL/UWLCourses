/*
 * BTree.java
 * 
 * The class maintains a BTree representation of the DBTable 
 * 
 * Author: Ethan Booker
 * 
 * BTree maintains the data for DBTable and helps find addresses for DBTable 
 */

/**
 * Given imports 
 */
import java.io.*;
import java.util.LinkedList;
import java.util.Stack;
/**
 * Implementation of a BTree
 * The arrays will make use of the [ary_size + 1] implementation 
 * 
 * 
 * @author Ethan Booker
 * @since 4/20/2018
 * @version 1.0
 */
public class BTree {
    /**
     * File for maintaining the BTree data
     */
    RandomAccessFile f;
    /**
     * The order of the BTree
     */
    int order;
    /**
     * Blocksize for the contents
     */
    int blockSize;
    /**
     * posing address of the BTree
     */
    long root;
    /**
     * posing address of free space in the file 
     */
    long free;
    /**
     * Delimiter
     */
    static final String FILE_DELIMITER = "BTreeFile";
    /**
     *  
     */
    boolean changeMinimum;
    
    //add instance variables as needed.
    private class BTreeNode {
        private int count;
        private int keys[];
        private long children[];
        private long address; //the address of the node in the file
        //constructors and other method
        
        //CREATE MANUAL NODE 
        public BTreeNode(long address, int count, int keys[], long children[]) {
            this.address = address;
            this.count = count;
            this.keys = keys;
            this.children = children;
        }
        
        //READING A NODE OUT
        public BTreeNode(long address) throws IOException {
            this.address = address;
            //System.out.println(address);
            if(address != 0) {
                f.seek(address);
                count = f.readInt(); 
                
                keys = new int[order-1]; //keys 
                for(int i = 0; i < order-1; i++) {
                    keys[i] = f.readInt();
                }
                
                children = new long[order]; //
                for(int i = 0; i < order; i++) {
                    children[i] = f.readLong();
                }
            }
        }
        
        private void printNode() {
            System.out.println("Node details: ");
            System.out.println("Node address: " + address);
            System.out.println("Node count: " + count);
            
            System.out.println("Keys: " + "\t");
            for(int i = 0; i < Math.abs(count); i++) {
                System.out.print(keys[i] + "\t");
            }
            System.out.println("Children: " + "\t");
             for(int i = 0; i < Math.abs(count)+1; i++) {
                System.out.print(children[i] + "\t");
            }
        }
        
        //WRITING NODE CONTENTS OUT
        public void writeBTree() throws IOException {
            f.seek(address);
            f.writeInt(count);
            
            for(int i = 0; i < keys.length; i++) {
                f.writeInt(keys[i]);
            }
            
            for(int i = 0; i < children.length; i++) {
                f.writeLong(children[i]);
            }
            //Writing out the keys
        }
    }
    
    private long getFree() throws IOException {
        if(free == 0) {
            return f.length();
        }
        long temp = free;
        f.seek(free);
        free = f.readLong();
        return temp;
    }
    
    private void addFree(long addr) throws IOException {
        f.seek(addr);
        f.writeLong(free);
        free = addr;
    }
    
    //remember the variable of the new next childr is 
    /**
     * BTree constructor
     * @param String filename for BTree
     * @param int block size for determining order
     */
    public BTree(String filename, int bsize) throws IOException {
        //bsize is the block size. This value is used to calculate the order
        //of the B+Tree
        //all B+Tree nodes will use bsize bytes
        //makes a new B+tree
        File path = new File(filename);
        
        f = new RandomAccessFile(path, "rw");
        
        //Ensures file is reset
        if(path.exists()) {
            f.setLength(0);
        }
        
        blockSize = bsize;
        order = (int)Math.floor(blockSize / 12); 
        root = 0;
        free = 0;
        changeMinimum = false;
        //Location of the block size in BTree file 
        f.seek(16);
        f.writeInt(bsize);
    }
    
    public BTree(String filename) throws IOException {
        //open an existing B+Tree
        File path = new File(filename);
        f = new RandomAccessFile(path, "rw");
        f.seek(0);
        root = f.readLong();
        free = f.readLong();
        blockSize = f.readInt();
        order = (int)Math.floor(blockSize / 12); 
        changeMinimum = false;
    }
    
    //Split 
    //Check if root has children to determine of need to do special case insert 
    public boolean insert(int key, long addr) throws IOException {
        /*
         * If key is not a duplicate add, key to the B+tree
         * addr (in DBTable) is the address of the row that contains the key
         * return true if the key is added
         * return false if the key is a duplicate
         */
        BTreeNode node;
        BTreeNode newNode;
        
        //Deals with the initial root inserting values
        if(root == 0){
            node = new BTreeNode(getFree(), -1, new int[order-1], new long[order]);
            node.keys[0] = key; 
            node.children[0] = addr;
            root = node.address;
            node.writeBTree();
            return true;
        } else {
            boolean split = true;
            Stack<BTreeNode> nodePath = searchNode(key);
            node = nodePath.pop();
            long loc = -1;
            int val = -1;
            
            
            //Checking if value already exists 
            if(checkValueExists(key, node)) {
                return false;
            }
            
            //Inserting into a leaf with room
            if(hasKeyRoom(node)) {
                insertLeafKey(key, addr, node);
                node.writeBTree();
                split = false;
            } else { //Splitting a leaf
                newNode = splitLeaf(node, key, addr);
                
                loc = newNode.address;
                val = newNode.keys[0];
                newNode.writeBTree();
                split = true;
            }
            
            
            while(!nodePath.empty() && split) {
                node = nodePath.pop();
                
                if(hasKeyRoom(node)) {
                    insertNonLeafKey(val, loc, node);
                    node.writeBTree();
                    split = false;
                } else {
                    newNode = new BTreeNode(getFree(), -1, new int[order-1], new long[order]);
                    val = splitNonLeaf(node, newNode, val, loc);
                    loc = newNode.address;
                    split = true;
                }
            }
            
            if(split) {
                newNode = new BTreeNode(getFree(), 1, new int[order-1], new long[order]);
                newNode.keys[0] = val;
                newNode.children[0] = node.address; 
                newNode.children[1] = loc;
                newNode.writeBTree();

                root = newNode.address;
            }
        }
        return true;
    }
    
    private BTreeNode splitLeaf(BTreeNode left, int key, long addr) throws IOException{
        //Split the BTreeNode
        BTreeNode right = new BTreeNode(getFree(), -1, new int[order-1], new long[order]);
        boolean insertedKey = false;
        int[] tempKeys = new int[order]; //Holds all the keys
        long[] tempChildren = new long[order]; //Holds all the children
        int keysLength = Math.abs(left.count);
        int endCase = keysLength-1;
        
        //Insert into temp array and insert key into right position
        for(int i = 0, j = 0; i < keysLength; i++, j++) {
            if(!insertedKey && key < left.keys[i]) {
                //System.out.println(i);
                tempKeys[j] = key;
                tempChildren[j] = addr;
                ++j; //iterate by one 
                insertedKey = true;
            } else if(i == endCase) {
                //Last value of left node was smaller than key, put at end of temp array
                tempKeys[j] = left.keys[i];
                tempChildren[j] = left.children[i];
                tempKeys[keysLength] = key;
                tempChildren[keysLength] = addr;
            }
            tempKeys[j] = left.keys[i];
            tempChildren[j] = left.children[i];
        }
        
        
        for(int rightHalf = tempKeys.length/2, i = 0; rightHalf < tempKeys.length; rightHalf++, i++) {
            right.keys[i] = tempKeys[rightHalf];
            right.children[i] = tempChildren[rightHalf];
        }
        
        for(int leftHalf = 0; leftHalf < tempKeys.length/2; leftHalf++) {
            left.keys[leftHalf] = tempKeys[leftHalf];
            left.children[leftHalf] = tempChildren[leftHalf];
        }
        
     
        right.count = (left.count/2) - 1;//adding one more to the right side
        left.count = (tempKeys.length/2)*-1; //Use new tempcount 
        
        right.children[keysLength] = left.children[keysLength]; //Take left's previous link 
        left.children[keysLength] = right.address; //Link left to right node
        
        left.writeBTree();
        return right;
    }
    
    /**
     * Helper method to 
     * @param BTreeNode should be a full node
     * @param BTreeNode should be an empty node
     * @param int the key value to be inserted 
     * @param long the address of next node the key holds
     * @return the key middle key value
     */
    private int splitNonLeaf(BTreeNode left, BTreeNode right, int key, long addr) throws IOException{
        int middle = 0; //default val

        
        int[] tempKeys = new int[order];
        long[] tempChildren = new long[order+1];
        boolean insertedKey = false;
        int keyLength = left.count;
        int endCase = keyLength-1;
        
        for(int i = 0, j = 0; i < keyLength; i++, j++) {
            if(!insertedKey && key < left.keys[i]) {
                insertedKey = true;
                tempKeys[j] = key;
                tempChildren[j+1] = addr;
                tempKeys[j+1] = left.keys[i];
                tempChildren[j] = left.children[i];
                ++j;      
            } else if(i == endCase){
                if(!insertedKey) {
                    tempChildren[j+1] = left.children[j+1]; 
                    tempChildren[j+2] = addr;
                    tempKeys[j+1] = key;
                } else {
                    tempChildren[j+1] = left.children[j];
                }
                tempKeys[j] = left.keys[i];
                tempChildren[j] = left.children[i];
            } else {
                tempKeys[j] = left.keys[i];
                tempChildren[j] = left.children[i];
            }
        }
        
        
        left.count = tempKeys.length/2;
        if(order % 2 == 0) {
            right.count = left.count-1;
        } else {
            right.count = left.count;
        }
        
        for(int i = 0; i < left.count; i++) {
            left.keys[i] = tempKeys[i];
            left.children[i] = tempChildren[i];
           
        }
        left.children[tempKeys.length/2] = tempChildren[tempKeys.length/2];

        
        for(int i = (tempKeys.length/2)+1, j = 0; i < tempKeys.length; i++, j++) {
            right.keys[j] = tempKeys[i];
            right.children[j] = tempChildren[i];

        }
        right.children[right.count] = tempChildren[tempKeys.length];

        left.writeBTree();
        right.writeBTree();
        middle = tempKeys[tempKeys.length/2];
        return middle;
    }
    
    public long remove(int key) throws IOException{
        /*
         * If the key is in the Btree, remove the key and return the address of the
         * row return 0 if the key is not found in the B+tree
         */
         //Combine method
        //Decide which left and right 
        //Remove the parent the key that splits x - y 
            //the key is removed --> find replacement 
            //Combine leafs -->
            //Combine nonleaf node --> deleted value gets put into the combined node
        
            
        //Deals with empty root
        if(root == 0) {
            return 0;
        }
        
        long keyAddr = 0;
        boolean tooSmall = false;
        Stack<BTreeNode> nodePath = searchNode(key);
        BTreeNode node = nodePath.pop();
        BTreeNode child;
        
        //Check if key exists
        if(!checkValueExists(key, node)) {
            return keyAddr;
        }  else {
            //Remove key
            keyAddr = removeLeafKey(key, node);
            
            //check if root is leaf 
            if(node.address == root) {
                if(node.count == 0) {
                    addFree(root);
                    root = 0;
                    return keyAddr;
                }
                node.writeBTree();
            } else {
                if(hasTooFewKeys(node)) {
                    //Check to replace minimum value 
                    tooSmall = true;
                } else { 
                    if(changeMinimum) {
                        BTreeNode parent = nodePath.pop();
                        
                        //Replace minimum
                        for(int i = 0; i < parent.count && key >= parent.keys[i]; i++) {
                            if(key == parent.keys[i]) {
                                parent.keys[i] = node.keys[0];
                            }
                        }
                        changeMinimum = false;
                        parent.writeBTree();
                    }

                    node.writeBTree();
                    return keyAddr;
                }
            }
        }
        
        while(!nodePath.empty() && tooSmall) {
            child = node; //Node that is too small
            node = nodePath.pop(); // parent of child
            
            
            if(changeMinimum) {
                //Replace minimum
                for(int i = 0; i < node.count && key >= node.keys[i]; i++) {
                    if(key == node.keys[i]) {
                        node.keys[i] = child.keys[0];
                    }
                }
                changeMinimum = false;
            }
            
            //Checks if can borrow from same parent neighbors 
            int borrowPos = canBorrow(node, child);
            if(borrowPos != -1) {
                borrow(node, child, borrowPos);
                tooSmall = false; 
            } else {
                //Combine(child, GetNeighbor(node, child)) 
                //When combining put everything in the left child 
                int combinePos = comebinePartner(node, child);
                combine(node, child, combinePos); 
                if(!hasTooFewKeys(node)) {
                    tooSmall = false; 
                }
            }
        }
        
        if(tooSmall) {
            root = node.children[0]; //left most child 
            addFree(node.address);
        }
        
        return keyAddr;
    }
    
    /**
     * Doesn't replace parent key value if keys[0] is taken out 
     */
    private long removeLeafKey(int key, BTreeNode node) {
        //Leafs have negative count values
        node.count++; 
        int max = Math.abs(node.count);
        long keyAddr;
        int pos = 0; 
        //find position of key
        while(node.keys[pos] != key) {
            pos++;
        }
        
        if(pos == 0 && root != node.address) {
            changeMinimum = true;
        }
        
        keyAddr = node.children[pos];
        
        while(pos < max) {
            node.keys[pos] = node.keys[pos+1];
            node.children[pos] = node.children[pos+1];
            pos++;
        }
        
        return keyAddr;
    }
    
   
    /**
     * Gives position of borrow partner 
     */
    private int canBorrow(BTreeNode parent, BTreeNode keyChild) throws IOException{
        int borrow = -1;
        
        long childAddr = keyChild.address; 
        
        //Find neighbors in parent 
        int pos = 0; 
        int childLimit = Math.abs(parent.count); 
        
        while(parent.children[pos] != childAddr) {
            pos++;
        }
        
        //First child, last, or middle 
        if(pos == 0) {
            BTreeNode right = new BTreeNode(parent.children[1]);
            
            if(hasExtraKeys(right)) {
                borrow = 1; 
            }
        } else if(pos == childLimit) {
            BTreeNode left = new BTreeNode(parent.children[childLimit-1]);
            
            if(hasExtraKeys(left)) {
                borrow = childLimit-1;
            }
        } else {
            //check left first
            BTreeNode left = new BTreeNode(parent.children[pos-1]);
            if(hasExtraKeys(left)){
                borrow = pos-1;
            } else {  //check right 
                BTreeNode right = new BTreeNode(parent.children[pos+1]);
                if(hasExtraKeys(right)) {
                    borrow = pos+1;
                }
            }
        }
        
        return borrow;
    }
    
    private void borrow(BTreeNode parent, BTreeNode child, int pos) throws IOException{
        BTreeNode borrowPartner = new BTreeNode(parent.children[pos]); 
              
        //Leaf borrowing
        if(isLeaf(child)) {
            //Borrow partner on left
            if(borrowPartner.keys[0] < child.keys[0]) {
                borrowFromLeaf(borrowPartner, child, parent, true);
            } else {
                borrowFromLeaf(borrowPartner, child, parent, false);
            }
            child.count--;
            borrowPartner.count++;
        } else { //Nonleaf borrow
            //Borrow partner on left
            if(borrowPartner.keys[0] < child.keys[0]) {
                borrowFromNonLeaf(borrowPartner, child, parent, true, pos);
            } else {
                borrowFromNonLeaf(borrowPartner, child, parent, false, pos);
            }
            child.count++;
            borrowPartner.count--;
        }
        
        child.writeBTree();
        parent.writeBTree();
        borrowPartner.writeBTree();
    }
    
    private int comebinePartner(BTreeNode parent, BTreeNode keyChild) {
        long keyChildAddr = keyChild.address; 
        int pos = 0; 
        
        //Finds child position
        while(keyChildAddr != parent.children[pos]) {
            pos++;
        }
            
        if(pos == 0) {
            return 1;
        } else {
            return pos-1;
        }
    }
    
    private void combine(BTreeNode parent, BTreeNode child, int partnerAddr) throws IOException {
        BTreeNode partner = new BTreeNode(parent.children[partnerAddr]);
        int parentKeyPos;
        if(child.address == parent.children[0]) {
            parentKeyPos = 0;
        } else {
            parentKeyPos = partnerAddr;
        }
        
        
        if(isLeaf(child)) {
            combineLeaf(partner, parent, child);
        } else {
            combineNonLeaf(partner, parent, child);
        }
        
        //Move parent over
        while(parentKeyPos != parent.count) {
            parent.keys[parentKeyPos] = parent.keys[parentKeyPos+1];
            parent.children[parentKeyPos] = parent.children[parentKeyPos+1];
            parentKeyPos++;
        }
        parent.children[parentKeyPos] = parent.children[parentKeyPos+1];
        parent.count--;
        parent.writeBTree();
    }
    
    private void combineNonLeaf(BTreeNode partner, BTreeNode parent, BTreeNode child) throws IOException {
          
    }
    
    private void combineLeaf(BTreeNode partner, BTreeNode parent, BTreeNode child) throws IOException { 
        int newKeyTotal = child.count + partner.count; 
        
        int posChild = Math.abs(child.count);
        int posPartner = Math.abs(partner.count);

        int posTotal = posChild + posPartner;
        if(child.address == parent.children[0]) {
            for(int i = posChild, j = 0; i < posTotal; i++, j++) {
                child.keys[i] = partner.keys[j];
                child.children[i] = partner.children[j]; 
            }
            
            child.count = newKeyTotal;
            child.children[child.keys.length] = partner.children[partner.keys.length]; //swap next reference 
            child.writeBTree();
            addFree(partner.address);
        } else {
            for(int i = posPartner, j = 0; i < posTotal; i++, j++) {
                partner.keys[i] = child.keys[j];
                partner.children[i] = child.children[j]; 
            } 
            
            partner.count = newKeyTotal;
            partner.children[partner.keys.length] = child.children[child.keys.length];
            partner.writeBTree();
            addFree(child.address);
        }
    }
    
    private void borrowFromLeaf(BTreeNode borrowPartner, BTreeNode child, BTreeNode parent, boolean left) {
        //current info
        int maxCKeys = Math.abs(child.count);
        
        //Borrow info
        int maxBKeys = Math.abs(borrowPartner.count);
        
        //Parent info
        int parentKeys = parent.count;
        
        if(left) {
            for(int i = maxCKeys; i > 0; i--) {
               child.keys[i] = child.keys[i-1];
               child.children[i] = child.children[i-1];
            }
                    
            child.keys[0] = borrowPartner.keys[maxBKeys-1]; 
            child.children[0] = borrowPartner.children[maxBKeys-1];
                    
            //Change parent key value 
            for(int i = 0; i < parentKeys; i++) {
                if(child.keys[0] < parent.keys[i]) {
                    parent.keys[i] = child.keys[0];
                    break;
                }
            }
        } else {
                int changeParentKeysPos;
                
                //Changes the parent key value for the borrowed partner key value
                //Algorithm would be better if I just passed in the borrow position 
                for(changeParentKeysPos = 0; changeParentKeysPos < parentKeys; changeParentKeysPos++) {
                    if(parent.keys[changeParentKeysPos] == borrowPartner.keys[0]) {
                        parent.keys[changeParentKeysPos] = borrowPartner.keys[1];
                        break;
                    }
                }
                
                child.keys[maxCKeys] = borrowPartner.keys[0];
                child.children[maxCKeys] = borrowPartner.children[0];
                
                for(int i = 0; i < maxBKeys; i++) {
                    borrowPartner.keys[i] = borrowPartner.keys[i+1];
                    borrowPartner.children[i] = borrowPartner.children[i+1];
                }
                
                //In case child's key[0] got removed 
                parent.keys[changeParentKeysPos-1] = child.keys[0]; 
        }
    }
    
    private void borrowFromNonLeaf(BTreeNode borrowPartner, BTreeNode child, BTreeNode parent, boolean left, int borrowPos) throws IOException{ 
        //current info
        int maxCKeys = Math.abs(child.count);
        
        //Borrow info
        int maxBKeys = Math.abs(borrowPartner.count);
        
        //Parent info
        //int parentKeys = parent.count;

        if(left) {
            //Shift keys to right
            for(int i = maxCKeys; i > 0; i--) {
               child.keys[i] = child.keys[i-1];
               child.children[i] = child.children[i-1];
            }
            
            child.keys[0] = parent.keys[borrowPos];
            child.children[0] = borrowPartner.children[maxBKeys];
            parent.keys[borrowPos] = borrowPartner.keys[maxBKeys-1];
        } else  {
             child.keys[maxCKeys] = parent.keys[borrowPos-1];
             child.children[maxCKeys+1] = borrowPartner.children[0];
             parent.keys[borrowPos-1] = borrowPartner.keys[0]; 
             
             //shift keys to left 
             for(int i = 0; i < maxBKeys-1; i++) {
                    borrowPartner.keys[i] = borrowPartner.keys[i+1];
                    borrowPartner.children[i] = borrowPartner.children[i+1];                   
             }
             borrowPartner.children[maxBKeys-1] = borrowPartner.children[maxBKeys];
        }
    }
    
    /**
     * Assumes its a leaf
     */
    private void insertLeafKey(int key, long addr, BTreeNode node) {
        int pos = Math.abs(node.count);
        //Shift the key values for right position 
        while(pos-1 != -1 && key < node.keys[pos-1]) {
            node.keys[pos] = node.keys[pos-1];
            node.children[pos] = node.children[pos-1];
            pos--;
        }

        node.keys[pos] = key;
        node.children[pos] = addr;
        node.count--;
    }
    
    private void insertNonLeafKey(int key, long addr, BTreeNode node) {
        int length = node.count;
        int pos = length; 
        
        while(pos-1 != -1 && key < node.keys[pos-1]) {
            node.keys[pos] = node.keys[pos-1];
            node.children[pos+1] = node.children[pos];
            pos--;
        }
        
        if(pos-1 == -1) { //Ran off start of keys array
            //node.children[1] = node.children[0];
            node.children[1] = addr;
            node.keys[0] = key;
        } else if(pos == length) { //Ran off end of keys array
            node.keys[length] = key;
            node.children[length+1] = addr;
        } else { //Found the value between the start and end of the keys array
            node.keys[pos] = key;
            node.children[pos+1] = addr;
        }
        node.count++;
    }
    
    private boolean checkValueExists(int k, BTreeNode temp) {
        //Middle of keys array
        if(temp.address == 0) {
            return false;
        }
        
        int length = Math.abs(temp.count);
        
        for(int i = 0; i < length; i++) {
            if(temp.keys[i] == k) {
                return true;
            }
        }
        //Not found
        return false;
    }
    
    /**
     *  Method to find stack of Nodes along the path to key value
     *  @param int key value to search
     *  @return Stack<TreeNode> the nodes along the path to finding K 
     */
    private Stack<BTreeNode> searchNode(int k) throws IOException{
        Stack<BTreeNode> nodes = new Stack<>();
        return searchNode(k, nodes, new BTreeNode(root));
    } 
    
    /**
     * 
     */
    private Stack<BTreeNode> searchNode(int k, Stack<BTreeNode> stack, BTreeNode temp) throws IOException{       
        stack.push(temp);
        
        if(isLeaf(temp)) {
            return stack;
        } 
        
        int length = temp.count;
        int pos = length; 
        
        while(pos-1 != -1 && k < temp.keys[pos-1]) {
            pos--;
        }
        
        //Checks whether which child address to get 
        if(pos-1 == -1) { //Ran off start of keys array
            temp = new BTreeNode(temp.children[0]);
        } else if(pos == length) { //Ran off end of keys array
            temp = new BTreeNode(temp.children[length]); 
        } else { //Found the value between the start and end of the keys array
            temp = new BTreeNode(temp.children[pos]);
        }
        
        return searchNode(k, stack, temp);
    }
    
    /**
     * Search method to find key value
     * @param int key value 
     * @return long address to key
     */
    public long search(int k) throws IOException{
        /*
         * This is an equality search
         * If the key is found return the address of the row with the key
         * otherwise return 0
         */
         
        BTreeNode temp = new BTreeNode(root);
        return search(temp, k);
    }
    
    /**
     * Recursive impelmentation of searching for a specific address 
     */
    private long search(BTreeNode temp, int k) throws IOException{
        //Stopping case
        //System.out.println("Address: " + temp.address);
        if(temp.address == 0) {
            //System.out.println("Node address is 0: " + temp.address);
            return 0;
        }
        
        //Middle of keys array
        int length;
        
        if(isLeaf(temp)) {
            //Move left or right through keys array
            int startPos = 0;
            length = Math.abs(temp.count);
            while(startPos < length) {
                if(k == temp.keys[startPos]){
                    return temp.children[startPos];
                }
                startPos++;
            }
            
            //System.out.println(k);
            //System.out.println("Position" + pos);
            return 0;
            //check key values 
        }
        
        length = temp.count;
        
        int pos = length; 
        
        while(pos-1 != -1 && k < temp.keys[pos-1]) {
            pos--;
        }
                
        if(pos == -1) {
            temp = new BTreeNode(temp.children[0]);
        } else if(pos == length) {
            temp = new BTreeNode(temp.children[length]); 
        } else {
            temp = new BTreeNode(temp.children[pos]);
        }
        
        return search(temp, k);
    }
    
    public LinkedList<Long> rangeSearch(int low, int high) throws IOException{
        //PRE: low <= high
        /*
        * return a list of row addresses for all keys in the range low to high inclusive
        * return an empty list when no keys are in the range
        */ 
       //Go find LOW then look until at HIGH
       LinkedList<Long> list = new LinkedList<>();
       return rangeSearch(low, high, new BTreeNode(root), list);
    }
    
    public LinkedList<Long> rangeSearch(int low, int high, BTreeNode node, LinkedList<Long> link) throws IOException {
        if(node.address == 0) {
            return link;
        }
        
        int length;
        if(isLeaf(node)) {
            //Move left or right through keys array
            
            int startPos = 0;
            length = Math.abs(node.count);

            while(startPos < length) {
                //Too high 
                if(high < node.keys[startPos]) {
                    break;
                }
                
                //Within range
                if(low <= node.keys[startPos]) {
                    link.add(node.children[startPos]);
                }
                startPos++;
            }
            
            if(startPos >= length) {
                checkNextLink(high, new BTreeNode(node.children[node.keys.length]), link);
            }
            
            return link;
            //check key values 
        }
        
        length = node.count;
        int pos = length; 
        
        while(pos-1 != -1 && low < node.keys[pos-1]) {
            pos--;
        }
        
        //System.out.println("POS: " + pos);
        
        //Checks whether which child address to get 
        if(pos-1 == -1) { //Ran off start of keys array
            node = new BTreeNode(node.children[0]);
        } else if(pos == length) { //Ran off end of keys array
            node = new BTreeNode(node.children[length]); 
        } else { //Found the value between the start and end of the keys array
            node = new BTreeNode(node.children[pos]);
        }
        
        return rangeSearch(low, high, node, link); 
    }
    
    private void checkNextLink(int high, BTreeNode node, LinkedList<Long> link) throws IOException {
        if(node.address == 0) {
            return; 
        }
        
        int startPos = 0;
        int length = Math.abs(node.count);

        while(startPos < length) {
                if(high < node.keys[startPos]) {
                    break;
                }
                link.add(node.children[startPos]);
                
                startPos++;
        }
           
        checkNextLink(high, new BTreeNode(node.children[node.keys.length]), link);
    }
    
   
    public void print() throws IOException{
        //print the B+Tree to standard output
        //print one node per line
        //This method can be helpful for debugging
        System.out.println("CURRENTLY PRINTING BTREE: ");
        System.out.println("Order: " + order);
        System.out.println("blockSize: " + blockSize);
        System.out.println("Root: " + root);
        System.out.println("Free: " + free);
        
        Stack<BTreeNode> nodes = new Stack<>();
        nodes.push(new BTreeNode(root));
        while(!nodes.empty()) {
            BTreeNode temp = nodes.pop();
            if(temp.address != 0) {
                if(isLeaf(temp)) {
                    System.out.println("Leaf count: " + temp.count);
                    System.out.println("Leaf Node: " + "\t" + "Address: " + temp.address + "\t" + "Count Field: " + temp.count);
                    System.out.println("Keys: ");
                    for(int i = 0; i < Math.abs(temp.count); i++) {
                        System.out.print(temp.keys[i] + "\t");
                    }
                    System.out.println();
                    System.out.println("Children: ");
                    for(int i = 0; i < Math.abs(temp.count); i++) {
                        System.out.print(temp.children[i] + "\t");
                    }
                    
                    System.out.println("Next child: " + temp.children[temp.children.length-1]); 
                } else {
                    System.out.println("NonLeaf count: " + temp.count);
                    System.out.println("Nonleaf Node: " + "\t" + "Address: " + temp.address + "\t" + "Count Field: " + temp.count);
                    System.out.println("Keys: ");
                    for(int i = 0; i < temp.count; i++) {
                        System.out.print(temp.keys[i] + "\t");
                    }
                    System.out.println();
                    System.out.println("Children: ");
                    for(int i = 0; i < temp.count+1; i++) {
                        System.out.print(temp.children[i] + "\t");
                         if(temp.children[i] != 0) {
                            nodes.push(new BTreeNode(temp.children[i]));
                        }
                    }
                    System.out.println();
                }
            }
        }
        System.out.println("End of Tree");
    }
    
    private boolean isLeaf(BTreeNode node) {
        return node.count < 0 ? true : false;
    }
    
    private boolean hasKeyRoom(BTreeNode node) {
        return Math.abs(node.count) < order-1 ? true : false;
    }
    
    private boolean hasTooFewKeys(BTreeNode node) {
        //Decrement count prior to this call to check
        //Only works with order 3 or higher 
        if(node.address == root) {
            return node.count < 1? true : false;
        } else {
             return  Math.abs(node.count) < Math.ceil(order/2.0)-1 ? true : false;
        }
    }
    
    private boolean hasExtraKeys(BTreeNode node) {
        return Math.abs(node.count) > Math.ceil(order/2.0)-1 ? true : false; 
    }
    
    public void close() throws IOException  {
        //close the B+tree. The tree should not be accessed after close is called
        f.seek(0);
        f.writeLong(root);
        f.writeLong(free);
        //May need to update
        f.close();
        //
    }
}
