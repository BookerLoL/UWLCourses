/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    Ident = 258,
    IntLit = 259,
    BoolLit = 260,
    StrLit = 261,
    FloatLit = 262,
    Read = 263,
    Int = 264,
    Bool = 265,
    Float = 266,
    Void = 267,
    EQ = 268,
    NE = 269,
    LTE = 270,
    GTE = 271,
    AND = 272,
    OR = 273,
    Write = 274,
    Print = 275,
    PrintLn = 276,
    PrintSp = 277,
    PrintStr = 278,
    If = 279,
    Else = 280,
    While = 281,
    Return = 282
  };
#endif
/* Tokens.  */
#define Ident 258
#define IntLit 259
#define BoolLit 260
#define StrLit 261
#define FloatLit 262
#define Read 263
#define Int 264
#define Bool 265
#define Float 266
#define Void 267
#define EQ 268
#define NE 269
#define LTE 270
#define GTE 271
#define AND 272
#define OR 273
#define Write 274
#define Print 275
#define PrintLn 276
#define PrintSp 277
#define PrintStr 278
#define If 279
#define Else 280
#define While 281
#define Return 282

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED

union YYSTYPE
{
#line 24 "ExprEval.y" /* yacc.c:1909  */

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

#line 121 "y.tab.h" /* yacc.c:1909  */
};

typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
