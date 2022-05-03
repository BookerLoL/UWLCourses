/*
 * HuffmanInputStream.java 
 * 
 * Reads the Huffman encoding 
 * 
 * Author: Ethan Booker
 * 
 * HuffmanInputStream will read the encoded file 
 * and reconvert the file into readable text
 */
import java.io.*;
import java.util.*;
public class HuffmanInputStream extends BitInputStream {
     //add additional private variables as needed
     //do not modify the public method signatures or add public methods
     /**
      * The postorder representation from the input file
      */
     private String tree;
     /**
      * The total number of characters in the file
      */
     private int totalChars;
     
     /**
      * HuffmanInputStream constructor
      * 
      * @String the encoded file name that needs to be read 
      */
     public HuffmanInputStream(String filename) {
         super(filename);
         try {
             tree = d.readUTF();
             totalChars = d.readInt(); 
         } catch (IOException ex) {
             ex.printStackTrace();
         }
     } 
     
     /**
      * Retrieves the HuffmanTree representation
      * 
      * @String the postorder representation of the tree
      */
     public String getTree() {
         return tree;
     }
     
     /**
      * Retrieves the number of characters that were converted
      * 
      * @return int the total characters
      */
     public int getTotalChars() {
         return totalChars;
     }
}
