#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "CodeGen.h"

extern FILE *file;
int next_label = 1;
char buffer[16];

struct TempReg {
  unsigned char free;
  unsigned char used;
  char *name;
} temp_registers[10] = {
  {1, 0, "$t0"}, {1, 0, "$t1"}, {1, 0, "$t2"}, {1, 0, "$t3"}, {1, 0, "$t4"}, 
  {1, 0, "$t5"}, {1, 0, "$t6"}, {1, 0, "$t7"}, {1, 0, "$t8"}, {1, 0, "$t9"}
};

struct FloatReg {
  unsigned char free;
  unsigned char used;
  char *name;
} float_registers[30] = {
  {1, 0, "$f1"}, {1, 0, "$f2"}, {1, 0, "$f3"}, {1, 0, "$f4"}, {1, 0, "$f5"}, 
  {1, 0, "$f6"}, {1, 0, "$f7"}, {1, 0, "$f8"}, {1, 0, "$f9"}, {1, 0, "$f10"}, 
  {1, 0, "$f11"}, {1, 0, "$f13"}, {1, 0, "$f14"}, {1, 0, "$f15"}, {1, 0, "$f16"}, 
  {1, 0, "$f17"}, {1, 0, "$f18"}, {1, 0, "$f19"}, {1, 0, "$f20"}, {1, 0, "$f21"}, 
  {1, 0, "$f22"}, {1, 0, "$f23"}, {1, 0, "$f24"}, {1, 0, "$f25"}, {1, 0, "$f26"}, 
  {1, 0, "$f27"}, {1, 0, "$f28"}, {1, 0, "$f29"}, {1, 0, "$f30"}, {1, 0, "$f31"}
};


char *copy_str(char *str) {
  return (str) ? strdup(str) : NULL;
}

struct InstrSeq *gen_instr(char *label, char *op_code, char *op_1, char *op_2, char *op_3) {
  struct InstrSeq *instr;

  instr = (struct InstrSeq *)malloc(sizeof(struct InstrSeq));
  instr->label = copy_str(label);
  instr->op = copy_str(op_code);
  instr->oper_1 = copy_str(op_1);
  instr->oper_2 = copy_str(op_2);
  instr->oper_3 = copy_str(op_3);
  instr->next = NULL;

  return instr;
}

extern struct InstrSeq *append_seq(struct InstrSeq *seq_1, struct InstrSeq *seq_2)
{
  if (!seq_1) {
    return seq_2;
  } else if (!seq_2) {
    return seq_1;
  }

  struct InstrSeq *instr = seq_1;

  while (instr->next) {
    instr = instr->next;
  }

  instr->next = seq_2;

  return seq_1;
}

void write_seq(struct InstrSeq *seq)
{
  struct InstrSeq *instr;
  instr = seq;

  while (instr) {
    if (instr->label) fprintf(file, "%s:", instr->label);

    if (instr->op) {
      fprintf(file, "\t%s\t", instr->op);

      if (instr->oper_1) fprintf(file, "\t%s", instr->oper_1);
      if (instr->oper_2) fprintf(file, ", %s", instr->oper_2);
      if (instr->oper_3) fprintf(file, ", %s", instr->oper_3);
    }

    fprintf(file, "\n");
    instr = instr->next;
  }

  if (file != stdout) fclose(file);
}

char *gen_label() {
  char *label;
  label = (char *)malloc(8);
  sprintf(label, "L%d", next_label++);

  return label;
}

int get_avail_temp_reg() {
  for (int i = 0; i < MAX_TEMP_REG; i++) {
    if (temp_registers[i].free) {
      temp_registers[i].free = 0;
      temp_registers[i].used = 1;
      return i;
    }
  }

  return -1;
}

int get_avail_float_reg() {
  for (int i = 0; i < MAX_FLOAT_REG; i++)
  {
    if (float_registers[i].free) {
      float_registers[i].free = 0;
      float_registers[i].used = 1;
      return i;
    }
  }

  return -1;
}

int is_within_boundary(int val, int max) {
  return val >= 0 && val < max;
}

int get_avail_reg(enum register_type r_type) {
  int reg = -1;

  if (r_type == TEMP_REG) {
    reg = get_avail_temp_reg();
  } else if (r_type == FLOAT_REG) {
    reg = get_avail_float_reg();
  } else {
    return reg;
  }
}

char *get_reg_name(int reg_num, enum register_type r_type) {
  if (r_type == TEMP_REG) {
    if (is_within_boundary(reg_num, MAX_TEMP_REG)) {
      return temp_registers[reg_num].name;
    }
  } else if (r_type == FLOAT_REG) {
    if (is_within_boundary(reg_num, MAX_FLOAT_REG)) {
      return float_registers[reg_num].name;
    }
  } else {
      return NULL;
  }
}

void release_reg(int reg_num, enum register_type r_type)
{
  if (r_type == TEMP_REG) {
    if (is_within_boundary(reg_num, MAX_TEMP_REG)) {
      temp_registers[reg_num].free = 1;
    }
  } else if (r_type == FLOAT_REG) {
    if (is_within_boundary(reg_num, MAX_FLOAT_REG)) {
      float_registers[reg_num].free = 1;
    }
  }
}


int is_avail_reg(int reg_num, enum register_type r_type) {
  if (r_type == FLOAT_REG && is_within_boundary(reg_num, MAX_FLOAT_REG) && float_registers[reg_num].free) {
    return 1;
  } else if (r_type == TEMP_REG && is_within_boundary(reg_num, MAX_TEMP_REG) && temp_registers[reg_num].free) {
    return 1;
  } else {
    return 0;
  }
}




void restore_temp_regs(int temp_regs[]) {
  for (int i = 0; i < MAX_TEMP_REG; i++) {
    if (temp_regs[i]) {
      temp_registers[i].free = 0;
      temp_registers[i].used = 1;
    }
  }
}

void restore_float_regs(int float_regs[]) {
  for (int i = 0; i < MAX_FLOAT_REG; i++) {
    if (float_regs[i]) {
      float_registers[i].free = 0;
      float_registers[i].used = 1;
    }
  }
}

void restore_all_regs(int temp_regs[], int float_regs[]) {
  restore_temp_regs(temp_regs);
  restore_float_regs(float_regs);
}

void reset_all_temp_reg() {
  for (int i = 0; i < MAX_TEMP_REG; i++) {
    temp_registers[i].free = 1;
    temp_registers[i].used = 0;
  }
}

void reset_all_float_reg() {
  for (int i = 0; i < MAX_FLOAT_REG; i++) {
    float_registers[i].free = 1;
    float_registers[i].used = 0;
  }
}

void reset_all_regs() {
  reset_all_temp_reg();
  reset_all_float_reg();
}
