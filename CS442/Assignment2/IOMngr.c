/*
 * IOMngr.c
 *
 *  Created on: Sep 21, 2019
 *      Author: Ethan
 */
#include "IOMngr.h"

#include <stdlib.h>
#include <stdio.h>
#define MAXLINE 1024

FILE *src;
FILE *listing;
char buffer[MAXLINE];
int currentLine = 0;
int currentColumn = 0;
int called = 0;
int offset = 0;

int OpenFiles(const char *ASourceName, const char *AListingName)
{
    if (!(src = fopen(ASourceName, "r")))
    {
        return 0;
    }

    if (AListingName && !(listing = fopen(AListingName, "w")))
    {
        return 0;
    }
    return 1;
}
void CloseFiles()
{
    if (src)
    {
        fclose(src);
    }

    if (listing)
    {
        fclose(listing);
    }
}

int calcOffSet()
{
    int i = currentLine;
    int count = 1;
    while (i != 0)
    {
        i /= 10;
        count++;
    }
    return count;
}

char GetSourceChar()
{
    if (currentLine == 0 || buffer[currentColumn] == '\0')
    {
        if (fgets(buffer, sizeof buffer, src) == NULL)
        {
            return EOF;
        }
        currentLine++;
        if (listing)
        {
            if (currentLine == 1 || called == 0)
            {
                fprintf(listing, "%d. %s", currentLine, buffer);
            }
            else
            {
                fprintf(listing, "\n%d. %s", currentLine, buffer);
            }
        }
        offset = calcOffSet();
        currentColumn = 0;
        called = 0;
    }
    return buffer[currentColumn++];
}

void WriteIndicator(int AColumn)
{
    // Does not handle white spaces or tabs
    int realColumn = AColumn + offset;
    if (called == 0)
    {
        if (listing)
        {
            if (feof(src))
            {
                fprintf(listing, "\n%*s", realColumn, "^");
            }
            else
            {
                fprintf(listing, "%*s", realColumn, "^");
            }
        }
        else
        {
            printf("\n%d. %s", currentLine, buffer);
            if (feof(src))
            {
                printf("\n%*s", realColumn, "^");
            }
            else
            {
                printf("%*s", realColumn, "^");
            }
        }
        called = 1;
    }
    else
    {
        if (listing)
        {
            fprintf(listing, "\n%*s", realColumn, "^");
        }
        else
        {
            printf("\n%*s", realColumn, "^");
        }
    }
}
void WriteMessage(const char *AMessage)
{
    if (listing)
    {
        fprintf(listing, "\n%s", AMessage);
    }
    else
    {
        printf("\n%s", AMessage);
    }
}

int GetCurrentLine()
{
    return currentLine;
}
int GetCurrentColumn()
{
    return currentColumn + 1;
}