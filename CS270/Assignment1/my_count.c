/*
 * A program that starts at a value and counts down by a certain given value
 * for a specific amount of times
 *
 * Author: Ethan Booker
 * Date: 2/1/2018
 */
#include <stdlib.h>
#include <stdio.h>

// Global constants
#define MAX_ARGUMENTS 4
#define MIN_ITER_VALUE 0

 int main(int argc, char **argv) {

    // Variable declaration
 	int start_value, interval, iterations;
    
    // Checking number of arguments
    if(argc != MAX_ARGUMENTS) {
		printf("Incorrect number of arguments. Expecting 3 arguments: value, interval, count.");
		return -1;
	}  

    // Checking whether the iteration value 
	if(strtol(argv[3], NULL, 10) < MIN_ITER_VALUE) {
    	printf("Invalid number of iterations. Must be a nonnegative number.");
    	return -1;
    }
    
    start_value = strtol(argv[1], NULL, 10); // Starting value, can be negative
    interval = strtol(argv[2], NULL, 10); // Interval value, can be negative 
    iterations = strtol(argv[3], NULL, 10); // Number of iterations, must be nonnegative 

	int i; // Loop variable declaration
    for(i = 0; i < iterations; i++) {
    	printf("%d ", start_value);
    	start_value -= interval;
    }

	return 0;
}


