/*
 * TestDriver.c
 *
 *  Created on: Sep 21, 2019
 *      Author: Ethan
 */
#include "SymTab.h"
#include "IOMngr.h"
#include "List.h"
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char* createName(int len, char *letters) {
	char *rstr = malloc((len + 1) * sizeof(char));
	int i;
	for (i = 0; i < len; i++) {
		rstr[i] = letters[rand() % strlen(letters)];
	}
	rstr[len] = '\0';
	return rstr;
}

int createNumbers(int max) {
	return rand() % max + 1;
}

int main() {
	/*
	 struct SymTab *table = CreateSymTab(10);
	 char *letters = "1234567890-=qwertyuiop[]asdfghjkl;'zxcvbnm,./";
	 char *value;
	 int numbers = 30;
	 int i = 0;
	 srand(time(NULL));
	 struct SymEntry **entry = NULL;
	 while (i != numbers) {
	 value = createName(1, letters);
	 EnterName(table, value, NULL);
	 i++;
	 }
	 printEntireTable(table);
	 DestroySymTab(table);
	 printEntireTable(table);
	 */
	struct SymTab *table = CreateSymTab(10);
	struct SymEntry *entry;
	OpenFiles("source.txt", "listing.txt");
	char ch;
	while ((ch = GetSourceChar()) != EOF) {
		char temp[2] = { ch, '\0' };
		EnterName(table, temp, &entry);
		struct List *list = GetAttr(entry);
		if (!list) {
			list = CreateList(0);
			entry->Attributes = list;
		}
		AddLineNumber(list, GetCurrentLine());
	}
	CloseFiles();

	entry = FirstEntry(table);
	while (entry) {
		printf("Name: %s\tLine Number:\t\n", GetName(entry));
		PrintLineNumber(GetAttr(entry));
		printf("\n");
		DestroyList(GetAttr(entry));
		entry = NextEntry(table, entry);
	}
	printf("=======================================================\n");
	printf("=======================================================\n");
	printf("=======================================================\n");

	entry = FirstEntry(table);
		while (entry) {
			printf("Name: %s\tLine Number:\t\n", GetName(entry));
			if (GetAttr(entry) == NULL) {
				printf("NULL\n");
			}
			printf("\n");
			entry = NextEntry(table, entry);
		}
	
	//CHECK LEX USING LEX PROGRAM
	return 0;
}

