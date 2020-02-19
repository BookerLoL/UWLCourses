/*
 * Author: Ethan Booker
 * Date:   2/15/2018
 * Description: Using a function to return a pointer to an array 
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function prototyping 
int * initalize_ary(int size);
void print_ary(int *ary, int size);
int print_sum(int *ary, int size);
double print_avg(int *ary, int size);

int main(int argc, char **argv) {
  // Variable initalization
  int size = 10;
  int *r = initalize_ary(size);

  print_ary(r, size);
  printf("Sum: %d\n", print_sum(r, size));
  printf("Average: %.3f\n", print_avg(r, size));
  
  return 0;
}

int * initalize_ary(int size) {
  // Variable initalization
  int *ary = NULL;

  // Dynamically allocating space
  ary = (int*) malloc(sizeof(int) * size);
  int iter; 
  srand(time(NULL));
  
  for(iter = 0; iter < size; iter++) {
    int r = rand();
    ary[iter] = r % 100;
  }

  return ary;
}

void print_ary(int *ary, int size) {
  // variable declaration
  int iter;

  for(iter = 0; iter < size; iter++) {
    printf("Value: %d\t address: %p\n", ary[iter], &(ary[iter]));
  }
}

int print_sum(int *ary, int size) {
  // Variable initalization
  int sum = 0;
  int iter;

  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];
  }

  return sum;
}

double print_avg(int *ary, int size) {
  // Variable initalization	
  int sum = 0;
  int iter;

  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];   
  }
  
  return (double)sum/size;
}
