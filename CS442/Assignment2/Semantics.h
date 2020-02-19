/* Semantic Records */
enum expr_type {
  BOOL, INT, FLOAT, STRING,
  BOOL_ARRAY, INT_ARRAY, FLOAT_ARRAY, 
  BOOL_FUNCTION, INT_FUNCTION, FLOAT_FUNCTION, STRING_FUNCTION,
  INT_ARRAY_FUNCTION, BOOL_ARRAY_FUNCTION, FLOAT_ARRAY_FUNCTION
};
static char *expr_type_name[] = {"bool", "int", "float", "string",  
                                 "bool array", "int array", "float array"};

struct VarSignature {
  char *name;
  int size;
};

struct FunctionSignature {
  char *name;
  enum expr_type return_type;
};

struct ExprRes {
  enum expr_type type;
  char *name;  
  int addr_flag;
  int reg;
  struct InstrSeq *instrs;
};

struct ExprResList {
  struct ExprRes *expr;
  struct ExprResList *next;
};

struct FunctionLocalSignature {
  enum expr_type type;
  int index;
  struct VarSignature *var_sig;
  struct FunctionLocalSignature *next;
};

struct TypeAttr {
  enum expr_type type;
  int index;
  int size;
  int is_addr;
  struct FunctionParams *params;
  struct InstrSeq *instrs;
};

struct IdList {
  struct SymEntry *entry;
  struct ExprRes *idx;
  struct IdList *next;
};

struct FunctionParams {
  enum expr_type type;
  char *name;
  int index;
  int size;
  int is_address;
  struct FunctionParams *next;
};


extern void init_function_table();
extern void add_string(char *name, char *value);
extern char *gen_str_name(char *name);

extern enum expr_type expr_type;

extern struct TypeAttr *new_type_attr(enum expr_type type, int size);
extern struct IdList *new_id_item_signature(char *id, struct ExprRes *idx);
extern struct VarSignature *new_var_signature(char *name, char *size);
extern struct FunctionSignature *new_func_signature(char *type, char *name);
struct ExprRes *new_expr_res(enum expr_type t, char *name);
struct ExprResList *new_expr_res_list(struct ExprRes *expr_res, struct ExprResList *next_expr_seq, int should_print);

extern struct InstrSeq *eval_id_list(struct IdList *id_list);
extern struct InstrSeq *eval_expr_res_list(struct ExprResList *expr_seq);

extern struct FunctionLocalSignature *new_local_list_item(char * type, struct VarSignature* var_sig);
extern struct FunctionLocalSignature *new_local_list(struct FunctionLocalSignature *list, struct FunctionLocalSignature *item);

extern struct ExprRes *function_call_eval(char *func_name, struct ExprResList *params);
extern struct ExprRes *lit_eval(char *input, enum expr_type type);
extern struct ExprRes *arith_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op);
extern struct ExprRes *relat_eval(struct ExprRes *res_1, struct ExprRes *res_2, char *op);
extern struct ExprRes *rval_eval(char *name, struct ExprRes *index);

extern void var_eval(char *type, struct VarSignature *var_sig);
extern void func_eval(struct FunctionSignature *func_info, struct FunctionParams *param_list, struct FunctionLocalSignature *local_list, struct InstrSeq *seq);

extern struct FunctionParams *func_params_eval(struct FunctionParams *param_list, struct FunctionParams *var);
extern struct FunctionParams *new_func_param_signature(char *type, char *addr_flag, char *name, char *ary_size, int action_flag);

extern struct IdList *id_item_eval(struct IdList *id_list, struct IdList *item);
extern struct ExprResList *expr_eval(struct ExprResList *expr_seq, struct ExprRes *res, int print_flag);

extern struct InstrSeq *return_eval(struct ExprRes *results);
extern struct InstrSeq *print_eval(struct ExprRes *expr, char *delim, int free_flag);
extern struct InstrSeq *assign_eval(char *name, struct ExprRes *value, struct ExprRes *index);
extern struct InstrSeq *cond_eval(struct ExprRes *bool_resultant, struct InstrSeq *option_1, struct InstrSeq *option_2, char *op);

extern void finish(struct InstrSeq *code);
