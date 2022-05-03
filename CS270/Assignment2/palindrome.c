/*
 * Author: Ethan Booker
 * Date:   2/7/2018
 * Description: Palindrome checker to see if stdin values are palidromes 
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

// Buffer size
#define LINELEN 1024

// Function declaration
int is_palindrome(char []); 

// Reads stdin values and checks if they are a palindrome 
int main(int argc, char **argv) {
  
  char *fgets_rtn = NULL;  // Variable to capture return code
  char buffer[ LINELEN ];  // Where we are reading the data intoint l


  int count; // Number of palidromes
  int total; // All the stdin values checked
  int test; // Result from is_palindrome(buffer)

  do {
    // Read data (up to LINELEN chars) into buffer from stdin 
    fgets_rtn = fgets( buffer, LINELEN, stdin );
    if( fgets_rtn != NULL ) {
      // fgets was successful!
      if( '\n' == buffer[ strlen(buffer)-1 ] ) { 

        // Reads if file is a new line
        if(buffer[0] == '\n') {
          continue;
        }

        //Checks if stdin value is a palindrome
        test = is_palindrome(buffer);
        if(test == 1) {
            printf("[Palindrome!]: %s", buffer);
            count++;
         } else {
            printf("[Regular String]: %s", buffer);
         }
         total++;
      } 
    }
  } while( fgets_rtn != NULL );
  
  printf("%s\n", "--------------------------------");
  printf("Total Palindromes: %d %s %d %s\n", count, "(out of", total, "strings)" );
  printf("%s\n", "--------------------------------");
  
  return 0;
}

int is_palindrome(char x[]) {

  int start; // Start of string input
  int end = strlen(x); // End of string input
  int status = 1; // Status acts as boolean, true or false

  // Iterate through until start is greater than end
  for(start = 0; start <= end; ) {
    // Makes sure both values are characters
    while(!isalpha(x[start]) || !isalpha(x[end]))
    {
      // Shifts start over if not a character 
      if(!isalpha(x[start])) {
        start++;
      }  
      // Shifts end over if not a character
      if(!isalpha(x[end])) { 
        end--;
      }
    }
    
    // Compares the character values regardless of case
    if(tolower(x[start]) == tolower(x[end])) {
      start++;
      end--;
    } else { 
      // Characters weren't the same, stop looping
      status = 0;
      break;
    }
  } // End of loop

  // Return status 
  if(status == 1) { 
    // Palindrome
    return 1;
  }  else { 
    //Not palidrome
    return 0;
  }
}


