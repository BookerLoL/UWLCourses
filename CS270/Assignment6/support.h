/*
 * Author: Samantha Foley
 * Date: 3/17/18
 * Description: a collection of data structures
 */

#include <string.h>
#include "gen_list.h"

/*
 * Enum of states for pizzas and orders
 */
enum states {
  CREATED,
  COOKING,
  FINISHED
};

/*
 * Enum of states for ovens
 */
enum cook_states {
  AVAILABLE,
  BUSY
};

/*
 * Models an order of one or more pizzas
 *
 * id: an identifier for the kitchen and pizzas
 * state: the state of the order
 * pizzas: list of pizzas
 */
struct order {
  struct gen_list * pizzas;
  enum states state;
  int id;
};
typedef struct order order;

/*
 * Models a pizza in a pizza shop
 *
 * id: an identifier for computers
 * order_id: the id of the order this pizza belongs to
 * state: the state of the pizza 
 * time_left: the amount of time that the pizza has left to cook
 * name: a human identifier for printing
 */
struct pizza {
  int id;
  int order_id;
  enum states state;
  int time_left;
  char * name;
  struct oven * my_oven;
};
typedef struct pizza pizza;

/*
 * Models menu items (pizzas) in a pizza shop
 *
 * id: an identifier for computers
 * name: name of the menu item for displaying
 * time_to_cook: amount of time (in minutes) to cook the item (pizza)
 */
struct menu_item {
  int id;
  char * name;
  int time_to_cook;
};
typedef struct menu_item menu_item;


/*
 * Models an oven in a pizza shop.
 *
 * id: an identifier for computers
 * name: an identifier for humans and printing
 * state: the state of the oven (cook_states)
 * cur_pizza: pointer to the pizza that is in the oven (NULL if none)
 */
struct oven {
  int id;
  enum cook_states state;
  struct pizza * cur_pizza;
};
typedef struct oven oven;

/*
 * Models a kitchen with a menu, ovens, and orders.
 *
 * my_menu: array of menu_items
 * my_ovens: array of ovens
 * my_orders: list of orders
 */
struct kitchen {
  int num_ovens;
  int menu_len;
  oven * my_ovens;
  menu_item * my_menu;
  struct gen_list * my_orders;
};
typedef struct kitchen kitchen;

/*
 * Create a new order on the heap with id i and a new list pizzas.
 * state is set to CREATED.
 * Returns NULL if unsuccessful.
 */
order * create_order(int i);

/*
 * Create a new pizza with id i, order id oid, and with menu item info
 * from item.  state is set to CREATED.
 * Returns NULL if unsuccessful.
 */
pizza * create_pizza(int i, int oid, menu_item item);
