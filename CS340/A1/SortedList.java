/*
 * SortedList.java 
 * 
 * Implementation of a singly linked list 
 * 
 * Author: Ethan Booker
 * 
 * The SortedList class is a singly linked list 
 * that uses Comparable. The list order is oganized
 * in ascending order.
 */

/**
 * Initially given import for the class implementation
 */
import java.io.*;
/**
 * Initially given import for the class implementation
 */
import java.util.*;
/**
 * A singly linked sorted list 
 * @param <T> the type the data the list should contain
 * 
 * @author Ethan Booker
 * @since 1/29/2018
 * @version 1.1 
 * 
 * Note: This file has been revamped to be more efficient than version 1.0 I had previously created 
 */
public class SortedList<T extends Comparable<? super T>> {
    //Implements a generic singly linked list of Comparable objects
    //Sorted in ascending order
    /**
     * Node class is used to act as the nodes for SortedList class
     */
    private class Node {
        /**
         * Holds data for Node
         */
        private T data;
        /**
         * Holds reference to next Node 
         */
        private Node next;
        
        /**
         * Node constructor 
         * 
         * @param T the Node data
         * @param Node current Node's next Node reference
         */
        private Node(T d, Node n) {
            data = d;
            next = n;
        }
    }
    
    /**
     * Reference to the first node in the list
     */
    private Node head; 
    /**
     * The number of elements in the list
     */
    private int size; 
    
    /**
     * Default constructor
     */
    public SortedList() {
        //Initializing empty list 
        head = null; 
        size = 0;
    }
    
    /**
     * Constructor 
     * 
     * @param SortedList<T> sortedlist that contains at least 1 content 
     * @param SortedList<T> sortedlist2 that contains at least 1 content
     */   
    public SortedList(SortedList<T> s1, SortedList<T> s2){
        //Precondition: both lists have a size of at least 1
        //No implementation if argument lists are size 0
        
        //Initialize current list size 
        size = 0;
        
        Node temp1 = s1.head; 
        Node temp2 = s2.head;
        
        //Start of the list depending on which list has the lowest starting value 
        if(temp1.data.compareTo(temp2.data) <= 0) {
            this.head = new Node(temp1.data, this.head);
            temp1 = temp1.next;
        } else {
            this.head = new Node(temp2.data, this.head);
            temp2 = temp2.next;
        } 
        
        //Added item to current list
        size++;

        //Reference to current list we will continue to add onto
        Node list = this.head; 
        
        //Continues iterating until both argument lists have no more content to add to current list
        while(temp1 != null || temp2 != null) {
            if(temp1 == null) { 
               //Adds content of list2 if list1 is empty
               list.next = new Node(temp2.data, null);
               temp2 = temp2.next;
            } else if(temp2 == null) { 
                //Adds content of list1 if list2 is empty
                list.next = new Node(temp1.data, null);
                temp1 = temp1.next;
            } else { 
                //Compares both list to see which has lowest value to be inserted 
                if(temp1.data.compareTo(temp2.data) <= 0) {
                    list.next = new Node(temp1.data, list.next);
                    temp1 = temp1.next;
                } else {
                    list.next = new Node(temp2.data, list.next);
                    temp2 = temp2.next;
                }
            }
            //Shift current list and increment current list size
            list = list.next;
            size++;
        }  
    }
    
   
    /**
     * The item is inserted in the list in ascending order 
     * 
     * @param T the item to be inserted into the list
     */
    public void insert(T item){
        //Reference to current list
        Node temp = this.head;
        
        //The list is empty or item needs to be first in the list 
        if(this.size() == 0 ||temp.data.compareTo(item) >= 0) {
            head = new Node(item, head);  
        } else { 
            //Loop till list next is null or the list next data is equal to or greater than the item
            while(temp.next != null && temp.next.data.compareTo(item) < 0) {
                temp = temp.next;
            }
            
            //Shifts the reference list properly by inserting item into the right position
            temp.next = new Node(item, temp.next);
        }
        
        //list size added one item
        size++;
    }
   
    /**
     * All items in the list that is the same as the requested item is removed
     * 
     * @param T the item desired to be removed from list
     */
    public void remove(T item) {
        //List is empty
        if(this.size() == 0) {
            return;
        }
        
        //Keeps track of items to be removed
        int total = 0;
        Node temp = this.head;
        
        //Checks if list starts with desired item
        if(temp.data.compareTo(item) == 0) {
            //Iterates through list until null or data is greater than item
            while(temp != null && temp.data.compareTo(item) == 0) {
                //Iterating through matching items  
                temp = temp.next; 
                total++;
            }
            //Shifted list to new correct reference starting point
            this.head = temp; 
        } else {
            //Loops through list until found correct previous position or at end of list 
            while(temp.next != null && temp.next.data.compareTo(item) < 0) {
                temp = temp.next;
            }
            
            //At end of list and desired item was not found
            if(temp.next == null || temp.next.data.compareTo(item) > 0){
                return;
            }
            else {
                //Keeps track of the correct next reference 
                Node nextPos = temp;
                
                //Loops through starting point of temp until at end list or at a different value
                while(nextPos.next != null && nextPos.next.data.compareTo(item) == 0) {
                    //Iterating through matching items
                    total++;
                    nextPos = nextPos.next;
                }
                
                //Found correct next reference, shift the list
                temp.next = nextPos.next;
            }
        }      
        
        //size should be decreased by the amount of items removed
        size -= total;
    }
       
    /**
     * Returns the number of item found in list
     * 
     * @param T the specific item looking for
     * @return int The number of same items found
     */
    public int find(T item) {
        //Keeps track of occurences
        int count = 0;
        //Reference to start of list
        Node temp = this.head;
        
        //Iterates through list until list data is greater than item searched or null 
        while(temp != null && temp.data.compareTo(item) <= 0) {
          //Item was found, occurences increase by 1
          if(item.compareTo(temp.data) == 0) {
              ++count;
          }
          //Shifts through list
          temp = temp.next;
        }
        
        return count;
    }
    
    /**
     * The number of contents in the list
     * 
     * @return int the size of the list 
     */
    public int size() {
        return size;
    }
    
    /**
     * The output of the List
     * 
     * @String the list contained within brackets 
     */
    public String toString(){
        //Start of list output
        String list = "[";
        
        //Refernce to start of list 
        Node temp = this.head;
        //Iterate through list
        while(temp != null) {
            //Checks if last item in list
            if(temp.next == null) {
                list += temp.data;
                break;
            }
            
            //Content added with comma
            list += temp.data + ",";
            //Shifts list
            temp = temp.next;
        }
        //Adds last part of list output 
        list += "]";
        
        return list;
    }    
}
