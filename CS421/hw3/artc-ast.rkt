#lang racket

(require "./utils.rkt")
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; THESE CLASSES CORRESPOND TO THE ABSTRACT SYNTAX SUCH THAT A "PROGRAM"
;; REPRESENT A PARSE-TREE.  THESE FUNCTIONS OPERATE AT THE 'SYNTACTIC' LEVEL
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; (PROGRAM BODY)
(define program-body last)

(define (program-declarations pgm)
  (take-until (lambda (x) (not (stmt-is-declaration? x))) (cdr pgm))) ;; (car stmt) is 'program

;; (BLOCK S1...SN)
(define (block-body stmt)
  (drop-until (lambda (x) (not (stmt-is-declaration? x))) (cdr stmt))) ;; (car stmt) is 'block

(define (block-declarations stmt)
  (take-until (lambda (x) (not (stmt-is-declaration? x))) (cdr stmt))) ;; (car stmt) is 'block(define

;; (DECLARE TYPE VAR)
(define (declaration-type stmt)
  (cadr stmt))

(define (declaration-variable stmt)
  (caddr stmt))

;; (:= VAR EXP)
(define (assignment-var stmt)
  (cadr stmt))

(define (assignment-exp stmt)
  (caddr stmt))

;; (IF TEST THEN [ELSE])
(define (if-test stmt)
  (cadr stmt))

(define (if-then stmt)
  (caddr stmt))

(define (if-has-else? stmt)
  (= (length stmt) 4))

(define (if-else stmt)
  (cadddr stmt))

;; (WHILE TEST BODY)
(define (while-test stmt)
  (cadr stmt))

(define (while-body stmt)
  (caddr stmt))

;; (SPRINT LABEL EXP)
(define (sprint-has-exp? stmt)
  (and (list? stmt)
       (= (length stmt) 3)))

(define (sprint-label stmt)
  (cadr stmt))

(define (sprint-exp stmt)
  (caddr stmt))

;; (BINARY-OPERATOR E1 E2)
(define (is-binary-exp? exp)
  (and (list? exp)
       (member (car exp) '(+ - * / @ ? < > = <= >= & % ~))))

(define binary-op car)
(define binary-e1 cadr)
(define binary-e2 caddr)

;; (UNARY-OPERATOR E1)
(define (is-unary-exp? exp)
  (and (list? exp)
       (member (car exp) '(~ -))))

(define unary-op car)
(define unary-e1 cadr)

;; STATEMENT CHECKING PREDICATES
(define (stmt-is-block? stmt) (and (list? stmt) (equal? (car stmt) 'block)))
(define (stmt-is-assign? stmt) (and (list? stmt) (equal? (car stmt) ':=)))
(define (stmt-is-if? stmt) (and (list? stmt) (equal? (car stmt) 'if)))
(define (stmt-is-while? stmt) (and (list? stmt) (equal? (car stmt) 'while)))
(define (stmt-is-sprint? stmt) (and (list? stmt) (equal? (car stmt) 'sprint)))
(define (stmt-is-declaration? stmt) (and (list? stmt) (equal? (car stmt) 'declare)))

(provide
 program-body
 program-declarations
 block-body
 block-declarations
 declaration-type
 declaration-variable
 assignment-var
 assignment-exp
 if-test
 if-then
 if-else
 if-has-else?
 while-test
 while-body
 sprint-has-exp?
 sprint-label
 sprint-exp
 is-binary-exp?
 binary-op
 binary-e1
 binary-e2
 is-unary-exp?
 unary-op
 unary-e1
 stmt-is-block?
 stmt-is-assign?
 stmt-is-if?
 stmt-is-while?
 stmt-is-sprint?
 stmt-is-declaration?
 )
 