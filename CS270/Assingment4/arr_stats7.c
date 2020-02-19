/*
 * Author: Ethan Booker
 * Date:   2/19/2018
 * Description: Reading user input to allocate an array
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

int initalize_ary(int *size, int **ary);
void find_value(int *ary, int size, int search_val);
void print_ary(int *ary, int size);
int print_sum(int *ary, int size);
double print_avg(int *ary, int size); 

// buffer size
#define LINELEN 1024

int main(int argc, char **argv) { 
  int *p = NULL;
  int size = -1; 
  int searchVal = initalize_ary(&size, &p);
  print_ary(p, size);
  find_value(p, size, searchVal);
  printf("Sum: %d\n", print_sum(p, size));
  printf("Average: %.3f\n", print_avg(p, size));

 
  return 0;
}

void find_value(int *ary, int size, int search_val) {
  int iter2;
  int count = 0;
  for(iter2 = 0; iter2 < size; iter2++){
  	if(search_val == ary[iter2]) {
  		if(count == 0) {
  			printf("The value: %d was found\n", search_val);	
  		}
  		printf("The value was found in the array at position: %d\n", iter2);
  		count++;
  	}
  }
  if(count ==0) {
  	printf("The value: %d was not found\n", search_val);
  }
}

int initalize_ary(int *size, int **ary) {
  //Variable initalization
  char *fgets_rtn = NULL;  // Variable to capture return code
  char buffer[ LINELEN ];  // Where we are reading the data into
  int value;
  int iteration = 0; 
  int ary_pos = 0;
  int tempVal;

  do {
    // read data (up to LINELEN chars) into buffer from stdin 
    fgets_rtn = fgets( buffer, LINELEN, stdin );
    if( fgets_rtn != NULL ) {
      // fgets was successful!
      if(iteration == 0) {
      	(*size) = (int)strtol(buffer, NULL, 10);
      } 
      else if(iteration == 1) {
        (*ary) = (int*)malloc(sizeof(int) * (*size));

      	char *rest = buffer;
      	char *token;

      	while(ary_pos < *size && (token = strtok_r(rest, " ", &rest)))
      	{
      		(*ary)[ary_pos] = (int)strtol(token, NULL, 10);
      		ary_pos++;
      	}
      }
      else{
      		value = (int)strtol(buffer, NULL, 10);	
      	}
      }
      iteration++;
  } while( iteration != 3 );
  return value;
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
