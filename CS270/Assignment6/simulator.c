/*
 * Author: Ethan Booker
 * Date: 4/9/2018
 * Description: Pizza shop simulator.
 */

#include "support.h"

/* DEFINES and GLOBALS */
#define LINELEN 1024
int current_time;
kitchen the_kitchen;
int num_orders;

/* PROTOTYPES */
void print_kitchen();
void print_orders();
void print_menu();
void read_data();
int find_oven();
void update_ovens();
void run_simulation();
//void print_all();

#define LINELEN 1024

int main(int argc, char ** argv){
  // declare/initialize vars
  current_time = 0;

  // read in kitchen data from file and set up the kitchen
  read_data();

  // print initial state of kitchen
  print_menu();
  print_kitchen();
  print_orders();
 
  // run simulation
  run_simulation();

  // display stats
  printf("The kitchen processed %d orders in %d minutes.\n", num_orders, current_time);
  return 0;
}


int find_oven() {
  // declarations
  int idx;
  int max_oven = the_kitchen.num_ovens;
  oven* temp_oven = the_kitchen.my_ovens;

  // loop through all the ovens
  for(idx = 0; idx < max_oven; idx++){
    // find an empty oven
    if(temp_oven[idx].state == AVAILABLE){
      return idx;
    }
  }
  // no empty oven
  return -1;
}

void update_ovens(){
  // declarations
  oven* temp_oven = the_kitchen.my_ovens;
  pizza* temp_pizza;
  int max = the_kitchen.num_ovens;
  int idx;

  // loop through all the ovens
  for(idx = 0; idx < max; idx++) {
    //The current oven's pizza
    temp_pizza = temp_oven[idx].cur_pizza;
    // no pizza in the oven
    if(temp_pizza == NULL) {
      continue;
    }           
    // check for a finished pizza
    if(temp_pizza->state == FINISHED) {
      // update the oven
      temp_oven[idx].state = AVAILABLE;
      temp_oven[idx].cur_pizza = NULL;
    }
  }
}

void run_simulation(){
  // declarations
  int created_orders = num_orders;
  int cooking_orders = 0;
  int finished_orders = 0; 
  int available_oven_status = -1;
  int ordered_finished_status = 0;
  oven* temp_oven = the_kitchen.my_ovens;
  gen_list* temp_orders = the_kitchen.my_orders;
  node* temp_order_node;
  node* temp_pizza_node;
  order* order_ptr; 
  pizza* pizza_ptr; 
  
  printf("%7d%7d%7d\n", created_orders,cooking_orders, finished_orders);
  printf("%s\n", "--------------------------------------------");
  printf("%s\n", "--------------------------------------------");

  // keep iterating until kitchen orders have all been finished
  while(finished_orders != num_orders) {
    // get the first order of the list
    temp_order_node = temp_orders->head;

    // iterate through all the orders 
    while(temp_order_node != NULL) {
      // get the order data
      order_ptr = (order*)temp_order_node->data;
      
      // check if order is not finished 
      if(order_ptr->state != FINISHED) {
        // get the current order's first pizza
        temp_pizza_node = order_ptr->pizzas->head; 
        //status for determining if order is finished
        ordered_finished_status = 0;

        // iterate through all the pizzas in the order
        while(temp_pizza_node != NULL) {
          // get the pizza data
          pizza_ptr = (pizza*)temp_pizza_node->data;

          // find pizza to be cooked
          if(pizza_ptr->state == CREATED) {
            // finding an oven for the pizza 
            available_oven_status = find_oven();

            // found an oven for the pizza
            if(available_oven_status != -1) {
              // check if the order status isn't cooking
              if(order_ptr->state != COOKING) {
                // update order
                order_ptr->state = COOKING;
                created_orders--;
                cooking_orders++;
              }
              // update the status of pizza and oven
              pizza_ptr->state = COOKING;
              temp_oven[available_oven_status].state = BUSY;
              temp_oven[available_oven_status].cur_pizza = pizza_ptr;
            }          
          } 

          // update cooking pizza 
          if(pizza_ptr->state == COOKING) {
            // decrement time
            pizza_ptr->time_left--;
            // check if pizza is finished
            if(pizza_ptr->time_left == 0) {
              // update status of pizza to finish
              pizza_ptr->state = FINISHED;
            } 
          } 

          // order in pizza is still not finished
          if(pizza_ptr->time_left != 0) {
            // order's status will remain cooking
            ordered_finished_status = -1;
          }

          // iterate to next pizza
          temp_pizza_node = temp_pizza_node->next; 

          // at end of pizza list and all pizzas in the list are finished
          if(temp_pizza_node == NULL && ordered_finished_status == 0) {
            // update order
            order_ptr->state = FINISHED;
            cooking_orders--;
            finished_orders++;
          }
        }
      }
      // iterate to next order 
      temp_order_node = temp_order_node->next;   
    }

    // update the oven, time, then print kitchen and orders
    update_ovens();
    current_time++;
    print_kitchen();  
    print_orders(); 
    printf("%7d%7d%7d\n", created_orders,cooking_orders, finished_orders);
    printf("%s\n", "--------------------------------------------");
    printf("%s\n", "--------------------------------------------");
  }
}

void read_data(){
  /* file format
     first line: # ovens, # menu items, # orders
     menu lines: <time to cook> <menu name>
     orders: space separated menu item ids (starting at 1)
   */
  // declarations
  char *fgets_rtn = NULL;
  char buffer[LINELEN];
  int parse_stage = 0; 
  char *input_ptr;
  int ovens;
  int menu_items;
  int menu_time;
  int pos = 0;
  int pos2 = 0;
  int iter2 = 0;
  menu_item* temp_menu; 
  oven* temp_oven;
  order* new_order;
  pizza* new_pizza;
  gen_list* temp_orders;

  do{
    fgets_rtn = fgets(buffer, LINELEN, stdin); 
    if(fgets_rtn != NULL) {
      // reading first line
      if(parse_stage == 0) {
        // reading the oven #, menu item #, and order #
        ovens = strtol(buffer, &input_ptr, 10);
        menu_items = strtol(input_ptr, &input_ptr, 10);
        num_orders = strtol(input_ptr, &input_ptr, 10);

        // assignning the kitchen oven # and menu item #
        the_kitchen.num_ovens = ovens;
        the_kitchen.menu_len = menu_items;

        // allocating space for the kitchen ovens
        the_kitchen.my_ovens = (oven *)malloc(sizeof(oven) * ovens);
        // safey check
        if(the_kitchen.my_ovens == NULL) {
          exit(-1);
        }

        // pointer to the kitchen oven list
        temp_oven = the_kitchen.my_ovens;

        // creating the kitchen oven values  
        while(pos < ovens) {
          temp_oven[pos].id = pos;
          temp_oven[pos].state = AVAILABLE;
          pos++;
        }

        // allocate space for the kitchen menu list
        the_kitchen.my_menu = (menu_item *)malloc(sizeof(menu_item) * menu_items);
        // safey check
        if(the_kitchen.my_menu == NULL) {
          exit(-1);
        }

        // allocate space for the kitchen orders list
        the_kitchen.my_orders = (gen_list*)malloc(sizeof(gen_list));
        // safey check
        if(the_kitchen.my_orders == NULL) {
          exit(-1);
        }

        // init the kitchen order list
        init_list(the_kitchen.my_orders);

        // finished first line
        parse_stage++;
      } else if(parse_stage == 1) {
 
          // pointer to the kitchen menu
          temp_menu = the_kitchen.my_menu;

          // continue reading menu items 
          while(menu_items > 0) {
            // buffer has read one line already 
            // parsing the data then assigning values to the menu item
            menu_time = strtol(buffer, &input_ptr, 10);
            temp_menu[pos].id = pos;
            temp_menu[pos].name = strdup(input_ptr);;
            temp_menu[pos].time_to_cook = menu_time;

            if(menu_items != 1) {
              fgets_rtn = fgets(buffer, LINELEN, stdin); 
            }
            menu_items--;
            pos++;
          }
          // finished second part of read_data
          parse_stage++;
      } else {

        // read rest of the data
        temp_orders = the_kitchen.my_orders; 

        // continue creating orders with pizzas until # orders have been met
        while(pos < num_orders) {
          // order pointer
          new_order = create_order(pos);
          // assign input_ptr the buffer input
          input_ptr = buffer;

          // parse the input for pizzas
          while( (pos2 = strtol(input_ptr, &input_ptr, 10)) != 0 ) {
            // new pizza 
            new_pizza = create_pizza(iter2, pos, temp_menu[pos2-1]);
            // append to order's pizza list
            append(new_order->pizzas, new_pizza);
            iter2++;
          }
          // reset pizza id 
          iter2 = 0; 
          // append to list 
          append(temp_orders, new_order);
          pos++;
          fgets_rtn = fgets(buffer, LINELEN, stdin); 
        }
      }
    } 
    pos = 0;
  } while( fgets_rtn != NULL );
} 




void print_kitchen(){
  // declaration
  oven* temp_oven = the_kitchen.my_ovens;
  pizza* temp_pizza;
  int max = the_kitchen.num_ovens;
  int idx;

  printf("Kitchen state at time %d:\n", current_time);

  for(idx = 0; idx < max; idx++) {

    if(temp_oven[idx].state == AVAILABLE) {
      printf("    Oven %d is available.\n", temp_oven[idx].id);
    } else {
      temp_pizza = temp_oven[idx].cur_pizza;
      printf("    Oven %d is busy working on pizza %d of order %d\n",
      temp_oven[idx].id, temp_pizza->id, temp_pizza->order_id);
    }
  }
}

void print_orders(){
  // declaration
  gen_list* temp_orders = the_kitchen.my_orders; 
  int temp_state;
  node* temp = temp_orders->head; 
  order* order_ptr; 
  int order_idx;

  printf("State of the orders at time %d:\n", current_time);

    while(temp != NULL) {
      // get order data
      order_ptr = (order*)temp->data;
      temp_state = order_ptr->state;
      order_idx = order_ptr->id;

      if(temp_state == 0) {
        printf("    Order %d is in state %s\n", order_idx, "CREATED");
      } else if(temp_state == 1) {
        printf("    Order %d is in state %s\n", order_idx, "COOKING");
      } else {
        printf("    Order %d is in state %s\n", order_idx, "FINISHED");
      }

      temp = temp->next;
    }
}

void print_menu() {
   // declaration
   menu_item* temp_menu = the_kitchen.my_menu;
   int max = the_kitchen.menu_len;
   int idx;

   printf("%s\n", "The Menu" );

   for(idx = 0; idx < max; idx++) {
    printf("%5d) %s", temp_menu[idx].id + 1, temp_menu[idx].name);
   }

  printf("%s\n", "--------------------------------------------");
}

/*
void print_all(){
  int temp_state;
  gen_list* temp_orders = the_kitchen.my_orders;
  node* temp = temp_orders->head;  //3rd and last order id
  node* temp2;
  printf("%s\n", "Entire list of orders and their pizzas" );
  while(temp != NULL) {
    temp2 = ((order*)temp->data)->pizzas->head;
    temp_state = ((order*) temp->data)->state;
    if(temp_state == 0) {
      printf("Order %d is in state %s\n", ((order*) temp->data)->id, "CREATED");
    } else if(temp_state == 1) {
      printf("Order %d is in state %s\n", ((order*) temp->data)->id, "COOKING");
    } else {
      printf("Order %d is in state %s\n", ((order*) temp->data)->id, "FINISHED");
    }
    while(temp2 != NULL) {
      printf("Pizza ID: %d  Order_id: %d  Time_left: %d  name: %s  \n", ((pizza*) temp2->data)->id, 
        ((pizza*) temp2->data)->order_id, ((pizza*) temp2->data)->time_left, ((pizza*) temp2->data)->name);
      temp2 = temp2->next;
    }
    temp = temp->next;
  }
}
*/