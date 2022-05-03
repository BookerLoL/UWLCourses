/*
 * Author: Samantha Foley
 * Date: 3/17/18
 * Description: Generic list of structs
 */

#include <stdio.h>
#include <stdlib.h>

struct node {
  void * data;
  struct node * next;
  struct node * prev;
};
typedef struct node node;

struct gen_list {
  node * head;
  node * tail;
  int size;
};
typedef struct gen_list gen_list;


/* Initialize empty list, set head and tail to NULL, size to 0. */
void init_list(gen_list * the_list);

/* create a generic node */
node * create_node(void * stuff);

/* Append to end of list. */
void append(gen_list * the_list, void * stuff);

/* Delete at an index.  return a pointer to the data */
void * delete_at(gen_list * the_list, int i);
