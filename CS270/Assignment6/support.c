/*
 * Author: Samantha Foley
 * Date: 3/17/18
 * Description: a collection of data structures and helper functions.
 */


#include "support.h"

order * create_order(int i){
  order * new_order = (order *) malloc(sizeof(order));
  if(new_order == NULL){
    return NULL;
  }
  new_order->id = i;
  new_order->state = CREATED;
  new_order->pizzas = (gen_list *)malloc(sizeof(gen_list));
  init_list(new_order->pizzas);
  if(new_order->pizzas == NULL){
    return NULL;
  }
  return new_order;
}

pizza * create_pizza(int i, int oid, menu_item item){
  pizza * new_pizza = (pizza *) malloc(sizeof(pizza));
  if(new_pizza == NULL){
    return NULL;
  }
  new_pizza->id = i;
  new_pizza->order_id = oid;
  new_pizza->state = CREATED;
  new_pizza->time_left = item.time_to_cook;
  new_pizza->name = strdup(item.name);

  return new_pizza;
}
