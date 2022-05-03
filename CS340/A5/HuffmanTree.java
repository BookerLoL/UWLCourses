/*
 * HuffmanTree.java
 * 
 * Implementation of a HuffmanTree 
 * 
 * Author: Ethan Booker
 * 
 * The huffmanTree class is a representation for storing data for creating 
 * paths and allows the user to call an iterator for the paths.
 */
/**
 * Initially given import
 */
import java.util.Iterator;
/**
 * Necessary import 
 */
import java.util.Stack;
/**
 * A representation of the HuffmanTree
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 3/16/2018
 */
public class HuffmanTree {
     //add additional private variables as needed
     //do not modify the public method signatures or add public methods
     /**
      * A representation of a node in the HuffmanTree
      */
     private class Node {
         /**
          * Reference to a left Node
          */
         private Node left;
         /**
          * The data the Node will contain
          */
         private char data;
         /**
          * Reference to a right Node
          */
         private Node right;
         /**
          * Reference to a parent Node
          */
         private Node parent;
         
         /**
          * Constuctor for setting a Node 
          * 
          * @Node left reference Node
          * @char data the Node will hold
          * @Node right reference Node
          * @Node parent reference Node
          */
         private Node(Node L, char d, Node R, Node P) {
             left = L;
             data = d;
             right = R;
             parent = P;
         }
     }
     /**
      * The starting node of the tree
      */
     private Node root;
     
     /**
      * Reference node to iterate through the tree
      */
     private Node current; //this value is changed by the move methods
     
     /**
      * Additional field, used for iterator maintenance 
      */
     private int leafs = 0; //this value is used for iterator condition
     
     /**
      * Default constructor
      */
     public HuffmanTree() {
         root = null;
         current = null;
     }
     
     /**
      * HuffmanTree constructor
      * 
      * @param char the root value
      */
     public HuffmanTree(char d) {
         //makes a single node tree
         root = new Node(null, d, null, null);
         moveToRoot();
     }
     
     /**
      * HuffmanTree constructor
      * 
      * @param String the postorder representation of the tree 
      * @param char the nonleaf value of the tree
      */
     public HuffmanTree(String t, char nonLeaf) {
         //Assumes t represents a post order representation of the tree as discussed
         //in class
         //nonLeaf is the char value of the data in the non-leaf nodes
         //in classs we used (char) 128 for the non-leaf value
         //create the tree using a stack like a postfile expression, nonleaf = operator
         
         //Stack used to create the tree 
         Stack<HuffmanTree> stack = new Stack<>(); 
         
         //Length of the post order representation
         int length = t.length();
         
         //Iterate through the post order representation
         for(int i = 0; i < length; i++) {
             char temp = t.charAt(i);
             
             //Check if char value is equal to the default nonLeaf value
             if(temp == nonLeaf) {
                 //First one popped should be right tree
                 HuffmanTree right = stack.pop();
                 //Second one popped should be left tree
                 HuffmanTree left = stack.pop();
                 HuffmanTree combine = new HuffmanTree(left, right, nonLeaf);
                 stack.push(combine);
             } else { //Leaf 
                 stack.push(new HuffmanTree(temp));
             }
         }
         
         //Should be a full tree
         HuffmanTree endResult = stack.pop();
         
         //Change references of the root
         root = endResult.root;
         moveToRoot();
     } 
     
     
     
     
     /**
      * HuffmanTree constructor
      * 
      * @param HuffmanTree the lowest root value tree
      * @param HuffmanTree the greater root value tree
      * @param char the new root value of the two trees when being referenced together
      */
     public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
         //makes a new tree where b1 is the left subtree and b2 is the right subtree
         //d is the data in the root
         root = new Node(b1.root, d, b2.root, null);
         
         //Assigning parent refernces
         root.left.parent = root;
         root.right.parent = root;
         
         moveToRoot();
     }
     
     
     //use the move methods to traverse the tree
     //the move methods change the value of current
     /**
      * Moves current refernece to root reference
      */
     public void moveToRoot() {
         current = root;
     }
     
     /**
      * Moves current reference to the current's left reference
      */
     public void moveToLeft() {
         current = current.left;
     } 
     
     /**
      * Moves current reference to the current's right reference
      */
     public void moveToRight() {
         current = current.right;
     }
    
     /**
      * Moves current reference to the current's parent reference
      */
     public void moveToParent() {
         current = current.parent;
     }
     
     /**
      * Current is at a leaf
      * 
      * @return boolean both of current's left and right references are null return true
      */
     public boolean atLeaf() {
         //returns true if current references a leave other wise returns false
         if(current.left == null && current.right == null) {
             return true;
         }
         return false;
     }
     
     /**
      * Retrieves the current node data
      * 
      * @return char the node data
      */
     public char current() {
     //returns the data value in the node referenced by current
     return current.data;
     }
     
     /**
      * Helper method for initializing iterator HuffmanTree reference
      * 
      * @return HuffmanTree the copy of the current HuffmanTree
      */
     private HuffmanTree getCopy(){ 
         //Create a deep copy of the tree
         
         //Checks if tree is empty
         if(root == null) {
             return null;
         }
         
         //Start the tree root
         moveToRoot();
         HuffmanTree copyHead = new HuffmanTree(this.current());
         
         //Move through the tree iteratively
         while(current != null) {
             //Check if current has left node reference
             if(current.left != null) {
                 moveToLeft();
                 copyHead.current.left = new HuffmanTree(this.current()).root;
                 copyHead.current.left.parent = copyHead.current;
                 copyHead.moveToLeft();
             } else if(current.right != null) { //Check if current has right node reference
                 moveToRight();
                 copyHead.current.right = new HuffmanTree(this.current()).root;
                 copyHead.current.right.parent = copyHead.current;
                 copyHead.moveToRight();
             } else {
                 //At a leaf 
                 copyHead.leafs++;
                 //Shift back up to the tree
                 while(current != null) {
                     //Ensures that the current hasn't visited that right node before
                     if(current.right != null && copyHead.current.right == null) {
                         moveToRight();
                         copyHead.current.right = new HuffmanTree(this.current()).root;
                         copyHead.current.right.parent = copyHead.current;
                         copyHead.moveToRight();
                         break;
                     }
                     //Keeps moving back up to the tree
                     moveToParent();
                     copyHead.moveToParent();
                 }
             }
         }
         
         //Shifts current references back to root
         moveToRoot();
         copyHead.moveToRoot();
         return copyHead;
     }
     
     //the iterator returns the path (a series of 0s and 1s) to each leaf
     /**
      * Iterator that returns the paths within the trees 
      * 0 is left, and 1 is right
      */
     private class PathIterator implements Iterator<String> {
         /**
          * Modifiable String variable
          */
         private StringBuffer path;
         
         /**
          * Reference to a copy of the tree during the time of being iterator being called
          */
         private HuffmanTree copied;
         
         /**
          * Number of times the iterator can call next
          */
         private int count = 0;
         
         /**
          * Max number of times next can be called on 
          */
         private int maxCalls;
         
         /**
          * Default constructor
          */
         public PathIterator() { 
             //Choose 128 for safe measures. 
             path = new StringBuffer(128); 
             copied = getCopy();
             maxCalls = copied.leafs;
         }
         
         /**
          * Checks if the current iterator still has 
          * 
          * @return boolean true if available path 
          */
         public boolean hasNext() {
             //Number of paths available 
             if(count < maxCalls) {
                 return true;
             }
             
             //At last path, no more paths
             return false;
         }
         
         /**
          * Returns the next path 
          * 
          * @return path of HuffmanTree
          */
         public String next() {
             //Comparing string previous string length
             int length = path.length();
             
                      
             //Find first path
             if(length == 0) {
                //copied.moveToRoot();
                 
                //Move to the far left of the tree
                while(!copied.atLeaf()) {
                    path.append(0);
                    copied.moveToLeft();
                }
             } else { 
                 //Find the next path from the previous path
                 
                 //Using a temp string to hold reference to previous path for determining next path
                 String temp = path.toString();
                 
                 //Move current back to parent to determine next path to take 
                 path.deleteCharAt(length-1);
                 copied.moveToParent();
                 
                 //Check which path to take
                 if(temp.charAt(temp.length()-1) == '0') {
                     //Previous was on left node, so go right 
                     copied.moveToRight();
                     path.append(1);
                     
                     //In case of nested trees on the right side
                     while(!copied.atLeaf()) {
                         path.append(0);
                         copied.moveToLeft();
                     }
                 } else {
                     //Previous was right, so find next parent right 
                     while(true) {
                         //Found a nonchecked right subtree, leave loop
                         if(path.charAt(path.length()-1) == '0') {
                             copied.moveToParent();
                             path.deleteCharAt(path.length()-1);
                             break;
                         }
                         
                         //Continue moving back up the tree until finding a right subtree
                         copied.moveToParent();
                         path.deleteCharAt(path.length()-1);
                     }
                     
                     //Shift to the right subtree
                     copied.moveToRight();
                     path.append(1);
                     
                     //In case right subtree has a left subtree
                     while(!copied.atLeaf()) {
                         path.append(0);
                         copied.moveToLeft();
                     }
                 }
             }
             //Path found, increment path
             count++;
             
             //Parsing character, ": " 
             return copied.current() + ": " + path.toString();
         }
         
         /**
          * Method not implemented 
          */
         public void remove() {
              //optional method not implemented
              throw new UnsupportedOperationException();
         }
     }
     
     /**
      *  Creates an iterator that finds paths of the HuffmanTree, left to right
      *  
      *  @return Iterator<String> paths in the HuffmanTree
      */
     public Iterator<String> iterator() {
         //return a PathIterator object
         return new PathIterator();
     }
     
     /**
      * The postorder representation of the tree
      * 
      * @return String representation of the tree
      */
     public String toString() {
         //return a post order representation of the tree
         //using the format we discussed in class
         
         //Ensures that current is at the root
         this.moveToRoot();
         
         String post = toString(current);
         
         //Returns current back to root
         this.moveToRoot();
         return post;
     } 
     
     /**
      * Helper method to recursively do a postorder traversal of the HuffmanTree
      * 
      * @return postorder representation of the HuffmanTree
      */
     private String toString(Node cur) {
         //Stopping case, runs off tree
         if(cur == null) {
             return "";
         }
         
         String order = "";
         
         order += toString(cur.left) + toString(cur.right) + cur.data;
         
         return order;
     } 
}
