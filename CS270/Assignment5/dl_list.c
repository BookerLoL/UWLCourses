/*
 * Author: Ethan Booker
 * Date:   2/27/2018
 *
 * Description: a simple doubly-linked list of integers
 */

#include "dl_list.h"

/*
 * implementations of all the functions
 */

/*
 * Create node
 */
node * create(int val) {
  //Initializing node default values and memory
  node * new_node = (node *)malloc(sizeof(node));
  new_node->value = val;
  new_node->next = NULL;
  new_node->prev = NULL;
  return new_node;
}

/*                                                                                        
 * Initialize an empty list                                                               
 */
void init_list(dl_list * the_list) {
  //Initializing list
  the_list->head = create(0);
  the_list->tail = create(0);
  the_list->head->next = the_list->tail;
  the_list->tail->prev = the_list->head;

  the_list->size = 0;
}

/*
 * Append
 * Creates a new node with value val and adds it to the end of the list.
 */
void append(dl_list * the_list, int val) {
  //Declare node
  node * new_n = NULL;

  // create the node
  new_n = create(val);

  // add to end which is just before tail
  new_n->next = the_list->tail;
  new_n->prev = the_list->tail->prev;
  the_list->tail->prev->next = new_n;
  the_list->tail->prev = new_n;

  // update size
  (the_list->size)++;
}

/*
 * Prepend
 * Creates a new node with value val and adds it to the beginning of the list.
 */
void prepend(dl_list * the_list, int val) {
  //Declare node
  node * new_n = NULL;
  
  // create the node
  new_n = create(val);

  // add to the front
  new_n->next = the_list->head->next;
  new_n->prev = the_list->head;
  the_list->head->next->prev = new_n;
  the_list->head->next = new_n;

  // update size
  (the_list->size)++;
}

/*
 * Insert at location i.  If i >= size, append to end of list.
 * If i < 0, prepend to beginning.
 */
void insert_at(dl_list * the_list, int val, int i) {
  //Declare node
  node * temp = NULL;
  
  //Create node
  temp = create(val);

  //List size variable
  int list_size = the_list -> size;

  //Check where to insert val 
  if(i <= 0) {
  	//Add to front
    temp -> next = the_list -> head -> next;
    temp -> prev = the_list -> head;
    the_list -> head -> next -> prev = temp; 
    the_list -> head -> next = temp;
  } else if(i >= list_size) {
  	//Add to end of list
    temp -> prev = the_list -> tail -> prev;
    temp -> next = the_list -> tail;
    the_list -> tail -> prev -> next = temp;
    the_list -> tail -> prev = temp;
  } else {
  	  //In the first half of the list
      if(i <= list_size/2) {
      node * holder = the_list -> head -> next;
      int pos = 0; 
      //Iterating to correct node position 
      while(pos < i-1) {
        holder = holder -> next;
        pos++;
      } //Changing reference
      holder -> next -> prev = temp;
      temp -> next = holder -> next; 
      temp -> prev = holder;
      holder -> next = temp;
    } else { //In the second half of the list
      node * holder = the_list -> tail -> prev; 
      int pos = list_size-1; 
      //Iterating to correct node position
      while(pos > i-1) {
        holder = holder -> prev;
        pos--;
      } //Changing reference
      holder -> next -> prev = temp;
      temp -> next = holder -> next; 
      temp -> prev = holder;
      holder -> next = temp;
    }
  }
  
  //Decreasing size of list
  (the_list->size)++;
}

/*
 * Index of - returns the index of the val in the list.  Returns -1 if not found.
 */
int index_of(dl_list * the_list, int val) {
  //Variable initialization
  int index = 0;
  node * cur_pos = the_list->head->next;

  //Search through list
  while(cur_pos != the_list->tail) {
  	//Found value
    if(cur_pos->value == val) {
      return index;
    }

    index++;
    cur_pos = cur_pos->next;
  }

  // not found
  return -1;
}

/*
 * Delete value
 * Deletes the value from the list.  If not found, list unchanged.
 * Returns 0 on success, -1 on not found.
 */
int delete_from_list(dl_list * the_list, int val) {
  //Variable intialization
  node * del = NULL;  
  
  del = the_list->head->next;
  while(del != the_list->tail) 	{
    // check for a match
    if(del->value == val){
      // found!
      // delete the node
      // update pointers, THEN free
      del->prev->next = del->next;
      del->next->prev = del->prev;
      free(del);
      del = NULL;
      (the_list->size)--;
      return 0;
    }
    del = del->next;
  }
  // not found - don't delete
  return -1;
}


/*
 * Delete element at location i.  If i >= size or i < 0, do nothing.
 */
void delete_at(dl_list * the_list, int i) {
  //Variable initialization
  int list_size = the_list -> size;
  
  if(i >= list_size || i < 0) {
    return;
  } 

  node * holder;

  //Check to see where to start in the list
  if(i == 0) { 
  	//At start of the list
    holder = the_list -> head -> next;
    holder -> next -> prev = the_list -> head;
    the_list -> head -> next = holder -> next;
  } else if(i == list_size-1) {
  	//At end of the list
    holder = the_list -> tail -> prev;
    holder -> prev -> next = the_list -> tail;
    the_list -> tail -> prev = holder -> prev;
 } else if(i <= (list_size/2)) {
 	//In the first half of the list
    holder = the_list -> head -> next;
    int pos = 0; 
    
    //Getting correct node to remove
    while(pos < i) {
      holder = holder -> next;
      pos++;
    }

    //Change reference
    holder -> next -> prev = holder -> prev;
    holder -> prev -> next = holder -> next;
  } else {
  	//In the second half of the list
    holder = the_list -> tail -> prev;
    int pos = the_list -> size;

    //Getting correct node to remove
    while(pos > i-1) {
        holder = holder -> prev;
        pos--;
    }
    //Change reference
    holder -> next -> prev = holder -> prev;
    holder -> prev -> next = holder -> next;
  }

  //Garbage collection
  free(holder);
  holder = NULL;
  (the_list -> size)--;
}

/*
 * Print
 */
void print_list(dl_list * the_list) {
  // start at the beginning
  node * cur_pos = the_list->head->next;

  printf("{");
  // while we are not at the end...
  while(cur_pos != the_list->tail){
    if(cur_pos->next != the_list->tail){
      // not the last value
      printf("%d, ", cur_pos->value);
    }
    else{
      // last value
      printf("%d", cur_pos->value);
    }
    // move on to the next element
    cur_pos = cur_pos->next;
  }
  printf("}\n");
}


/*
 * Set the value of the element at location idx to the value val.
 */
int set(dl_list * the_list, int idx, int val) {
  //Variable initialization
  int i = 0;
  node * cur_pos = the_list->head->next;
  
  if(the_list->size < idx){
    return -1;
  }
  
  //Iterate through list until found
  while(cur_pos != the_list->tail){
    if(idx == i){
      cur_pos->value = val;
      return 0;
    }
    cur_pos = cur_pos->next;
    i++;
  }
  return -1;
}
