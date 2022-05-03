#include "gen_list.h"

void init_list(gen_list * the_list){
  the_list->size = 0;
  the_list->head = NULL;
  the_list->tail = NULL;
}

node * create_node(void * stuff){
  node * new_node = (node *)malloc(sizeof(node));
  if(new_node == NULL){
    return NULL;
  }
  new_node->data = stuff;
  new_node->next = NULL;
  new_node->prev = NULL;
  return new_node;
}

void append(gen_list * the_list, void * stuff){
  node * new_node = create_node(stuff);
  if(the_list->tail == NULL){
    the_list->head = new_node;
    the_list->tail = new_node;
  }
  else{
    the_list->tail->next = new_node;
    new_node->prev = the_list->tail;
    the_list->tail = new_node;
  }
  (the_list->size)++;
}

/*
 * removes the node from the list and returns a pointer to the data.
 * DOES NOT FREE.
 */
void * delete_at(gen_list * the_list, int idx){
  node * cur_pos = the_list->head;
  int i = 0;
  
  // check error cases
  if(cur_pos == NULL || idx < 0 || idx >= the_list->size){
    return NULL;
  }

  if(idx == 0){
    the_list->head->next->prev = NULL;
    the_list->head = the_list->head->next;
    (the_list->size)--;
    return cur_pos->data;
  }

  while(cur_pos != NULL && idx > i){
    cur_pos = cur_pos->next;
    i++;
  }
  
  cur_pos->next->prev = cur_pos->prev;
  cur_pos->prev->next = cur_pos->next;
  (the_list->size)--;
  return cur_pos->data;
}
