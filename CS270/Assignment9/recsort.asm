# Program: MIPS Merge Sort
# Author: Ethan Booker
# Date Last Modified: 5/3/2018
# begin data segment
             .data
size:        .space 4                                               # holds an int to maintain array size
counter:     .space 4                                               # holds an int to act as a counter
order_msg:   .asciiz "The elements sorted in ascending order are: " # sorted message
             .align 2                                               # align data segment after each string
comma_space: .asciiz ", "                                           # seperate data
             .align 2                                               # align data segement after each string                                            
arr:         .space 80                                              # size 20 int array
aux:         .space 80                                              # size 20 int array 
# end of data segment

#=========================================================

# begin text segment 
            .text                        # instructions start here
MAIN:       la $s0, size                 # load address of size
            addi $v0, $zero, 5           # change syscall mode to read int
            syscall                      # read int
            sw $v0, 0($s0)               # store size
            lw $s0, 0($s0)               # load size into $s0 register
            la $s1, counter              # load address of counter
            sw $zero, 0($s1)             # load zero into counter
            lw $s1, 0($s1)               # load zero into $s1 register
            la $s2, arr                  # load arr address
for:        slt $t0, $s1, $s0            # counter < size
            beq $t0, $zero, init_func    # jump to init_func
            addi $v0, $zero, 5           # change syscall mode to read int 
            syscall                      # read int
            sll $t0, $s1, 2              # offset counter * 4
            add $t0, $t0, $s2            # arr adress + offset
            sw $v0, 0($t0)               # store input at arr[counter]
            addi $s1, $s1, 1             # increment counter 
            j for                        # loop label 
init_func:  add $a0, $zero, $s2          # store arr at $a0
            add $a1, $zero, $s0          # store size at $a1
            jal mergesort                # calling mergesort
            addi $t0, $s0, -1            # size -1 
            addi $s1, $zero, 0           # reset counter
            la $s3, comma_space          # load comma_space
            la $a0, order_msg            # load order_msg
            addi $v0, $zero, 4           # syscall mode to print string
            syscall                      # printing string
for2:       slt $t1, $s1, $t0            # counter < size-1
            beq $t1, $zero, end          # j to end 
            sll $t1, $s1, 2              # i * 4
            add $t1, $t1, $s2            # address ary[i*4]
            lw $t1, 0($t1)               # ary[i]
            add $a0, $zero, $t1          # ary[i] into $a0 register
            addi $v0, $zero, 1           # syscall mode to print ints
            syscall                      # printing int 
            add $a0, $zero, $s3          # ", " 
            addi $v0, $zero, 4           # change syscall to print string mode
            syscall                      # printing string
            addi $s1, $s1, 1             # counter++ 
            j for2                       # j to for2 loop
end:        sll $t0, $s1, 2              # size-1 * 4
            add $t0, $s2, $t0            # address ary[size-1] 
            lw $a0, 0($t0)               # ary[size-1] 
            addi $v0, $zero, 1           # syscall mode to print ints 
            syscall                      # printing int 
            addi $v0, $zero, 10          # change syscall mode to terminate 
            syscall                      # terminate program       

#=========================================================
# Starts the mergesort 
# void mergesort(int Y[], int SizeY) 
#=========================================================
mergesort:  addi $sp, $sp, -12           # storing arguments and $ra on stack
            sw $a0, 0($sp)               # store $a0
            sw $a1, 4($sp)               # store $a1
            sw $ra, 8($sp)               # store $ra
            addi $a2, $zero, 0           # 3rd argument is 0
            addi $a3, $a1, -1            # 4th argument is Size Y - 1 
            la $a1, aux                  # load auxillary, 2nd argument 
            jal mergesort1               # mergeSort1 (ary, aux, 0, size Y - 1)
            lw $a0, 0($sp)               # restore $a0
            lw $a1, 4($sp)               # restore $a1
            lw $ra, 8($sp)               # restore $ra
            jr $ra                       # return to jal mergesort

#=========================================================
# Split the arrays then merge 
# void mergesort1(int Y[], int A[], int s, int e) 
#=========================================================
mergesort1: addi $sp, $sp, -20           # storing the 4 args 
            sw $a0, 0($sp)               # save a0   
            sw $a1, 4($sp)               # save a1
            sw $a2, 8($sp)               # save a2
            sw $a3, 12($sp)              # save a3
            sw $ra, 16($sp)              # save $ra
            slt $t0, $a2, $a3            # s < e 
            beq $t0, $zero, ret          # return to mergesort1 call 
            add $a3, $a3, $a2            # s + e 
            srl $a3, $a3, 1              # ( s + e ) / 2
            jal mergesort1               # mergeSort1(Y, A, s, (s+e)/2)
            lw $a3, 12($sp)              # load e 
            add $a2, $a2, $a3            # s + e 
            srl $a2, $a2, 1              # (s + e) / 2
            addi $a2, $a2, 1             # (s + e) / 2 + 1
            jal mergesort1               #  mergeSort1(Y, A, s+e/2 +1, e)
            lw $a2, 8($sp)               # restore s
            lw $t0, 12($sp)              # restore e 
            add $a3, $a2, $t0            # s + e
            srl $a3, $a3, 1              # ( s + e ) / 2
            addi $a3, $a3, 1             # (s+e)/2 + 1 
            sw $t0, -4($sp)              # 5th argument e 
            jal merge                    # merge(Y, A, s, (s+e)/2 + 1, e)       
ret:        lw $a0, 0($sp)               # restore a0
            lw $a1, 4($sp)               # restore a1
            lw $a2, 8($sp)               # restore a2
            lw $a3, 12($sp)              # restore a3
            lw $ra, 16($sp)              # restore ra
            addi $sp, $sp, 20            # restore stack 
            jr $ra                       # return to jal mergesort1 in mergesort 
#=========================================================
# Merges the split arrays in order
# merge(int Y[], int A[], int s1, int s2, int e2) 
#=========================================================                    
merge:      addi $sp, $sp, -20           # 3 local vars and 1 extra var
            sw $s0, 0($sp)               # var i 
            sw $s1, 4($sp)               # var j
            sw $s2, 8($sp)               # var k
            sw $s3, 12($sp)              # var e2 
            lw $s3, 16($sp)              # retrieve e2
            add $s0, $zero, $a2          # i = s1
            add $s1, $zero, $a3          # j = s2
            add $s2, $zero, $a2          # k = s1
 while1:    slt $t0, $s0, $a3            # i < s2
            beq $t0, $zero, while2       # jump to while2
            slt $t0, $s3, $s1            # e2 < j 
            bne $t0, $zero, while2       # jump to while2
            sll $t0, $s0, 2              # i * 4
            sll $t1, $s1, 2              # j * 4
            add $t0, $t0, $a0            # address of Y[i]
            add $t1, $t1, $a0            # address of Y[j] 
            lw $t2, 0($t0)               # Y[i]
            lw $t3, 0($t1)               # Y[j]
            slt $t4, $t2, $t3            # y[i] < y[j] 
            beq $t4, $zero, wh1_else     # j to wh1_else
            sll $t4, $s2, 2              # k * 4
            add $t4, $t4, $a1            # address of A[k]
            sw $t2, 0($t4)               # A[k] = Y[i]
            addi $s0, $s0, 1             # i++
            j end_while1                 # j to end_while1
 wh1_else:  sll $t4, $s2, 2              # k * 4
            add $t4, $t4, $a1            # address of A[k]
            sw $t3, 0($t4)               # A[k] = Y[j]
            addi $s1, $s1, 1             # j++
 end_while1:addi $s2, $s2, 1             # k++
            j while1                     # j to while1            
 while2:    slt $t0, $s0, $a3            # i < s2
            beq $t0, $zero, while3       # j to while3
            sll $t0, $s0, 2              # i * 4
            add $t0, $t0, $a0            # address of Y[i] 
            lw $t0, 0($t0)               # Y[i]
            sll $t1, $s2, 2              # k * 4
            add $t1, $t1, $a1            # address of A[k] 
            sw $t0, 0($t1)               # A[k] = Y[i] 
            addi $s0, $s0, 1             # i++
            addi $s2, $s2, 1             # k++ 
            j while2                     # j to while2 
 while3:    slt $t0, $s3, $s1            # e2 < j 
            bne $t0, $zero, pr_while4   # j pre_while4
            sll $t0, $s1, 2              # j * 4
            add $t0, $t0, $a0            # address of Y[j] 
            lw $t0, 0($t0)               # Y[j]
            sll $t1, $s2, 2              # k * 4
            add $t1, $t1, $a1            # address of A[k] 
            sw $t0, 0($t1)               # A[k] = Y[j] 
            addi $s1, $s1, 1             # j++
            addi $s2, $s2, 1             # k++ 
            j while3                     # j to while3 
 pr_while4: add $s0, $zero, $a2          # i = s1
 while4:    slt $t0, $s3, $s0            # e2 < i
            bne $t0, $zero, merge_ret    # j to merge_ret
            sll $t0, $s0, 2              # i * 4
            add $t1, $t0, $a1            # address A[i]
            lw $t1, 0($t1)               # A[i] 
            add $t0, $t0, $a0            # address of Y[i]
            sw $t1, 0($t0)               # Y[i] = A[i]
            addi $s0, $s0, 1             # i++
            j while4                     # j to while4 
 merge_ret: lw $s0, 0($sp)               # restore $s0
            lw $s1, 4($sp)               # restore $s1
            lw $s2, 8($sp)               # restore $s2
            lw $s3, 12($sp)              # restore $s3
            addi $sp, $sp, 20            # restore stack
            jr $ra                       # return to previous calling point  
