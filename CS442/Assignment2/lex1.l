%{
#include <string.h>
#include "SymTab.h"
#include "IOMngr.h"
#include "y.tab.h"

#define YY_INPUT(buf,result,max_size) \
     { int c = GetSourceChar(); \
          result = (c == EOF) ? YY_NULL : (buf[0] = c, 1); \
        }

%}


letter [A-Za-z]
digit [0-9]

%%
bool                            { yylval.string = strdup(yytext); return Bool; }
int                             { yylval.string = strdup(yytext); return Int; }
float                           { yylval.string = strdup(yytext); return Float; }
void                            { yylval.string = strdup(yytext); return Void; }

write                           { return Write; }
read                            { return Read; }
printstr                        { return PrintStr; }
printsp                         { return PrintSp; }
println                         { return PrintLn; }
print                           { return Print; }
return                          { return Return; }

if                              { return If; }
else                            { return Else; }
while                           { return While; }
true|false                      { yylval.string = strdup(yytext); return BoolLit; }

{letter}({letter}|{digit})*     { yylval.string = strdup(yytext); return Ident; }
{digit}+"."{digit}+             { yylval.string = strdup(yytext); return FloatLit; }
{digit}{digit}*                 { yylval.string = strdup(yytext); return IntLit; }
\".*\"                          { yylval.string = strdup(yytext); return StrLit; }

\!\=                            { return NE; }
\=\=                            { return EQ; }
\<\=                            { return LTE; }
\>\=                            { return GTE; }
\<                              { return '<'; }
\>                              { return '>'; }

\!                              { return '!'; }
\|\|                            { return OR; }
\&\&                            { return AND; }

\=                              { return '='; }
\+                              { return '+'; }
\*                              { return '*'; }
\-                              { return '-'; }
\/                              { return '/'; }
\%                              { return '%'; }
\&                              { return '&'; }
\^                              { return '^'; }
\;                              { return ';'; }
\,                              { return ','; }
\{                              { return '{'; }
\}                              { return '}'; }
\(                              { return '('; }
\)                              { return ')'; }
\[                              { return '['; }
\]                              { return ']'; }

[ ]                             {}
\t                              {}
\r                              {}
\n                              {}

.                               {WriteIndicator(GetCurrentColumn()); 
                                 WriteMessage("Illegal Character in lex");}

%%


int yywrap () {
     return 1;
}
