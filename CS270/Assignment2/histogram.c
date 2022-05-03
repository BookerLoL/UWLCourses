/*
 * Author: Ethan Booker
 * Date:   2/6/2018
 * Description: A program that reads data from a stdin one line at a time.
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

// buffer size
#define LINELEN 1024

void histogram(char x[], int *uc, int *lc, int *d, int *s, int *o);

int main(int argc, char **argv) {
  int uc = 0, lc = 0, d = 0, s = 0, o = 0;
  int str = 0;

  char *fgets_rtn = NULL;  // variable to capture return code
  char buffer[ LINELEN ];  // where we are reading the data into

  do {
    // read data (up to LINELEN chars) into buffer from stdin 
    fgets_rtn = fgets( buffer, LINELEN, stdin );
    if( fgets_rtn != NULL ) {
      // fgets was successful!
      if( '\n' == buffer[ strlen(buffer)-1 ] ) { 
        if(buffer[0] == '\n') {
          continue;
        }
        histogram(buffer, &uc, &lc, &d, &s, &o);
        str++;
      } 
   }
  } while( fgets_rtn != NULL );
  printf("----------------------------------------------\n");
  printf("%-30s%16d\n", "Total strings:"   ,str);
  printf("%-30s%16d\n", "Total upper case letters:", uc);
  printf("%-30s%16d\n", "Total lower case letters:", lc);
  printf("%-30s%16d\n", "Total digits:", d);
  printf("%-30s%16d\n", "Total spaces:", s);
  printf("%-30s%16d\n", "Total other:", o);
  printf("----------------------------------------------");
  return 0;
}

void histogram(char x[], int *uc, int *lc, int *d, int *s, int *o) {
  int v = strlen(x);
  int k;

  for(k = 0; k < v; k++) {
    if(x[k] == ' ' || x[k] == '\n'){
      (*s)++;
    } else if (isdigit(x[k])) {
      (*d)++;
    } else if (isupper(x[k])) {
      (*uc)++;
    } else if (islower(x[k])) {
      (*lc)++;
    } else if(isspace(x[k])) {
      continue;
    } else {
        (*o)++;
    }
  }
}
   
    
  




