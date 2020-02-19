#lang racket
(define create-node
  (lambda (val left right)
    (list val left right)))

(define (create-val-node val)
  (create-node val (list) (list)))

(define left cadr)
(define right caddr)
(define val car)

(define (get-node node part)
  (if (null? node) '()
      (part node)))

(define (not-P-value P h1 h2)
  (if (equal? P h1) h2
      h1))

(define (get-higher-priority h1 h2 f)
  (cond ((null? h1) h2)
        ((null? h2) h1)
        ((f (get-node h1 val) (get-node h2 val)) h1)
        (else h2)))

(define (heap-create)
  (list))

(define (heap-is-empty? h)
  (null? h))

(define (heap-size h)
  (cond ((null? h) 0)
        (else (+ (+ 1 (heap-size (get-node h left))) (heap-size (get-node h right))))))

(define (heap-merge h1 h2 f)
  (cond [(null? h1) h2]
        [(null? h2) h1]
        [else
          (let ([P (get-higher-priority h1 h2 f)])
            (let ([Q (not-P-value P h1 h2)] [R-right (get-node P left)])
              (let ([R-left (heap-merge (get-node P right) Q f)])
              (create-node (get-node P val) R-left R-right))))]))

(define (heap-insert h x f)
  (heap-merge h (create-val-node x) f))

(define (heap-peek h)
  (if (heap-is-empty? h) (list)
      (get-node h val)))

 (define (heap-contains? h x heq)
   (cond ((null? h) #f)
         ((heq (get-node h val) x) #t)
         (else (or (heap-contains? (get-node h left) x heq) (heap-contains? (get-node h right) x heq)))))

(define (heap-remove h f)
  (heap-merge (get-node h left) (get-node h right) f))

(define (heap-number-before h x f)
  (cond ((null? h) 0)
        ((f (get-node h val) x)  (+ 1 (+ (heap-number-before (get-node h left) x f) (heap-number-before (get-node h right) x f))))
        (else 0)))

(define (list->heap xs f)
  (foldl (lambda (item heap) (heap-insert heap item f)) (heap-create) xs))

(define (heap->list h f)
  (cond ((null? h) '())
        (else (cons (heap-peek h) (heap->list (heap-remove h f) f)))))

(provide heap-create heap-is-empty? heap-size heap-merge heap-insert heap-peek heap-contains? heap-remove heap-number-before list->heap heap->list)
