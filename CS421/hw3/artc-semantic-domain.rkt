#lang racket

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; THE ENTRY DATA TYPE
;; Associates a KEY with a VALUE
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (entry-create k v)
  (cons k (cons v '())))

(define entry-key car)
(define entry-value cadr)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; THE MAP DATA TYPE
;; Supports map-create, map-get, map-contains, map-replace, map-add and map-delete
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; A MAP is a list of key-value pairs
;; INPUT: NONE
;; OUTPUT: An empty map
(define (map-create) '())

;; INPUT: a MAP and a KEY
;; OUTPUT: The value associated with key k or 'error
(define (map-get map k)
  (cond ((null? map) 'error)
        ((equal? (entry-key (car map)) k) (entry-value (car map)))
        (else (map-get (cdr map) k))))

;; INPUT : A MAP AND KEY
;; OUTPUT : true if the key is in the map and false otherwise
(define (map-contains map k)
  (cond ((null? map) #f)
        ((equal? (entry-key (car map)) k) #t)
        (else (map-contains (cdr map) k))))

;; INPUT : A MAP, KEY and VALUE
;; OUTPUT: The map that results from replacing the key with the new value.  If
;; the map doesn't contain KEY, then 'error is returned
(define (map-replace map key val)
  (cond ((null? map) (raise "replace: no key " (pretty-format key)))
        ((equal? (entry-key (car map)) key)
         (cons (entry-create key val) (cdr map)))
        (else
         (cons (car map) (map-replace (cdr map) key val)))))

;; INPUT : A MAP, Key and Value
;; OUTPUT : The map that results from adding a key-value pair.  This
;; allows for duplicate keys (the most-recently added is nearer the front of the list
(define (map-add map key val)
  (cons (entry-create key val) map))

;; INPUT: A MAP and KEY
;; OUTPUT: The map that results from deleting the key.  No errors occur if the map
;; doesn't contain the key
(define (map-delete map key)
  (cond ((null? map) map)
        ((equal? (entry-key (car map)) key) (cdr map))
        (else (cons (car map)
                    (map-delete (cdr map) key)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; TYPEMAP : A SEMANTIC DOMAIN DATA TYPE
;; A TYPEMAP is a mapping between variables and types
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; INPUT: NONE
;; OUTPUT: AN empty typemap
(define typemap-create map-create)

(define typemap-contains map-contains)

;; INPUT: A TYPEMAP tm and Variable x
;; OUTPUT: the type of x
(define typemap-type-of map-get)

;; INPUT: A TYPEMAP tm and DECLARATION decl
;; OUTPUT: THE TYPEMAP THAT RESULTS FROM PROCESSING A DECLARATION

(define (typemap-add tm var type)
  (map-add tm var type))

;; INPUT: A TYPEMAP tm and VARIABLE v
;; OUTPUT: The TYPEMAP that results from delete v from tm
(define typemap-delete map-delete)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; STATE : A SEMANTIC DOMAIN DATA TYPE
;; A STATE is a mapping between variables and values
;; NOTE: A map can contain duplicate keys but innermost KEYS occur
;;       before outermost KEYS and hide them
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; INPUT : NONE
;; OUTPUT: AN EMPTY STATE
(define state-create map-create)
  
;; INPUT: STATE state and VARIABLE v
;; OUTPUT: a new state such that the innermost scope now contains a
;;         new binding for the specified ID.  The bindings value is 'undefined.
(define (state-add state v)
  (map-add state v 'undefined))

;; INPUT : STATE and VARIABLE v
;; OUTPUT: A new state such that the innermost v is removed
(define (state-delete state v)
  (map-delete state v))

;; INPUT: STATE and VARIABLE v
;; OUTPUT: The value associated with the specified ID in the given state
(define (state-get-value state v)
  (map-get state v))
  
;; INPUT: STATE state, VARIABLE v, VALUE value
;; OUTPUT: The new STATE that results from changing the mapping from v->value in state
(define (state-update state id value)
  (map-replace state id value))

;; INPUT: STATE and LIST-OF-IDS (VARIABLES)
;; OUTPUT: A new state that results from deleting all ids (the variables) from
;;         the specified state
(define (state-delete-all state variables)
  (cond ((null? variables) state)
        (else (state-delete-all (state-delete state (car variables)) (cdr variables)))))

(provide
 typemap-create
 typemap-contains
 typemap-add
 typemap-type-of
 typemap-delete
 state-create
 state-add
 state-delete
 state-get-value
 state-update
 state-delete-all
)
 