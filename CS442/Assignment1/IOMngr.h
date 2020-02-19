/*
 * IOMngr.h
 *
 *  Created on: Sep 21, 2019
 *      Author: Ethan
 */
int OpenFiles(const char *ASourceName, const char *AListingName);
void CloseFiles();
char GetSourceChar();
void WriteIndicator(int AColumn);
void WriteMessage(const char *AMessage);
int GetCurrentLine();
int GetCurrentColumn();