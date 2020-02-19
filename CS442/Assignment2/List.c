#include "List.h"
#include <stdio.h>
#include <stdlib.h>

struct List *CreateList(int type)
{
    struct List *list = (struct List *)malloc(sizeof(struct List));
    if (!list)
    {
        perror("Failed to allocate memory for list");
        exit(-1);
    }
    list->type = type;
    list->count = 0;
    list->head = NULL;
    return list;
}
void AddLineNumber(struct List *list, int lineNumber)
{
    struct Info *node = (struct Info *)malloc(sizeof(struct Info));
    node->lineNumber = lineNumber;
    node->next = list->head;
    list->head = node;
    list->count = list->count + 1;
}
int GetType(struct List *list)
{
    return list->type;
}
int GetCount(struct List *list)
{
    return list->count;
}
void PrintLineNumber(struct List *list)
{
    struct Info *temp = list->head;
    while (temp)
    {
        printf("%d, ", temp->lineNumber);
        temp = temp->next;
    }
}
void DestroyList(struct List *list)
{
    struct Info *current = NULL;
    while ((current = list->head))
    {
        list->head = list->head->next;
        free(current);
    }
    free(list);
}