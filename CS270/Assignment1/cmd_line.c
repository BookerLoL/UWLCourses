#include <stdio.h> 
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv) {
	int i;

	for( i = 0; i < argc; i++) {
		printf("Argument %d = [%s] <len = %d>\n", i, argv[i], strlen(argv[i]));
	}
	
    return 0;
}
