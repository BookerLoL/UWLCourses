/*
 * Author: Ethan Booker
 * Date:   2/15/2018
 * Description: practice dynamically allocating data and manipulating the array with argument value input
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function prototyping 
void print_ary(int *ary, int size);
int print_sum(int *ary, int size);
double print_avg(int *ary, int size);

int main(int argc, char **argv) {
  // Check if argc is valid before doing anything else 
  if(argc != 2) {
    return -1;
  }
  
  // Variable initalization
  int size = strtol(argv[1], NULL, 10);
  int *ary = (int*)malloc(sizeof(int) * size);
  int iter; 
  srand(time(NULL));
  
  // Using random to fill values in array memory locations
  for(iter = 0; iter < size; iter++) {
    int r = rand();
    ary[iter] = r % 100;
  }
  
  print_ary(ary, size);
  printf("Sum: %d\n", print_sum(ary, size));
  printf("Average: %.3f\n", print_avg(ary, size));
  
  return 0;
}

void print_ary(int *ary, int size) {
  // Variable declaration 
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    printf("Value: %d\t address: %p\n", ary[iter], &(ary[iter]));
  }
}

int print_sum(int *ary, int size) {
  // Initalizaing variables
  int sum = 0;
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];
  }

  return sum;
}

double print_avg(int *ary, int size) {
  // Initalizaing variables
  double sum = 0.0;
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];   
  }
  
  return  sum/size;
}
