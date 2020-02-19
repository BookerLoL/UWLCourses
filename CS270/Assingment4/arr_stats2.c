/*
 * Author: Ethan Booker
 * Date:   2/15/2018
 * Description: practice using global variables and functions to manipulate array
 */

#include <stdio.h>
#include <stdlib.h>

// Function prototyping 
void print_ary();
int calc_sum();
double calc_avg();

// Global variables
const int ary[] = {4, 3, 2, 6, 7, 1, 0, 8, 9, 5};
const int size = 10;

int main(int argc, char **argv) {
  // Calling functions
  print_ary();
  printf("Sum: %d\n", calc_sum());
  printf("Average: %.3f\n", calc_avg());
  
  return 0;
}

void print_ary() {
  // Variable declaration
  int iter;
    
  for(iter = 0; iter < size; iter++) {
    printf("Value: %d\t address: %p\n", ary[iter], &(ary[iter]));
  }
}

int calc_sum() {
  // Variable intialization
  int sum = 0;
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];
  }

  return sum;
}

double calc_avg() {
  // Variable initalization 
  int sum = 0;
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];   
  }
  
  return (double)sum/size;
}
