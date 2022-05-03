/*
 * Author: Ethan Booker
 * Date:   2/20/2018
 * Description: returning status values for each method
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

// Function prototyping 
int initalize_ary(int *value, int *size, int **ary);
int find_value(int *ary, int size, int search_val);
int print_ary(int *ary, int size);
int print_sum(int *sum, int *ary, int size);
int print_avg(double *avg, int *ary, int size); 

// buffer size
#define LINELEN 1024

int main(int argc, char **argv) { 
  // Variable initialization
  int *p = NULL;
  int size = 0; 
  int searchVal = 0;
  int sum = 0;
  double avg = 0.0;
  
  int status = initalize_ary(&searchVal, &size, &p); // status will keep track of correct function
  if(status == 0) {
    status = print_ary(p, size);
    if(status == 0) {
      status = find_value(p, size, searchVal);
      if(status == 0) {
        status = print_sum(&sum, p, size);
        if(status == 0) {
           printf("Sum: %d\n", sum);
           status = print_avg(&avg, p, size); 
           
           if(status == 0) {
             printf("Average: %.3f\n", avg); 
           } else {
             printf("Input arguments weren't valid values for print_avg method"); 
           }
        } else {
          printf("Input arguments weren't valid values for print_sum method");
        }
      } else {
        printf("Input arguments weren't valid values for find_value method");
      }
    } else { 
      printf("Input arguments weren't valid values for print_ary method");
    } 
  } else {
      if(status == -1) {
        printf("The first input line should be a number that is greater than 0");
      } else if(status == -2) {
        printf("The second input line need to have at least the same amount of numbers as the first line");
      } 
  }
return 0;
}

int find_value(int *ary, int size, int search_val) {
  // Variable initialization
  int count = 0;
  int iter2;
  
  // Checking input data
  if(ary == NULL || size < 0) {
    return -1;
  }
  
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
  
  return 0;
}

int initalize_ary(int *value, int *size, int **ary) {
  // Variable initalization
  char *fgets_rtn = NULL;  // Variable to capture return code
  char buffer[ LINELEN ];  // Where we are reading the data into
  int iteration = 0; // Keeps track of number of lines read
  int ary_pos = 0; 
  int number_count = 0; // Keeps track of input values for 2nd line 
  
  do {
    // read data (up to LINELEN chars) into buffer from stdin 
    fgets_rtn = fgets( buffer, LINELEN, stdin );
    if( fgets_rtn != NULL ) {
      // fgets was successful!
      if(iteration == 0) { // First input line
        // Allocate memory 
      	(*size) = (int)strtol(buffer, NULL, 10);
      	
      	// Assumed size should
      	if(*size <= 0) {
      		return -1;
      	}
      } 
      else if(iteration == 1) { // Second input line
        (*ary) = (int*)malloc(sizeof(int) * (*size));

      	char *rest = buffer;
      	char *token;

      	while(ary_pos < *size && (token = strtok_r(rest, " ", &rest)))
      	{
      	  
      		(*ary)[ary_pos] = (int)strtol(token, NULL, 10);
      		ary_pos++;
      		number_count++;
      	}
      	
      	if(number_count != *size) {
      	  return -2;
      	}
      } else { // Third input line
      		(*value) = (int)strtol(buffer, NULL, 10);	
      }
    }
      iteration++;
  } while( iteration != 3 );
  return 0;
}

int print_ary(int *ary, int size) {
  //Variable declaration
  int iter;
  
  // Checking input data
  if(ary == NULL || size < 0) {
    return -1;
  }
  
  for(iter = 0; iter < size; iter++) {
    printf("Value: %d\t address: %p\n", ary[iter], &(ary[iter]));
  }
  
  return 0;
}

int print_sum(int *sum, int *ary, int size) {
  //Variable initalization
  (*sum) = 0;
  int iter;
  
  // Checking input data
  if(ary == NULL || size < 0) {
    return -1;
  }
  
  for(iter = 0; iter < size; iter++) {
     (*sum) += ary[iter];
  }

  return 0;
}

int print_avg(double *avg, int *ary, int size) {
  //Variable initalization
  (*avg) = 0;
  int sum = 0;
  int iter;
  
  // Checking input data
  if(ary == NULL || size < 0) {
      return -1;
  }
  
  for(iter = 0; iter < size; iter++) {
    sum += ary[iter];   
  }
  
  (*avg) = (double)sum/size;
  return 0;
}
