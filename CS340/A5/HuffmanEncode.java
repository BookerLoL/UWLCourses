/*
 * HuffmanEncode.java
 * 
 * The class does most of the work for Huffman encoding process
 * 
 * Author: Ethan Booker
 * 
 * HuffmanEncode does the encoding process of Huffman coding.
 * HuffmanEncode is only able to encode the char values from 0-127. 
 */
/**
 * Initially given imports
 */
import java.io.*;
import java.util.*;
/**
 * An implementation of Huffman Encoding
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 3/16/2018
 */
public class HuffmanEncode {
    /**
     * Inner class to help create the HuffmanTree from the input file
     */
    private class PQ { 
        /**
         * Inner class to help function with poll() for the priority queue 
         */
        private class Item implements Comparable {
            /**
             * Data that will be compared
             */
            private int pri;
            /**
             * The data that should contain a HuffmanTree
             */
            private Object data; 
            
            /**
             * Item constructor
             * 
             * @param int the priority (frequency) of the item
             * @param Object a HuffmanTree object
             */
            private Item(int p, Object d) { 
                pri = p;
                data = d;
            }
            
            /**
             * The compareTo method for poll() 
             * 
             * @param Object to be compared with
             * @return int the compared priority value
             */
            public int compareTo(Object x) {
                return pri- ((Item) x).pri;
            }
        }
    }
    /**
     * Number of characters read in the file
     */
    private int totalChars = 0;
    /**
     * The frequency counts for each character
     */
    private int[] freq = new int[128];
    /**
     * The encoding paths for each character
     */
    private String[] encodings = new String[128];
    /**
     * Reference to the HuffmanTree that is created
     */
    private HuffmanTree huffmanRep; 
    /**
     * BufferedReader to read characters the file
     */
    private BufferedReader br; 
    /**
     * PriorityQueue used for creating the HuffmanTree 
     */
    private PriorityQueue<PQ.Item> pq = new PriorityQueue<>(128); 
    /**
     * Default value for HuffmanTree
     */
    private static final int DEFAULT_CHAR_VAL = 128;
    
    /**
     * 
     */
    private HuffmanOutputStream send;
    /**
     * HuffmanEncode constructor
     * 
     * @param String input file
     * @param String output file destination
     */
    public HuffmanEncode(String in, String out) {
         //Implements the huffman encoding algorithm
         //Add private methods as needed
         //use huffman tree 
         try {
             br = new BufferedReader(new FileReader(in));
             createFrequencyTable();
             
             if(totalChars == 0 ) { //No characters found in file don't do anything
                 br.close();
             } else { //Continue on with encoding
                 initQueue();
                 createTree();
                 initializeEncodings();
                 send = new HuffmanOutputStream(out, huffmanRep.toString(), totalChars);
             
                 //Reread file and encode, close outputstream 
                 br = new BufferedReader(new FileReader(in));
                 encodeToFile();
                 send.close();
             }
         } 
         catch(IOException ex) {
             ex.printStackTrace();
         }
    }
    
    /**
     * Helper method to create the frequency array from the given text file 
     */
    private void createFrequencyTable() {
        try {
            //Value of the read char
            int val;
            
            //Checks if read character is valid
            while((val = br.read()) != -1){
                freq[val]++;
                totalChars++; 
            }
            
            //End of reading file
            br.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }
     
    /**
     * Adds values in freq table that is greater than 0 into the PriorityQueue
     */ 
    private void initQueue() { 
        for(int i = 0; i < freq.length; i++) {
            if(freq[i] != 0) {
              pq.add(new PQ().new Item(freq[i], new HuffmanTree((char)i)));
            }
        }
    }
    
    /**
     * Helper method to create a HuffmanTree from the frequency data
     * Left will contain lower frequency, right will contain higher frequency
     */
    private void createTree() {
        //Repeat until Queue only has 1 item 
        while(pq.size()!= 1) {
            //Get lowestet value
            PQ.Item temp1 = pq.poll();
            
            //Get second lowest value
            PQ.Item temp2 = pq.poll();
            
            //Combine the nodes, left should be lower of the two values
            HuffmanTree together = new HuffmanTree((HuffmanTree)temp1.data, (HuffmanTree)temp2.data, (char)DEFAULT_CHAR_VAL);
            int sum = temp2.pri + temp1.pri;
            
            //Readd to queue
            pq.add(new PQ().new Item(sum, together));
        }
        
        //Huffman tree is created
        huffmanRep = (HuffmanTree)pq.poll().data;
    }
    
    /**
     * Helper method to determine paths for each character
     */
    private void initializeEncodings() {
        Iterator<String> paths = huffmanRep.iterator();
        
        while(paths.hasNext()) {
            String path = paths.next();
            
            int character = path.charAt(0);
            
            //+2 because of the parsing delimiter length value  ": "
            String pathing = path.substring(path.indexOf(": ") + 2, path.length());
            
            encodings[character] = pathing;
        }
    }
    
    /**
     * Helper method to finish encoding the file
     */
    private void encodeToFile() {
        try {
            
            //Value of the read char
            int val;
            
            //Checks if read character is valid
            while((val = br.read()) != -1){
                String path = encodings[val];
                int pos = 0;
                int length = path.length();
                
                //Write the encoding path of that character into bits
                while(pos < length) {
                    send.writeBit(path.charAt(pos));
                    pos++;
                }
            }
            
            //End of reading file
            br.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        new HuffmanEncode(args[0], args[1]);
    }  
}
