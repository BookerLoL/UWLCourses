/*
 * Author: Ethan Booker
 * Date:   2/15/2018
 * Description: practice passing array through functions to manipulate array
 */

#include <stdio.h>
#include <stdlib.h>

// Function prototyping 
void print_ary(int ary[], int size);
int print_sum(int ary[], int size);
double print_avg(int ary[], int size);

int main(int argc, char **argv) {
  // Initalizing variables 
  int ary[] = {4, 3, 2, 6, 7, 1, 0, 8, 9, 5};
  int size = 10;
  
  // Calling functions
  print_ary(&ary[0], size);
  printf("Sum: %d\n", print_sum(&ary[0], size));
  printf("Average: %.3f\n", print_avg(&ary[0], size));
  
  return 0;
}

void print_ary(int ary[], int size) {
  // Variable declaration
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    printf("Value: %d\t address: %p\n", ary[iter], &(ary[iter]));
  }
}

int print_sum(int ary[], int size) {
  // Variable intialization
  int sum = 0;
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];
  }
  return sum;
}

double print_avg(int ary[], int size) {
  // Variable initalization 
  int sum = 0;
  int iter;
  
  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];   
  }
  
  return (double)sum/size; 
}
