/*
 * IntTree.java
 * 
 * Implementation of a tree using int data type
 * 
 * Author: Ethan Booker
 * 
 * The IntTree class is a representation of a tree that
 * can do the 3 types traversals. 
 */
/** 
 * Given imports
 */
import java.io.*;
/** 
 * Given imports
 */
import java.util.*;

/**
 * A presentation of an int Tree
 * 
 * @author Ethan Booker
 * @since 2/25/2018
 * @version 1.1
 */
public class IntTree {
    /**
     * Represenation of each node in the tree
     */
    private class Node {
        /**
         * Node int data that the Node will hold
         */
        private int data;
        /**
         * Reference to next child node
         */
        private Node firstChild;
        /**
         * Reference to next sibling node
         */
        private Node sibling;
        /**
         * Reference to next parent node
         */
        private Node parent;
        
        /**
         * Node constructor
         * 
         * @param int the int data 
         * @param Node the first child reference
         * @param Node the sibling reference
         * @param Node the parent reference
         */
        private Node (int d, Node f, Node s, Node p) {
            data = d;
            firstChild = f;
            sibling = s;
            parent = p;
        }
    }
    
    /**
     * Head of the tree
     */
    private Node root;
    
    /**
     * IntTree constructor
     * 
     * @param int tree root's int data
     */
    public IntTree(int d) {
        //create a one node tree
        root = new Node(d, null, null, null);
    }
    
    /**
     * IntTree constructor 
     * 
     * @param int[] the values for a root with children tree 
     */
    public IntTree(int d[]) {
     //create a tree with d[0] as the root value and the other values as children of the root
     root = new Node(d[0], null, null, null); //Array must have at least 1 value
     
     if(d.length > 1) {
         //Initialize child reference starting from d[1] 
         Node temp = root;
         temp.firstChild = new Node(d[1], null, null, temp);
         temp = temp.firstChild; 
         
         //Initialize rest of the sibling references in the array
         for(int i = 1; i < d.length-1; i++) {
             //Must establish reference back to parent
             temp.sibling = new Node(d[i+1], null, null, root);
             temp = temp.sibling;
         }
     }
    }
    
    /**
     * IntTree constructor 
     * 
     * @param int tree root's int data
     * @param IntTree[] the other subtrees that will be children to tree root
     */
    public IntTree(int d, IntTree t[]) {
     //create a new tree whose children are the trees in t and whose root value is d
     root = new Node(d, null, null, null);
     
     //Ensures list has at least 1 tree
     if(t.length >= 1) {
         Node temp = root; 
         
         //Initialize child reference on the t[0] root
         temp.firstChild = t[0].root;
         temp = temp.firstChild;
         temp.parent = root; 
         
         //Initialize rest of the siblings reference on the t[i+1] root 
         for(int i = 0; i < t.length-1; i++) {
             //Must establish reference back to parent
             temp.sibling = t[i+1].root;
             temp = temp.sibling; 
             temp.parent = root;
         }
     }
    }
    
    /**
     * IntTree constructor 
     * 
     * @param int tree root's int data
     * @param IntTree exisiting tree that will have a new root value 
     */
    public IntTree(int d, IntTree c) {
        root = new Node(d,c.root,null,null);
        c.root.parent = root;
    }
    
    
    /**
     * A preorder represenation of the current tree
     * 
     * @return String the tree representation
     */
    public String preorder() {
     //return a string of the ints in the tree in preorder
     //separate the ints with commas
     //the implementation must be recursive
     
     //Calls helper method 
     String pre = preorder(root);
     
     //Gets rid of last comma
     return pre.substring(0, pre.length()-1);
    }
    
    /**
     * Helper method
     * 
     * @param Node the current root Node 
     */
    private String preorder(Node r) {
        //Base case, ran off list
        if(r == null) {
            return "";
        } 
        
        //Visit node then children
        String x = r.data + "," + preorder(r.firstChild) + preorder(r.sibling);
        
        return x;
    }
    
    /**
     * A postorder represenation of the tree
     * 
     * @return String the tree representation
     */
    public String postorder() {
     //return a string of the ints in the tree in postorder
     //separate the ints with commas
     //the implementation must be recursive
     
     //Calls helper method
     String post = postorder(root);
     
     //Gets rid of last comma
     return post.substring(0, post.length()-1);
    }
    
    /**
     * Helper method for postorder
     * 
     * @param Node root node
     */
    private String postorder(Node r) {
        //Base case
        if(r == null) {
            return "";
        }
        
        String x = "";
        
        //Visit children
        x += postorder(r.firstChild) + r.data + "," + postorder(r.sibling);
        
        return x;
    }
    
    /**
     * A level order representation of the tree
     * 
     * @return the representation of the tree
     */
    public String levelorder() {
     //return a string of the ints in the tree in level order (also know a breadth first order)
     //separate the ints with commas
     //the implementation must be iterative
     
     //Ensures tree exists
     if(root == null) {
         return "";
     }
     
     //Creating a Queue 
     String level = "";
     Queue<Node> fifo = new LinkedList<Node>();
     Node temp = root; 
     
     //Added the root of the tree
     fifo.add(temp);
     
     //Continue adding the rest of the tree 
     while(fifo.peek() != null) {
         temp = fifo.remove();
         level += temp.data + ","; 
         
         //Check for children
         temp = temp.firstChild; 
         
         //Add child and the siblings
         while(temp != null) {
             fifo.add(temp);
             temp = temp.sibling; 
         }
     }
     
     //Gets rid of last comma
     return level.substring(0, level.length()-1);
    }
    
    /**
     * Finds and creates a path of the first occurence of a value to the root
     * 
     * @param the desired value to be looked for
     * @return String the path from the value to the root 
     */
    public String path(int d) {
     //return the ints in the path from the first occurrence of d in the tree to the root of the tree
     //the “first occurrence” means the first occurrence found in a preorder traversal
     //the implementation must use the parent reference to create the path
     //separate the ints with commas
     //the implementation must be iterative
     
     //Check to ensure tree isn't empty
     if(root == null) {
         return "No path";
     }
     
     
     boolean found = false; //Status for value
     Node temp = root;
     
     //Proorder iteration implementation
     while(true) { //Repeat until at root of the tree
          //Must check first before shifting
          if(temp.data == d) {
              found = true;
              break;
          }
          
          //Iterate through children first, siblings of children, then move back up to the parent's siblings and repeat pattern
          if(temp.firstChild != null) {
              temp = temp.firstChild;
              
          } else if (temp.sibling != null) {
              temp = temp.sibling;
          } //Moving back up the tree until sibling found or at root
          else if(temp.firstChild == null && temp.sibling == null && temp.parent!= null) {
              //Iterates up through tree looking for a parent with a sibling 
              while(temp != root) {
                  temp = temp.parent;
                  if(temp.sibling != null) {
                      temp = temp.sibling;
                      break;
                  }
              }
          }
          
          //Iterated through the entire family of the tree
          if(temp == root) {
              break;
          }
     }
     
     //Didn't find value in tree
     if(!found) {
         return "No path";
     }
     
     //Creating path starting from value to root
     String path = "";
     
     //Iterate back up by using parent reference
     while(temp != null) {
         if(temp.parent != null) {
             path += temp.data + ",";
         } else { 
             path += temp.data; 
         }
         temp = temp.parent;
     }
     
     return path;
    }
    
    /**
     * Finds the total occurneces of a desired value in the tree
     * 
     * @param int the desired value to be searched for
     * @return int the number of times the value was found in the tree
     */
    public int count(int d) {
     //return the number of times d appears in the tree
     //the implementation must be recursive
     
     //calls helper method
     return count(root, d);
    }
    
    /**
     * Helper method using a recursive preorder traversal  
     * 
     * @param Node the root Node
     * @param int the value to be searched for
     * 
     * @return int the occurences found
     */
    private int count(Node r, int d) {
        //Base case
        if(r == null) {
            return 0;
        }
        
        int count = 0; 
        
        //Found a node with the searched value
        if(r.data == d) {
            count += 1;
        }
        
        count += count(r.firstChild, d) + count(r.sibling, d);
        
        return count;
    }
    
    
    /**
     * The summation of the tree
     * 
     * @return int summation of the tree
     */
    public int sum() {
     //return the sum of the ints in the tree
     //the implementation must be iterative
     
     //Ensures root has a value
     if(root == null) {
         return 0;
     }
     
     int sum = 0;
     Node temp = root; 
     
     //Proorder iteration implementation
     while(true) {
          sum += temp.data;
          
          //Iterate through children first, siblings of children, then parent's siblings and repeat pattern
          if(temp.firstChild != null) {
              temp = temp.firstChild;
          } else if (temp.sibling != null) {
              temp = temp.sibling;
          } //Moving back up the tree until sibling found or at root
          else if(temp.firstChild == null && temp.sibling == null && temp.parent!= null) {
              while(temp != root) {
                  temp = temp.parent;
                  if(temp.sibling != null) {
                      temp = temp.sibling;
                      break;
                  }
              }
          }
          
          //Iterated through relatives of root, can stop
          if(temp == root) {
              break;
          }
     }
     return sum;
     
     //Queue implementation of sum doing level order   IGNORE
     /*  
     Node temp = root; 
     Queue<Node> fifo = new LinkedList<Node>();
     fifo.add(temp);
     while(fifo.peek() != null) {
         temp = fifo.remove();
         sum += temp.data;
         
         temp = temp.firstChild; 
         while(temp != null) {
             fifo.add(temp);
             temp = temp.sibling; 
         }
     }
     */ 
    } 
}
