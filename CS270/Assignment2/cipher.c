/*
 * Author: Ethan Booker
 * Date:   2/12/2018
 * Description: A program that can encrypt and decipher messages
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

// buffer size
#define LINELEN 1024

// Function declaration
void decipher(char x[], int shifter);
void encript(char x[], int shifter);

int main(int argc, char **argv) {

  //Checks if arguments are valid 
 if(strcmp(argv[1],"-d") != 0 && strcmp(argv[1],"-e") != 0) {
  return -1;
 } 
 
 //Gets the shift count number
 int shifter = strtol(argv[2], NULL, 10);


  char *fgets_rtn = NULL;  // Variable to capture return code
  char buffer[ LINELEN ];  // Where we are reading the data into

  do {
    // read data (up to LINELEN chars) into buffer from stdin 
    fgets_rtn = fgets( buffer, LINELEN, stdin );
    if( fgets_rtn != NULL ) {
      // fgets was successful!
      //checks to see which option to do
      if(strcmp(argv[1],"-d") == 0) {
        decipher(buffer, shifter);
      } else {
        encript(buffer, shifter);
    }      
   }
  } while( fgets_rtn != NULL );
  return 0;
}

void decipher(char x[], int shifter) {
  int length = strlen(x);
  int i; 
  
  for(i = 0; i < length; i++){
    if(isalpha(x[i])){
      //Shifts the char value by the shift value
      x[i] = toupper(x[i]);
      int num = ((int) ((x[i] - shifter + 65) %26)) + 65;
      x[i] = (char)(num);
    } 
  }
  //print the changed string 
  printf("%s", x);
}


void encript(char x[], int shifter) {
  int length = strlen(x);
  int i; 

  for(i = 0; i < length; i++){
    if(isalpha(x[i])){
      //Shifts the char value by the shift value
      x[i] = toupper(x[i]);
      int num = ((int) ((x[i] + shifter - 65) %26)) + 65;
      x[i] = (char)(num);
    } 
  }
  printf("%s", x);
}