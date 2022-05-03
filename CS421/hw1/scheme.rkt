#lang racket
(define (interleave L1 L2)
  (cond ((null? L1) L2)
        (else (cons (car L1) (interleave L2 (cdr L1))))))

(define (key-store L1 KEY)
  (cond ((null? L1) '())
        ((equal? (caar L1) KEY) (cadar L1))
        (else (key-store (cdr L1) KEY))))

(define (list-replace ALIST SYM VAL)
  (cond ((null? ALIST) '())
        ((list? (car ALIST)) (cons (list-replace (car ALIST) SYM VAL) (list-replace (cdr ALIST) SYM VAL)))
        ((equal? (car ALIST) SYM) (cons VAL (list-replace (cdr ALIST) SYM VAL)))
        (else (cons (car ALIST) (list-replace (cdr ALIST) SYM VAL)))))

(define (first-n L1 N)
  (cond ((< N 1) '())
        ((> N (length L1)) L1)
        (else (cons (car L1) (first-n (cdr L1) (- N 1)))))) 

(define (forget-n L1 N)
  (cond ((< N 1) L1)
        ((> N (length L1)) '())
        (else (forget-n (cdr L1) (- N 1)))))

(define (accumlator L total)
  (cond ((null? L) '())
        (else (cons (+ (car L) total) (accumlator (cdr L) (+ (car L) total))))))

(define (running-sum L)
  (accumlator L 0))
        
(define (filter-out L item)
  (cond ((null? L) '())
        ((equal? (car L) item) (filter-out (cdr L) item))
        (else (cons (car L) (filter-out (cdr L) item)))))

(define (count L item)
  (cond ((null? L) 0)
        ((equal? (car L) item) (+ 1 (count (cdr L) item)))
        (else (count (cdr L) item))))

(define (counts XS)
  (cond ((null? XS) '())
        (else (cons (list (car XS) (count XS (car XS))) (counts (filter-out XS (car XS)))))))

(define (track L1 X index)
  (cond ((null? L1) '())
        ((equal? (car L1) X) (cons index (track (cdr L1) X (+ index 1))))
        (else (track (cdr L1) X (+ index 1)))))

(define (indices L1 X)
  (cond ((null? L1) '())
        (else (track L1 X 0))))

(define (join-together L1 L2)
  (cond ((null? L1) L2)
        ((null? L2) L1)
        ((<= (car L1) (car L2)) (cons (car L1) (join-together (cdr L1) L2)))
        (else (cons (car L2) (join-together L1 (cdr L2))))))

(define (merge-sorter L1)
  (let ((half (quotient (length L1) 2)))
    (if (zero?  half) L1
     (join-together (merge-sorter (first-n L1 half))
            (merge-sorter (forget-n L1 half))))))

(define (contains? L item)
  (cond ((null? L) #t)
        ((equal? (car L) item) #f)
        (else (contains? (cdr L) item))))

(define (unique L unique-list)
  (cond ((null? L) unique-list)
        ((contains? unique-list (car L)) (unique (cdr L) (append unique-list (list (car L)))))
        (else (unique (cdr L) unique-list))))

(define (flatten-list L)
  (cond ((null? L) '())
        ((list? (car L)) (append (flatten-list (car L)) (flatten-list (cdr L))))
        (else (cons (car L) (flatten-list (cdr L))))))
  
(define (graph-x graph not-visited-nodes)
  (cond ((null? not-visited-nodes) '())
        (else  (cons (cons (car not-visited-nodes) (list (find-adj graph (car not-visited-nodes)))) (graph-x graph (cdr not-visited-nodes))))))

(define (find-adj graph node)
  (cond ((null? graph) '())
        ((eq? (caar graph) node) (cons (cadar graph)(find-adj (cdr graph) node)))
        (else (find-adj (cdr graph) node))))

(define unique-node-list
  (lambda (g)
    (unique (flatten-list g) (list))))

(define (el-graph->x-graph g)
  (if (null? g) '()
      (graph-x g (unique-node-list g))))

(define (set-edges targets source-node)
  (cond ((null? targets) '())
        (else (cons (cons source-node (list (car targets))) (set-edges (cdr targets) source-node)))))

(define (x-graph->el-graph g)
  (cond ((null? g) '())
        ((append (set-edges (cadar g) (caar g)) (x-graph->el-graph (cdr g))))))

(provide interleave key-store list-replace first-n forget-n running-sum counts indices join-together merge-sorter el-graph->x-graph x-graph->el-graph)