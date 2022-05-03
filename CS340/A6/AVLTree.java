/*
 * AVLTree.java
 * 
 * The class maintains an balanced binary tree using AVL Tree properties
 * 
 * Author: Ethan Booker
 * 
 * AVLTree maintains the tree through a binary file 
 */
import java.io.*;
import java.util.*;
/**
 * An implementation of an AVL Tree 
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 3/29/2018
 */
public class AVLTree {
    /*
    Implements a ALV tree of ints stored in a random access file.
    Duplicates are recorded by a count field associated with the int
    */
    /**
     * Constant for a new file
     */
    final int CREATE = 0;
    /**
     * Constant for reusing a file
     */
    final int REUSE = 1;
    /**
     * Used to access anywhere in the file
     */
    private RandomAccessFile f;
    /**
     * The address of the first node in the file
     */
    long root;
    /**
     * The address in the file of the first node in the free list
     */
    long free; 
    /**
     * The address that will replace the removed valued
     */
    long replacer;
    /**
     * Inner class to maintain the nodes in the AVL Tree
     */
    private class Node {
        /**
         * Reference to left address in file
         */
        private long left; 
        /**
         * Reference to value stored in file
         */
        private int data; 
        /**
         * Reference to the number of data
         */
        private int count; 
        /**
         * Reference to right address in file
         */
        private long right;
        /**
         * The height of tree with the current node being the root
         */
        private int height;
        /**
         * Node constructor 
         * 
         * @param long the left address
         * @param int the data
         * @param long the right address
         */
        private Node(long L, int d, long r) {
            //constructor for a new node
            left = L;
            right = r;
            data = d;
            count = 1;
            height = 0;
        }
        
        /**
         * Node constructor that gathers all the current info at that address 
         * 
         * @param long address of the node
         * @throws IOException
         */
        private Node(long addr) throws IOException{
            //constructor for a node that exists and is stored in the file
            f.seek(addr);
            data = f.readInt();
            count = f.readInt();
            height = f.readInt();
            left = f.readLong();
            right = f.readLong();
        }
        
        /**
         * Updates the file for the given node 
         * @param long the desired node address and it's content
         * @throws IOException
         */
        private void writeNode(long addr) throws IOException {
           //writes the node to the file at location addr
           f.seek(addr);
           f.writeInt(data);
           f.writeInt(count);
           f.writeInt(height);
           f.writeLong(left);
           f.writeLong(right);
        }
    }
    
    /**
     * AVLTree constructor 
     * @param String the file name
     * @param int the mode 
     * @throws IOException
     */
    public AVLTree(String fname, int mode) throws IOException {
        //if mode is CREATE a new empty file is created
        //if mode is CREATE and a file with file name fname exists the file with
        // fname must be deleted before the new empty file is created
        //if mode is REUSE an existing file is used if it exists otherwise a new empty
        // file is created
        File path = new File(fname);
        //Check if exists
        if(mode == CREATE && path.exists()) {
            path.delete();
        }
        
        //Allow read and write
        f = new RandomAccessFile(path, "rw");
         
        //New file
        if(mode == CREATE) {
            f.setLength(0);
            root = 0;
            free = 0;   
            f.writeLong(root);
            f.writeLong(free);
        } else { 
            //Reuse file
            f.seek(0);
            root = f.readLong();
            free = f.readLong();
        }
    }
    
    /**
     * Determines the height of based on the address 
     * 
     * @param the address to be checked for it's height 
     * @return int the height 
     * @throws IOException
     */
    private int height(long addr) throws IOException {
        //Checks address height 
        if(addr == 0) {
            return -1;
        } else { 
            f.seek(addr+8);
            return f.readInt();
        }
    }
    
    /**
     * Inserts the desired value into the tree 
     * including duplicates 
     * 
     * @param int the value to be inserted
     * @throws IOException
     */
    public void insert(int d) throws IOException {
        //insert d into the tree
        //if d is in the tree increment the count field associated with d
        root = insert(root, d);
    }
    
    /**
     * Auxillary method for insert 
     * 
     * @param long the address of the subtree 
     * @param int value to be inserted
     * @return long the updated addresses
     * @throws IOException
     */
    private long insert(long r, int d) throws IOException {
        //Variable declaration
        Node x;
        
        //The address is located at a "0" 
        if(r == 0) {
            x = new Node(0, d, 0);
            
            //Get an available address to store the node data
            long addr = getFree();
            
            x.writeNode(addr);
            
            return addr;
        }
        
        //Used for comparing where to go next
        x = new Node(r);
        
        //Check which direction to go 
        if(d < x.data) { 
            //Go left, update the left reference 
            x.left = insert(x.left, d);    
        } else if (d > x.data) {
            //Go right, update the right reference
            x.right = insert(x.right, d);
        } else {
            //Found value, update count
            x.count++; 
        }
        
        //Update the changes
        x.writeNode(r);
        
        //Check for inbalances while returning back up the tree
        return balance(r);
    }
   
    /**
     * Checks for inbalances and determines which rotation to use
     * 
     * @param long the current root address
     * @return long the updated subtree root value
     * @throws IOException
     */
    private long balance(long r) throws IOException {
        //At 0 value
        if(r == 0) {
            return r;
        }
        
        //Roots left and right addresses
        f.seek(r+12);
        long left = f.readLong();
        long right = f.readLong();
        
        //The next left or right to check
        long nextLeft;
        long nextRight;
        
        //Check root's height of left and right
        if(height(left) - height(right) > 1) {
            //Check the height root left's left and right
            f.seek(left+12);
            nextLeft = f.readLong();
            nextRight = f.readLong();
            
            //See which rotation must be done
            if(height(nextLeft) >= height(nextRight)) {
                //Left rotation 
                r = leftRotation(r);
            } else {
                //Left Right rotation
                r = rightLeftRotation(r);
            }
        } else {
            //Check the height of the right compared to the left
            if(height(right) - height(left) > 1) {
                //Check the height root right's left and right
                f.seek(right+12);
                nextLeft = f.readLong();
                nextRight = f.readLong();
                
                //See which rotation must be done
                if(height(nextRight) >= height(nextLeft)) {
                    //Right rotation
                    r = rightRotation(r);
                } else {
                    //Left rotation
                    r = leftRightRotation(r);
                }
            }  
        }
        
        //Update the height of the current root 
        f.seek(r+12);
        
        //Get the left and right of the root
        long left2 = f.readLong();
        long right2 = f.readLong();
        
        //Comparing heights and updating height
        int h = Math.max( height(left2), height(right2)) + 1;
        f.seek(r+8);
        f.writeInt(h);
        
        return r;
    }
    
    /**
     *  Method to shift the subtree with a left rotation
     *  
     *  @param long root of the subtree
     *  @throws IOException
     */
    private long leftRotation(long r2) throws IOException {
        //Get the root's left
        f.seek(r2+12); 
        long rLeft = f.readLong();
        
        //Roots left's right
        f.seek(rLeft+20); 
        long rLeftsRight = f.readLong(); 
        
        //Change root's left with roots left's right
        f.seek(r2+12); 
        f.writeLong(rLeftsRight); 
        
        //Change roots left's right with root
        f.seek(rLeft+20); 
        f.writeLong(r2);

        //Check height of root's left and right
        f.seek(r2+12); 
        long r2L = f.readLong(); //Roots left
        long r2R = f.readLong(); //Roots right
        
        //Height of the left and right 
        int temp = Math.max( height(r2L), height(r2R)) + 1;
        f.seek(r2+8); //Update the root's height
        f.writeInt(temp);
        
        //Get roots left's left
        f.seek(rLeft+12); 
        long r1L = f.readLong();
        
        //Get the updated root's height
        f.seek(r2+8); 
        int r2H = f.readInt();
        
        //Height of left and right 
        temp = Math.max(height(r1L), r2H) + 1;
        
        //Update the root's left 
        f.seek(rLeft+8);
        f.writeInt(temp);
        
        //Return the root's left
        return rLeft;
    }
    
    /**
     * Method to shift the subtree with a right rotation
     * 
     * @param long the root of subtree
     * @throws IOException
     */
    private long rightRotation(long r2) throws IOException {
        //Get root's right
        f.seek(r2+20); 
        long r1 = f.readLong();
        
        //Get roots right's left
        f.seek(r1+12); 
        long r1L = f.readLong(); 
        
        //Change roots right with roots right's left
        f.seek(r2+20); 
        f.writeLong(r1L);
        
        //Change roots right's left with root
        f.seek(r1+12); 
        f.writeLong(r2);
        
        //Get root's left and right 
        f.seek(r2+12); 
        long r2L = f.readLong();
        long r2R = f.readLong();
        
        //Update root's  height
        int temp = Math.max(height(r2L), height(r2R)) + 1;
        f.seek(r2+8);
        f.writeInt(temp);
       
        //Root right's right
        f.seek(r1+20);
        long r1R = f.readLong();
        
        //Root's height
        f.seek(r2+8);
        int r2H = f.readInt();
        
        //Update root's right height
        temp = Math.max(height(r1R), r2H) + 1;
        f.seek(r1+8);
        f.writeInt(temp);
        
        return r1;
    }
    
    /**
     * Method to do a double rotation doing a left rotation then a right rotation
     * 
     * @param long the root of subtree
     * @throws IOException
     */
    private long rightLeftRotation(long r3) throws IOException {
        //Get root's left and rotate it right 
        f.seek(r3 + 12);
        long r3L = f.readLong();
        long temp = rightRotation(r3L);
        //Update root's left
        f.seek(r3 + 12); 
        f.writeLong(temp);
        //Rotate the root
        return leftRotation(r3);
    }
    
    /**
     * Method to do a double rotation doing a right rotation then a left rotation
     * 
     * @param long the root of subtree
     * @throws IOException
     */
    private long leftRightRotation(long r3) throws IOException {
        //Get root's right and rotate it left
        f.seek(r3 + 20);
        long r3R = f.readLong();
        long temp = leftRotation(r3R);
        //Update root's right
        f.seek(r3 + 20);
        f.writeLong(temp);
        //Rotate the root
        return rightRotation(r3);
    }
    
    /**
     * Finds the count of the value
     * 
     * @param int the desired value 
     * @return int the count of that value
     * @throws IOException
     */
    public int find(int d) throws IOException {
        //if d is in the tree return the value of count associated with d
        //otherwise return 0
        return find(root, d);
    }
    
    /**
     * Helper method to find the desired value
     * 
     * @param long root in subtree
     * @param int desired value to find
     * @return int the count value of the desired value
     */
    private int find(long r, int d) throws IOException {
        //Stopping case
        if(r == 0) {
            return 0;
        }
        
        Node x = new Node(r);

        if(d < x.data) {
            return find(x.left, d);
        } else if (d > x.data) {
            return find(x.right, d);
        } else {
            return x.count;
        }
    }
    
    /**
     * Removes desired value from the tree
     */
    public void removeOne(int d) throws IOException {
     //remove one copy of d from the tree
     //if the copy is the last copy remove d from the tree
     //if d is not in the tree the method has no effect
     root = removeOne(root, d);
    }
    
    /**
     * Helper method that will remove one count of the node 
     * if the count is 0 then the value will be removed
     * 
     * @param long root in subtrees
     * @param long value to remove
     * @return long the updated address
     * @throws IOException
     */
    private long removeOne(long r, int d) throws IOException {
        if(r == 0) {
            return 0; //Not found
        } 
        
        Node x = new Node(r);
        
        if(d < x.data) {
            x.left = removeOne(x.left, d);
        } else if(d > x.data) {
            x.right = removeOne(x.right, d);
        } else {
            --x.count;
        }
        
        if(x.count == 0) {
            addFree(r);
            if(x.left != 0 && x.right != 0) {
                //Roots replacement
                r = findReplacement(r);
                return balance(r);
            } else { //root with 1 child case 
              long right = x.right;
              long left = x.left;
              
              r = (right != 0 ? right : left); 
              return balance(r);
            }
        }
        //Node still has a count of at least one
        x.writeNode(r);
        return balance(r);
    }
    
    /**
     * Finds the replacement address and updates the tree
     * @param long the root address
     * @return long replacement address
     * @throws IOException
     */
    private long findReplacement(long r) throws IOException {
        //root being replaced
        f.seek(r+12);
        
        //Maintain it's left
        long left = f.readLong();
        
        //Search through right
        long right = f.readLong();
      
        //Updated right
        long diffRight = changeRight(right);
        
        //Update the new replacement left and right 
        f.seek(replacer+12);
        //Root's left
        f.writeLong(left);
        //Updated root's right 
        f.writeLong(diffRight);
        
        return replacer;
    }
    
    /**
     * Finds the replacement address and balances the tree
     * 
     * @param long address on right side of the tree
     * @throws IOException
     */
    private long changeRight(long r) throws IOException {
        //Check the children
        f.seek(r+12);
        long left = f.readLong();
        long right = f.readLong();
        
        //Update the left 
        long newLeft;
        if(left != 0) { //Keep moving left until at far most left
            newLeft = changeRight(left);
            f.seek(r+12);
            f.writeLong(newLeft);
            return balance(r);
        } else {
            //Found replacement value
            replacer = r;
            
            //Return the right child of the replacement address
            return balance(right);
        }
    }
    
    /**
     * Removes the all counts of the specific value 
     * 
     * @param int the desired value to be removed
     * @throws IOException
     */
    public void removeAll(int d) throws IOException {
        //remove d from the tree
        //if d is not in the tree the method has no effect
        root = removeAll(root, d);
    }
    
    /** 
     * Helper method to remove the desired value
     * 
     * @param long the address of a root
     * @param int the desired value to be deleted
     * @return long address to updated tree
     * @throws IOException
     */
    private long removeAll(long r, int d) throws IOException {
        if(r == 0) {
            return 0; //Not found
        } 
        
        Node x = new Node(r);
        
        //Move through the tree 
        if(d < x.data) {
            x.left = removeAll(x.left, d);
        } else if(d > x.data) {
            x.right = removeAll(x.right, d);
        } else {
            //Found value and set to 0
            x.count = 0;
        }
        
        //Remove the value
        if(x.count == 0) {
            //Add the address to free
            addFree(r);
            
            //Two child case
            if(x.left != 0 && x.right != 0) {
                r = findReplacement(r);
                return balance(r);
            } else { //root with 1 child case 
              long right = x.right;
              long left = x.left;
              
              //Right is not null if so then return left address
              r = (right != 0 ? right : left); 
              return balance(r);
            }
        }
        //Node still has a count of at least one
        x.writeNode(r);
        return balance(r);
    }
    
    /**
     * Method that will add the address to the free list
     * 
     * @param long the freed up address
     * @throws IOException
     */
    private void addFree(long addr) throws IOException {
        f.seek(addr);
        f.writeLong(free);
        free = addr;   
    }
    
    /**
     * Method that will find free space in the file
     * 
     * @return long free file address
     * @throws IOException
     */
    private long getFree() throws IOException {
        if(free == 0) {
            return f.length();
        }
        long temp = free;
        f.seek(free);
        free = f.readLong();
        return temp;
    }
        
    /**
     * Updates the rest of the file and closes the file 
     * @throws IOException
     */
    public void close() throws IOException{
        //close the random access file
        //before closing update the values of root and free if necessary
        f.seek(0);
        //Update the file's root and free
        f.writeLong(root);
        f.writeLong(free);
        f.close();
    }
    
    /**
     * Prints out an inorder representation of the tree
     * @throws IOException
     */
    public void print() throws IOException {
        print(root);
        System.out.println("New free: " + free);
        System.out.println();
    }
    
    /**
     * Helper method for print() 
     * 
     * @param long the current address 
     * @throws IOException
     */
    private void print(long r) throws IOException {
         if (r == 0) return;
         Node x = new Node(r);
         print(x.left);
         System.out.print("reference: " + r + "\t" + " ("+x.data+","+x.count+")\n ");
         print(x.right);
    }
    
    /**
     * Helper method to show the current free list 
     * @throws IOException
     */
    public void printFree() throws IOException {
        System.out.println(free);
        f.seek(free);
        long x = f.readLong();
        while(x != 0) {
            System.out.println(x);
            f.seek(x);
            x = f.readLong();
        }
    }
    
    /**
     * Helper method to show if file contents have been updated correctly
     * @throws IOException
     */
    public void printFile() throws IOException {
        System.out.println("head: " + root);
        System.out.println("free: " + free);
        f.seek(16);
        long x = 16;
        long y = f.length();
        while(x < y) {
           System.out.print("\nreference val: " + x);
           System.out.print("  data: " + f.readInt());
           System.out.print("  count: " + f.readInt());
           System.out.print("  height: " + f.readInt());
           System.out.print("  left: " + f.readLong());
           System.out.print("  right: " + f.readLong());
           x += 28;
        }
        System.out.println();
    }
    
    /**
     * Helper method to show if file size hasn't grown unecessarily 
     * @throws IOException
     */
    public void length() throws IOException {
        System.out.println("File length: " + f.length());
    }
}
