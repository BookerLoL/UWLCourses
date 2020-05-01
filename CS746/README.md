# **CS746 Notes**

## **Software Models**

Model
: An abstraction to help us understand something

### **Characteristics of Model**

1) Abstraction
    - only most important things modeled
2) Expressiveness
    - express some property of interest
3) Implementable: 
    - impl with same properties 
4) Relationships: 
    - if 1+ model for system, must have a relationship 



### **Modeling Notation**
- Descriptive (descr intended properties)
- Expressiveness (understand & derive additional properties)
- Analysis (informal/formal/rigirous)
- Refinement


### **Modeling Maturity Level**

0) No Specs
    - No specs
    - model: no descr
1) Textual 
    - specs in text
    - model: desc in text/no descr
2) Text and Diagrams
    - specs in text & diagrams
    - models: diagrammatic & weak semantics
3) Models with text
    - Level 2 improved
4) Precise models
    - specs are precise
    - model has precise notation & more semantics
    - possible to automate refinement of model to sys



### **Types of Modeling**

- Data/Info 
    - ER diagrams, Data Flow diagrams
- Behavior 
    - Use case analysis, FSM, state charts, petri nets, fault tree analysis
- Architectural 
    - Design patterns, Service-oriented architecture, distributed sys, component-based sys
- Enterprise modeling 
    - Workflow models, Business processing modeling, Organzational structs
- Mathematical models 
    - formal specs, safety-critical systems


---
## **Software Architecture**

Collection of structural, behavioral, and interface components, with design decisions. 

Belongs to data-centric/process-centric/client-server

### **Goals**
    define sys in uniform manner

    abstraction and separation of concern

    reason about structural and behavioral components

### **Design Plan**
    Abstraction to manage complexity, basis comm between stakeholders, and a blueprint for implementation


### **Sys vs Software**
System

- mapping software -> architecture 
- interactions -> hardware and software components
- reasoning of sys qualities (power/speed/etc)


Software
    
- software components & relationships
- interaction -> software components
- software qualities (performance/reliability/etc)


### **Quality Attribute** 
- measurable of testable property eval sys satisfies stakeholder needs  (Funct/nonfunc reqs, perf constrants, etc)

- Software Architecture helps eval quality attribute of sys


### Architectural View
- particular perspective (modular structures, components and connectors, allocation structures)
- viewpoint: descr of model & analysis of view 


### Modular Structures
- modules (classes/layers/subsys)
- **_static_** relationships
- UML class diagram, Seven-layer diagram 
- Can answer
    - functionality responsibility of a module
    - which modules interacting with given module
    - depedency relationships given module

### Components and Connectors
- run-time behavioral components (objs, clients, servers) and interactions 
- UML Seq and State Diagrams
- make dec on runtime properties (perf/availability/reliability)
- Can answer 
    - which data is shared? important? stored where?
    - how info pass through sys
    - sys parts perform concurrently/parallel

### Allocation Structures
- sys related to entities in env (db, file sys, networks)
- choose right hardward/env unit for sys
- can answer
    - which processor selected to run algo
    - types of file sys used and approp for sys 
    - protocol and data comm between sys and external entity

--- 

## **Styles of Software Architectures**

### **Data Centric Software Architecture** 
- data store contains major data elements 
- data store: **data, meta data, common interface**
- heterogenous sys share data via data store
- each sys impl interface to access data store data
- large db, data mine, data repo


![Data centric](./img/data-centric.png)

Pros
- Uniform data acess via common interface
- non-duplicated and sharable data

Difficulties
- hard to maintain, esp when data changed since other sys need to update that data value
- new daya needs meta data and interface

Ex
- Airline reservation sys, hotels, online application sys by UW-System

### **Data Flow / Pipe-and-Filter** 
- set of heterogeneous sys connected via data flow 
    - may change data when passes through
    - each sys indepedent in handling the data
- process data via stages to final output 

![Pipe and Filter](./img/pipe-and-filter.png)

Pros
- easy to impl, each sys indepen
- each sys determines the format of input and output

Difficultues
- most apps use diff sys at diff stages of data flow making connection harder


Ex
- compilers, computer graphics, order processing (Boeing, Airbus)

### **Layered/n-tier Architecture**
- Several layers, each with componenets/modules, 1 ore fewer task

![n-tier](./img/n-tier.png)

Pros
- easy to assign work to diff groups of devs
- easy to maintain

Difficulties
- slow performance, services go through several layers

Ex
- 3 tier: GUI/presentation, app, db
- 4 tier: GUI/presentation, business, app, db
- ISO OSI network: 7 layesrs

### **Client Server** 
- server provides set of services
- comm via network (distributed systems)
    - client interface service reqs
    - comm synchronous/asynchronous (don't wait)

- Client, Communication network, Server
Pros
- each sys indepdent 
- several clients servered capability same time

Difficulties
- severs must be reliable
- changing interface -> probs for clients

Ex
- Online banking, shopping, J RMI


### **Service Oriented Architecture**
- Distributed sys with multiple services
    - services provided by diff servers (diff platforms, langs, etc)
- Providers comm via protocols
    - excahnge info, uniformity of services, consistency in data and services

- Clients look for service
- Clients register with node (broker, provide list of services) before asking for service
- Service providers publish services via broker
- sign contract before formally interacting

![Service Oriented](./img/soa.png)

Pros
- Decoulping clients and servers -> indepedence
- Services independent of platofrms and langs

Difficulties
- broker and contracts
- security important concern

Ex
- WWW, CORBA, COM/DCOM


### **MVC**
- Model: entities
- View: services to display
- Controller: coordinate model and view

Pro
- Easy to impl
- Easy to divide work among devs
- clearly defined comm patterns

Difficulties
- layer/grp may be bigger than two -> uneven work allocation

![MVC](./img/mvc.png)

Ex 
- Javascript, Python, Ruby uses MVC for web/mobile app dev

--- 
## **Data Centric Software Architecture Case Study** 
- 3 levels of detail

![Notations](./img/notation.png)
![Level 1 details](./img/lvlone.png)
![Level 2 details](./img/lvltwo.png)
![Level 3 details](./img/lvlthree.png)

---
## **Design Patterns**
- reusable microarchitecture, best & suitable design for problema and supports reuse

### Components
- Pattern name
- Problem descr
- Solution
- Consequences 

### Toolkits & Frameworks
- Toolkits: low-lvl classes & lib for spec apps
    - code reuse, specific [GUI toolkits in Java]
- Frameworks: set of classes make up app
    - higher abstr than toolkits
    - emphasize design

### Design Patterns Abstraction
- more abstract & smaller than frameworks
- generally no code

### Interface
- descr abstract behavior only, not state vars

### Design Patterns
- Iterator 
    - want to iterate over diff collections
    - hasNext(), next()
    - pros:
        - traverse w/o impl
        - separate maintence of collect from traversal
        - several traversals
    - cons: 
        - if several iterators work, may be inconsistent
    - ![iterator](./img/iterator.png)
- Adapter 
    - interface same name as service expected 
        - define adapter class 
    - convert interface C to interface which expects C
    - wrapper 
    - pros:
        - client use adaptee with minimal modifications to either classes
    - cons: 
        - 1 more lvl of indirection before actual data
    - ![adapter](./img/adapter.png)
- Observer 
    - subject notify objservers 
    - pros: 
        - plenty of observers
        - sbj & observers decoupled
    - cons:
        - too many state changes, notify might be inefficient
    - ![observer](./img/observer.png)
- Factory 
    - product objs of varying types/classes using a selector
    - pros: 
        - client code doesn't need to know specific impl
    - cons: 
        - not much code saving, same set of cond
    - ![factory](./img/factory.png)
- Abstract Factory
    - interface for creating families of related/dependent objs w/o specifcying concrete classes
    - pros:
        - consistency among products
        - doesn't know concrete class details
    - cons: 
        - adding new products difficult since interacts wiht abstract factory interface
            - updated when new product added
    - ![abstractfactory](./img/abstractfactory.png)
--- 
## Modeling Software Behavior
### Structural vs Behavioral
- structural: what is sys / sys composed of?
    - class diagrams, software architectures
- Behavioral: what sys does/perform
    - FSM, UML State diagram
![bmdfd](./img/bmdfd.png)
![bmex](./img/bmex.png)
![bmumldiagram](./img/bmumldiagram.png)
![bmumlseq](./img/bmumlseq.png)
---
## State Diagrams
### State Machines
- descr dynamic behavior of objs over time
- one diagram corresponds to each class
- 1 init state, 1+ final states
    - unq name
    - has entry, action, do action
- State
    - coll of attr & corresponding vals
    - remains finite time
    - can have AND and OR relationships with sub-states
- Event: noteworthy occurrence
    - 4 types of events
        - Signal: obj sends signal to another
        - Call: methodd is invoked
        - Change: bool cond changed
        - Time: time limit reached
    
    - change event labels preceded by "when"
    - time events are preceded by "after"
    - may have precedence relationships among them
- Transition
    - change of states 
    - finite & signiticant op
    - triggered by occurrence of event
- Simplified representation
    - ONly keep attributes that define change of state
![statediagram](./img/statediagram.png)
![statediagramdescr](./img/statediagramdescr.png)
--- 
## UML Seq Diagram
- seq order of interactions
- Lifelines
    - instance name: class name
- Figure
    - portray internal sys obj
- Message 
    - comm betweenm objs
    - Synchronous msg
        - solid line arrow forward
        - dashed line arrow back
    - Asynchronous 
        - backwward solid line arrow


## Object-Z
- must define first to use
- predefined types
    - Z, Ints
    - N, Ints, N >= 0
    - N1, Ints, N > 0
    - R, real 
    - B, Boolean

- Class definition (optional components, but if show then must be in order)
    - Classname
        - Visiblity list (not public then all attr & op is public)
            - attributes & ops that are public 
        - inherited class names
        - type & constant defs
        - State Schema  (req, attributes)
        - Init
            - var = val
        - Operation schemas
            - unq name
            - decl & predicate
            - input?  ,   output!
            - delta() == declaration part
            - name` , val before operation 
            - name  , val after operation
- Global types
    - [IDNUMBER, NAME]
        - basic types
    - Status ::== Active|Inactive|Dismissed  
        - enum 
    - students : P Student 
        - set of Student objects  
- Composite operations to use functions to call another function

- Predicate Logic 
    - Z, set of ints
    - N, N >= 0
    - N1, N > 0
    - R, set of reals
    - mod
    - div
    - E, set membership

- Predicate
    - ensure type compatability at all times
    - < quantifier > < declaration >  dot  < predicate >
        - ex: V n : N  dot n E Z
        - ex: V n : Z; s : String  dot n = s.length => n >= 0 
    - < quantifier > < declaration > | < constraint > dot  < predicate >
        - ∃ bus : Bus | runsBetween(Minneapolis, Madison) • stopsAtLaCrosse(bus)
    - ex
        - n mod 2 = 0, n E Z
        - patientVisited(doctor, patient, date)
    - ∧ logical AND (conjunction)
    - ∨ logical OR (disjunction)
    - ¬ logical NOT (negation)
    - ⇒ implication (if . . . then)
    - ⇔ equivalence (if and only if)

- Set
    - unordered, collection of distinct elements
    - types
        - untyped set
        - typed set
    - defining
        - as type
            - frits: P Fruit
        - set enumeration 
            - X == {10, 23, -1}
        - set comprehension 
            - Even == { n: Z | n mods 2 = 0 }
            - evenSet1 : P Even
            - evenSet1 = { -10, -4, 4, 10}
    - extended syntax
        - EvenSquare == {n : Z | n mode 2 = 0 dot n * n }
            - evenSquareSet1 : P EvenSquare
            - evenSquareSet1 = {100, 64, 16, 4, 0}

        - apply dot first then check predicate 
        - if multiple variables used then must use dot 
    - operators
        - ∅     |    ∅   |       empty set
        - {}     |    {}  |       empty set
        - ∈     |   x ∈ S    |   set membership
        - U     |    S1 U S2   |       set union
        - N     |    S1 N S2   |       set intersection
        - \     |    S1 \ S2   |       set difference
        - `#`     |    `#S`   |       cadinality of set  (# ele in set)
        - ⊆     |    S1 ⊆ S2   |       subset
        - ⊂     |    S1 ⊂ S2   |       proper subset
        - =     |    S1 = S2   |       set equality 
        - U     |    ∪ SS    |      generalized union of sets 
        - N     |   ∩ SS   |       generalized intersection of sets
        - P     |    P S   |       power set 
        - F     |    F S   |       finite subset
        - =/

    - Consistency check of operations
        - every op respects "state invaraint"
            - state invariant true before and after