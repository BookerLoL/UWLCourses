#lang racket

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; UTILITY FUNCTIONS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; this is for all error handling.
;; programmers don't use this function but
;; the interpreter calls this function to
;; signal some type of programmer error
(define (error msg)
  (raise msg))
;;  (display "ERROR: ")
;;  (display msg)
;;  (newline))

;; INPUT: Predication function p, and list vals
;; OUTPUT: A list of the elements of vals that occur before the first element in vals that satisfies p
(define (take-until p vals)
  (cond ((null? vals) '())
        ((p (car vals)) '())
        (else (cons (car vals) (take-until p (cdr vals))))))

;; INPUT: Predication function p, and list vals
;; OUTPUT: A list of the elements of vals beginning with the first element of vals that satisfies p
(define (drop-until p vals)
  (cond ((null? vals) '())
        ((p (car vals)) vals)
        (else (drop-until p (cdr vals)))))

(provide
 error
 take-until
 drop-until
 )
 