%{
#include "lex1.h"
#include "list.h"
#undef YY_INPUT
#define YY_INPUT(buf,result,max_size) \
	{ \
		int c = GetSourceChar(); \
		result = (c == EOF) ? YY_NULL : (buf[0] = c, 1); \
    	}


struct SymTab *puncTable;
struct SymTab *primitiveTable;
struct SymTab *keywordTable;
struct SymTab *idTable;
struct SymEntry *entry;
struct List* attribute;

void PrintTable(struct SymTab *table, int printOption, int type) 
{
	char *TYPE;
	switch (type) {
		case 1: TYPE = "ERROR";
			break;
		case 2: TYPE = "KEYWORD";
			break;
		case 3: TYPE = "ID";
			break;
		case 4: TYPE = "INT";
			break;
		case 5: TYPE = "FLOAT";
			break;
		case 6: TYPE = "CHAR";
			break;
		case 7: TYPE = "STRING";
			break;
		case 8: TYPE = "PUNCTUATION";
			break;
		default: TYPE = "UNSUPPORTED TYPE";
	}
	printf("\n%s\n", "--------------------------------------------------------");
	printf("\n%s%s\n", "Printing out Type:\t", TYPE);
	entry = FirstEntry(table);
	while (entry) {
		attribute = GetAttr(entry);
		if (attribute) {
			if (GetType(attribute) == type) {
				if (printOption == 0) {
					printf("Token:\t%s\tNumber of Appearances:\t%d", GetName(entry), GetCount(attribute));
				} else {
					printf("Token:\t%s", GetName(entry));
					printf("\tLine Numbers: ");	
					PrintLineNumber(attribute);
				}
				printf("\n");
				DestroyList(attribute);
				SetAttr(entry, NULL);
			}
		}
		entry = NextEntry(table, entry);
	}
	entry = NULL;
	attribute = NULL;
}

struct List* Insert(struct List *list, int lineNumber, int type) 
{
	if (!list) {
		list = CreateList(type);
	}
	AddLineNumber(list, lineNumber);
    	return list;
}
%}

DIGIT [0-9]
LETTER [a-zA-Z]

%%
{DIGIT}+ {return INT;}
{DIGIT}+"."{DIGIT}+ {return FLOAT;}
\'.\' {return CHAR;}
\".*\" {return STRING;}

while {return KEYWORD;}
if {return KEYWORD;}
else {return KEYWORD;}
int {return KEYWORD;}
float {return KEYWORD;}
char {return KEYWORD;}
boolean {return KEYWORD;}
true {return KEYWORD;}
false {return KEYWORD;}
print {return KEYWORD;}
println {return KEYWORD;}
printstr {return KEYWORD;}
printsp {return KEYWORD;}
read {return KEYWORD;}

\{ {return PUNCTUATION;}
\} {return PUNCTUATION;}
\( {return PUNCTUATION;}
\) {return PUNCTUATION;}
\[ {return PUNCTUATION;}
\] {return PUNCTUATION;}
\+ {return PUNCTUATION;}
\- {return PUNCTUATION;}
\* {return PUNCTUATION;}
\/ {return PUNCTUATION;}
\^ {return PUNCTUATION;}
\&\& {return PUNCTUATION;}
\|\| {return PUNCTUATION;}
! {return PUNCTUATION;}
\< {return PUNCTUATION;}
\<= {return PUNCTUATION;}
\> {return PUNCTUATION;}
\>= {return PUNCTUATION;}
!= {return PUNCTUATION;}
== {return PUNCTUATION;}
:= {return PUNCTUATION;}
\, {return PUNCTUATION;}
; {return PUNCTUATION;}

[ \t\n\r\00] {}

{LETTER}({DIGIT}|{LETTER})* {return ID;}
. {return ERROR;} 
%%

int main(int argc, char *argv[])
{
	int token;
	puncTable = CreateSymTab(5);
	primitiveTable = CreateSymTab(10);
	keywordTable = CreateSymTab(5);
	idTable = CreateSymTab(10);
	OpenFiles(argv[1], argv[2]);
	while ((token = yylex()))
	{
		switch (token)
		{
			case ERROR:
				WriteIndicator(GetCurrentColumn());
				WriteMessage("Illegal Character");
				break;
			case PUNCTUATION:
				EnterName(puncTable, yytext, &entry);
				break;
			case ID:
				EnterName(idTable, yytext, &entry);
				break;
			case KEYWORD:
				EnterName(keywordTable, yytext, &entry);
				break;
			default:
				EnterName(primitiveTable, yytext, &entry);
		}

		if (token != ERROR)
		{
			attribute = Insert(GetAttr(entry), GetCurrentLine(), token);
			SetAttr(entry, attribute);
		}

		attribute = NULL;
		entry = NULL;
	}

	CloseFiles();
	return 0;
}

int yywrap() 
{
	PrintTable(keywordTable, 0, KEYWORD);
	DestroySymTab(keywordTable);
	keywordTable = NULL;
	PrintTable(puncTable, 0, PUNCTUATION);
	DestroySymTab(puncTable);
	puncTable = NULL;
	PrintTable(primitiveTable, 0, INT);
	PrintTable(primitiveTable, 0, FLOAT);
	PrintTable(primitiveTable, 0, CHAR);
	PrintTable(idTable, 1, ID);
	DestroySymTab(idTable);
	idTable = NULL;
	PrintTable(primitiveTable, 1, STRING);
	DestroySymTab(primitiveTable);
	primitiveTable = NULL;
	exit(0);
}
