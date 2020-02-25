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






