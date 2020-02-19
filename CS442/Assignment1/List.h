struct List
{
	int type;
	int count;
	struct Info *head;
};

struct Info
{
	int lineNumber;
	struct Info * next;
};

struct List* CreateList(int type);
void AddLineNumber(struct List *list, int lineNumber);
int GetType(struct List *list);
int GetCount(struct List *list);
void PrintLineNumber(struct List *list);
void DestroyList(struct List *list);
