	.text	
	.globl		main
main:
	li		$t1, 20
	sw		$t1, k
	lw		$t1, k
	subu		$sp, $sp, 4
	sw		$t0, 4($sp)
	sw		$t1, 8($sp)
	jal		add
	lw		$t0, 4($sp)
	lw		$t1, 8($sp)
	addu		$sp, $sp, 8
	move		$t2, $v1
	sw		$t2, i
	lw		$t2, i
	li		$v0, 1
	move		$a0, $t2
	syscall	
	li		$v0, 4
	la		$a0, _s
	syscall	
	lw		$t2, k
	li		$v0, 1
	move		$a0, $t2
	syscall	
	li		$v0, 4
	la		$a0, _e
	syscall	
	li		$v0, 10
	syscall	
add:
	subu		$sp, $sp, 8
	sw		$ra, ($sp)
	sw		$t0, 4($sp)
	lw		$t0, 4($sp)
	li		$t1, 5
	add		$t2, $t0, $t1
	sw		$t2, 4($sp)
	lw		$t0, 4($sp)
	li		$t1, 30
	sle		$t2, $t0, $t1
	beq		$t2, 0, L1
	lw		$t0, 4($sp)
	subu		$sp, $sp, 4
	sw		$t0, 4($sp)
	sw		$t2, 8($sp)
	jal		add
	move		$t1, $v1
	move		$v1, $t1
L1:
	li		$t1, 5
	move		$v1, $t1
	lw		$t0, 4($sp)
	lw		$ra, ($sp)
	addu		$sp, $sp, 12
	jr		$ra
	.data	
	.align		4
i:	.word		0
k:	.word		0
_s:	.asciiz		" "
_e:	.asciiz		""
