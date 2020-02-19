#include <stdio.h>

extern FILE *AssmFile;

#define MAX_FLOAT_REG 30
#define MAX_TEMP_REG 10

enum register_type
{
  TEMP_REG,
  FLOAT_REG
};

struct InstrSeq
{
  char *label;
  char *op;
  char *oper_1;
  char *oper_2;
  char *oper_3;
  struct InstrSeq *next;
};

extern enum register_type register_type;
extern void InitCodeGen(char *filename);
extern struct InstrSeq *gen_instr(char *label, char *op_code, char *operand_1, char *operand_2, char *operand_3);
extern struct InstrSeq *append_seq(struct InstrSeq *seq_1, struct InstrSeq *seq_2);
extern void reset_all_regs();
extern void restore_all_regs(int temp_regs[], int float_regs[]);
extern void release_reg(int reg_num, enum register_type r_type);
extern void write_seq(struct InstrSeq *seq);
extern int is_avail_reg(int reg_num, enum register_type reg_type);
extern char *gen_label();
extern char *get_reg_name(int reg_num, enum register_type r_type);
extern int get_avail_reg(enum register_type r_type);