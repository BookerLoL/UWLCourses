/* Semantics.c
   Support and semantic action routines.
   
*/
#include <stdlib.h>
#include <string.h>
#include "CodeGen.h"
#include "Semantics.h"
#include "SymTab.h"
#include "IOMngr.h"

extern struct SymTab *syms;
extern struct SymTab *funcs;
extern struct SymEntry *entry;

//Print labels
static char *TRUE_LABEL = "_t";
static char *FALSE_LABEL = "_f";
static char *SPACE_LABEL = "_s";
static char *NUM_SPACE_LABEL = "_sp";
static char *EMPTY_LABEL = "_e";
static char *NEW_LINE_LABEL = "_nl";
static char *INPUT_LABEL = "input";
static char *STR_LABEL = "str_";
static char *FLOAT_LABEL = "fl_";
static char *FLOAT_ZERO_LABEL = "fl_zero";
static char *FLOAT_ONE_LABEL = "fl_one";

int space_flag = 0;
int no_delim_flag = 0;
int new_line_flag = 0;
int bool_flag = 0;
int input_flag = 0;
int zero_float_flag = 0;
int one_float_flag = 0;

struct InstrSeq *strings_intern = NULL;
static int str_label = 0;

struct InstrSeq *floats_intern = NULL;
static int float_label = 0;

struct InstrSeq *func_call_resotre = NULL;

static char *INPUT_SIZE = "100";

static enum expr_type function_type = BOOL;
int func_var_index = 1;
static char *func_name;
int save_offset = 0;
int is_recursive_flag = 1;

int temp_reg_used[MAX_TEMP_REG] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
int float_reg_used[MAX_FLOAT_REG] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                     0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                     0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


void error(char *msg)
{
  WriteIndicator(GetCurrentColumn());
  WriteMessage(msg);
}
int get_and_incr_func_index() { return func_var_index++; }

int is_index_valid(enum expr_type idx_type) { return idx_type == INT || idx_type == BOOL; }

enum register_type get_reg_type(enum expr_type type) { return type == FLOAT || type == FLOAT_ARRAY ? FLOAT_REG : TEMP_REG; }


int get_highest_index_var()
{
  int highest = 0;
  entry = FirstEntry(funcs);

  while (entry)
  {
    struct TypeAttr *attribute = (struct TypeAttr *)entry->Attributes;

    if (attribute->index > highest)
    {
      highest = attribute->index;
    }

    entry = NextEntry(funcs, entry);
  }

  return highest;
}

void need_zero_float_label() { zero_float_flag = 1; }
void need_one_float_label() { one_float_flag = 1; }

int find_entry(char *name)
{
  entry = NULL;

  if (funcs && (entry = FindName(funcs, name)))
  {
    return 1;
  }

  if (!(entry = FindName(syms, name)))
  {
    error("Variable not found");
  }

  return 0;
}

int get_sizeof(enum expr_type var_type) { return var_type <= STRING ? 1 : 4; }

int get_function_type(enum expr_type type)
{
  if (type == BOOL) {
    return BOOL_ARRAY;
  } else if (type == INT) {
    return INT_ARRAY;
  } else if (type == FLOAT) {
    return FLOAT_ARRAY;
  } else {
    return type;
  }
}

enum expr_type get_expr_type(char *type, int arr_flag)
{
  enum expr_type t = -1;

  if (!arr_flag)
  {
    if (strcmp(type, "int") == 0) {
      t = INT;

    }
    else if (strcmp(type, "boolean") == 0) {
      t = BOOL;

    }
    else if (strcmp(type, "float") == 0) {
      t = FLOAT;

    }
    else {
      t = STRING;

    }
  }
  else
  {
     if (strcmp(type, "int") == 0) {
      t = INT_ARRAY;

    }
    else if (strcmp(type, "boolean") == 0) {
      t = BOOL_ARRAY;

    }
    else if (strcmp(type, "float") == 0) {
      t = FLOAT_ARRAY;
    }
  }

  return t;
}

struct VarSignature *new_var_signature(char *name, char *size)
{
  struct VarSignature *signature = (struct VarSignature *)malloc(sizeof(struct VarSignature));
  if (name) {
    signature->name = strdup(name);
  } else {
    signature->name = NULL;
  } 
  if (size) {
    signature->size = atoi(size);

  } else {
    signature->size = 0;
  }

  return signature;
}

struct TypeAttr *new_type_attr(enum expr_type type, int size)
{
  struct TypeAttr *attribute = (struct TypeAttr *)malloc(sizeof(struct TypeAttr));

  attribute->type = type;
  attribute->size = size;
  attribute->is_addr = 0;
  attribute->index = 0;
  attribute->params = NULL;
  attribute->instrs = NULL;

  return attribute;
}

void var_eval(char *type, struct VarSignature *var_sig)
{
  enum expr_type var_type = get_expr_type(type, var_sig->size);
  struct TypeAttr *attribute = new_type_attr(var_type, var_sig->size * get_sizeof(var_type));

  if (funcs)
  {
    EnterName(funcs, var_sig->name, &entry);
    attribute->index = get_and_incr_func_index();
  }
  else
  {
    EnterName(syms, var_sig->name, &entry);
  }

  SetAttr(entry, attribute);
  entry = NULL;
}

char *get_tag(char *name, int is_float)
{
  struct InstrSeq *intern_seq = is_float ? floats_intern : strings_intern;

  while (intern_seq)
  {
    if (strcmp(intern_seq->oper_1, name) == 0)
    {
      return intern_seq->label;
    }

    intern_seq = intern_seq->next;
  }

  return NULL;
}

void intern_symbol(char *name, char *value, int is_float)
{
  if (is_float)
  {
    floats_intern = append_seq(floats_intern, gen_instr(name, ".float", value, NULL, NULL));
  }
  else
  {
    strings_intern = append_seq(strings_intern, gen_instr(name, ".asciiz", value, NULL, NULL));
  }
}

char *gen_str_name(char *name)
{
  char *str_name = NULL, *str_num = (char *)malloc(50 * sizeof(char));

  if (strcmp(name, STR_LABEL) == 0)
  {
    str_name = (char *)malloc(50 * sizeof(char));
    sprintf(str_name, "%s%d", STR_LABEL, str_label++);
  }
  else if (strcmp(name, FLOAT_LABEL) == 0)
  {
    str_name = (char *)malloc(50 * sizeof(char));
    sprintf(str_name, "%s%d", FLOAT_LABEL, float_label++);
  }
  else
  {
    str_name = strdup(name);
  }

  free(str_num);
  return str_name;
}

struct ExprRes *new_expr_res(enum expr_type t, char *name)
{
  struct ExprRes *res = (struct ExprRes *)malloc(sizeof(struct ExprRes));

  res->type = t;
  res->reg = get_avail_reg(get_reg_type(t));
  res->instrs = NULL;
  if (name) {
    res->name = strdup(name);
  } else {
    res->name = NULL;
  }

  return res;
}

struct ExprResList *new_expr_res_list(struct ExprRes *expr_res, struct ExprResList *next_expr_seq, int print_flag)
{
  struct ExprResList *expr_list = (struct ExprResList *)malloc(sizeof(struct ExprResList));
  expr_list->expr = expr_res;
  expr_list->next = next_expr_seq;

  if (print_flag)
  {
    expr_list->expr->instrs = append_seq(print_eval(expr_res, SPACE_LABEL, 0), NULL);
  }

  return expr_list;
}

void release_and_free(struct ExprRes *res)
{
  if (res)
  {
    release_reg(res->reg, get_reg_type(res->type));
    free(res);
  }
}

int is_narrowing(enum expr_type lhs, enum expr_type rhs) { return lhs < rhs; }

int is_widening(enum expr_type lhs, enum expr_type rhs) { return (lhs == FLOAT && rhs < lhs) || (rhs == FLOAT && lhs < rhs); }

struct ExprRes *cast(struct ExprRes *value, enum expr_type cast_type)
{
  if (value->type == cast_type) { return value; }

  int reg_type = get_reg_type(cast_type), reg = get_avail_reg(reg_type);

  if (cast_type == FLOAT)
  {
    append_seq(value->instrs, gen_instr(NULL, "mtc1", get_reg_name(value->reg, get_reg_type(value->type)), get_reg_name(reg, reg_type), NULL)); //T -> F
    append_seq(value->instrs, gen_instr(NULL, "cvt.s.w", get_reg_name(reg, reg_type), get_reg_name(reg, reg_type), NULL));                      //F -> F
  }
  else if (cast_type == INT && value->type == FLOAT)
  {
    append_seq(value->instrs, gen_instr(NULL, "cvt.w.s", get_reg_name(value->reg, get_reg_type(value->type)), get_reg_name(value->reg, get_reg_type(value->type)), NULL)); // F -> F
    append_seq(value->instrs, gen_instr(NULL, "mfc1", get_reg_name(reg, reg_type), get_reg_name(value->reg, get_reg_type(value->type)), NULL));                            // F -> T
  }
  else
  {
    if (value->type = FLOAT)
    {
      append_seq(value->instrs, gen_instr(NULL, "cvt.w.s", get_reg_name(value->reg, get_reg_type(value->type)), get_reg_name(value->reg, get_reg_type(value->type)), NULL)); // F -> F
      append_seq(value->instrs, gen_instr(NULL, "mfc1", get_reg_name(reg, reg_type), get_reg_name(value->reg, get_reg_type(value->type)), NULL));                            // F -> T
      append_seq(value->instrs, gen_instr(NULL, "sne", get_reg_name(reg, reg_type), get_reg_name(reg, reg_type), "0"));
    }
    else
    {
      append_seq(value->instrs, gen_instr(NULL, "sne", get_reg_name(reg, reg_type), get_reg_name(value->reg, get_reg_type(value->type)), "0"));
    }
  }

  release_reg(value->reg, get_reg_type(value->type));
  value->reg = reg;
  value->type = cast_type;
  return value;
}

struct ExprRes *lit_eval(char *input, enum expr_type type)
{
  struct ExprRes *res = new_expr_res(type, NULL);
  char *val = strdup(input), *name;

  switch (type)
  {
  case INT:
    res->instrs = gen_instr(NULL, "li", get_reg_name(res->reg, TEMP_REG), val, NULL);
    break;

  case BOOL:
    res->instrs = gen_instr(NULL, "li", get_reg_name(res->reg, TEMP_REG), strcmp(input, "false") == 0 ? "0" : "1", NULL);
    break;

  case FLOAT:
    name = get_tag(val, 1);

    if (!name)
    {
      name = gen_str_name(FLOAT_LABEL);
      intern_symbol(name, val, 1);
    }

    res->instrs = gen_instr(NULL, "lwc1", get_reg_name(res->reg, FLOAT_REG), name, NULL);
    break;

  case STRING:
    name = get_tag(val, 0);

    if (!name)
    {
      name = gen_str_name(STR_LABEL);
      intern_symbol(name, val, 0);
      EnterName(syms, name, &entry);

      if (!entry->Attributes)
      {
        struct TypeAttr *entry_attr = new_type_attr(STRING, 0);
        entry->Attributes = entry_attr;
      }
    }
    else
    {
      error("Invalid Value!");
    }

    struct InstrSeq *code = gen_instr(NULL, "li", "$v0", "4", NULL);
    append_seq(code, gen_instr(NULL, "la", "$a0", name, NULL));
    append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));
    res->instrs = code;
    break;
  }

  free(val);
  return res;
}

enum expr_type get_atomic_type(enum expr_type type)
{
  switch (type)
  {
  case BOOL_FUNCTION:
  case BOOL_ARRAY:
    return BOOL;

  case INT_FUNCTION:
  case INT_ARRAY:
    return INT;

  case FLOAT_FUNCTION:
  case FLOAT_ARRAY:
    return FLOAT;


  default:
    return type;
  }
}

char *gen_paren_label(char *label)
{
  char *paren_label = (char *)malloc(sizeof(char) * (strlen(label) + 3));
  sprintf(paren_label, "(%s)", label);
  return paren_label;
}

struct ExprRes *rval_eval(char *name, struct ExprRes *index)
{
  struct ExprRes *res = NULL;
  int is_func = find_entry(name);
  struct SymEntry *sym_entry = entry;

  if (!sym_entry)
  {
    return NULL;
  }

  struct TypeAttr *var_attribute = (struct TypeAttr *)sym_entry->Attributes;
  enum expr_type var_type = get_atomic_type(var_attribute->type);
  res = new_expr_res(get_atomic_type(var_attribute->type), name);

  if (is_func)
  {
    find_entry(func_name);

    int offset = 0;
    struct TypeAttr *func_attr = (struct TypeAttr *)entry->Attributes;
    struct FunctionParams *param_list = func_attr->params;
    char stack_name[100];

    if (param_list && var_attribute->index <= param_list->index)
    {
      offset = save_offset + var_attribute->index * 4;
    }
    else
    {
      offset = (param_list ? (var_attribute->index - param_list->index) : var_attribute->index) * 4;
    }

    sprintf(stack_name, "%d($sp)", offset);

    if (!index)
    {
      if (var_attribute->is_addr)
      {
        res->instrs = append_seq(res->instrs, gen_instr(NULL, "la", get_reg_name(res->reg, get_reg_type(var_attribute->type)), stack_name, NULL));
      }
      else
      {
        res->instrs = append_seq(res->instrs, gen_instr(NULL, var_attribute->type < FLOAT ? "lw" : "l.s", get_reg_name(res->reg, get_reg_type(var_attribute->type)), stack_name, NULL));
      }
    }
    else
    {
      res->instrs = append_seq(res->instrs, index->instrs);
      append_seq(res->instrs, gen_instr(NULL, "sll", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), "2"));

      if (!var_attribute->is_addr)
      {
        sprintf(stack_name, "%d", offset);
        append_seq(res->instrs, gen_instr(NULL, "addi", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), stack_name));
        append_seq(res->instrs, gen_instr(NULL, "add", get_reg_name(index->reg, TEMP_REG), "$sp", get_reg_name(index->reg, TEMP_REG))); // maybe subu or little tricky math
        char *label = gen_paren_label(get_reg_name(index->reg, TEMP_REG));
        append_seq(res->instrs, gen_instr(NULL, var_type < FLOAT ? "lw" : "l.s", get_reg_name(res->reg, get_reg_type(var_attribute->type)), label, NULL));
      }
      else
      {
        enum register_type array_addr = get_avail_reg(TEMP_REG);
        append_seq(res->instrs, gen_instr(NULL, "lw", get_reg_name(array_addr, TEMP_REG), stack_name, NULL));
        sprintf(stack_name, "%d", offset);
        append_seq(res->instrs, gen_instr(NULL, "add", get_reg_name(array_addr, TEMP_REG), get_reg_name(array_addr, TEMP_REG), get_reg_name(index->reg, TEMP_REG)));
        char *label = gen_paren_label(get_reg_name(array_addr, TEMP_REG));
        append_seq(res->instrs, gen_instr(NULL, var_type < FLOAT ? "lw" : "l.s", get_reg_name(res->reg, get_reg_type(var_attribute->type)), label, NULL));
        release_reg(array_addr, TEMP_REG);
      }
    }
  }
  else
  {
    if (index)
    {
      res->instrs = index->instrs; 
      if (index->type <= INT)
      {
        if (res->type < FLOAT)
        {
          char *label = gen_paren_label(get_reg_name(res->reg, TEMP_REG));

          append_seq(res->instrs, gen_instr(NULL, "la", get_reg_name(res->reg, TEMP_REG), sym_entry->Name, NULL));
          append_seq(res->instrs, gen_instr(NULL, "sll", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), "2")); //i * 4
          append_seq(res->instrs, gen_instr(NULL, "add", get_reg_name(res->reg, TEMP_REG), get_reg_name(res->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG)));
          append_seq(res->instrs, gen_instr(NULL, "lw", get_reg_name(res->reg, TEMP_REG), label, NULL));

          free(label);
        }
        else
        {
          int temp_reg = get_avail_reg(TEMP_REG); 
          char *label = gen_paren_label(get_reg_name(temp_reg, TEMP_REG));

          append_seq(res->instrs, gen_instr(NULL, "la", get_reg_name(temp_reg, TEMP_REG), sym_entry->Name, NULL));
          append_seq(res->instrs, gen_instr(NULL, "sll", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), "2")); //i * 4
          append_seq(res->instrs, gen_instr(NULL, "add", get_reg_name(temp_reg, TEMP_REG), get_reg_name(temp_reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG)));
          append_seq(res->instrs, gen_instr(NULL, "l.s", get_reg_name(res->reg, FLOAT_REG), label, NULL));

          release_reg(temp_reg, TEMP_REG);
          free(label);
        }
      }
      else
      {
        error("Index is not a valid integer");
      }
    }
    else
    {
      if (var_attribute->size > 0)
      {
        res->instrs = gen_instr(NULL, "la", get_reg_name(res->reg, TEMP_REG), name, NULL);
      }
      else
      {
        res->instrs = gen_instr(NULL, res->type == FLOAT ? "lwc1" : "lw", get_reg_name(res->reg, get_reg_type(res->type)), name, NULL);
      }
    }
  }
  if (index)
  {
    release_and_free(index);
  }
  return res;
}

int is_numbered_space_label(char *delim) { return strcmp(delim, NUM_SPACE_LABEL) == 0; }
int is_space_label(char *delim) { return strcmp(delim, SPACE_LABEL) == 0; }
int is_empty_label(char *delim) { return strcmp(delim, EMPTY_LABEL) == 0; }
int is_new_line_label(char *delim) { return strcmp(delim, NEW_LINE_LABEL) == 0; }
int is_str_label(char *delim) { return strcmp(delim, STR_LABEL) == 0; }

void update_delim_flag(char *delim)
{
  if (is_empty_label(delim)) {
    no_delim_flag = 1;
  }
  else if (is_new_line_label(delim)) {
    new_line_flag = 1;
  }
  else if (is_space_label(delim) || is_numbered_space_label(delim)) {
    space_flag = 1;
  }
}

struct InstrSeq *print_new_line()
{
  struct InstrSeq *code = gen_instr(NULL, "li", "$v0", "4", NULL);
  append_seq(code, gen_instr(NULL, "la", "$a0", NEW_LINE_LABEL, NULL));
  append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));

  return code;
}

struct InstrSeq *print_eval(struct ExprRes *expr, char *delim, int free_flag)
{
  struct InstrSeq *code = NULL;
  update_delim_flag(delim);

  if (expr)
  {
    code = expr->instrs;

    if (is_numbered_space_label(delim) && is_index_valid(expr->type))
    {
      enum register_type reg_type = get_reg_type(expr->type);
      int temp_reg = get_avail_reg(reg_type);
      char *finish_label = gen_label(), *while_label = gen_label();

      append_seq(code, gen_instr(NULL, "sgt", get_reg_name(temp_reg, reg_type), get_reg_name(expr->reg, reg_type), "0"));
      append_seq(code, gen_instr(NULL, "beq", get_reg_name(temp_reg, reg_type), "$zero", finish_label));
      append_seq(code, gen_instr(while_label, "beq", get_reg_name(expr->reg, reg_type), "$zero", finish_label));
      append_seq(code, gen_instr(NULL, "li", "$v0", "4", NULL));
      append_seq(code, gen_instr(NULL, "la", "$a0", SPACE_LABEL, NULL));
      append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));
      append_seq(code, gen_instr(NULL, "addi", get_reg_name(expr->reg, reg_type), get_reg_name(expr->reg, reg_type), "-1"));
      append_seq(code, gen_instr(NULL, "j", while_label, NULL, NULL));
      append_seq(code, gen_instr(finish_label, NULL, NULL, NULL, NULL));

      free(finish_label);
      free(while_label);

      release_reg(temp_reg, reg_type);
    }
    else if (!is_str_label(delim))
    {
      if (expr->type == INT)
      {
        append_seq(code, gen_instr(NULL, "li", "$v0", "1", NULL));
        append_seq(code, gen_instr(NULL, "move", "$a0", get_reg_name(expr->reg, get_reg_type(expr->type)), NULL));
        append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));
      }
      else if (expr->type == BOOL)
      {
        bool_flag = 1;
        char *false_label = gen_label();
        char *finish_label = gen_label();
        append_seq(code, gen_instr(NULL, "li", "$v0", "4", NULL));
        append_seq(code, gen_instr(NULL, "beq", get_reg_name(expr->reg, get_reg_type(expr->type)), "$zero", false_label));
        append_seq(code, gen_instr(NULL, "la", "$a0", TRUE_LABEL, NULL));
        append_seq(code, gen_instr(NULL, "j", finish_label, NULL, NULL));
        append_seq(code, gen_instr(false_label, "la", "$a0", FALSE_LABEL, NULL));
        append_seq(code, gen_instr(finish_label, "syscall", NULL, NULL, NULL));

        free(false_label);
        free(finish_label);
      }
      else
      {
        append_seq(code, gen_instr(NULL, "li", "$v0", "2", NULL));
        append_seq(code, gen_instr(NULL, "mov.s", "$f12", get_reg_name(expr->reg, get_reg_type(expr->type)), NULL));
        append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));
      }

      append_seq(code, gen_instr(NULL, "li", "$v0", "4", NULL));
      append_seq(code, gen_instr(NULL, "la", "$a0", delim, NULL));
      append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));
    }

    release_reg(expr->reg, get_reg_type(expr->type));

    if (free_flag)
    {
      free(expr);
    }
  }
  else
  {
    if (is_new_line_label(delim))
    {
      code = print_new_line();
    }
  }

  return code;
}

struct InstrSeq *assign_eval(char *name, struct ExprRes *value, struct ExprRes *index)
{
  struct InstrSeq *code = NULL;

  if (value)
  {
    int is_func = find_entry(name);
    struct SymEntry *var = entry;
    char *var_name = var->Name;
    struct TypeAttr *var_attribute = (struct TypeAttr *)(var->Attributes);
    enum expr_type var_type = get_atomic_type(var_attribute->type);

    if (is_widening(var_type, value->type) || is_narrowing(var_type, value->type))
    {
      value = cast(value, var_type);
    }

    code = value->instrs;

    if (is_func)
    {
      find_entry(func_name);

      int offset = 0;
      struct TypeAttr *func_attr = (struct TypeAttr *)entry->Attributes;
      struct FunctionParams *param_list = func_attr->params;
      char stack_name[100];

      if (param_list && var_attribute->index <= param_list->index)
      {
        offset = save_offset + var_attribute->index * 4;
      }
      else
      {
        offset = (param_list ? (var_attribute->index - param_list->index) : var_attribute->index) * 4;
      }

      sprintf(stack_name, "%d($sp)", offset);

      if (!index)
      {
        append_seq(code, gen_instr(NULL, var_type < FLOAT ? "sw" : "s.s", get_reg_name(value->reg, get_reg_type(var_type)), stack_name, NULL));
      }
      else
      {
        append_seq(code, index->instrs);
        append_seq(code, gen_instr(NULL, "sll", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), "2"));

        if (!var_attribute->is_addr)
        {
          sprintf(stack_name, "%d", offset);
          append_seq(code, gen_instr(NULL, "addi", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), stack_name));
          append_seq(code, gen_instr(NULL, "add", get_reg_name(index->reg, TEMP_REG), "$sp", get_reg_name(index->reg, TEMP_REG))); // maybe subu or little tricky math
          char *label = gen_paren_label(get_reg_name(index->reg, TEMP_REG));
          append_seq(code, gen_instr(NULL, var_type < FLOAT ? "sw" : "s.s", get_reg_name(value->reg, get_reg_type(var_type)), label, NULL));
        }
        else
        {
          enum register_type array_addr = get_avail_reg(TEMP_REG);
          append_seq(code, gen_instr(NULL, "lw", get_reg_name(array_addr, TEMP_REG), stack_name, NULL));
          sprintf(stack_name, "%d", offset);
          append_seq(code, gen_instr(NULL, "add", get_reg_name(array_addr, TEMP_REG), get_reg_name(array_addr, TEMP_REG), get_reg_name(index->reg, TEMP_REG)));
          char *label = gen_paren_label(get_reg_name(array_addr, TEMP_REG));
          append_seq(code, gen_instr(NULL, var_type < FLOAT ? "sw" : "s.s", get_reg_name(value->reg, get_reg_type(var_type)), label, NULL));
          release_reg(array_addr, TEMP_REG);
        }
      }
    }
    else
    {
      if (index)
      {
        if (is_index_valid(index->type))
        {
          code = append_seq(index->instrs, code);
          int reg = get_avail_reg(TEMP_REG);

          append_seq(code, gen_instr(NULL, "la", get_reg_name(reg, TEMP_REG), var->Name, NULL));                             //la $t0 array
          append_seq(code, gen_instr(NULL, "sll", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), "2")); //i * 4
          append_seq(code, gen_instr(NULL, "add", get_reg_name(reg, TEMP_REG), get_reg_name(reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG)));

          char *label = gen_paren_label(get_reg_name(reg, TEMP_REG));
          append_seq(code, gen_instr(NULL, var_type < FLOAT ? "sw" : "s.s", get_reg_name(value->reg, get_reg_type(var_type)), label, NULL));

          free(label);
          release_reg(reg, TEMP_REG);
        }
        else
        {
          error("Index is not an valid integer value");
        }
      }
      else
      {
        append_seq(code, gen_instr(NULL, value->type == FLOAT ? "swc1" : "sw", get_reg_name(value->reg, get_reg_type(value->type)), var->Name, NULL));
      }
    }

    release_and_free(value);
    if (index)
    {
      release_and_free(index);
    }
  }

  return code;
}

struct InstrSeq *cond_eval(struct ExprRes *res, struct InstrSeq *seq1, struct InstrSeq *seq2, char *op)
{
  struct InstrSeq *code = NULL;
  char *else_label = gen_label();

  if ((strcmp(op, "if") == 0) || (strcmp(op, "ifelse") == 0))
  {
    code = append_seq(res->instrs, gen_instr(NULL, "beq", get_reg_name(res->reg, TEMP_REG), "0", else_label));
    append_seq(code, seq1);

    if (strcmp(op, "ifelse") == 0)
    {
      char *finish_label = gen_label();

      append_seq(code, gen_instr(NULL, "j", finish_label, NULL, NULL));
      append_seq(code, gen_instr(else_label, NULL, NULL, NULL, NULL));
      append_seq(code, seq2);
      append_seq(code, gen_instr(finish_label, NULL, NULL, NULL, NULL));

      free(finish_label);
    }
    else
    {
      append_seq(code, gen_instr(else_label, NULL, NULL, NULL, NULL));
    }
  }
  else if (strcmp(op, "while") == 0)
  {
    char *while_label = gen_label();

    code = gen_instr(while_label, NULL, NULL, NULL, NULL);
    append_seq(code, res->instrs);
    append_seq(code, gen_instr(NULL, "beq", get_reg_name(res->reg, TEMP_REG), "0", else_label));
    append_seq(code, seq1);
    append_seq(code, gen_instr(NULL, "j", while_label, NULL, NULL));
    append_seq(code, gen_instr(else_label, NULL, NULL, NULL, NULL));

    free(while_label);
  }

  free(else_label);
  release_and_free(res);

  return code;
}

struct ExprRes *normal_relat_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op)
{
  int reg = get_avail_reg(TEMP_REG);

  if (strcmp(op, "or") == 0)
  {
    append_seq(res_1->instrs, gen_instr(NULL, "or", get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), get_reg_name(res_2->reg, TEMP_REG)));
    append_seq(res_1->instrs, gen_instr(NULL, "sne", get_reg_name(reg, TEMP_REG), get_reg_name(reg, TEMP_REG), "0"));
  }
  else if (strcmp(op, "and") == 0)
  {
    append_seq(res_1->instrs, gen_instr(NULL, "sne", get_reg_name(res_1->reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), "0"));
    append_seq(res_1->instrs, gen_instr(NULL, "sne", get_reg_name(res_2->reg, TEMP_REG), get_reg_name(res_2->reg, TEMP_REG), "0"));
    append_seq(res_1->instrs, gen_instr(NULL, "and", get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), get_reg_name(res_2->reg, TEMP_REG)));
  }
  else if (strcmp(op, "not") == 0)
  {
    append_seq(res_1->instrs, gen_instr(NULL, "seq", get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), "0"));
  }
  else
  {
    append_seq(res_1->instrs, gen_instr(NULL, op, get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), get_reg_name(res_2->reg, TEMP_REG)));
  }

  release_reg(res_1->reg, TEMP_REG);
  res_1->reg = reg;
  res_1->type = BOOL;

  if (res_2)
  {
    release_reg(res_2->reg, TEMP_REG);
    free(res_2);
  }

  return res_1;
}

struct ExprRes *float_relat_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op)
{
  int result_reg = get_avail_reg(TEMP_REG);

  if (strcmp(op, "or") == 0)
  {
    char *true_label = gen_label(), *finish_label = gen_label();
    need_zero_float_label();
    int zero_float_reg = get_avail_reg(FLOAT_REG);

    append_seq(res_1->instrs, gen_instr(NULL, "lwc1", get_reg_name(zero_float_reg, FLOAT_REG), FLOAT_ZERO_LABEL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(zero_float_reg, FLOAT_REG), NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "bc1f", true_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(zero_float_reg, FLOAT_REG), NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "bc1f", true_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "li", get_reg_name(result_reg, TEMP_REG), "0", NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "j", finish_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(true_label, "li", get_reg_name(result_reg, TEMP_REG), "1", NULL));
    append_seq(res_1->instrs, gen_instr(finish_label, NULL, NULL, NULL, NULL));

    free(true_label);
    free(finish_label);
    release_reg(zero_float_reg, FLOAT_REG);
  }
  else if (strcmp(op, "and") == 0)
  {
    char *false_label = gen_label(), *finish_label = gen_label();
    need_zero_float_label();
    int zero_float_reg = get_avail_reg(FLOAT_REG);

    append_seq(res_1->instrs, gen_instr(NULL, "lwc1", get_reg_name(zero_float_reg, FLOAT_REG), FLOAT_ZERO_LABEL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(zero_float_reg, FLOAT_REG), NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "bc1t", false_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(zero_float_reg, FLOAT_REG), NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "bc1t", false_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "li", get_reg_name(result_reg, TEMP_REG), "1", NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "j", finish_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(false_label, "li", get_reg_name(result_reg, TEMP_REG), "0", NULL));
    append_seq(res_1->instrs, gen_instr(finish_label, NULL, NULL, NULL, NULL));

    free(false_label);
    free(finish_label);
    release_reg(zero_float_reg, FLOAT_REG);
  }
  else if (strcmp(op, "not") == 0)
  {
    char *true_label = gen_label(), *finish_label = gen_label();
    need_zero_float_label();
    int zero_float_reg = get_avail_reg(FLOAT_REG);

    append_seq(res_1->instrs, gen_instr(NULL, "lwc1", get_reg_name(zero_float_reg, FLOAT_REG), FLOAT_ZERO_LABEL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(zero_float_reg, FLOAT_REG), NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "bc1t", true_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "li", get_reg_name(result_reg, TEMP_REG), "0", NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "j", finish_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(true_label, "li", get_reg_name(result_reg, TEMP_REG), "1", NULL));
    append_seq(res_1->instrs, gen_instr(finish_label, NULL, NULL, NULL, NULL));

    free(true_label);
    free(finish_label);
    release_reg(zero_float_reg, FLOAT_REG);
  }
  else
  {
    char *true_label = gen_label(), *finish_label = gen_label();
    int bc1t_flag = 1;

    if (strcmp("seq", op) == 0)
    {
      append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG), NULL));
    }
    else if (strcmp("sne", op) == 0)
    {
      bc1t_flag = 0;
      append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG), NULL));
    }
    else if (strcmp("slt", op) == 0)
    {
      append_seq(res_1->instrs, gen_instr(NULL, "c.lt.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG), NULL));
    }
    else if (strcmp("sle", op) == 0)
    {
      append_seq(res_1->instrs, gen_instr(NULL, "c.le.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG), NULL));
    }
    else if (strcmp("sgt", op) == 0)
    {
      append_seq(res_1->instrs, gen_instr(NULL, "c.lt.s", get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG), NULL));
    }
    else
    {
      append_seq(res_1->instrs, gen_instr(NULL, "c.le.s", get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG), NULL));
    }

    append_seq(res_1->instrs, gen_instr(NULL, bc1t_flag ? "bc1t" : "bclf", true_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "li", get_reg_name(result_reg, TEMP_REG), "0", NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "j", finish_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(true_label, "li", get_reg_name(result_reg, TEMP_REG), "1", NULL));
    append_seq(res_1->instrs, gen_instr(finish_label, NULL, NULL, NULL, NULL));
  }

  release_reg(res_1->reg, FLOAT_REG);
  res_1->reg = result_reg;
  res_1->type = BOOL;

  if (res_2)
  {
    release_reg(res_2->reg, FLOAT_REG);
    free(res_2);
  }

  return res_1;
}

struct ExprRes *relat_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op)
{
  int float_relat_flag = 0;

  if (res_2)
  {
    append_seq(res_1->instrs, res_2->instrs);
  }

  if (res_2 && is_widening(res_1->type, res_2->type))
  {
    res_1 = cast(res_1, FLOAT);
    res_2 = cast(res_2, FLOAT);
    float_relat_flag = 1;
  }

  return float_relat_flag || res_1->type == FLOAT ? float_relat_eval(res_1, res_2, op) : normal_relat_eval(res_1, res_2, op);
}

struct ExprRes *normal_arith_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op)
{
  int reg = get_avail_reg(TEMP_REG);

  if (strcmp(op, "mod") == 0)
  {
    append_seq(res_1->instrs, gen_instr(NULL, "div", get_reg_name(res_1->reg, TEMP_REG), get_reg_name(res_2->reg, TEMP_REG), NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "mfhi", get_reg_name(reg, TEMP_REG), NULL, NULL));
  }
  else if (strcmp(op, "exp") == 0)
  {
    char *finish_label = gen_label(), *zero_power_label = gen_label(), *while_label = gen_label();

    append_seq(res_1->instrs, gen_instr(NULL, "beq", get_reg_name(res_2->reg, TEMP_REG), "0", zero_power_label)); //x^y, y == 0? go to zero label
    append_seq(res_1->instrs, gen_instr(NULL, "addi", get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), "0"));
    append_seq(res_1->instrs, gen_instr(while_label, "addi", get_reg_name(res_2->reg, TEMP_REG), get_reg_name(res_2->reg, TEMP_REG), "-1"));         //decrement power
    append_seq(res_1->instrs, gen_instr(NULL, "beq", get_reg_name(res_2->reg, TEMP_REG), "0", finish_label));                                        //t# == 0 ?
    append_seq(res_1->instrs, gen_instr(NULL, "mul", get_reg_name(reg, TEMP_REG), get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG))); //x * x
    append_seq(res_1->instrs, gen_instr(NULL, "j", while_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(zero_power_label, "addi", get_reg_name(reg, TEMP_REG), "$zero", "1")); //X ^ 0 = 1
    append_seq(res_1->instrs, gen_instr(finish_label, NULL, NULL, NULL, NULL));

    free(finish_label);
    free(zero_power_label);
    free(while_label);
  }
  else if (strcmp(op, "neg") == 0)
  {
    append_seq(res_1->instrs, gen_instr(NULL, "neg", get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), NULL));
  }
  else
  {
    append_seq(res_1->instrs, gen_instr(NULL, op, get_reg_name(reg, TEMP_REG), get_reg_name(res_1->reg, TEMP_REG), get_reg_name(res_2->reg, TEMP_REG)));
  }

  release_reg(res_1->reg, TEMP_REG);
  res_1->reg = reg;
  res_1->type = INT;

  if (res_2)
  {
    release_and_free(res_2);
  }

  return res_1;
}

struct ExprRes *float_arith_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op)
{
  int reg = get_avail_reg(FLOAT_REG); //float arith -> float

  if (strcmp(op, "mod") == 0)
  {
    char *while_label = gen_label(), *finish_label = gen_label();

    append_seq(res_1->instrs, gen_instr(while_label, "c.lt.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG), NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "bc1t", finish_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "sub.s", get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG)));
    append_seq(res_1->instrs, gen_instr(NULL, "j", while_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(finish_label, "mov.s", get_reg_name(reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG), NULL));

    free(while_label);
    free(finish_label);
  }
  else if (strcmp(op, "exp") == 0)
  { 
    need_zero_float_label();
    need_one_float_label();

    char *finish_label = gen_label(), *zero_power_label = gen_label(), *while_label = gen_label();
    int zero_reg = get_avail_reg(FLOAT_REG), one_reg = get_avail_reg(FLOAT_REG);

    append_seq(res_1->instrs, gen_instr(NULL, "lwc1", get_reg_name(zero_reg, FLOAT_REG), FLOAT_ZERO_LABEL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "lwc1", get_reg_name(one_reg, FLOAT_REG), FLOAT_ONE_LABEL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "c.eq.s", get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(zero_reg, FLOAT_REG), NULL)); //x^y, y == 0? go to zero label
    append_seq(res_1->instrs, gen_instr(NULL, "bc1t", zero_power_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "add.s", get_reg_name(reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(zero_reg, FLOAT_REG)));
    append_seq(res_1->instrs, gen_instr(while_label, "sub.s", get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(one_reg, FLOAT_REG)));
    append_seq(res_1->instrs, gen_instr(NULL, "c.le.s", get_reg_name(res_2->reg, FLOAT_REG), get_reg_name(zero_reg, FLOAT_REG), NULL)); //x^y, y == 0? go to zero label
    append_seq(res_1->instrs, gen_instr(NULL, "bc1t", finish_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(NULL, "mul.s", get_reg_name(reg, FLOAT_REG), get_reg_name(reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG))); //x * x
    append_seq(res_1->instrs, gen_instr(NULL, "j", while_label, NULL, NULL));
    append_seq(res_1->instrs, gen_instr(zero_power_label, "add.s", get_reg_name(reg, FLOAT_REG), get_reg_name(zero_reg, FLOAT_REG), get_reg_name(one_reg, FLOAT_REG))); //X ^ 0 = 1
    append_seq(res_1->instrs, gen_instr(finish_label, NULL, NULL, NULL, NULL));

    free(finish_label);
    free(zero_power_label);
    free(while_label);

    release_reg(zero_reg, FLOAT_REG);
    release_reg(one_reg, FLOAT_REG);
  }
  else if (strcmp(op, "neg") == 0)
  {
    append_seq(res_1->instrs, gen_instr(NULL, "neg.s", get_reg_name(reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG), NULL));
  }
  else
  {
    char *float_op = (char *)malloc(sizeof(char) * 7);
    sprintf(float_op, "%s%s", op, ".s");
    append_seq(res_1->instrs, gen_instr(NULL, float_op, get_reg_name(reg, FLOAT_REG), get_reg_name(res_1->reg, FLOAT_REG), get_reg_name(res_2->reg, FLOAT_REG)));
  }

  release_reg(res_1->reg, FLOAT_REG);
  res_1->reg = reg;
  res_1->type = FLOAT;

  if (res_2)
  {
    release_and_free(res_2);
  }

  return res_1;
}

struct ExprRes *arith_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op)
{
  int float_arith_flag = 0;

  if (res_2)
  {
    append_seq(res_1->instrs, res_2->instrs);
  }

  if (res_2 && is_widening(res_1->type, res_2->type))
  {
    res_1 = cast(res_1, FLOAT);
    res_2 = cast(res_2, FLOAT);
    float_arith_flag = 1;
  }

  return float_arith_flag || res_1->type == FLOAT ? float_arith_eval(res_1, res_2, op) : normal_arith_eval(res_1, res_2, op);
}

struct ExprResList *expr_eval(struct ExprResList *expr_seq, struct ExprRes *res, int print_flag)
{
  struct ExprResList *list = new_expr_res_list(res, NULL, print_flag);

  if (!expr_seq)
  {
    return list;
  }
  else
  {
    struct ExprResList *temp_seq = expr_seq;

    while (temp_seq->next)
    {
      temp_seq = temp_seq->next;
    }

    temp_seq->next = list;
  }

  return expr_seq;
}

struct InstrSeq *eval_expr_res_list(struct ExprResList *exprSeq)
{
  struct InstrSeq *head_instr = NULL;
  struct ExprResList *trailer = NULL;
  struct ExprResList *temp_list = exprSeq;

  while (temp_list->next)
  {
    if (!head_instr)
    {
      head_instr = append_seq(head_instr, temp_list->expr->instrs);
    }
    else
    {
      append_seq(head_instr, temp_list->expr->instrs);
    }

    trailer = temp_list;
    temp_list = temp_list->next;

    free(trailer->expr);
    free(trailer);
  }

  //Grabbing instructions before printing the last trailing space
  struct InstrSeq *head = temp_list->expr->instrs;

  while (head->next)
  {
    if (head->next->oper_1 && head->next->oper_2)
    {
      if (strcmp(head->next->oper_1, "$a0") == 0 && strcmp(head->next->oper_2, SPACE_LABEL) == 0)
      {
        break;
      }
    }

    head = head->next;
  }

  //replacing the trailing space instructions
  struct InstrSeq *next = head->next->next; //should be a syscall instruction
  free(head->next);

  head->next = gen_instr(NULL, "la", "$a0", EMPTY_LABEL, NULL);
  no_delim_flag = 1;
  head->next->next = next;

  if (!head_instr)
  {
    head_instr = append_seq(head_instr, temp_list->expr->instrs);
  }
  else
  {
    append_seq(head_instr, temp_list->expr->instrs);
  }

  free(temp_list->expr);
  free(temp_list);

  return head_instr;
}

struct SymEntry *new_not_found_entry(char *id)
{
  struct SymEntry *not_found = (struct SymEntry *)malloc(sizeof(struct SymEntry));

  not_found->Name = strdup(id);
  not_found->Next = NULL;
  not_found->Attributes = NULL;

  return not_found;
}

struct IdList *new_id_item_signature(char *id, struct ExprRes *index)
{
  struct IdList *id_list = (struct IdList *)malloc(sizeof(struct IdList));
  int func_flag = find_entry(id);
  struct SymEntry *entry2 = entry;

  if (!entry2)
  {
    entry2 = new_not_found_entry(id);
  }

  id_list->entry = entry2;
  id_list->idx = index;
  id_list->next = NULL;

  return id_list;
}

struct IdList *id_item_eval(struct IdList *list, struct IdList *item)
{
  if (!list)
  {
    return item;
  }
  else
  {
    struct IdList *temp_list = list;

    while (temp_list->next)
    {
      temp_list = temp_list->next;
    }
    temp_list->next = item;

    return list;
  }
}

struct InstrSeq *eval_id_list(struct IdList *list)
{
  struct IdList *temp_list = list;  
  struct ExprRes *res = lit_eval("\"Please enter correct values.\"", STRING);
  struct ExprRes *res_2 = lit_eval("\"Undeclared variables will be skipped.\"", STRING);
  struct InstrSeq *code = append_seq(res->instrs, print_eval(NULL, NEW_LINE_LABEL, 1));

  append_seq(code, res_2->instrs);
  append_seq(code, print_eval(NULL, NEW_LINE_LABEL, 1));

  release_and_free(res);
  release_and_free(res_2);

  while (temp_list)
  {
    char *var_name = temp_list->entry->Name;

    if (temp_list->entry->Attributes)
    {
      input_flag = 1; 
      struct TypeAttr *var_attribute = ((struct TypeAttr *)temp_list->entry->Attributes);
      enum expr_type var_type = var_attribute->type;

      char *temp_msg = (char *)malloc(sizeof(char) * 1024);
      sprintf(temp_msg, "\"Value for %s %s: \"", expr_type_name[var_type], var_name);
      struct ExprRes *msgRes = lit_eval(temp_msg, STRING);

      code = append_seq(code, msgRes->instrs);

      free(temp_msg);
      release_and_free(msgRes);
    
      struct ExprRes *index = temp_list->idx;

      append_seq(code, gen_instr(NULL, "li", "$v0", get_atomic_type(var_type) < FLOAT ? "5" : "6", NULL));
      append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));

      if (index)
      {
        if (is_index_valid(index->type))
        {
          code = append_seq(code, index->instrs);
          int reg = get_avail_reg(TEMP_REG);

          append_seq(code, gen_instr(NULL, "la", get_reg_name(reg, TEMP_REG), var_name, NULL));                              //la $t0 array
          append_seq(code, gen_instr(NULL, "sll", get_reg_name(index->reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG), "2")); //i * 4
          append_seq(code, gen_instr(NULL, "add", get_reg_name(reg, TEMP_REG), get_reg_name(reg, TEMP_REG), get_reg_name(index->reg, TEMP_REG)));

          char *label = gen_paren_label(get_reg_name(reg, TEMP_REG));

          if (get_atomic_type(var_type) < FLOAT)
          {
            if (get_atomic_type(var_type) == BOOL)
            {
              append_seq(code, gen_instr(NULL, "sne", "$v0", "$v0", "$zero"));
            }
            append_seq(code, gen_instr(NULL, "sw", "$v0", label, NULL));
          }
          else
          {
            append_seq(code, gen_instr(NULL, "s.s", "$f0", label, NULL));
          }

          free(label);
          release_reg(reg, TEMP_REG);
          release_and_free(index);
        }
        else
        {
          error("Index is not of type integer");
        }
      }
      else
      { 
        if (var_type < FLOAT)
        {
          if (var_type == BOOL)
          {
            append_seq(code, gen_instr(NULL, "sne", "$v0", "$v0", "$zero"));
          }
          append_seq(code, gen_instr(NULL, "sw", "$v0", var_name, NULL));
        }
        else
        {
          append_seq(code, gen_instr(NULL, "swc1", "$f0", var_name, NULL));
        }
      }
    }
    else
    {
      char *error_msg = (char *)malloc(sizeof(char) * 1024);
      sprintf(error_msg, "\"Coulnd't find var name: %s\"", var_name);

      struct ExprRes *error_res = lit_eval(error_msg, STRING);

      append_seq(code, error_res->instrs);
      append_seq(code, print_eval(NULL, NEW_LINE_LABEL, 1));

      free(error_msg);
      release_and_free(error_res);
    }
    temp_list = temp_list->next;
  }

  return code;
}

void init_function_table(char *name)
{
  if (!funcs)
  {
    func_name = strdup(name);
    func_var_index = 1;
    save_offset = 0;
    funcs = CreateSymTab(33);
  }
}



struct InstrSeq *local_save_seq(struct FunctionLocalSignature *locals)
{
  int i, size;
  char addr[8], offset[8];
  struct FunctionLocalSignature *local_list = locals;

  size = 4;

  while (local_list)
  {
    size += (local_list->var_sig->size == 0 ? 1 : local_list->var_sig->size) * 4;
    local_list = local_list->next;
  }
  sprintf(offset, "%d", size);

  return append_seq(gen_instr(NULL, "subu", "$sp", "$sp", offset), gen_instr(NULL, "sw", "$ra", "($sp)", NULL));
}

struct InstrSeq *func_call_save_seq(struct ExprResList *args)
{
  struct InstrSeq *save, *code;
  int i, scnt;
  char addr[8], offset[8];

  scnt = -1;
  save = NULL;
  code = NULL;

  struct TypeAttr *attribute = (struct TypeAttr *)entry->Attributes;
  struct FunctionParams *function_params = attribute->params;
  struct FunctionParams *temp_function_params = function_params;

  struct ExprResList *temp_arg_list = args;

  while (temp_function_params && temp_arg_list)
  {
    if (temp_arg_list->expr->type != temp_function_params->type)
    {
      error("TYPE MISMATCH FOR ARGUMENT");
    }
    scnt++;
    sprintf(addr, "%d($sp)", scnt * 4);

    if (!temp_function_params->is_address && (temp_function_params->type >= BOOL_ARRAY && temp_function_params->type <= FLOAT_ARRAY))
    {
      int arr_reg = get_avail_reg(TEMP_REG);
      int index_reg = get_avail_reg(TEMP_REG);

      append_seq(save, gen_instr(NULL, "la", get_reg_name(arr_reg, TEMP_REG), temp_arg_list->expr->name, NULL));
      enum expr_type array_type = get_atomic_type(temp_function_params->type);
      for (int i = 0; i < temp_function_params->size; i += 4)
      {
        int tmp_reg = get_avail_reg(array_type < FLOAT ? TEMP_REG : FLOAT_REG);

        char li[15];
        sprintf(li, "%d", i);

        append_seq(save, gen_instr(NULL, "li", get_reg_name(index_reg, TEMP_REG), li, NULL));
        append_seq(save, gen_instr(NULL, "add", get_reg_name(arr_reg, TEMP_REG), get_reg_name(arr_reg, TEMP_REG), get_reg_name(index_reg, TEMP_REG)));

        char *label = gen_paren_label(get_reg_name(tmp_reg, get_reg_type(tmp_reg)));
        append_seq(save, gen_instr(NULL, array_type < FLOAT ? "lw" : "lwc1", get_reg_name(tmp_reg, get_reg_type(tmp_reg)), label, NULL));
        append_seq(save, gen_instr(NULL, array_type < FLOAT ? "sw" : "s.s", get_reg_name(tmp_reg, get_reg_type(tmp_reg)), addr, NULL));

        free(label);
        release_reg(tmp_reg, get_reg_type(tmp_reg));

        scnt++;
        sprintf(addr, "%d($sp)", scnt * 4);
      }
    }
    else if (temp_function_params->type == FLOAT)
    {
      save = append_seq(save, gen_instr(NULL, "swc1", get_reg_name(temp_arg_list->expr->reg, FLOAT_REG), addr, NULL));
    }
    else
    {
      save = append_seq(save, gen_instr(NULL, "sw", get_reg_name(temp_arg_list->expr->reg, TEMP_REG), addr, NULL));
    }

    temp_arg_list = temp_arg_list->next;
    temp_function_params = temp_function_params->next;
  }

  for (i = 0; i < MAX_TEMP_REG; i++)
  {
    if (!is_avail_reg(i, TEMP_REG))
    {
            temp_reg_used[i] = 1;

      scnt++;
      sprintf(addr, "%d($sp)", scnt * 4);
      save = append_seq(save, gen_instr(NULL, "sw", get_reg_name(i, TEMP_REG), addr, NULL));
      code = append_seq(code, gen_instr(NULL, "lw", get_reg_name(i, TEMP_REG), addr, NULL));
    }
  }

  for (i = 0; i < MAX_FLOAT_REG; i++)
  {
    if (!is_avail_reg(i, FLOAT_REG))
    {
            float_reg_used[i] = 1;

      scnt++;
      sprintf(addr, "%d($sp)", scnt * 4);
      save = append_seq(save, gen_instr(NULL, "swc1", get_reg_name(i, FLOAT_REG), addr, NULL));
      code = append_seq(code, gen_instr(NULL, "lwc1", get_reg_name(i, FLOAT_REG), addr, NULL));
    }
  }

  sprintf(offset, "%d", (1 + scnt) * 4);

  func_call_resotre = append_seq(code, gen_instr(NULL, "addi", "$sp", "$sp", offset));
  code = append_seq(gen_instr(NULL, "subu", "$sp", "$sp", offset), save);
  reset_all_regs();
  return code;
}

struct InstrSeq *func_call_resotre_seq(struct ExprResList *args)
{
  struct InstrSeq *save;

  if (func_call_resotre)
  {
    save = append_seq(func_call_resotre, NULL);
    func_call_resotre = NULL;
  }

  restore_all_regs(temp_reg_used, float_reg_used);

  for (int i = 0; i < MAX_TEMP_REG; i++)
  {
    temp_reg_used[i] = 0;
  }

  for (int i = 0; i < MAX_FLOAT_REG; i++)
  {
    float_reg_used[i] = 0;
  }

  return save;
}

struct FunctionSignature *new_func_signature(char *type, char *name)
{
  struct FunctionSignature *info = (struct FunctionSignature *)malloc(sizeof(struct FunctionSignature));
  function_type = get_atomic_type(get_expr_type(type, 0));
  info->return_type = get_function_type(function_type);
  info->name = strdup(name);
  init_function_table(name);

  is_recursive_flag = 1;
  EnterName(syms, name, &entry); 
 
  struct TypeAttr *attribute = new_type_attr(info->return_type, 0);
  entry->Attributes = attribute;
  return info;
}

struct InstrSeq *return_eval(struct ExprRes *results)
{
  struct InstrSeq *value_instr = NULL;

 
    if (results)
    {
      if (is_widening(function_type, results->type) || is_narrowing(function_type, results->type) )
      {
        results = cast(results, function_type);
      }
      value_instr = results->instrs;

      if (results->type == FLOAT)
      {
        append_seq(value_instr, gen_instr(NULL, "mfc1", "$v1", get_reg_name(results->reg, get_reg_type(results->type)), NULL));
      }
      else if (results->type <= INT)
      {
        append_seq(value_instr, gen_instr(NULL, "move", "$v1", get_reg_name(results->reg, get_reg_type(results->type)), NULL));
      }

      append_seq(value_instr, gen_instr(NULL, "lw", "$ra", "($sp)", NULL));
      char offset[30];
      sprintf(offset, "%d", save_offset + 4);
      append_seq(value_instr, gen_instr(NULL, "addi", "$sp", "$sp", offset));
      append_seq(value_instr, gen_instr(NULL, "jr", "$ra", NULL, NULL));
    

    release_and_free(results);
  }

  return value_instr;
}

struct FunctionLocalSignature *new_local_list(struct FunctionLocalSignature *list, struct FunctionLocalSignature *item)
{
  if (!list)
  {
    return item;
  }
  else if (!item)
  {
    return list;
  }

  
    struct FunctionLocalSignature *temp_list = list;
    while (temp_list->next)
    {
      temp_list = temp_list->next;
    }
    temp_list->next = item;
  
  return list;
}

struct FunctionLocalSignature *new_local_list_item(char *type, struct VarSignature *var_sig)
{
  init_function_table(func_name);

  struct FunctionLocalSignature *var = (struct FunctionLocalSignature *)malloc(sizeof(struct FunctionLocalSignature));

  var->index = get_and_incr_func_index();
  var->var_sig = var_sig;
  var->type = get_expr_type(type, var_sig->size);
  var->next = NULL;
  save_offset += (var_sig->size <= 0 ? 1 : var_sig->size) * 4;

  EnterName(funcs, var_sig->name, &entry);

  struct TypeAttr *attribute = new_type_attr(var->type, var_sig->size);

  attribute->index = var->index;
  entry->Attributes = attribute;

  return var;
}

void func_eval(struct FunctionSignature *func_sig, struct FunctionParams *function_params, struct FunctionLocalSignature *local_list, struct InstrSeq *seq)
{
  is_recursive_flag = 0;
  int func_flag = find_entry(func_sig->name);

  struct TypeAttr *func_attr = (struct TypeAttr *)entry->Attributes;
  func_attr->params = function_params;

  struct InstrSeq *funcLabelInstr = gen_instr(gen_str_name(func_sig->name), NULL, NULL, NULL, NULL);
  func_attr->instrs = append_seq(NULL, funcLabelInstr);
  append_seq(func_attr->instrs, local_save_seq(local_list));
  func_attr->instrs = append_seq(func_attr->instrs, seq);

  DestroySymTab(funcs);
  funcs = NULL;
  func_var_index = 1;
}

struct ExprRes *function_call_eval(char *funcName, struct ExprResList *args)
{
  int func_flag;
  struct SymEntry *function;
  struct TypeAttr *function_attr;
  struct ExprRes *function_result;

  func_flag = find_entry(funcName);
  function = entry;
  function_attr = (struct TypeAttr *)function->Attributes;

  function_type = get_atomic_type(function_attr->type);
  function_result = new_expr_res(function_type, NULL);
  release_reg(function_result->reg, get_reg_type(function_type));

  struct ExprResList *arg_list = args;
  while (arg_list)
  {
    function_result->instrs = append_seq(function_result->instrs, arg_list->expr->instrs);
    arg_list = arg_list->next;
  }

  function_result->instrs = append_seq(function_result->instrs, func_call_save_seq(args)); 
  function_result->instrs = append_seq(function_result->instrs, gen_instr(NULL, "jal", function->Name, NULL, NULL));
  function_result->instrs = append_seq(function_result->instrs, func_call_resotre_seq(args));

  function_result->reg = get_avail_reg(get_reg_type(function_result->type));
  if (function_result->type == FLOAT)
  {
    append_seq(function_result->instrs, gen_instr(NULL, "mtc1", "$v1", get_reg_name(function_result->reg, get_reg_type(function_result->type)), NULL));
  }
  else if (function_result->type <= INT)
  {
    append_seq(function_result->instrs, gen_instr(NULL, "move", get_reg_name(function_result->reg, get_reg_type(function_result->type)), "$v1", NULL));
  }

  return function_result;
}

struct FunctionParams *function_params_eval(struct FunctionParams *var_list, struct FunctionParams *var)
{
  if (!var_list)
  {
    return var;
  }
  else if (!var)
  {
    return var_list;
  }

  struct FunctionParams *temp_list = var_list;
  while (temp_list->next)
  {
    temp_list = temp_list->next;
  }

  temp_list->next = var;
  return var_list;
}

struct FunctionParams *func_params_eval(struct FunctionParams *var_list, struct FunctionParams *var)
{
  if (!var_list)
  {
    return var;
  }
  else if (!var)
  {
    return var_list;
  }

  struct FunctionParams *temp_list = var_list;
  while (temp_list->next)
  {
    temp_list = temp_list->next;
  }
  temp_list->next = var;

  return var_list;
}


struct FunctionParams *new_func_param_signature(char *type, char *addr_flag, char *name, char *size, int type_flag)
{
  struct FunctionParams *params = (struct FunctionParams *)malloc(sizeof(struct FunctionParams));
  params->type = get_expr_type(type, type_flag);
  params->name = name;
  params->index = get_and_incr_func_index();
  params->next = NULL;
  int arr_size = 0;

  if (size)
  {
    arr_size = atoi(size) * get_sizeof(params->type);
  }

  params->size = arr_size;
  params->is_address = addr_flag ? 1 : 0;

  EnterName(funcs, name, &entry);

  struct TypeAttr *attribute = (struct TypeAttr *)malloc(sizeof(struct TypeAttr));
  attribute->type = params->type;
  attribute->index = params->index;
  attribute->instrs = NULL;
  attribute->size = arr_size;
  attribute->is_addr = params->is_address;
  entry->Attributes = attribute;

  EnterName(syms, func_name, &entry);
  struct TypeAttr *func_attr = (struct TypeAttr *)entry->Attributes;
  func_attr->params = params; 
  entry->Attributes = func_attr;

  return params;
}

struct InstrSeq *get_word_data()
{
  struct InstrSeq *code = NULL;
  entry = FirstEntry(syms);

  while (entry)
  {
    struct TypeAttr *info = (struct TypeAttr *)entry->Attributes;
    if (info->type < FLOAT)
    {
      code = append_seq(code, gen_instr((char *)GetName(entry), ".word", "0", NULL, NULL));
    }

    entry = NextEntry(syms, entry);
  }

  return code;
}

struct InstrSeq *get_float_data()
{
  struct InstrSeq *code = NULL;
  if (zero_float_flag) {
        code = append_seq(code, gen_instr(FLOAT_ZERO_LABEL, ".float", "0.0", NULL, NULL));

  }
  if (one_float_flag) {
    code = append_seq(code, gen_instr(FLOAT_ONE_LABEL, ".float", "1.0", NULL, NULL));

  }
  if (floats_intern) {
    code = append_seq(code, floats_intern);

  }

  entry = FirstEntry(syms);
  while (entry)
  {
    struct TypeAttr *info = (struct TypeAttr *)entry->Attributes;
    if (info->type == FLOAT)
    {
      code = append_seq(code, gen_instr((char *)GetName(entry), ".float", "0.0", NULL, NULL));
    }

    entry = NextEntry(syms, entry);
  }

  return code;
}

struct InstrSeq *get_asciiz_data()
{
  struct InstrSeq *code = NULL;
  if (new_line_flag) {
    code = append_seq(code, gen_instr(NEW_LINE_LABEL, ".asciiz", "\"\\n\"", NULL, NULL));
  }
  if (space_flag) {
    code = append_seq(code, gen_instr(SPACE_LABEL, ".asciiz", "\" \"", NULL, NULL));
  }
  if (no_delim_flag) {
    code = append_seq(code, gen_instr(EMPTY_LABEL, ".asciiz", "\"\"", NULL, NULL));
  }

  if (bool_flag)
  {
    code = append_seq(code, gen_instr(TRUE_LABEL, ".asciiz", "\"true\"", NULL, NULL));
    code = append_seq(code, gen_instr(FALSE_LABEL, ".asciiz", "\"false\"", NULL, NULL));
  }

  if (strings_intern) {
        code = append_seq(code, strings_intern);
  }

  return code;
}

struct InstrSeq *get_space_data()
{
  struct InstrSeq *code = NULL;
  if (input_flag) {
        code = append_seq(code, gen_instr(INPUT_LABEL, ".space", INPUT_SIZE, NULL, NULL));

  }

  entry = FirstEntry(syms);
  while (entry)
  {
    struct TypeAttr *info = (struct TypeAttr *)entry->Attributes;

    if (info->type >= BOOL_ARRAY && info->type <= FLOAT_ARRAY)
    {
      char *arySize = (char *)malloc(100 * sizeof(char));
      sprintf(arySize, "%d", info->size);

      code = append_seq(code, gen_instr((char *)GetName(entry), ".space", arySize, NULL, NULL));
      free(arySize);
    }

    entry = NextEntry(syms, entry);
  }

  return code;
}

struct InstrSeq *get_functions()
{
  struct InstrSeq *code = NULL;
  entry = FirstEntry(syms);

  while (entry)
  {
    struct TypeAttr *info = (struct TypeAttr *)entry->Attributes;
    if (info->type >= BOOL_FUNCTION)
    {
      code = append_seq(code, info->instrs);
    }

    entry = NextEntry(syms, entry);
  }

  return code;
}

struct InstrSeq *get_data(enum expr_type type)
{
  struct InstrSeq *instr = NULL;
  if (type <= INT) {
    instr = get_word_data();
  }
  else if (type == FLOAT) {
    instr = get_float_data();
  }
  else if (type == STRING) {
    instr = get_asciiz_data();
  }
  else if (type >= BOOL_FUNCTION) {
    instr = get_functions();
  }
  else {    
    instr = get_space_data();
  }

  return instr;
}

void finish(struct InstrSeq *Code)
{
  struct SymEntry *entry = NULL;
  struct SymEntry *trailer = NULL;
  struct InstrSeq *code = NULL;

  code = gen_instr(NULL, ".text", NULL, NULL, NULL);
  append_seq(code, gen_instr(NULL, ".globl", "main", NULL, NULL));
  append_seq(code, gen_instr("main", NULL, NULL, NULL, NULL));
  append_seq(code, Code);
  append_seq(code, gen_instr(NULL, "li", "$v0", "10", NULL));
  append_seq(code, gen_instr(NULL, "syscall", NULL, NULL, NULL));
  append_seq(code, get_data(INT_FUNCTION)); 
  append_seq(code, gen_instr(NULL, ".data", NULL, NULL, NULL));
  append_seq(code, gen_instr(NULL, ".align", "4", NULL, NULL));
  append_seq(code, get_data(INT_ARRAY));
  append_seq(code, get_data(INT));
  append_seq(code, get_data(FLOAT));
  append_seq(code, get_data(STRING));

  write_seq(code);
}