/*
 * HuffmanDecode.java
 * 
 * The class does most of the work for Huffman decoding process
 * 
 * Author: Ethan Booker
 * 
 * HuffmanDecode does the decoding process of Huffman coding.
 * HuffmanDecode is only able to decode the char values from 0-127. 
 */
/**
 * Initially given imports
 */
import java.io.*;
import java.util.*;
/**
 * An implementation of Huffman Decoding
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 3/16/2018
 */
public class HuffmanDecode {
    /**
     * Default value for HuffmanTree
     */
    private static final int DEFAULT_CHAR_VAL = 128;
    /**
     * Reads the bytes bits by bits
     */
    private HuffmanInputStream input;
    /**
     *  Writes out the translated bits 
     */
    private BufferedWriter bw;
    /**
     * The tree from the given input file that is in postorder 
     */
    private HuffmanTree tree;
    /**
     * The total characters from the given input file
     */
    private int characters;
    /**
     * HuffmanDecode constructor
     * 
     * @param String input file
     * @param String output file destination
     */
    public HuffmanDecode(String in, String out) {
       //Implements the huffman decoding algorithm
       //Add private methods as needed
       try {
           //Read the input file and gather the necessary data
           input = new HuffmanInputStream(in);
           characters = input.getTotalChars();
           tree = new HuffmanTree(input.getTree(), (char)DEFAULT_CHAR_VAL);
           
           //Create the writer
           bw = new BufferedWriter(new FileWriter(out));
           
           //Check which file situation to go about
           if(determineSituation() == 0) {
               //close inputsream, write to file
               input.close();
               oneCharacterFile();
           } else {
               //Translate encoded file, print out the translation, close inputstream
               createDecodedFile();
               input.close();
           }
       } catch(Exception ex) {
           ex.printStackTrace();
       } 
    }
    
    /**
     * Determines which type of file situation to deal with 
     * 
     * @return int  a 0 return is a only 1 character unique file 
     */
    private int determineSituation() {
        //Only 1 character exists
        if(tree.atLeaf() == true) {
            return 0;
        }
        return 1;
    }
    
    /**
     * Writes out to the outfile containing only one character
     */
    private void oneCharacterFile() {
        try {
            //Get the char value of the one character
            int data = tree.current();
            int iter = 0; 
            
            //Write to file the number of total characters 
            while(iter < characters) {
                bw.write(data);
                iter++;
            }
            
            //Close writer
            bw.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Writes out to the outfile by decoding the file
     */
    private void createDecodedFile() {
        try {
            //Keep iterating until all characters have been written to outfile
            int iter = 0; 
            while(iter < characters) {
                //Continue reading file bytes until at a character leaf
                while(!tree.atLeaf()) {
                    //Get the bit value 
                    int bit = input.readBit();
                    
                    //Determine which way to move through the tree
                    if(bit == 0) {
                        tree.moveToLeft();
                    } else {
                        tree.moveToRight();
                    }
                }
                //Get the character leaf value
                int data = tree.current(); 
                //Write out to file
                bw.write(data);
                //Reset current back to root
                tree.moveToRoot();
                //Increment since character has been found
                iter++;
            }
            
            //Close writer
            bw.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
 
    public static void main(String args[]) {
        new HuffmanDecode(args[0], args[1]);
    }
}
