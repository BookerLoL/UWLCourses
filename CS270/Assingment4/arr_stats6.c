/*
 * Author: Ethan Booker
 * Date:   2/17/2018
 * Description: Passing address of array to allocate data
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function prototyping
void initalize_ary(int size, int **ary);
void print_ary(int *ary, int size);
int print_sum(int *ary, int size);
double print_avg(int *ary, int size);

int main(int argc, char **argv) {
  // Variable initalization
  int size = 10;
  int *ary = NULL;
  
  initalize_ary(size, &ary);

  print_ary(ary, size);
  printf("Sum: %d\n", print_sum(ary, size));
  printf("Average: %.3f\n", print_avg(ary, size));
  
  return 0;
}

void initalize_ary(int size, int **ary) {
  //Variable initalization
  (*ary) = (int*) malloc(sizeof(int) * size);
  int iter; 
  srand(time(NULL));
  
  for(iter = 0; iter < size; iter++) {
    int ran = rand();
    (*ary)[iter] = ran % 100;
  }
}

void print_ary(int *ary, int size) {
  //Variable declaration
  int iter;

  for(iter = 0; iter < size; iter++) {
    printf("Value: %d\t address: %p\n", ary[iter], &(ary[iter]));
  }
}

int print_sum(int *ary, int size) {
  //Variable initalization
  int sum = 0;
  int iter;
  
  for(iter = 0; iter < size; iter++) {
     sum += ary[iter];
  }

  return sum;
}

double print_avg(int *ary, int size) {
  //Variable initalization
  int sum = 0;
  int iter;

  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];   
  }
  
  return (double)sum/size;
}
