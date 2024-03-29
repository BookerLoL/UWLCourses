/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Bison implementation for Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "3.0.4"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* Copy the first part of user declarations.  */
#line 1 "ExprEval.y" /* yacc.c:339  */

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


#line 89 "y.tab.c" /* yacc.c:339  */

# ifndef YY_NULLPTR
#  if defined __cplusplus && 201103L <= __cplusplus
#   define YY_NULLPTR nullptr
#  else
#   define YY_NULLPTR 0
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* In a future release of Bison, this section will be replaced
   by #include "y.tab.h".  */
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
#line 24 "ExprEval.y" /* yacc.c:355  */

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

#line 196 "y.tab.c" /* yacc.c:355  */
};

typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */

/* Copy the second part of user declarations.  */

#line 213 "y.tab.c" /* yacc.c:358  */

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef YY_ATTRIBUTE
# if (defined __GNUC__                                               \
      && (2 < __GNUC__ || (__GNUC__ == 2 && 96 <= __GNUC_MINOR__)))  \
     || defined __SUNPRO_C && 0x5110 <= __SUNPRO_C
#  define YY_ATTRIBUTE(Spec) __attribute__(Spec)
# else
#  define YY_ATTRIBUTE(Spec) /* empty */
# endif
#endif

#ifndef YY_ATTRIBUTE_PURE
# define YY_ATTRIBUTE_PURE   YY_ATTRIBUTE ((__pure__))
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# define YY_ATTRIBUTE_UNUSED YY_ATTRIBUTE ((__unused__))
#endif

#if !defined _Noreturn \
     && (!defined __STDC_VERSION__ || __STDC_VERSION__ < 201112)
# if defined _MSC_VER && 1200 <= _MSC_VER
#  define _Noreturn __declspec (noreturn)
# else
#  define _Noreturn YY_ATTRIBUTE ((__noreturn__))
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

#if defined __GNUC__ && 407 <= __GNUC__ * 100 + __GNUC_MINOR__
/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN \
    _Pragma ("GCC diagnostic push") \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")\
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# define YY_IGNORE_MAYBE_UNINITIALIZED_END \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif


#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYSIZE_T yynewbytes;                                            \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / sizeof (*yyptr);                          \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, (Count) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYSIZE_T yyi;                         \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  13
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   190

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  47
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  43
/* YYNRULES -- Number of rules.  */
#define YYNRULES  96
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  191

/* YYTRANSLATE[YYX] -- Symbol number corresponding to YYX as returned
   by yylex, with out-of-bounds checking.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   282

#define YYTRANSLATE(YYX)                                                \
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, without out-of-bounds checking.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    46,     2,     2,     2,    44,    36,     2,
      31,    32,    42,    40,    35,    41,     2,    43,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    28,
      38,    37,    39,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    29,     2,    30,    45,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    33,     2,    34,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27
};

#if YYDEBUG
  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_uint8 yyrline[] =
{
       0,    94,    94,    96,    97,    99,   100,   102,   103,   105,
     107,   108,   109,   111,   112,   114,   115,   117,   119,   120,
     122,   123,   124,   125,   127,   128,   129,   130,   132,   133,
     135,   136,   137,   138,   139,   140,   142,   143,   144,   145,
     146,   147,   148,   149,   151,   152,   153,   154,   155,   156,
     157,   158,   159,   160,   161,   162,   163,   165,   167,   168,
     170,   171,   173,   174,   175,   177,   178,   179,   180,   181,
     183,   185,   186,   187,   188,   189,   190,   191,   192,   193,
     195,   196,   198,   199,   200,   202,   203,   204,   205,   206,
     208,   209,   210,   211,   212,   214,   215
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 0
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "Ident", "IntLit", "BoolLit", "StrLit",
  "FloatLit", "Read", "Int", "Bool", "Float", "Void", "EQ", "NE", "LTE",
  "GTE", "AND", "OR", "Write", "Print", "PrintLn", "PrintSp", "PrintStr",
  "If", "Else", "While", "Return", "';'", "'['", "']'", "'('", "')'",
  "'{'", "'}'", "','", "'&'", "'='", "'<'", "'>'", "'+'", "'-'", "'*'",
  "'/'", "'%'", "'^'", "'!'", "$accept", "Prog", "Declarations", "Dec",
  "Locals", "LocalDec", "VarDec", "VarID", "FuncDec", "FuncSignature",
  "Params", "FuncParams", "FuncParamDec", "VoidSpecifier", "TypeSpecifier",
  "StmtSeq", "Stmt", "ExprSeq", "ArgSeq", "IdSeq", "IdRef", "PrintStmt",
  "ReadStmt", "ReturnStmt", "AssignStmt", "CondStmt", "IterStmt", "Expr",
  "ORExpr", "ANDExpr", "BExpr", "RExpr", "ROPS", "Basic", "BOP", "Term",
  "TOP", "Factor", "Unary", "RStmt", "Literal", "Id", "length", YY_NULLPTR
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[NUM] -- (External) token number corresponding to the
   (internal) symbol number NUM (which must be that of a token).  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,    59,    91,
      93,    40,    41,   123,   125,    44,    38,    61,    60,    62,
      43,    45,    42,    47,    37,    94,    33
};
# endif

#define YYPACT_NINF -121

#define yypact_value_is_default(Yystate) \
  (!!((Yystate) == (-121)))

#define YYTABLE_NINF -96

#define yytable_value_is_error(Yytable_value) \
  0

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
static const yytype_int16 yypact[] =
{
      64,  -121,  -121,  -121,  -121,     2,   106,    64,  -121,  -121,
     -18,    17,    17,  -121,  -121,     4,    53,    16,    21,    36,
      56,    77,    80,     5,  -121,   106,  -121,  -121,  -121,  -121,
    -121,  -121,    24,  -121,    61,  -121,    -3,    54,    17,    69,
    -121,  -121,  -121,  -121,    53,    53,    53,    88,    74,   103,
      65,    -1,    66,    47,  -121,    78,  -121,  -121,    72,    53,
      90,    53,    53,    53,    53,  -121,   107,  -121,    53,    53,
    -121,    99,   101,  -121,    -2,  -121,   130,    30,  -121,   108,
     112,  -121,  -121,  -121,    53,    53,    53,    53,  -121,  -121,
    -121,  -121,    53,  -121,  -121,    53,  -121,  -121,  -121,    53,
      53,    53,    23,    31,  -121,   118,   115,   117,   119,   120,
    -121,   110,   122,     7,  -121,    86,    17,   124,  -121,   125,
     126,    17,    53,  -121,   103,    65,    -1,    -1,    66,    47,
    -121,  -121,   129,  -121,    45,  -121,   128,    53,  -121,   132,
     133,   131,   135,   134,  -121,   106,  -121,    17,    12,  -121,
     136,   130,  -121,  -121,  -121,   139,  -121,  -121,    53,  -121,
    -121,  -121,  -121,   106,   106,    53,   138,   142,   137,   106,
     130,   143,  -121,  -121,   140,   141,   148,  -121,  -121,   144,
     147,  -121,   154,  -121,  -121,  -121,  -121,   150,   106,   146,
    -121
};

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       4,    25,    26,    27,    24,     0,    29,     4,     5,     6,
       0,     0,     0,     1,    95,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     2,    29,    30,    31,    32,    35,
      33,    34,     0,     3,     0,    16,     0,    11,     0,    94,
      90,    91,    93,    92,     0,     0,     0,     0,    57,    59,
      61,    64,    70,    72,    76,    81,    84,    85,     0,     0,
       0,     0,     0,     0,     0,    51,     0,    28,     0,     0,
       8,     0,    17,    19,     0,    10,     0,     0,    41,    42,
       0,    82,    83,    48,     0,     0,     0,     0,    68,    69,
      66,    67,     0,    73,    74,     0,    77,    78,    79,     0,
       0,     0,     0,     0,    37,     0,     0,     0,     0,     0,
      50,     0,     0,     0,     8,     0,     0,    20,    96,     0,
       0,     0,     0,    86,    58,    60,    62,    63,    65,    71,
      75,    80,     0,    89,     0,    39,     0,     0,    46,     0,
       0,     0,     0,     0,    53,    29,     7,     0,     0,    18,
      21,     0,    12,    49,    40,     0,    87,    88,     0,    44,
      36,    47,    45,    29,    29,     0,     0,     0,    11,    29,
       0,     0,    43,    38,     0,     0,     0,    14,     9,     0,
       0,    22,    54,    56,    52,    13,    23,     0,    29,     0,
      55
};

  /* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -121,  -121,   155,  -121,    67,  -121,  -121,    37,  -121,  -121,
    -121,  -121,    70,  -121,   -27,   -21,  -121,  -121,  -121,  -121,
      68,  -121,  -121,  -121,  -121,  -121,  -121,   -20,  -121,   102,
     105,    18,  -121,    95,  -121,    93,  -121,    13,    73,  -121,
    -121,    -6,  -120
};

  /* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int16 yydefgoto[] =
{
      -1,     5,     6,     7,   113,   146,     8,    36,     9,    10,
      71,    72,    73,    11,    12,    24,    25,   103,   134,    77,
      78,    26,    27,    28,    29,    30,    31,    47,    48,    49,
      50,    51,    92,    52,    95,    53,    99,    54,    55,    56,
      57,    58,   119
};

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int16 yytable[] =
{
      32,    14,    13,    66,    67,    35,    37,    74,    39,    40,
      41,    42,    43,    34,    88,    89,     1,     2,     3,    32,
      14,     1,     2,     3,    80,    75,    39,    40,    41,    42,
      43,   171,    79,    65,   116,    38,    44,    90,    91,   104,
     145,   106,   107,   108,   109,   169,    45,    59,   111,   112,
     180,    46,    60,    68,    44,   133,    39,    40,    41,    42,
      43,    69,   120,   136,    45,   121,   137,    61,   117,    46,
       1,     2,     3,     1,     2,     3,     4,   157,    86,    87,
     158,   132,   135,    76,    44,   -15,   147,    62,    74,    96,
      97,    98,    84,    70,    45,     1,     2,     3,   -95,    46,
     -95,   101,   155,   102,   126,   127,    93,    94,    63,    14,
     150,    64,   130,   131,    15,    79,    83,   160,    81,    82,
      85,   147,   105,   100,   166,    16,    17,    18,    19,    20,
      21,   114,    22,    23,   118,   110,   115,   122,   173,    32,
     143,   168,   174,   175,   123,   176,   138,   139,   179,   140,
     144,   141,   142,   151,   153,   152,   159,    32,    32,   156,
     161,   162,    33,    32,   163,   170,    76,   189,   164,   172,
     178,   165,   177,   181,   182,   183,   184,   186,   185,   187,
     190,   148,    32,   188,   167,   149,   124,   128,   129,   154,
     125
};

static const yytype_uint8 yycheck[] =
{
       6,     3,     0,    23,    25,    11,    12,    34,     3,     4,
       5,     6,     7,    31,    15,    16,     9,    10,    11,    25,
       3,     9,    10,    11,    44,    28,     3,     4,     5,     6,
       7,   151,    38,    28,    36,    31,    31,    38,    39,    59,
      33,    61,    62,    63,    64,    33,    41,    31,    68,    69,
     170,    46,    31,    29,    31,    32,     3,     4,     5,     6,
       7,    37,    32,    32,    41,    35,    35,    31,    74,    46,
       9,    10,    11,     9,    10,    11,    12,    32,    13,    14,
      35,   101,   102,    29,    31,    31,   113,    31,   115,    42,
      43,    44,    18,    32,    41,     9,    10,    11,    29,    46,
      31,    29,   122,    31,    86,    87,    40,    41,    31,     3,
     116,    31,    99,   100,     8,   121,    28,   137,    45,    46,
      17,   148,    32,    45,   145,    19,    20,    21,    22,    23,
      24,    32,    26,    27,     4,    28,    35,    29,   158,   145,
      30,   147,   163,   164,    32,   165,    28,    32,   169,    32,
      28,    32,    32,    29,    28,    30,    28,   163,   164,    30,
      28,    28,     7,   169,    33,    29,    29,   188,    33,    30,
      28,    37,    34,    30,    34,    34,    28,    30,    34,    25,
      34,   114,   188,    33,   147,   115,    84,    92,    95,   121,
      85
};

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,     9,    10,    11,    12,    48,    49,    50,    53,    55,
      56,    60,    61,     0,     3,     8,    19,    20,    21,    22,
      23,    24,    26,    27,    62,    63,    68,    69,    70,    71,
      72,    73,    88,    49,    31,    88,    54,    88,    31,     3,
       4,     5,     6,     7,    31,    41,    46,    74,    75,    76,
      77,    78,    80,    82,    84,    85,    86,    87,    88,    31,
      31,    31,    31,    31,    31,    28,    74,    62,    29,    37,
      32,    57,    58,    59,    61,    28,    29,    66,    67,    88,
      74,    85,    85,    28,    18,    17,    13,    14,    15,    16,
      38,    39,    79,    40,    41,    81,    42,    43,    44,    83,
      45,    29,    31,    64,    74,    32,    74,    74,    74,    74,
      28,    74,    74,    51,    32,    35,    36,    88,     4,    89,
      32,    35,    29,    32,    76,    77,    78,    78,    80,    82,
      84,    84,    74,    32,    65,    74,    32,    35,    28,    32,
      32,    32,    32,    30,    28,    33,    52,    61,    51,    59,
      88,    29,    30,    28,    67,    74,    30,    32,    35,    28,
      74,    28,    28,    33,    33,    37,    62,    54,    88,    33,
      29,    89,    30,    74,    62,    62,    74,    34,    28,    62,
      89,    30,    34,    34,    28,    34,    30,    25,    33,    62,
      34
};

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    47,    48,    49,    49,    50,    50,    51,    51,    52,
      53,    54,    54,    55,    55,    56,    56,    57,    58,    58,
      59,    59,    59,    59,    60,    61,    61,    61,    62,    62,
      63,    63,    63,    63,    63,    63,    64,    64,    65,    65,
      66,    66,    67,    67,    68,    68,    68,    68,    68,    69,
      70,    70,    71,    71,    72,    72,    73,    74,    75,    75,
      76,    76,    77,    77,    77,    78,    79,    79,    79,    79,
      78,    80,    80,    81,    81,    82,    82,    83,    83,    83,
      84,    84,    85,    85,    85,    86,    86,    86,    86,    86,
      87,    87,    87,    87,    87,    88,    89
};

  /* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     2,     2,     0,     1,     1,     2,     0,     3,
       3,     1,     4,     8,     7,     2,     2,     1,     3,     1,
       2,     3,     5,     6,     1,     1,     1,     1,     2,     0,
       1,     1,     1,     1,     1,     1,     3,     1,     3,     1,
       3,     1,     1,     4,     5,     5,     4,     5,     3,     5,
       3,     2,     7,     4,     7,    11,     7,     1,     3,     1,
       3,     1,     3,     3,     1,     3,     1,     1,     1,     1,
       1,     3,     1,     1,     1,     3,     1,     1,     1,     1,
       3,     1,     2,     2,     1,     1,     3,     4,     4,     3,
       1,     1,     1,     1,     1,     1,     1
};


#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)
#define YYEMPTY         (-2)
#define YYEOF           0

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                  \
do                                                              \
  if (yychar == YYEMPTY)                                        \
    {                                                           \
      yychar = (Token);                                         \
      yylval = (Value);                                         \
      YYPOPSTACK (yylen);                                       \
      yystate = *yyssp;                                         \
      goto yybackup;                                            \
    }                                                           \
  else                                                          \
    {                                                           \
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;                                                  \
    }                                                           \
while (0)

/* Error token number */
#define YYTERROR        1
#define YYERRCODE       256



/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)

/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Type, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*----------------------------------------.
| Print this symbol's value on YYOUTPUT.  |
`----------------------------------------*/

static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  FILE *yyo = yyoutput;
  YYUSE (yyo);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# endif
  YYUSE (yytype);
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyoutput, "%s %s (",
             yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yytype_int16 *yyssp, YYSTYPE *yyvsp, int yyrule)
{
  unsigned long int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       yystos[yyssp[yyi + 1 - yynrhs]],
                       &(yyvsp[(yyi + 1) - (yynrhs)])
                                              );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
static YYSIZE_T
yystrlen (const char *yystr)
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            /* Fall through.  */
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYSIZE_T *yymsg_alloc, char **yymsg,
                yytype_int16 *yyssp, int yytoken)
{
  YYSIZE_T yysize0 = yytnamerr (YY_NULLPTR, yytname[yytoken]);
  YYSIZE_T yysize = yysize0;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULLPTR;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected"). */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[*yyssp];
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYSIZE_T yysize1 = yysize + yytnamerr (YY_NULLPTR, yytname[yyx]);
                  if (! (yysize <= yysize1
                         && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
                    return 2;
                  yysize = yysize1;
                }
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    YYSIZE_T yysize1 = yysize + yystrlen (yyformat);
    if (! (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM))
      return 2;
    yysize = yysize1;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          yyp++;
          yyformat++;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
{
  YYUSE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}




/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       'yyss': related to states.
       'yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        YYSTYPE *yyvs1 = yyvs;
        yytype_int16 *yyss1 = yyss;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * sizeof (*yyssp),
                    &yyvs1, yysize * sizeof (*yyvsp),
                    &yystacksize);

        yyss = yyss1;
        yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yytype_int16 *yyss1 = yyss;
        union yyalloc *yyptr =
          (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
        if (! yyptr)
          goto yyexhaustedlab;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
                  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:
#line 94 "ExprEval.y" /* yacc.c:1646  */
    { finish((yyvsp[0].InstrSeq)); }
#line 1425 "y.tab.c" /* yacc.c:1646  */
    break;

  case 3:
#line 96 "ExprEval.y" /* yacc.c:1646  */
    {  }
#line 1431 "y.tab.c" /* yacc.c:1646  */
    break;

  case 4:
#line 97 "ExprEval.y" /* yacc.c:1646  */
    {  }
#line 1437 "y.tab.c" /* yacc.c:1646  */
    break;

  case 5:
#line 99 "ExprEval.y" /* yacc.c:1646  */
    {  }
#line 1443 "y.tab.c" /* yacc.c:1646  */
    break;

  case 6:
#line 100 "ExprEval.y" /* yacc.c:1646  */
    {  }
#line 1449 "y.tab.c" /* yacc.c:1646  */
    break;

  case 7:
#line 102 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionLocalSignature) = new_local_list((yyvsp[-1].FunctionLocalSignature), (yyvsp[0].FunctionLocalSignature)); }
#line 1455 "y.tab.c" /* yacc.c:1646  */
    break;

  case 8:
#line 103 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionLocalSignature) = NULL; }
#line 1461 "y.tab.c" /* yacc.c:1646  */
    break;

  case 9:
#line 105 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionLocalSignature) = new_local_list_item((yyvsp[-2].string), (yyvsp[-1].VarSignature)); }
#line 1467 "y.tab.c" /* yacc.c:1646  */
    break;

  case 10:
#line 107 "ExprEval.y" /* yacc.c:1646  */
    { var_eval((yyvsp[-2].string), (yyvsp[-1].VarSignature)); }
#line 1473 "y.tab.c" /* yacc.c:1646  */
    break;

  case 11:
#line 108 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.VarSignature) = new_var_signature((yyvsp[0].string), NULL); }
#line 1479 "y.tab.c" /* yacc.c:1646  */
    break;

  case 12:
#line 109 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.VarSignature) = new_var_signature((yyvsp[-3].string), (yyvsp[-1].string));   }
#line 1485 "y.tab.c" /* yacc.c:1646  */
    break;

  case 13:
#line 111 "ExprEval.y" /* yacc.c:1646  */
    { func_eval((yyvsp[-7].FunctionSignature), (yyvsp[-5].FunctionParams), (yyvsp[-3].FunctionLocalSignature), (yyvsp[-1].InstrSeq));   }
#line 1491 "y.tab.c" /* yacc.c:1646  */
    break;

  case 14:
#line 112 "ExprEval.y" /* yacc.c:1646  */
    { func_eval((yyvsp[-6].FunctionSignature), NULL, (yyvsp[-3].FunctionLocalSignature), (yyvsp[-1].InstrSeq)); }
#line 1497 "y.tab.c" /* yacc.c:1646  */
    break;

  case 15:
#line 114 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionSignature) = new_func_signature((yyvsp[-1].string), (yyvsp[0].string)); }
#line 1503 "y.tab.c" /* yacc.c:1646  */
    break;

  case 16:
#line 115 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionSignature) = new_func_signature((yyvsp[-1].string), (yyvsp[0].string)); }
#line 1509 "y.tab.c" /* yacc.c:1646  */
    break;

  case 17:
#line 117 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionParams) = (yyvsp[0].FunctionParams); }
#line 1515 "y.tab.c" /* yacc.c:1646  */
    break;

  case 18:
#line 119 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionParams) = func_params_eval((yyvsp[-2].FunctionParams), (yyvsp[0].FunctionParams)); }
#line 1521 "y.tab.c" /* yacc.c:1646  */
    break;

  case 19:
#line 120 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionParams) = func_params_eval((yyvsp[0].FunctionParams), NULL); }
#line 1527 "y.tab.c" /* yacc.c:1646  */
    break;

  case 20:
#line 122 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionParams) = new_func_param_signature((yyvsp[-1].string), NULL, (yyvsp[0].string), NULL, 0); }
#line 1533 "y.tab.c" /* yacc.c:1646  */
    break;

  case 21:
#line 123 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionParams) = new_func_param_signature((yyvsp[-2].string), "&", (yyvsp[0].string), NULL, 0); }
#line 1539 "y.tab.c" /* yacc.c:1646  */
    break;

  case 22:
#line 124 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionParams) = new_func_param_signature((yyvsp[-4].string), NULL, (yyvsp[-3].string), (yyvsp[-1].string), 1); }
#line 1545 "y.tab.c" /* yacc.c:1646  */
    break;

  case 23:
#line 125 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.FunctionParams) = new_func_param_signature((yyvsp[-5].string), "&", (yyvsp[-3].string), (yyvsp[-1].string), 1); }
#line 1551 "y.tab.c" /* yacc.c:1646  */
    break;

  case 24:
#line 127 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = yylval.string; }
#line 1557 "y.tab.c" /* yacc.c:1646  */
    break;

  case 25:
#line 128 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = yylval.string; }
#line 1563 "y.tab.c" /* yacc.c:1646  */
    break;

  case 26:
#line 129 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = yylval.string; }
#line 1569 "y.tab.c" /* yacc.c:1646  */
    break;

  case 27:
#line 130 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = yylval.string; }
#line 1575 "y.tab.c" /* yacc.c:1646  */
    break;

  case 28:
#line 132 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = append_seq((yyvsp[-1].InstrSeq), (yyvsp[0].InstrSeq)); }
#line 1581 "y.tab.c" /* yacc.c:1646  */
    break;

  case 29:
#line 133 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = NULL; }
#line 1587 "y.tab.c" /* yacc.c:1646  */
    break;

  case 30:
#line 135 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = (yyvsp[0].InstrSeq); }
#line 1593 "y.tab.c" /* yacc.c:1646  */
    break;

  case 31:
#line 136 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = (yyvsp[0].InstrSeq); }
#line 1599 "y.tab.c" /* yacc.c:1646  */
    break;

  case 32:
#line 137 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = (yyvsp[0].InstrSeq); }
#line 1605 "y.tab.c" /* yacc.c:1646  */
    break;

  case 33:
#line 138 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = (yyvsp[0].InstrSeq); }
#line 1611 "y.tab.c" /* yacc.c:1646  */
    break;

  case 34:
#line 139 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = (yyvsp[0].InstrSeq); }
#line 1617 "y.tab.c" /* yacc.c:1646  */
    break;

  case 35:
#line 140 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = (yyvsp[0].InstrSeq); }
#line 1623 "y.tab.c" /* yacc.c:1646  */
    break;

  case 36:
#line 142 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprResList) = expr_eval((yyvsp[-2].ExprResList), (yyvsp[0].ExprRes), 1); }
#line 1629 "y.tab.c" /* yacc.c:1646  */
    break;

  case 37:
#line 143 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprResList) = expr_eval(NULL, (yyvsp[0].ExprRes), 1); }
#line 1635 "y.tab.c" /* yacc.c:1646  */
    break;

  case 38:
#line 144 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprResList) = expr_eval((yyvsp[-2].ExprResList), (yyvsp[0].ExprRes), 0); }
#line 1641 "y.tab.c" /* yacc.c:1646  */
    break;

  case 39:
#line 145 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprResList) = expr_eval(NULL, (yyvsp[0].ExprRes), 0); }
#line 1647 "y.tab.c" /* yacc.c:1646  */
    break;

  case 40:
#line 146 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.IdList) = id_item_eval((yyvsp[-2].IdList), (yyvsp[0].IdList)); }
#line 1653 "y.tab.c" /* yacc.c:1646  */
    break;

  case 41:
#line 147 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.IdList) = id_item_eval((yyvsp[0].IdList), NULL); }
#line 1659 "y.tab.c" /* yacc.c:1646  */
    break;

  case 42:
#line 148 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.IdList) = new_id_item_signature((yyvsp[0].string), NULL); }
#line 1665 "y.tab.c" /* yacc.c:1646  */
    break;

  case 43:
#line 149 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.IdList) = new_id_item_signature((yyvsp[-3].string), (yyvsp[-1].ExprRes)); }
#line 1671 "y.tab.c" /* yacc.c:1646  */
    break;

  case 44:
#line 151 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = eval_expr_res_list((yyvsp[-2].ExprResList)); }
#line 1677 "y.tab.c" /* yacc.c:1646  */
    break;

  case 45:
#line 152 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = print_eval((yyvsp[-2].ExprRes), "str_", 1); }
#line 1683 "y.tab.c" /* yacc.c:1646  */
    break;

  case 46:
#line 153 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = print_eval(NULL, "_nl", 1); }
#line 1689 "y.tab.c" /* yacc.c:1646  */
    break;

  case 47:
#line 154 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = print_eval((yyvsp[-2].ExprRes), "_sp", 1); }
#line 1695 "y.tab.c" /* yacc.c:1646  */
    break;

  case 48:
#line 155 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = print_eval((yyvsp[-1].ExprRes), "_nl", 1); }
#line 1701 "y.tab.c" /* yacc.c:1646  */
    break;

  case 49:
#line 156 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = eval_id_list((yyvsp[-2].IdList)); }
#line 1707 "y.tab.c" /* yacc.c:1646  */
    break;

  case 50:
#line 157 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = return_eval((yyvsp[-1].ExprRes)); }
#line 1713 "y.tab.c" /* yacc.c:1646  */
    break;

  case 51:
#line 158 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = return_eval(NULL); }
#line 1719 "y.tab.c" /* yacc.c:1646  */
    break;

  case 52:
#line 159 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = assign_eval((yyvsp[-6].string), (yyvsp[-1].ExprRes), (yyvsp[-4].ExprRes)); }
#line 1725 "y.tab.c" /* yacc.c:1646  */
    break;

  case 53:
#line 160 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = assign_eval((yyvsp[-3].string), (yyvsp[-1].ExprRes), NULL); }
#line 1731 "y.tab.c" /* yacc.c:1646  */
    break;

  case 54:
#line 161 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = cond_eval((yyvsp[-4].ExprRes), (yyvsp[-1].InstrSeq), NULL, "if"); }
#line 1737 "y.tab.c" /* yacc.c:1646  */
    break;

  case 55:
#line 162 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = cond_eval((yyvsp[-8].ExprRes), (yyvsp[-5].InstrSeq), (yyvsp[-1].InstrSeq), "ifelse"); }
#line 1743 "y.tab.c" /* yacc.c:1646  */
    break;

  case 56:
#line 163 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.InstrSeq) = cond_eval((yyvsp[-4].ExprRes), (yyvsp[-1].InstrSeq), NULL, "while"); }
#line 1749 "y.tab.c" /* yacc.c:1646  */
    break;

  case 57:
#line 165 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1755 "y.tab.c" /* yacc.c:1646  */
    break;

  case 58:
#line 167 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = relat_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), "or"); }
#line 1761 "y.tab.c" /* yacc.c:1646  */
    break;

  case 59:
#line 168 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1767 "y.tab.c" /* yacc.c:1646  */
    break;

  case 60:
#line 170 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = relat_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), "and"); }
#line 1773 "y.tab.c" /* yacc.c:1646  */
    break;

  case 61:
#line 171 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1779 "y.tab.c" /* yacc.c:1646  */
    break;

  case 62:
#line 173 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = relat_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), "seq"); }
#line 1785 "y.tab.c" /* yacc.c:1646  */
    break;

  case 63:
#line 174 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = relat_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), "sne"); }
#line 1791 "y.tab.c" /* yacc.c:1646  */
    break;

  case 64:
#line 175 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1797 "y.tab.c" /* yacc.c:1646  */
    break;

  case 65:
#line 177 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = relat_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), (yyvsp[-1].string)); }
#line 1803 "y.tab.c" /* yacc.c:1646  */
    break;

  case 66:
#line 178 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "slt"; }
#line 1809 "y.tab.c" /* yacc.c:1646  */
    break;

  case 67:
#line 179 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "sgt"; }
#line 1815 "y.tab.c" /* yacc.c:1646  */
    break;

  case 68:
#line 180 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "sle"; }
#line 1821 "y.tab.c" /* yacc.c:1646  */
    break;

  case 69:
#line 181 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "sge"; }
#line 1827 "y.tab.c" /* yacc.c:1646  */
    break;

  case 70:
#line 183 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1833 "y.tab.c" /* yacc.c:1646  */
    break;

  case 71:
#line 185 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = arith_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), (yyvsp[-1].string)); }
#line 1839 "y.tab.c" /* yacc.c:1646  */
    break;

  case 72:
#line 186 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1845 "y.tab.c" /* yacc.c:1646  */
    break;

  case 73:
#line 187 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "add"; }
#line 1851 "y.tab.c" /* yacc.c:1646  */
    break;

  case 74:
#line 188 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "sub"; }
#line 1857 "y.tab.c" /* yacc.c:1646  */
    break;

  case 75:
#line 189 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = arith_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), (yyvsp[-1].string)); }
#line 1863 "y.tab.c" /* yacc.c:1646  */
    break;

  case 76:
#line 190 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1869 "y.tab.c" /* yacc.c:1646  */
    break;

  case 77:
#line 191 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "mul"; }
#line 1875 "y.tab.c" /* yacc.c:1646  */
    break;

  case 78:
#line 192 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "div"; }
#line 1881 "y.tab.c" /* yacc.c:1646  */
    break;

  case 79:
#line 193 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = "mod"; }
#line 1887 "y.tab.c" /* yacc.c:1646  */
    break;

  case 80:
#line 195 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = arith_eval((yyvsp[-2].ExprRes), (yyvsp[0].ExprRes), "exp"); }
#line 1893 "y.tab.c" /* yacc.c:1646  */
    break;

  case 81:
#line 196 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1899 "y.tab.c" /* yacc.c:1646  */
    break;

  case 82:
#line 198 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = arith_eval((yyvsp[0].ExprRes), NULL, "neg"); }
#line 1905 "y.tab.c" /* yacc.c:1646  */
    break;

  case 83:
#line 199 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = relat_eval((yyvsp[0].ExprRes), NULL, "not"); }
#line 1911 "y.tab.c" /* yacc.c:1646  */
    break;

  case 84:
#line 200 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1917 "y.tab.c" /* yacc.c:1646  */
    break;

  case 85:
#line 202 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[0].ExprRes); }
#line 1923 "y.tab.c" /* yacc.c:1646  */
    break;

  case 86:
#line 203 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = (yyvsp[-1].ExprRes); }
#line 1929 "y.tab.c" /* yacc.c:1646  */
    break;

  case 87:
#line 204 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = rval_eval((yyvsp[-3].string), (yyvsp[-1].ExprRes)); }
#line 1935 "y.tab.c" /* yacc.c:1646  */
    break;

  case 88:
#line 205 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = function_call_eval((yyvsp[-3].string), (yyvsp[-1].ExprResList)); }
#line 1941 "y.tab.c" /* yacc.c:1646  */
    break;

  case 89:
#line 206 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = function_call_eval((yyvsp[-2].string), NULL); }
#line 1947 "y.tab.c" /* yacc.c:1646  */
    break;

  case 90:
#line 208 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = lit_eval(yylval.string, INT); }
#line 1953 "y.tab.c" /* yacc.c:1646  */
    break;

  case 91:
#line 209 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = lit_eval(yylval.string, BOOL); }
#line 1959 "y.tab.c" /* yacc.c:1646  */
    break;

  case 92:
#line 210 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = lit_eval(yylval.string, FLOAT); }
#line 1965 "y.tab.c" /* yacc.c:1646  */
    break;

  case 93:
#line 211 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = lit_eval(yylval.string, STRING); }
#line 1971 "y.tab.c" /* yacc.c:1646  */
    break;

  case 94:
#line 212 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.ExprRes) = rval_eval(yylval.string, NULL); }
#line 1977 "y.tab.c" /* yacc.c:1646  */
    break;

  case 95:
#line 214 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = yylval.string; }
#line 1983 "y.tab.c" /* yacc.c:1646  */
    break;

  case 96:
#line 215 "ExprEval.y" /* yacc.c:1646  */
    { (yyval.string) = yylval.string; }
#line 1989 "y.tab.c" /* yacc.c:1646  */
    break;


#line 1993 "y.tab.c" /* yacc.c:1646  */
      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = (char *) YYSTACK_ALLOC (yymsg_alloc);
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYTERROR;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined yyoverflow || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  return yyresult;
}
#line 216 "ExprEval.y" /* yacc.c:1906  */


int yyerror(char *s)  {
  WriteIndicator(GetCurrentColumn());
  WriteMessage("Illegal Character in YACC");
  return 1;
}
