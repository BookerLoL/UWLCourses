# MIPS Simple Program
# Ethan Booker

# begin data segment
             .data
reverse:     .asciiz "The array in reverse is: "       # 1st message of the program
             .align 2                                   # align data segment after each string
num:         .word 5                                     # hold user input data
newline:     .asciiz "\n"                               # creates new line 
             .align 2                                   # align data segment after each string
comma_space: .asciiz ", "                               # seperates data
             .align 2                                   # align data segment after each string
largest:     .asciiz "The largest element is: "        # 2nd message of the program
             .align 2                                   # align data segment after each string
finish:      .asciiz "Thank you and have a nice day!"  # final message of the program
             .align 2                                   # align data segment after each string
arr:         .space 400                                 # size 100 int array

# end of data segment

#===================================================================================

# begin text segment 
         .text                         # instructions start here
MAIN:     addi $v0, $zero, 5         # set v0 to read int
          syscall                    # read user
          addi $s0, $v0, 0            # set array size at $s0 
          beq $s0, $zero, END         # check size of array
          addi $s1, $zero, 0         # setting counter at $s1
          la $s2, arr                 # loading address of arr at $2    
FOR:      slt $t0, $s1, $s0         # counter < size
          beq $t0, $zero, REVERSE     # go to REVERSE when done
          addi $v0, $zero, 5         # set v0 to read int
          syscall                    # read user
          sll $t1, $s1, 2              # i * 4
          add $t1, $t1, $s2         # address of A[i]
          sw $v0, 0($t1)              # store value at A[i]
          addi $s1, $s1, 1            # counter++
          j FOR                        # loop! 
REVERSE:  la  $s4, reverse           # loading address of string reverse to $s4
          add $a0, $zero, $s4         # adding reverse to be printed
          addi $v0, $zero, 4         # setting mode to string
          syscall                     # printing
          la $s5, comma_space        # loading address of comma_space to $s5
          addi $s3, $s0, -1            # size - 1
          sll $s3, $s3, 2             # size * 4 to get location size-1 index 
          add $s3, $s3, $s2         # address of A[last_idx]
          lw $t0, 0($s3)            # loading value of A[last_idx]
          addi $s4, $t0, 0             # gets a starting max value and store at $s3           
FOR2:     slti $t1, $s1, 2             # counter > 1, stop at first index of array
          bne $t1, $zero, REVERSE2     # counter < 1
          add $a0, $zero, $t0         # set address to be printed with A[counter] 
          addi $v0, $zero, 1        # set v0 to print int mode
          syscall                    # printing int 
          add $a0, $zero, $s5        # set address to be printed with comma_space
          addi $v0, $zero, 4        # set v0 to print string mode
          syscall                    # printing 
          addi $s3, $s3, -4          # shifting s3 to A[counter-1] 
          lw $t0, 0($s3)            # get A[counter-1] value
          addi $s1, $s1, -1            # counter-- 
          slt $t1, $s4, $t0            # checking max < A[counter-1]
          bne $t1, $zero, MAX        # max < A, jump to MAX
          j FOR2                     # jump back to top of loop
MAX:      addi $s4, $t0, 0             # switching the max value
          j FOR2                     # return back to loop     
REVERSE2: lw $t0, 0($s2)               # load A[0] value
          add $a0, $zero, $t0         # set address with A[0] value
          addi $v0, $zero, 1        # set v0 to print int mode
          syscall                    # printing int
          la $s5 newline            # load newline to $s5
          add $a0, $zero, $s5        # set address with newline
          addi $v0, $zero, 4        # set v0 to print string mode
          syscall                    # printing
          j LARGEST                  # jump to LARGEST
LARGEST:  la $s1, largest             # load largest to $s1
          add $a0, $zero, $s1        # set address with largest 
          addi $v0, $zero, 4        # set v0 to print string mode
          syscall                    # printing
          add $a0, $zero, $s4        # set address with max
          addi $v0, $zero, 1        # set v0 to print int mode
          syscall                    # printing
          add $a0, $zero, $s5        # set address with newline
          addi $v0, $zero, 4        # set v0 to print string mode
          syscall                    # printing
          j END                        # jump to END
END:      la $s1, finish             # load finish to $s1
          add $a0, $zero, $s1        # set address with finish
          addi $v0, $zero, 4        # set v0 to print string mode
          syscall                    # printing
    
