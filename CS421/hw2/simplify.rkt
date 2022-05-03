#lang racket
(define get-operator car)
(define get-exp1 cadr)
(define get-exp2 caddr)
(define get-exp3 cadddr)
(define subexpression? list?)
(define error! 'error)
(define unique-list (list))
(define same? equal?)

(define (zero? exp)
  (and (number? exp) (= 0 exp)))

(define (one? exp)
  (and (number? exp) (= 1 exp)))

(define (error? exp)
  (eq? error! exp))

(define (pick? exp)
  (eq? 'pick exp))

(define (three-expression-operator? exp)
  (pick? exp))

(define (operator? exp)
  (or
   [eq? '+ exp]
   [eq? '- exp]
   [eq? '* exp]
   [eq? '/ exp]
   [pick? exp]
   ))

(define (variable? exp)
  (and (symbol? exp) (not (operator? exp))))

(define (symbol<=? s1 s2)
  (string<=? (symbol->string s1) (symbol->string s2)))

(define (subexpression-case operator left right)
  (cond [(not (subexpression? left)) (list operator left right)]
        [(not (subexpression? right))(list operator right left)]
        [(symbol<=? (get-operator left) (get-operator right)) (list operator left right)]
        [else (list operator right left)]))

(define (variable-case operator left right)
  (cond [(number? left) (list operator right left)]
        [(number? right) (list operator left right)]
        [(symbol<=? left right) (list operator left right)]
        [else (list operator right left)]))
                                  
(define (simplification-case operator left right)
    (if (or [subexpression? left] [subexpression? right])
        (subexpression-case operator left right)
        (variable-case operator left right)))

(define (evaluate-pick exp1 exp2 exp3)
   (cond[(zero? exp1) exp2]
        [(error? exp1) error!]
        [(number? exp1) exp3]
        [(same? exp2 exp3) exp2]
        [else (list 'pick exp1 exp2 exp3)]))

(define (evaluate-three-expressions operator exp1 exp2 exp3)
  (cond [(pick? operator) (evaluate-pick exp1 exp2 exp3)]
        [else error!]))

(define (evaluate-plus exp1 exp2)
  (cond [(zero? exp1) exp2]
        [(zero? exp2) exp1]
        [(not (and (number? exp1) (number? exp2))) (simplification-case '+ exp1 exp2)] 
        [else (+ exp1 exp2)]))
  
(define (evaluate-minus exp1 exp2)
  (cond [(zero? exp2) exp1]
        [(same? exp1 exp2) 0]
        [(not (and (number? exp1) (number? exp2))) (list '- exp1 exp2)]
        [else (- exp1 exp2)]))

(define (evaluate-division exp1 exp2)
  (cond [(zero? exp2) error!]
        [(one? exp2) exp1]
        [(zero? exp1) 0]
        [(same? exp1 exp2) 1]
        [(not (and (number? exp1) (number? exp2))) (list '/ exp1 exp2)]
        [else (/ exp1 exp2)]))

(define (evaluate-multiply exp1 exp2)
  (cond [(zero? exp1) 0]
        [(zero? exp2) 0]
        [(one? exp1) exp2]
        [(one? exp2) exp1]
        [(not (and (number? exp1) (number? exp2))) (simplification-case '* exp1 exp2)]
        [else (* exp1 exp2)]))

(define (evaluate-two-expressions operator exp1 exp2)
  (cond [(or (error? exp1) (error? exp2)) error!]
        [(eq? '+ operator) (evaluate-plus exp1 exp2)]
        [(eq? '- operator) (evaluate-minus exp1 exp2)]
        [(eq? '* operator) (evaluate-multiply exp1 exp2)]
        [(eq? '/ operator) (evaluate-division exp1 exp2)]
        (else error!)))

(define (simplify exp)
  (cond [(null? exp) '()]
        [(subexpression? exp)
         (let ([operator (get-operator exp)])
           (cond ((three-expression-operator? operator) (evaluate-three-expressions operator (simplify (get-exp1 exp)) (simplify (get-exp2 exp)) (simplify (get-exp3 exp))))
                 (else (evaluate-two-expressions operator (simplify (get-exp1 exp)) (simplify (get-exp2 exp))))))]
        [else exp])) 

(define (footprint exp)
  (* 4 (calculate-footprint (flatten exp) unique-list)))

(define (calculate-footprint exp unq-list)
  (if (null? exp) 0
      (let ([item (car exp)])
        (if (and (or (variable? item) (number? item)) (not (member? item unq-list)))
            (+ 1 (calculate-footprint (cdr exp) (cons item unq-list)))
            (calculate-footprint (cdr exp) unq-list)))))

(define (member? item list)
  (cond [(null? list) #f]
        [(same? item (car list)) #t]
        [else (member? item (cdr list))]))

(define create-pair cons)
(define get-pair-exp caar)
(define get-pair-len cdar)
(define remove-first cdr)
(define create-let* (list))
(define let-name car)
(define let-exp cadr)

(define (optimize exp)
  (let ([simplified-exp (simplify exp)]) 
    (let ([common-list (create-common-list simplified-exp)])
      (if (null? common-list)
          simplified-exp   
          (replace-all-common simplified-exp common-list create-let*)))))

(define (apply-let-star let-star-list s-exp)
  (cond ([null? let-star-list] s-exp)
        (apply-let-star (cdr let-star-list) (replace-exp s-exp (let-name let-star-list)))))

(define (replace-exp exp s-exp)
  (cond ((or (null? exp) (not (subexpression? exp))) exp)
        ((same? (let-exp s-exp) exp) (let-name s-exp))
        (else (let ([operator (get-operator exp)])
                (let ([first-exp (replace-exp (get-exp1 exp) s-exp)])
                  (let ([second-exp (replace-exp (get-exp2 exp) s-exp)])
                    (if (three-expression-operator? operator)
                        (let ([third-exp (replace-exp (get-exp3 exp) s-exp)])
                          (list operator first-exp  second-exp third-exp))
                        (list operator first-exp second-exp))))))))

(define (add-to-let* let-exp s-exp)
  (append let-exp (list s-exp)))

(define (replace-all-common exp common-list let-star)
  (cond [(null? common-list) (list 'let* let-star exp)]
        [else (let ([S-EXP (get-pair-exp common-list)])
                (let  ([modified-S-EXP (apply-let-star let-star S-EXP)])
                  (let ([named-S-EXP (list (gensym) modified-S-EXP)])
                    (replace-all-common (replace-exp exp named-S-EXP) (remove-first common-list) (add-to-let* let-star named-S-EXP)))))]))
                                                  
(define (create-common-list simplified-exp)
  (common-unique-list (sort-by-length (length-list (remove-first (find-expressions simplified-exp))))))

(define (sort-by-length lst)
  (sort lst (lambda (x y) (< (cdr x) (cdr y)))))

(define (find-expressions exp)
  (if (or (null? exp) (not (subexpression? exp))) '()
      (let ([operator (get-operator exp)])
        (let ([first-exp (find-expressions (get-exp1 exp))])
          (let ([second-exp (find-expressions(get-exp2 exp))])
            (if (three-expression-operator? operator)
                (let ([third-exp (find-expressions(get-exp3 exp))])
                (append (list exp) first-exp  second-exp third-exp))
                (append (list exp) first-exp  second-exp)))))))

(define (common-unique-list lst)
  (cond [(null? lst) '()]
        [(member? (car lst) (remove-first lst)) (cons (car lst) (common-unique-list (remove-pair (get-pair-exp lst)(get-pair-len lst) (cdr lst))))]
        [else (common-unique-list (remove-pair (get-pair-exp lst)(get-pair-len lst) (cdr lst)))]))
                                                                                                                              
(define (remove-pair item len lst)
  (cond [(null? lst) '()]
        [(> (get-pair-len lst) len) lst]
        [(and (= len (get-pair-len lst)) (same? item (get-pair-exp lst))) (remove-pair item len (cdr lst))]
        [else (cons (car lst) (remove-pair item len (cdr lst)))]))

(define (length-list lst)
  (foldl (lambda (x y) (cons (create-pair x (get-length x)) y)) '() lst))

(define (get-length lst)
  (cond [(null? lst) 0]
        [(list? lst) (+ (get-length (car lst)) (get-length (cdr lst)))]
        [else 1]))

(provide simplify footprint optimize)
