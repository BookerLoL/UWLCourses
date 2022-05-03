#lang racket
(require "./artc-ast.rkt")
(require "./utils.rkt")
(require "./artc-semantic-domain.rkt")

(define boolean 'boolean)
(define true 'true)
(define false 'false)
(define int 'int)
(define int-max 2147483647)
(define int-min -2147483648)
(define op-op car)
(define op-e1 cadr)
(define op-e2 caddr)
(define binary-int->int '(+ - * / @ ?))
(define binary-int->boolean '(< > = <= >=))
(define binary-boolean->boolean '(& %))
(define unary-boolean->boolean '(~))
(define unary-int->int '(-))
(define unary-arity 2)
(define keywords '(program block declare if while sprint boolean int true false))
(define member? memq)
(define is-letter? char-alphabetic?)
(define is-digit? char-numeric?)
(define same? eq?)
(define map-key caar)
(define map-value cadar)

(define missing-program-error "Missing keyword: program")
(define var-start-error "Variable must start with a letter")
(define var-syntax-error "Variable can only have letters and numbers")
(define var-keyword-error "Variable can not be a keyword")
(define expression-syntax-error "Expression is not in a valid form")
(define undefined-operator-error "That operator is undefined")
(define missing-boolean-arg-error "Expected a valid boolean value")
(define missing-int-arg-error "Expected a valid int value")
(define scheme-boolean-error "Expected a scheme boolean value")
(define unknown-stmt-keyword-error "Unknown statement keyword used")
(define type-mismatch-error "Types do not match")
(define undefined-type-error "Undefined type error")
(define redeclaration-error "Cannot redeclare a variable")
(define undeclared-variable-error "Variable is undeclared")
(define label-not-string-error "Label is not a string type")

(define (print msg)
  (display msg)
  (newline))

(define (stmt-is-program? stmt)
  (cond [(and (list? stmt) (same?  (first stmt) 'program)) #t]
        [else (print missing-program-error) #f]))

(define (stmt-is-assign? stmt)
  (and (list? stmt) (same?  (first stmt) ':=)))

(define (is-program-valid? pgm)
  (and (stmt-is-program? pgm)
       (is-block-valid? (program-body pgm) (tm-add-all (program-declarations pgm) (typemap-create)))))

(define (tm-add-all declrs map)
  (if (null? declrs) map
      (let* ([declr (first declrs)] [var-name (declaration-variable declr)] [var-type (declaration-type declr)])
        (cond [(not (is-var-name-valid? var-name)) #f]
              [(not (is-type-valid? var-type)) #f]
              [(typemap-contains map var-name) (print redeclaration-error) #f]
              [else (tm-add-all (rest declrs) (typemap-add map var-name var-type))]))))

(define (is-type-valid? exp)
  (cond [(or (is-int-type? exp) (is-boolean-type? exp)) #t]
        [else (print undefined-type-error) #f]))

(define (is-int-type? exp)
  (same?  int exp))

(define (is-boolean-type? exp)
  (same?  boolean exp))

(define (is-var-name-valid? name)
  (let* ([str (symbol->string name)] [char-lst (string->list str)])
    (cond [(not (is-letter? (first char-lst))) (print var-start-error) #f]
          [(not (is-each-char-valid? (rest char-lst))) (print var-syntax-error) #f]
          [(member? name keywords) (print var-keyword-error) #f]
          [else #t])))
    
(define (is-each-char-valid? char-lst)
  (cond [(null? char-lst) #t]
        [(or (is-letter? (first char-lst)) (is-digit? (first char-lst))) (is-each-char-valid? (rest char-lst))]
        [else #f]))

(define (update-outer-tm inner-tm outer-tm)
  (if (null? inner-tm) outer-tm 
      (let ([key (map-key inner-tm)])
        (if (typemap-contains outer-tm key)
            (update-outer-tm (rest inner-tm) (typemap-add (typemap-delete outer-tm key) key (map-value inner-tm)))
            (update-outer-tm (rest inner-tm) (typemap-add outer-tm key (map-value  inner-tm)) )))))

(define (is-body-valid? stmt-list tm)
  (cond [(null? stmt-list) #t]
        [(is-stmt-valid? (first stmt-list) tm) (is-body-valid? (rest stmt-list) tm)]
        [else #f]))

(define (is-stmt-valid? stmt tm)
  (or (is-block-valid? stmt tm)
      (is-assign-valid? stmt tm)
      (is-if-valid? stmt tm)
      (is-while-valid? stmt tm)
      (is-sprint-valid? stmt tm)))

(define (is-block-valid? stmt outer-tm)
  (and (stmt-is-block? stmt) outer-tm 
       (let ([inner-tm (tm-add-all (block-declarations stmt) (typemap-create))])
         (and inner-tm 
              (let ([updated-tm (update-outer-tm inner-tm outer-tm)])
                (is-body-valid? (block-body stmt) updated-tm))))))

(define (is-assign-valid? stmt tm)
  (and (stmt-is-assign? stmt)
       (let ([var (assignment-var stmt)] [exp (assignment-exp stmt)])
         (cond [(not (is-declared-var? var tm)) (print undeclared-variable-error)  #f]
               [(not (is-exp-valid? exp tm)) #f]
               [(not (same?  (typemap-type-of tm var) (expr-type-of exp tm))) (print type-mismatch-error) #f]
               [else #t]))))
               
(define (is-if-valid? stmt tm)
  (and (stmt-is-if? stmt)
       (is-operand-valid? (if-test stmt) tm is-boolean-type? missing-boolean-arg-error)
       (is-stmt-valid? (if-then stmt) tm)
       (if (if-has-else? stmt)
           (is-stmt-valid? (if-else stmt) tm)
           #t)))

(define (is-while-valid? stmt tm)
  (and (stmt-is-while? stmt)
       (is-operand-valid? (while-test stmt) tm is-boolean-type? missing-boolean-arg-error)
       (is-stmt-valid? (while-body stmt) tm)))

(define (is-sprint-valid? stmt tm)
  [cond [(not (stmt-is-sprint? stmt)) #f]
        [(not (string? (sprint-label stmt))) (print label-not-string-error) #f]
        [else (if (sprint-has-exp? stmt)
                  (is-exp-valid? (sprint-exp stmt) tm)
                  #t)]])

(define (is-exp-valid? exp tm)
  (or (is-int-value-valid? exp)
      (is-boolean-type-valid?  exp)
      (is-declared-var? exp tm)
      (is-unary-valid? exp tm)
      (is-binary-valid? exp tm)))

(define (is-int-value-valid? exp)
  (and (integer? exp) (is-within-int-range? exp))) 

(define (is-within-int-range? num)
  (cond [(and (>= num int-min) (<= num int-max)) #t]
        [else (print missing-int-arg-error) #f]))

(define (is-boolean-type-valid?  exp)
  (or (same?  true exp) (same?  false exp)))

(define (is-declared-var? exp tm)
  (typemap-contains tm exp))

(define (is-binary-valid? exp tm)
  (and (list? exp)
       (or (is-binary-int->int-valid? exp tm)
           (is-binary-int->boolean-valid?  exp tm)
           (is-binary-boolean->boolean-valid?  exp tm))))

(define (is-binary-int->int-valid? ops tm)
  (and (member? (op-op ops) binary-int->int)
       (is-operand-valid? (op-e1 ops) tm is-int-type?  missing-int-arg-error)  
       (is-operand-valid? (op-e2 ops) tm is-int-type?  missing-int-arg-error)))

(define (is-binary-int->boolean-valid?  ops tm)
   (and (member? (op-op ops) binary-int->boolean)
        (is-operand-valid? (op-e1 ops) tm is-int-type? missing-int-arg-error)  
        (is-operand-valid? (op-e2 ops) tm is-int-type? missing-int-arg-error)))
 
(define (is-binary-boolean->boolean-valid?  ops tm)
   (and (member? (op-op ops) binary-boolean->boolean)
        (is-operand-valid? (op-e1 ops) tm is-boolean-type? missing-boolean-arg-error)
        (is-operand-valid? (op-e2 ops) tm is-boolean-type? missing-boolean-arg-error)))

(define (is-unary-valid? exp tm)
  (and (list? exp)
       (<= (length exp) unary-arity)
       (or
        (is-unary-int->int-valid?  exp tm)
        (is-unary-boolean->boolean-valid?  exp tm)
        (is-exp-valid? (first exp) tm))))

(define (is-unary-int->int-valid?  ops tm)
  (and (member? (op-op ops) unary-int->int)
       (is-operand-valid? (op-e1 ops) tm is-int-type? missing-int-arg-error)))

(define (is-unary-boolean->boolean-valid?  ops tm)
   (and (member? (op-op ops) unary-boolean->boolean)
        (is-operand-valid? (op-e1 ops) tm is-boolean-type?  missing-boolean-arg-error)))

(define (is-operand-valid? exp tm predicate error-msg)
  (cond [(not (is-exp-valid? exp tm)) #f]
        [(not (predicate (expr-type-of exp tm))) (print error-msg) #f]
        [else #t]))
         
(define (expr-type-of exp tm)
  (cond [(integer? exp) int]
        [(is-boolean-type-valid?  exp) boolean]
        [(symbol? exp) (typemap-type-of tm exp)]                             
        [(list? (first exp)) (expr-type-of (first exp) tm)]
        [else (op-type-of (first exp) tm)]))

(define (op-type-of exp tm)
  (cond [(or (member? exp unary-int->int) (member? exp binary-int->int)) int]
        [(or (member? exp binary-int->boolean) (member? exp binary-boolean->boolean) (member? exp unary-boolean->boolean)) boolean]  
        [else (expr-type-of exp tm)]))

(define (logical-negation bool)
  (cond [(not (strict-valid-boolean? bool)) (error missing-boolean-arg-error)]
        [(same? false bool) true]
        [else false]))

(define (logical-or bool1 bool2)
  (cond [(not (and (strict-valid-boolean? bool1) (strict-valid-boolean? bool2))) (error missing-boolean-arg-error)]
        [(or (same?  true bool1) (same? true bool2)) true]
        [else false])) 

      
(define (logical-and bool1 bool2)
  (cond [(not (and (strict-valid-boolean? bool1) (strict-valid-boolean? bool2))) (error missing-boolean-arg-error)]
        [(and (same?  true bool1) (same? true bool2)) true]
        [else false])) 

(define (get-variable-list states)
  (if (null? states) '()
      (cons (map-key states) (get-variable-list (rest states)))))

(define (eval-name name)
   (let* ([str (symbol->string name)] [char-lst (string->list str)])
    (cond [(not (is-letter? (first char-lst))) (error var-start-error)]
          [(not (is-each-char-valid? (rest char-lst))) (error var-syntax-error)]
          [(member? name keywords) (error var-keyword-error)]
          [else name])))

(define (eval-declared-type type)
  (if (or (is-int-type? type) (is-boolean-type? type))
      type
      (error undefined-type-error)))

(define (eval-type value)
  (cond [(integer? value) int]
        [(is-boolean-type-valid? value) boolean]
        [(error undefined-type-error)]))
      
(define (map-add-all declrs tm)
  (if (null? declrs) tm
      (let* ([declr (first declrs)] [var-name (eval-name (declaration-variable declr))] [var-type (eval-declared-type(declaration-type declr))])
        (if (not (typemap-contains tm var-name))
            (map-add-all (rest declrs) (typemap-add tm var-name var-type))
            (error redeclaration-error)))))

(define (merge-maps inner-map outer-map)
  (if (null? inner-map) outer-map
      (merge-maps  (rest inner-map) (typemap-add (typemap-delete outer-map (map-key inner-map)) (map-key inner-map) (map-value  inner-map)))))

(define (fill-undefined-state state variables)
  (if (null? variables) state
      (fill-undefined-state (state-add state (first variables)) (rest variables))))

(define (interpret-program pgm)
  (cond [(not (same? (first pgm) 'program)) (error missing-program-error)]
        [else (let ([typemap (map-add-all (program-declarations pgm) (typemap-create))])
                (eval-block (program-body pgm) typemap (fill-undefined-state (state-create) (get-variable-list typemap))))]))

(define (eval-block stmt tm states)
  (let* ([new-tm (map-add-all (block-declarations stmt) (typemap-create))] 
         [new-variables (get-variable-list new-tm)]
         [merged-tm (merge-maps new-tm tm)]
         [merged-states (merge-maps (fill-undefined-state (state-create) new-variables) states)])
    (merge-maps (state-delete-all (eval-body (block-body stmt) merged-tm merged-states) new-variables) states)))

(define (eval-body stmt tm states)
  (if (null? stmt) states
      (eval-body (rest stmt) tm (eval-statement (first stmt) tm states))))

(define (eval-statement stmt tm states)
  (let ([stmt-word (first stmt)])
    (cond [(same? ':= stmt-word) (eval-assign stmt tm states)]
          [(same? 'block stmt-word) (eval-block stmt tm states)]
          [(same? 'if stmt-word) (eval-if stmt tm states)]
          [(same? 'while stmt-word) (eval-while stmt tm states)]
          [(same? 'sprint stmt-word) (eval-sprint stmt tm states)]
          [else (error unknown-stmt-keyword-error)])))

(define (eval-assign stmt tm states)
  (let ([var-name (eval-name (assignment-var stmt))] [var-value (eval-exp (assignment-exp stmt) states)])
    (if (same? (typemap-type-of tm var-name) (eval-type var-value))
        (state-update states var-name var-value)
        (error type-mismatch-error))))

(define (eval-if stmt tm states)
  (if (convert-scheme-boolean(eval-exp (if-test stmt) states))
      (eval-statement (if-then stmt) tm states)
      (if (if-has-else? stmt)
          (eval-statement (if-else stmt) tm states)
          states)))

(define (eval-while stmt tm states)
  (if (convert-scheme-boolean (eval-exp (while-test stmt) states))
      (eval-while stmt tm (eval-statement (while-body stmt) tm states))
      states))

(define (eval-sprint stmt tm states)
  (cond [(not (string? (sprint-label stmt))) (error label-not-string-error)]
        [(sprint-has-exp? stmt) (display (sprint-label stmt)) (display (eval-type (eval-exp (sprint-exp stmt) states))) (newline) states]
        [else (print states) states]))

(define (eval-exp exp state)
  (cond [(integer? exp) (strict-valid-int? exp)]
        [(is-boolean-type-valid? exp) exp]
        [(symbol? exp) (state-get-value state exp)]
        [(list? exp) (eval-operation exp state)]
        [else (error expression-syntax-error)]))

(define (eval-operation exp state)
  (if (<=  (length exp) unary-arity)
      (eval-unary exp state)
      (eval-binary exp state)))

(define (eval-unary exp state)
  (let ([operator (op-op exp)])
    (cond [(member? operator unary-boolean->boolean) (eval-unary-boolean->boolean exp state)] 
          [(member? operator unary-int->int) (strict-valid-int? (eval-unary-int->int exp state))]  
          [(list? exp) (eval-exp (first exp) state)]
          [else (error undefined-operator-error)])))

(define (eval-binary exp state)
   (let ([operator (op-op exp)])
    (cond [(member? operator binary-boolean->boolean) (eval-binary-boolean->boolean exp state)]
          [(member? operator binary-int->int) (strict-valid-int? (eval-binary-int->int exp state))]
          [(member? operator binary-int->boolean) (convert-boolean (eval-binary-int->boolean exp state))]
          [else (error undefined-operator-error)])))

(define (eval-unary-boolean->boolean exp state)
  (logical-negation (eval-exp (op-e1 exp) state)))

(define (eval-unary-int->int exp state)
  (- (eval-exp (op-e1 exp) state)))

(define (eval-binary-boolean->boolean exp state)
  (let ([op (op-op exp)]
        [oper1 (eval-exp (op-e1 exp) state)]
        [oper2 (eval-exp (op-e2 exp) state)])
    (cond [(same? '& op) (logical-and oper1 oper2)]
          [else (logical-or oper1 oper2)])))

(define (eval-binary-int->int exp state)
  (let ([op (op-op exp)]
        [oper1 (eval-exp (op-e1 exp) state)]
        [oper2 (eval-exp (op-e2 exp) state)])
    (cond [(same? '+ op) (+ oper1 oper2)]
          [(same? '- op) (- oper1 oper2)]
          [(same? '* op) (* oper1 oper2)]
          [(same? '/ op) (/ oper1 oper2)]
          [(same? '@ op) (expt oper1 oper2)]
          [else (remainder oper1 oper2)])))

(define (eval-binary-int->boolean exp state)
   (let ([op (op-op exp)]
         [oper1 (eval-exp (op-e1 exp) state)]
         [oper2 (eval-exp (op-e2 exp) state)])
     (cond [(same? '< op) (< oper1 oper2)]
          [(same? '> op) (> oper1 oper2)]
          [(same? '= op) (= oper1 oper2)]
          [(same? '<= op) (<= oper1 oper2)]
          [else (>= oper1 oper2)])))

(define (convert-boolean value)
  (cond [(same? #t value) true]
        [(same? #f value) false]
        [else (error scheme-boolean-error)]))

(define (convert-scheme-boolean value)
  (cond [(same? true value) #t]
        [(same? false value) #f]
        [else (error missing-boolean-arg-error)]))

(define (strict-valid-boolean? value)
  (if (or (same?  true value) (same?  false value))
      value
      (error missing-boolean-arg-error)))

(define (strict-valid-int? value)
  (if (and (>= value int-min) (<= value int-max))
      value
      (error  missing-int-arg-error)))

(provide is-program-valid? interpret-program)
