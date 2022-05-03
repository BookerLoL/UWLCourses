	/*
	 * SymTab.h
	 *
	 *  Created on: Sep 6, 2019
	 *      Author: Ethan
	 */

	#ifndef SYMTAB_H_
	#define SYMTAB_H_
	#endif
	/* SYMTAB_H_ */
	struct SymTab {
		int Size;
		struct SymEntry **Contents; 
	};

	struct SymEntry {
		char *Name;
		void *Attributes;
		struct SymEntry *Next;
	};

	struct SymTab* CreateSymTab(int Size);

	void DestroySymTab(struct SymTab *ATable);

	int EnterName(struct SymTab *ATable, const char *Name,
			struct SymEntry **AnEntry);

	struct SymEntry* FindName(struct SymTab *ATable, const char *Name);

	void SetAttr(struct SymEntry *AnEntry, void *Attributes);

	void* GetAttr(struct SymEntry *AnEntry);

	const char* GetName(struct SymEntry *AnEntry);

	struct SymEntry* FirstEntry(struct SymTab *ATable);

	struct SymEntry* NextEntry(struct SymTab *ATable, struct SymEntry *AnEntry);
