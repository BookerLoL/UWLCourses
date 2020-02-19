/*
 * HuffmanOutputStream.java 
 * 
 * Writes the Huffman encoding to the outfile
 * 
 * Author: Ethan Booker
 * 
 * HuffmanOutputStream will write to the outfile a representation of the tree, 
 * total characters that were read, and converting the characters into a binary file
 */
/**
 * Initially given imports
 */
import java.io.*;
import java.util.*;
/**
 * An implementation of a class that can write out the Huffman encoding to the desired output file
 */
public class HuffmanOutputStream extends BitOutputStream {
    /**
     * HuffmanOutputStream constructor
     * 
     * @String the desired output file name
     * @String the postorder representation of the HuffmanTree 
     * @intthe total characters in the file
     */
    public HuffmanOutputStream(String filename, String tree, int totalChars) {
        super(filename);
        try {
            d.writeUTF(tree);
            d.writeInt(totalChars);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
} 
