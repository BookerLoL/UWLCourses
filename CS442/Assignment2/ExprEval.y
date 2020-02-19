%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "SymTab.h"
#include "IOMngr.h"
#include "CodeGen.h"
#include "Semantics.h"

extern int yylex();    /* The next token function. */
extern char *yytext;   /* The matched token text.  */
extern int yyleng;      /* The token text length.   */
extern int yyparse();
extern int yyerror(char *s);
void dumpTable();

extern struct SymTab *syms;
extern struct SymTab *funcs;
extern struct SymEntry *entry;
extern struct TypeAttr *attr; 

%}

%union {
  long val;
  char * string;
  struct ExprRes * ExprRes;
  struct InstrSeq * InstrSeq;
  struct ExprResList * ExprResList;
  struct FunctionParams *FunctionParams;
  struct IdList * IdList;
  struct VarSignature * VarSignature;
  struct FunctionSignature * FunctionSignature;
  struct FunctionLocalSignature * FunctionLocalSignature;
}

%type <string> Id
%type <string> TypeSpecifier
%type <string> VoidSpecifier
%type <string> ROPS
%type <string> BOP
%type <string> TOP
%type <string> length

%type <ExprRes> Literal
%type <ExprRes> Unary
%type <ExprRes> RStmt
%type <ExprRes> Factor
%type <ExprRes> Term
%type <ExprRes> Basic
%type <ExprRes> RExpr
%type <ExprRes> BExpr
%type <ExprRes> ANDExpr
%type <ExprRes> ORExpr
%type <ExprRes> Expr

%type <ExprResList> ExprSeq
%type <ExprResList> ArgSeq

%type <IdList> IdSeq
%type <IdList> IdRef

%type <InstrSeq> StmtSeq
%type <InstrSeq> Stmt
%type <InstrSeq> ReadStmt
%type <InstrSeq> ReturnStmt
%type <InstrSeq> CondStmt
%type <InstrSeq> IterStmt
%type <InstrSeq> AssignStmt
%type <InstrSeq> PrintStmt

%type <FunctionParams> FuncParams
%type <FunctionParams> Params
%type <FunctionParams> FuncParamDec

%type <VarSignature> VarID
%type <FunctionSignature> FuncSignature
%type <FunctionLocalSignature> Locals 
%type <FunctionLocalSignature> LocalDec

%token Ident
%token IntLit BoolLit StrLit FloatLit
%token Read
%token Int Bool Float Void
%token EQ NE LTE GTE
%token AND OR 
%token Write
%token Print PrintLn PrintSp PrintStr
%token If Else While
%token Return

%%

Prog          : Declarations StmtSeq                                         { finish($2); } ;

Declarations  : Dec Declarations                                             {  };
Declarations  :                                                              {  };

Dec           : VarDec                                                       {  };
Dec           : FuncDec                                                      {  };

Locals        : Locals LocalDec                                              { $$ = new_local_list($1, $2); };
Locals        :                                                              { $$ = NULL; };

LocalDec      : TypeSpecifier VarID ';'                                      { $$ = new_local_list_item($1, $2); };

VarDec        : TypeSpecifier VarID ';'                                      { var_eval($1, $2); };
VarID         : Id                                                           { $$ = new_var_signature($1, NULL); }; 
VarID         : Id '[' length ']'                                            { $$ = new_var_signature($1, $3);   };    

FuncDec       : FuncSignature '(' Params ')' Locals '{' StmtSeq '}'          { func_eval($1, $3, $5, $7);   };
FuncDec       : FuncSignature '(' ')' Locals '{' StmtSeq '}'                 { func_eval($1, NULL, $4, $6); };

FuncSignature : TypeSpecifier Id                                             { $$ = new_func_signature($1, $2); };
FuncSignature : VoidSpecifier Id                                             { $$ = new_func_signature($1, $2); };

Params        : FuncParams                                                   { $$ = $1; };

FuncParams    : FuncParams ',' FuncParamDec                                  { $$ = func_params_eval($1, $3); };                                                                                      
FuncParams    : FuncParamDec                                                 { $$ = func_params_eval($1, NULL); };

FuncParamDec  : TypeSpecifier Id                                             { $$ = new_func_param_signature($1, NULL, $2, NULL, 0); };
FuncParamDec  : TypeSpecifier '&' Id                                         { $$ = new_func_param_signature($1, "&", $3, NULL, 0); };
FuncParamDec  : TypeSpecifier Id '[' length ']'                              { $$ = new_func_param_signature($1, NULL, $2, $4, 1); };
FuncParamDec  : TypeSpecifier '&' Id '[' length ']'                          { $$ = new_func_param_signature($1, "&", $3, $5, 1); };

VoidSpecifier : Void                                                         { $$ = yylval.string; };
TypeSpecifier : Int                                                          { $$ = yylval.string; };
TypeSpecifier : Bool                                                         { $$ = yylval.string; };
TypeSpecifier : Float                                                        { $$ = yylval.string; };

StmtSeq       : Stmt StmtSeq                                                 { $$ = append_seq($1, $2); } ;
StmtSeq       :                                                              { $$ = NULL; };

Stmt          : PrintStmt                                                    { $$ = $1; };
Stmt          : ReadStmt                                                     { $$ = $1; };
Stmt          : ReturnStmt                                                   { $$ = $1; };
Stmt          : CondStmt                                                     { $$ = $1; };
Stmt          : IterStmt                                                     { $$ = $1; };
Stmt          : AssignStmt                                                   { $$ = $1; };

ExprSeq       : ExprSeq ',' Expr                                             { $$ = expr_eval($1, $3, 1); };
ExprSeq       : Expr                                                         { $$ = expr_eval(NULL, $1, 1); };
ArgSeq        : ArgSeq ',' Expr                                              { $$ = expr_eval($1, $3, 0); };
ArgSeq        : Expr                                                         { $$ = expr_eval(NULL, $1, 0); };
IdSeq         : IdSeq ',' IdRef                                              { $$ = id_item_eval($1, $3); };
IdSeq         : IdRef                                                        { $$ = id_item_eval($1, NULL); };
IdRef         : Id                                                           { $$ = new_id_item_signature($1, NULL); };
IdRef         : Id '[' Expr ']'                                              { $$ = new_id_item_signature($1, $3); };

PrintStmt     : Print '(' ExprSeq ')' ';'                                    { $$ = eval_expr_res_list($3); };
PrintStmt     : PrintStr '(' Expr ')' ';'                                    { $$ = print_eval($3, "str_", 1); };
PrintStmt     : PrintLn '(' ')' ';'                                          { $$ = print_eval(NULL, "_nl", 1); };
PrintStmt     : PrintSp '(' Expr ')' ';'                                     { $$ = print_eval($3, "_sp", 1); };
PrintStmt     : Write Expr ';'                                               { $$ = print_eval($2, "_nl", 1); };
ReadStmt      : Read '(' IdSeq ')' ';'                                       { $$ = eval_id_list($3); };
ReturnStmt    : Return Expr ';'                                              { $$ = return_eval($2); };
ReturnStmt    : Return ';'                                                   { $$ = return_eval(NULL); };
AssignStmt    : Id '[' Expr ']' '=' Expr ';'                                 { $$ = assign_eval($1, $6, $3); };
AssignStmt    : Id '=' Expr ';'                                              { $$ = assign_eval($1, $3, NULL); } ;
CondStmt      : If '(' Expr ')' '{' StmtSeq '}'                              { $$ = cond_eval($3, $6, NULL, "if"); };
CondStmt      : If '(' Expr ')' '{' StmtSeq '}' Else '{' StmtSeq '}'         { $$ = cond_eval($3, $6, $10, "ifelse"); };
IterStmt      : While '(' Expr ')' '{' StmtSeq '}'                           { $$ = cond_eval($3, $6, NULL, "while"); };

Expr          : ORExpr                                                       { $$ = $1; };

ORExpr        : ORExpr OR ANDExpr                                            { $$ = relat_eval($1, $3, "or"); };
ORExpr        : ANDExpr                                                      { $$ = $1; };

ANDExpr       : ANDExpr AND BExpr                                            { $$ = relat_eval($1, $3, "and"); };
ANDExpr       : BExpr                                                        { $$ = $1; };

BExpr         : BExpr EQ RExpr                                               { $$ = relat_eval($1, $3, "seq"); };
BExpr         : BExpr NE RExpr                                               { $$ = relat_eval($1, $3, "sne"); };
BExpr         : RExpr                                                        { $$ = $1; };

RExpr         : RExpr ROPS Basic                                              { $$ = relat_eval($1, $3, $2); };
ROPS          : '<'                                                          { $$ = "slt"; };
ROPS          : '>'                                                          { $$ = "sgt"; };
ROPS          : LTE                                                          { $$ = "sle"; };
ROPS          : GTE                                                          { $$ = "sge"; };

RExpr         : Basic                                                        { $$ = $1; };

Basic         : Basic BOP Term                                               { $$ = arith_eval($1, $3, $2); };
Basic         : Term                                                         { $$ = $1; };
BOP           : '+'                                                          { $$ = "add"; };
BOP           : '-'                                                          { $$ = "sub"; };
Term          : Term TOP Factor                                              { $$ = arith_eval($1, $3, $2); };
Term          : Factor                                                       { $$ = $1; };
TOP           : '*'                                                          { $$ = "mul"; };
TOP           : '/'                                                          { $$ = "div"; };
TOP           : '%'                                                          { $$ = "mod"; };

Factor        : Unary '^' Factor                                             { $$ = arith_eval($1, $3, "exp"); };
Factor        : Unary                                                        { $$ = $1; };

Unary         : '-' Unary                                                    { $$ = arith_eval($2, NULL, "neg"); };
Unary         : '!' Unary                                                    { $$ = relat_eval($2, NULL, "not"); };
Unary         : RStmt                                                        { $$ = $1; };

RStmt         : Literal                                                      { $$ = $1; };
RStmt         : '(' Expr ')'                                                 { $$ = $2; };
RStmt         : Id '[' Expr ']'                                              { $$ = rval_eval($1, $3); };
RStmt         : Id '(' ArgSeq ')'                                            { $$ = function_call_eval($1, $3); };
RStmt         : Id '(' ')'                                                   { $$ = function_call_eval($1, NULL); };

Literal       : IntLit                                                       { $$ = lit_eval(yylval.string, INT); };
Literal       : BoolLit                                                      { $$ = lit_eval(yylval.string, BOOL); };
Literal       : FloatLit                                                     { $$ = lit_eval(yylval.string, FLOAT); };
Literal       : StrLit                                                       { $$ = lit_eval(yylval.string, STRING); };
Literal       : Ident                                                        { $$ = rval_eval(yylval.string, NULL); };

Id            : Ident                                                        { $$ = yylval.string; };
length        : IntLit                                                       { $$ = yylval.string; };
%%

int yyerror(char *s)  {
  WriteIndicator(GetCurrentColumn());
  WriteMessage("Illegal Character in YACC");
  return 1;
}
