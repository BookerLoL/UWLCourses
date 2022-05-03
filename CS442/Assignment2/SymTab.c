#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdbool.h>
#include "SymTab.h"

int hash(const char *Name, int tableSize) {
    unsigned long hash = 5381;
    int ch;

    while ((ch = *Name), *Name++) {
        hash = ((hash << 5) + hash) + ch;
    }

    return hash % tableSize;
}

void * check_mem_alloc(void *ptr) {
    if(ptr == NULL) {
        perror("memory allocation failed");
        exit(true);
    }

    return ptr;
}


struct SymTab * CreateSymTab(int Size) {
    struct SymTab *symtable;

    if(Size < 1) check_mem_alloc(NULL);

    symtable = (struct SymTab*) check_mem_alloc(malloc(sizeof(struct SymTab)));
    symtable->Contents = (struct SymEntry**) check_mem_alloc(calloc(Size, sizeof(struct SymEntry*)));
    symtable->Size=Size;

    return symtable;
}

void DestroySymTab(struct SymTab *ATable) {
    struct SymEntry *cur, *next;
    int i;

    for(i = 0; i < ATable->Size; i++) {
        cur = ATable->Contents[i];
        
        while(cur != NULL) {
            next = cur->Next;
            free(cur->Name);
            free(cur);
            cur = next;
        }

        ATable->Contents[i] = NULL;
    }

    free(ATable->Contents);
    free(ATable);
}

struct SymEntry* createEntry(const char *Name) {
    struct SymEntry *entry = (struct SymEntry*) check_mem_alloc(malloc(sizeof(struct SymEntry)));
    entry->Name = (char*) check_mem_alloc(malloc((strlen(Name) + 1) * sizeof(char)));

    strcpy(entry->Name, Name);
    entry->Next = NULL;
    entry->Attributes = NULL;

    return entry;
}

int EnterName(struct SymTab *ATable, const char *Name, struct SymEntry **AnEntry) {
    int row = hash(Name, ATable->Size);
    struct SymEntry *entry = ATable->Contents[row], *prev = NULL;

    while (entry) {
        if(strcmp(entry->Name, Name) == 0) {
            if(AnEntry) {
                *AnEntry = entry;
            }
            return 0;
        }
        
        prev = entry;
        entry = entry->Next;
    }

    entry = createEntry(Name);

    if (prev) {
        prev->Next = entry;
    } else {
        ATable->Contents[row] = entry;
    }

    if(AnEntry) {
        *AnEntry = entry;
    }

    return 1;
}

struct SymEntry* FindName(struct SymTab *ATable, const char *Name) {
    struct SymEntry *entry = ATable->Contents[hash(Name, ATable->Size)];

    while (entry) {
        if (strcmp(entry->Name, Name) == 0) {
            return entry;
        }

        entry = entry->Next;
    }

    return entry;
}

void SetAttr(struct SymEntry *AnEntry, void *Attributes) {
    AnEntry->Attributes = Attributes;
}

void* GetAttr(struct SymEntry *AnEntry) {
    return AnEntry->Attributes;
}

const char* GetName(struct SymEntry *AnEntry) {
    return AnEntry->Name;
}

struct SymEntry * FirstEntry(struct SymTab *ATable) {
    int i;

    for(i = 0; i < ATable->Size; i++) {
        if (ATable->Contents[i]) {
            return ATable->Contents[i];
        }
    }

    return NULL;
}

struct SymEntry* NextEntry(struct SymTab *ATable, struct SymEntry *AnEntry) {
    int i;

    if (AnEntry != NULL && AnEntry->Next != NULL) {
        return AnEntry->Next;
    } else if(AnEntry != NULL) {
        for(i = hash(AnEntry->Name, ATable->Size) + 1; i < ATable->Size; i++) {
            if (ATable->Contents[i]) {
                return ATable->Contents[i];
            }
        }
    }

    return NULL;
}
