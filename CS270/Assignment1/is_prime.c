/*
 * A program that takes as many arguements and determines 
 * if the number is prime if not then print out the divsiors
 *
 * Author: Ethan Booker
 * Date: 2/1/2018
 */

#include <stdio.h> 
#include <stdlib.h>

// Function declaration 
void is_prime(int);

// Determines whether the number be checked as a prime 
int main(int argc, char **argv) {
	// Variable declaration
	int iter;
	int num;

	// Loop through arguement values
	for(iter = 0; iter < argc-1; iter++) {
		printf("%s\n", "-----------------");

		num = strtol(argv[iter+1], NULL, 10);

		if(num < 1) {
			printf("%s %d %s\n", "Error: ", num, " is less than 1!");
		} else if (num == 1) {
			printf("%s\n", "1 is not prime. By definition!" );
		} else {
			is_prime(num);
		}
	}

return 0;
}


void is_prime(int num) {
	// Variable initialization
	int count = 0, iter = 2;

	printf("%s %d\n", "Checking: ", num);

	//Loop through half way point
	for( ;iter <= num / 2; iter++) {

		if(num % iter == 0) {
			printf("%s %d\n", "Divisible by ", iter);
			count++;
		}
	}

	//Conditions for determining if number is prime or not
	if(count > 0) {
		if(count == 1) {
			printf("%d %s\n", num, " is Composite! (1 divisor)");
		}
		else {
			printf("%d%s%d%s\n", num, " is Composite! (", count, " divisors)");
		}	
	} else {
		printf("%d %s\n", num, "is Prime!");
	}
}