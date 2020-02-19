/*
 * Author: Ethan Booker
 * Date:   2/10/2018
 * Description: Implementation on traversing arrays and using logical pieces 
 */

#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {
  
  // Variable declarations 
  int sum = 0;
  int size = 10;
  int ary[] = {4, 3, 2, 6, 7, 1, 0, 8, 9, 5};
  int iter;
  
  // Iterate through array 
  for(iter = 0; iter < size; iter++) {
    printf("Value: %d\t address: %p\n", ary[iter], &(ary[iter]));
    sum += ary[iter];
  }
  
  printf("Sum: %d\n", sum);
  printf("Average: %.3f\n", ((double)sum/size) );
}
