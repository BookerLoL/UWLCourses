/*
 * Author: Ethan Booker
 * Date:   2/27/2018
 *
 * Description: a simple array-based list of integers
 */
#include "ar_list.h"


/*
 * Now we need some operations that are performed on lists.
 */


/*
 * Initialize an empty list
 * Sets the data to NULL and size to 0.
 */
void init_list(ar_list * the_list) {
  //Initialize list 
  the_list -> data = NULL;
  the_list -> size = 0;
}

/*
 * Append
 * Extends the size of the array by 1 and writes val into the last position.
 */
void append(ar_list * the_list, int val) {
  //Array increases in size and memory
  (the_list -> size)++;
  the_list -> data = (int *)realloc(the_list -> data, sizeof(int) * the_list -> size);
  the_list -> data[the_list -> size - 1] = val;
}

/*
 * Prepend
 * Extends the size of the array by 1, moves all values down by one spot, and 
 * writes val into the first position.
 */
void prepend(ar_list * the_list, int val) {
  //Variable declaration
  int iter;

  //Array increases in size and memory
  (the_list -> size)++;
  the_list -> data = (int *)realloc(the_list -> data, sizeof(int) * the_list -> size);

  for(iter = the_list -> size-1; iter > 0; iter--) {
  //Shift contents to the right
  the_list -> data[iter] = the_list -> data[iter-1];
  }
	
  the_list -> data[0] = val;
}

/*
 *Index of - returns the index of val in the list.  If not found, -1 is returned.
 */
int index_of(ar_list * the_list, int val) {
  //Variable declaration
  int iter;
	
  for(iter = 0; iter < the_list -> size; iter++) {
  //Iterate through list
    if(val == the_list -> data[iter]) {
      return iter;
    }
  }
  
  //Not found
  return -1;
}

/*
 * Delete value
 * Deletes the value from the list, shifting all following elements up one spot.
 * If not found, list unchanged.
 * Returns 0 on success, -1 on not found.
 */
int delete_from_list(ar_list * the_list, int val) {
  //Variable initialization
  int pos = -1;
  int list_size = the_list -> size;
  int iter;

  for(iter = 0; iter < list_size; iter++) {
    //Find position of the value
    if(val == the_list -> data[iter]) {
      pos = iter; 
      break;
    }
  }
	
  //Value not found
  if(pos == -1) {
    return -1;
  }
	
  //Shift all contents after desired index to the left by one
  while(pos < list_size-1) {
    the_list -> data[pos] = the_list -> data[pos+1];
    pos++;
  }
	
  //Array decreases in size and memory
  (the_list -> size)--;
  the_list -> data = (int *)realloc(the_list -> data, sizeof(int)* the_list -> size);
	
  return 0;
}


/*
 * Print array
 */
void print_list(ar_list * the_list){
  //Variable initialization
  int length = the_list -> size;
  int iter;
	
  //Specific output format
  printf("{");
  for(iter = 0; iter < length; iter++) {
    if(iter != length-1) {
      printf("%d, ", the_list -> data[iter]);
      } else { //At end of list
	printf("%d", the_list -> data[iter]);
      }
  }
  printf("}\n");
}

/*
 * Set item at location i to value val.
 * Returns 0 on success, -1 on not found.
 */
int set(ar_list * the_list, int idx, int val) {
  if(idx >= the_list -> size || idx < 0) {
    return -1;
  }
  
  the_list -> data[idx] = val;
  return 0;
}

/*
 * Insert a node at location i with value val.  If i >= size, 
 * append to the end of the list.  If i < 0, prepend to the beginning.
 */
void insert_at(ar_list * the_list, int val, int i) {
  //Variable initialization
  (the_list -> size)++;
  int list_size = the_list -> size;
  int pos;
	
  //Increase memory size of array
  the_list -> data = (int *)realloc(the_list -> data, sizeof(int) * list_size);
	
  //Check which position to insert
  if(i >= list_size-1) {
    pos = list_size-1;
  } else if(i <= 0) {
    pos = 0;
  } else {
    pos = i;
  }
	
  //Check if at end of list
  if(pos == list_size-1) {
    the_list -> data[list_size-1] = val;
  } else {
    while(list_size > pos) {
      //Shift list over to the right by one
      the_list -> data[list_size-1] = the_list -> data[list_size-2]; 
      list_size--;  
    }
	  
    the_list -> data[list_size] = val;
  }
}

/*
 * Delete a node at location i.  If i >= size or i < 0,
 * do nothing.
 */
void delete_at(ar_list * the_list, int i){
  if(i >= the_list -> size || i < 0) {
    return;
  }
  
  //Shift everything after deleted value to the left by one
  while(i < the_list -> size) {
    the_list -> data[i] = the_list -> data[i+1];
    i++;
  }
  
  //Array decreases in size and memory
  (the_list -> size)--;
  the_list -> data = (int *)realloc(the_list -> data, sizeof(int)* the_list -> size);
}
