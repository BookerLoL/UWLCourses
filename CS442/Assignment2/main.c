#include <stdio.h>
#include "Semantics.h"
#include "CodeGen.h"
#include "SymTab.h"
#include "IOMngr.h"

extern int yyparse();

struct SymTab *syms;
struct SymTab *funcs;
struct SymEntry *entry;
int inProc =0;
FILE *file;

int main(int argc, char * argv[]) {
    syms = CreateSymTab(33);
    OpenFiles(argv[1], argv[2]);
    if (argc == 4) 
        file = fopen(argv[3], "w");
    else
        file = stdout;

    yyparse();

    CloseFiles();
}

