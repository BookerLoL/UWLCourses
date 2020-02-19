# MIPS Iterative Sort
# Ethan Booker

# begin data segment
             .data
size:        .word 0                                                # holds an int to maintain array size
counter:     .word 0                                                # holds an int to act as a counter
order_msg:   .asciiz "The elements sorted in ascending order are: " # sorted message
             .align 2                                               # align data segment after each string
comma_space: .asciiz ", "                                           # seperate data
             .align 2                                               # align data segement after each string                                            
arr:         .space 80                                              # size 20 int array

# end of data segment

#=========================================================

# begin text segment 
             .text                       # instructions start here
MAIN:        la $s0, size                # load address of size 
             addi $v0, $zero, 5          # set syscall to int mode
             syscall                     # get user input
             sw $v0, 0($s0)              # save value at address of size
             lw $s0, 0($s0)              # load value to $s0 register
             la $s1, counter             # load address of counter
             addi $s1, $zero, 0          # set counter to 0      
             la $s2, arr                 # load array address                     
FOR:         slt $t0, $s1, $s0           # counter < size
             beq $t0, $zero, INIT_FUNC   # jump to INIT_FUNC
             sll $t0, $s1, 2             # counter * 4
             add $t0, $t0, $s2           # counter + array address
             addi $v0, $zero, 5          # change syscall mode to read int 
             syscall                     # read user
             sw $v0, 0($t0)              # store input value into array 
             addi $s1, $s1, 1            # increment counter
             j FOR                       # jump to loop                               
INIT_FUNC:   add $a0, $zero, $s2         # 1st arg, int array
             add $a1, $zero, $s0         # 2nd arg, size of array
             jal bubble_sort             # calling bubble_sort method         
             la $s3, order_msg           # load order_msg
             la $s4, comma_space         # load comma_space
             add $a0, $zero, $s3         # load order_msg to $a0
             addi $v0, $zero, 4          # change syscall mode to print string
             syscall                     # printing           
             addi $s1, $zero, 0          # reset counter to 0
             addi $t0, $s0, -1           # size - 1                              
PRINT_ARY:   slt $t1, $s1, $t0           # counter < size - 1 
             beq $t1, $zero, PRINT_LAST  # jump to PRINT_LAST
             sll $t1, $s1, 2             # counter * 4
             add $t1, $t1, $s2           # (counter*4) + array address
             lw $t1, 0($t1)              # arr[counter]
             add $a0, $zero, $t1         # store arr[counter] onto $a0
             addi $v0, $zero, 1          # change syscall mode to print int
             syscall                     # printing int
             add $a0, $zero, $s4         # store comma_space onto $a0
             addi $v0, $zero, 4          # change syscall mode to print string
             syscall                     # printing
             addi $s1, $s1, 1            # increment counter
             j PRINT_ARY                 # jump to PRINT_ARY           
PRINT_LAST:  sll $t0, $t0, 2             # (size-1)*4
             add $t0, $t0, $s2           # (size-1)*4  + array address
             lw $t0, 0($t0)              # loading value at arr[size-1]
             add $a0, $zero, $t0         # store arr[size-1] onto $a0
             addi $v0, $zero, 1          # change syscall mode to print int
             syscall                     # printing 
             addi $v0, $zero, 10         # system call for exit
             syscall                     # exit
             
#bubble_sort method 
bubble_sort: addi $sp, $sp, -12          # allocate stack space
             sw $s0, 0($sp)              # store $s0 on stack
             sw $s1, 4($sp)              # store $s1 on stack
             sw $s2, 8($sp)              # store $s2 on stack
             addi $s0, $zero, 0          # c = 0
             addi $t0, $a1, -1           # n - 1
FOR1:        slt $t1, $s0, $t0           # c < n - 1
             beq $t1, $zero, FINISH_SORT # jump to FINISH _SORT           
             addi $s1, $zero, 0          # d = 0
             sub $t1, $t0, $s0           # n - c - 1  
FOR2:        slt $t2, $s1, $t1           # d < n - c - 1
             beq $t2, $zero, FOR1_END    # exit FOR2 loop
             la $t2, ($a0)               # address of D
             sll $t3, $s1, 2             # d * 4
             addi $t4, $s1, 1            # d + 1
             sll $t4, $t4, 2             # (d + 1) * 4 
             add $t3, $t3, $t2           # address of list[d]
             add $t4, $t4, $t2           # address of list[d+1]
             lw $t5, 0($t3)              # list[d]
             lw $t6, 0($t4)              # list[d+1]
             slt $t7, $t5, $t6           # list[d] < list[d+1]
             bne $t7, $zero, FOR2_END    # jump to FOR2_END
             add $s2, $zero, $t5         # t = list[d]
             add $t5, $zero, $t6         # list[d] = list[d+1]
             add $t6, $zero, $s2         # list[d+1] = t
             sw $t5, 0($t3)              # storing list[d]
             sw $t6, 0($t4)              # storing list[d+1]                      
FOR2_END:    addi $s1, $s1, 1            # increment d
             j FOR2                      # jump to FOR2
                   
FOR1_END:    addi $s0, $s0, 1            # increment c
             j FOR1                      # jump to FOR1
  
FINISH_SORT: lw $s0, 0($sp)              # restore $s0
             lw $s1, 4($sp)              # restore $s1
             lw $s2, 8($sp)              # restore $s2
             addi $sp, $sp, 12           # restore stack
             jr $ra                      # return to jal position
# end of text segment 
