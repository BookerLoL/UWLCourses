/*
 * BitOutputStream.java
 * 
 * The implmentation of writing out bit by bit to a file 
 * 
 * Author: Ethan Booker
 * 
 * BitOutputStream is able to read characters and convert the characters into readable bytes
 * that can later be decoded by BitInputStream class
 */
/**
 * Initially given imports
 */
import java.io.*;
import java.util.*;
/**
 * An implementation of writing bytes out bit by bit
 * 
 * @author Ethan Booker
 * @version 1.0
 * @since 3/16/2018
 */
public class BitOutputStream {
     //add additional protected variables as needed
     //do not modify the public methods signatures or add public methods
     /**
      * Used for the encoding writing to the desired file 
      */
     protected DataOutputStream d;
     /**
      * The number of current bits
      */
     protected short bitCount; 
     /**
      * The int value of the 8 bits
      */
     protected int byteValue;
    
     /**
      * BitOutputStream constructor
      * 
      * @param String the desired output encoding file 
      */
     public BitOutputStream(String filename) {
         try {
             d = new DataOutputStream(new FileOutputStream(filename));
             bitCount = 0;
             byteValue = 0;
             //Look up encoding 
             //Write it out to the textfile bit 
         }
         catch (IOException ex) {
             ex.printStackTrace();
         }
     }
    
     /**
      * Eventually converts bits into a byte 
      */
     public void writeBit(char bit) {
         //PRE: bit is a '0' or a '1'
         //write out to the file
         //1 bit to a file
         
         //Nonzero bit
         if(bit == '1') {
             //Writing out a 'bit' based on number of contained bits
             int basePow = 7 - bitCount;
             byteValue += Math.pow(2, basePow);
         }
         
         //Increment bits
         ++bitCount;
         
         //Max bits in a byte are reached
         if(bitCount == 8) { 
             try { 
                 //Write byte out to file
                 d.writeByte(byteValue);
                 
                 //Reset values
                 bitCount = 0;
                 byteValue = 0;
             } catch(Exception ex) {
                 ex.printStackTrace();
             }
         }
     }
     
     /**
      * Finishes filling up the remaining bits in the byte 
      */
     public void close() {
         //Use remaining bits 
         try {
             if(bitCount % 8 != 0) {
                 d.writeByte(byteValue);
                 
                 //Reset values
                 bitCount = 0;
                 byteValue = 0;
             }
             d.close();
         } catch(Exception ex) {
             ex.printStackTrace();
         }
     }
}
