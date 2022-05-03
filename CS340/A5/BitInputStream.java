/*
 * BitInputStream.java 
 * 
 * An implementation of reading bytes bit by bit from a file data
 * 
 * Author: Ethan Booker
 * 
 * BitInputStream is able to read a byte and translate it into reading a bit at a time
 * thus being able to return either a 0 or a 1 when being read.
 */
/**
 * Initially given imports
 */
import java.io.*;
import java.util.*;
/**
 * An implemenation of reading bytes bit by bit
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 3/16/2018
 */
public class BitInputStream {
     //add additional protected variables as needed
     //do not modify the public methods signatures or add public methods
     /**
      * Reads data from the file
      */
     protected DataInputStream d;
     
     /**
      * The current byte value that is going to reconverted
      */ 
     protected int byteValue;
     
     /**
      * The current bit that should be read
      */
     protected short bitCount;
     
     /**
      * Keeps a hold on the translated byte bit by bit
      */
     protected int[] bitSeq = new int[8];
     /**
      * BitInputStream constructor
      * 
      * @param String the file to be read
      */
     public BitInputStream(String filename) {
         try {
             d = new DataInputStream(new FileInputStream(filename));
         }
         catch (IOException ex) {
             ex.printStackTrace();
         }
     }
     
     /**
      * Reads the next bit in the file
      * 
      * @return int the bit value: 0 or 1
      */
     public int readBit() {
         //return the next bit in the file 
         
         //8 bits have been read 
         if(bitCount % 8 == 0) {
             //Reset values
             byteValue = 0;
             bitCount = 0;
             
             try{
                 //Try to read the next byte 
                 byteValue = d.readUnsignedByte();
                 createBitSeq(byteValue);
             } catch(Exception ex){
                 ex.printStackTrace();
             }
         }
         bitCount++;
         return bitSeq[bitCount-1];
     }
     
     /**
      * Helper method to read the Byte and store the bits 
      */
     private void createBitSeq(int bVal) {
         int byteVal = bVal;
         for(int pos = bitSeq.length-1; pos >= 0; pos--) {
             bitSeq[pos] = byteVal % 2;
             byteVal /= 2;
         }
     }
     
     /**
      * Finishes reading the file and closes the reading process
      */
     public void close() {
         try{ 
             d.close();
         } catch(IOException ex) {
             ex.printStackTrace();
         }
     }
} 
