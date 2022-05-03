# CS 454 / 564 Advanced Database Management Systems

- Grad extra project
  - 20 minute lecture
    - a database / db topic

# Notes

## Intro

### Stuff to learn

- Stored procedures
  - store procedures in db, can later be invoked
- Relation algebra and relational calculus
- Normalization
- Query processing
- Transaction processing
  - grouping actions together
- Data models
  - NoSQL
  - MongoDB
  - Neo4J
- Other Issues

### Problem statement

- authors, books, copies of books, libraries

- author

  - unique author aid
  - first name and last name
  - can WRITE MULTIPLE BOOKS

- Each book

  - unique book number
  - title, num pages
  - can HAVE MULTIPLE AUTHORS
  - percent of book written by author recorded

- Copy

  - unique copy number
  - price
  - copy of exactly one book
  - each copy stored in exactly one library

- Each library

  - unique libnum
  - capacity
    - num copies can be stored

- Simply ER Diagram (Representation varies)
  - rectangle for entities
    - underline PK
  - diamonds for relationships
    - Enttity A (0, M) <-> (1, 1) Entity B
      - Entity A can have 0 - many entities of B
      - Entity B can must and only have 1 entity of A
  - ovals for attributes

### ER Modeling

- What to look for

  - entities
  - relationships
  - attributes

- Real world

  - get all info from customer
  - create problem for yourself

- ER Diagram

### Translating ER Diagram into CREATS

- Each entity will have a table
  - 1-M relationship will have foreign key
  - M-M relationship, new table
    - PK will be PK of the two participating entity
    - **surrogate key** if don't want combined pk
  - FK can be null, but generally not good design

### Queries

```SQL
select pages from Book where title = 'War and Peace';

select title from Author A join Writes W on A.aid = W.aid join Book B on W.booknum = B.booknum where first = 'Anton' and last = 'Chekhov'; --join based on common attributes

select title from Author A, Writes W, Book B, where A.aid = W.aid and W.booknum = B.booknum and first = 'Anton' and last = 'Chekhov';

select first, last from Author natural join writes natural join book where pages > 600;

select price from Copy natural join Book where title = 'Middlemarch';

select price from copy join book using booknum where title = 'Middlemarch';

select aid, Count(booknum) from Writes group by aid;

select aid, first, last, Count(booknum) from Author natural join Writes group by aid, first, last;

select aid, first, last, Count(booknum) AS NumBooks from Author naturla join Writes group by aid;

select booknum, AVG(price) from Copy group by booknum;

select libnum from Library where capacity = (select Max(capacity) from Library)

select libnum, capacity-used as unused from Library natural join (select libnum, Count(copynum) as used from Copy group by libnum)

select libnum from Library L1 where NOT Exists ( (select aid from Author) Except (select aid from Writes natural join Book natural join Copy join Library L2 using libnum where L1.libnum = L2.libnum))
```

- indexes matter for performance
  - probably better to have join clause in the from section
- Generally, need to look at multiple tables requires join
- Group by should be single values

### SQL Keywords

- create table
  - constraints
  - primary key
  - unique
  - foreign key
  - not null
  - check clause
- alter table
  - add column
  - drop column
- drop table
- create view
- drop view
- select
  - distinct
  - aggregate
    - count, sum, avg, min, max
  - joins
    - natural join
    - join on
    - left join
    - right join
    - full join
  - in, exists, and, or, not, all, group by, having, order by
  - intersect
  - union
  - except
- update, delete, insert, grant, revoke

### Postgres

- ORDMS, implements most SQL standard

- db server
- pgAdmin (Managment tool)

#### Postgres Commands

- psql (open terminal)
- \connect (db)
  - **\c**
    - connect to db
- \list
  - **\l**
    - lists available db
- **\i** (file)
  - execute commands from file
  - \i file.sql
- **\d**
  - show available tables
  - \d table_name
    - provides more details
- **\conninfo**
  - current db info
- **\cd**
- **\h**
  - help sql command
- **\?**
  - help meta commands
- **\!**
  - execute command in shell
  - \! pwd
- **\q**

  - quit

- \df

  - check stored procedures

- Loading tables from file
  - **copy table_name from 'file_path' delimiter '|';**

#### Postgres operations

- create database db_name

### Modules

- ex: create extension cube;

### Stored Procedures and Triggers

- Stored Procedure
  - stored in db
  - Better to just import it from a file into db

```psql
create or replace function space_available(lib integer)
return integer AS $$
Declare
  cap integer;
  used integer;
  avail integer;
Begin
  select L.capacity into cap
  from library L
  where L.libnum = lib;

  select count(copynum) into used
  from copy C
  where C.libnum = lib;

  avail := cap - used;
  return avail;
End;
$$ Language plpgsql;
```

```sql
select space_available(libnum) from library;
```

- Triggers
  - similar to a stored procedure
  - **responds to certain sql statements**
    - ex: inserts/updates/deletes
    - before and after
    - for each row
    - for statement
  - can add an audit/log feature/constraint on db

```psql
create or replace funciton check_full() returns trigger AS $$
Declare
  avail integer;
Begin
  avail = space_available(NEW.libnum);
  if avail = 0 then
    raise exception '% is full', NEW.libnum;
    return null;
  end if;

  return NEW;
End;
$$Language plpgsql;

create trigger check_full before insert on copy
  for each row executre procedure check_full();
```

## Normalization

- BCNF to reduce funtional dependencies

- Redundancies
  - attribute value appears in many places
  - allows room for inconsistencies
- Anaomalies

  - update
    - if changed, must change in many places
  - delete
    - deletes an attribute can result in losing information about other things
  - insert
    - must wait to insert until there exists a value

- Functional dependencies

  - FD, X -> A
  - Given determinant, same value must always result in same dependency

    - if given 10 -> A then must always be 10 -> A

  - Armstrongs Axioms
    - Reflexivity
      - if Y subset of X, X -> Y
    - Augmentation
      - if X -> Y, then XZ -> YZ
    - Transitivity
      - if X -> Y and Y -> Z then X -> Z
    - Union
      - if X -> A and X -> B then X -> AB
    - Decomposition
      - if X -> AB then X -> A, X -> B
  - Closure of set is F+
  - Two FD sets are equivalent iff F+ = G+

  - Attribute closure

    - need FD set
    - find other dependencies that are implied
      - given X attributes

  - super keys
    - can determine all the attributes in the table
      - find closure of key
        - closure must contain all attributes in relation
  - key
    - superkey and no proper subset of key has super key property
    - **every key is a super key, but not every super key is a key**
  - candidate key
  - primary key

- 1NF
  - attribute values are atomic
  - no repeating groups
    - no lists in the form of a columns or list values in a row
- 2NF
  - no partial key dependency exists
  - no non-prime attribute functionally determined by proper subset of a key
  - right side is subset of left side
- 3NF
  - X -> A, X is super key or A is prime attribute (key attribute)
  - based on individual table only
- BCNF

  - X -> A, X is super key
  - based on individual table only

- Create 3NF tables
  - Identify all attributes (to store in db) and all functional dependencies
    - universal table: identify all attributes in R
    - designer works with customers
      - may need to create artificial key
  - create minimal cover FD set, G from F
  - apply 3NF synthesis using FD set G and set of attributes R
- Minimal Cover Set

  - cover set G, of an FD set F is FD set such that
  - G is equivalent to F
  - no FD can bre removed to create a smaller but equivalent FD set to F
  - no FD can have attribute removed to create a smaller but equivalent FD set to F
  - minimal cover set are not unique
    - there could be multiple versions, depends on which ones you remove

- Attribute Closure

  - Check attributes that don't ever appear on right hand side
  - example 1
    - R = {A, B, C, D, E, F}
    - FD
      - ABF -> C
      - CF -> B
      - CD -> A
      - BD -> AE
      - C -> F
      - B -> F
    - ABC+
      - ABC
      - F
    - ABD+
      - ABD
      - E
      - F
      - C

- Minimal Cover Algorithm

  1. Make all right hand side single attributes
     - use decomposition
  2. remove redundant attributes from LHS
     - XB -> A, if X -> A implied then remove XB -> A, but keep X -> A
  3. remove implied depndencies produced from step 2
     - can remove equivalent dependencies
       - ex: X -> A
       - if can find A with closure of X without X -> A then remove X -> A
  4. Combine FDs that have same LHS

- 3NF Synthesis Algorithm

  - input: set of attributes R and FDs F

  1. create minimal cover for F called G
  2. for each FD in G creat a table

  - If there are FD's where they have the same exact values (not subset)
    - ex: E -> BD, BD -> E then can just make 1 table instead of 2

  3. if none of the nwe tables contain a super key for universal table, create a new table containing the attributes of key for universal table

  - take closure of those tables
    - can make an inference and check if there are any attributes that never appear on RHS as a good starting point
    - if no closure contains super key for universal table, create a table that would (preferably minimum amount of attributes)

- **Example problem for 3NF Synthesis**

  - Given FDS
    - ABF -> C
    - CF -> B
    - CD -> A
    - BD -> AE
    - C -> F
    - B -> F
  - Minimal Cover
    - decompose rhs
      - BD -> AE
        - BD -> A
        - BD -> E
    - remove redundant lhs
      - take composite lhs and try removing attributes one by one
        - then try the find the closure
          - ABF -> AB (removed F) -> AB+ = ABFC
          - AB -> A (removed B) -> A+ = A
          - AB -> B (removed A) -> B+ = BF
          - CF -> F (removed C) -> F+ = F
          - CF -> C (removed F) -> C+ = CFB
          - BD -> D (removed B) -> D+ = D
        - If removing still determines the rhs then replace the lhs fds with the new reduced versions
    - Remove redundant dependencies
      - AB -> C
      - C -> B
      - CD -> A
      - BD -> A
      - C -> F
      - B -> F
      - Find closure of lhs without using that specific FD for each one
        - ex: CD -> A, CD+ (can't use CD -> A), since C -> F, C -> B, BD -> A
          - since can reach A without using CD -> A, it's redundant
      - Results
        - AB -> C
        - C -> B
        - BD -> A
        - BD -> E
        - B -> F
    - Combine common LHS
      - AB -> C
      - C -> B
      - BD -> A, E
      - B -> F
  - Table for each dependency
    - R1 = {A, B, C}
    - R2 = {C, B}
    - R3 = {B, D, A, E}
    - R4 = {B, F}
  - Check if a new table contains universal set, otherwise create new table with it
    - R3 = {B, D, A, E} -> BDAE+ = BDAECF

- Lossless decomposition
  - lossless if T1 intersect T2 -> T1 or T1 intersects T2 -> T2
  - lossless if every valid T (relative to FDs), T = T1 natural join T2
  - if don't have, will get more rows than should
- Dependency Presevering Decomposition

  - T1, T2 is decomposition of T with FD set F
  - F1, F2 be FDs from F+ lie in T1 and T2
  - preserving if and only if: F+ = F1 union F2

- Decomposition of Tables

  - 3NF synthesis is equivalent to series of lossless, dependency preserving decompositions
  - lossless decomposition in BCNF is possible but dependency preserving may not
    - would need to join in order to confirm constraint holds true

- Removing 3NF or BCNF violator through decomposition

  - break violator into it's own table

- Check if table in BCNF?

  - if non-trivial dependency, X -> A that lies in T where X is not a super key
    - check by taking closure
  - non-trivial dependency

    - rhs not a subset of lhs

  - A -> D, decompose: {A, D}, {A, C}

# Indexes

- Additional data structure to reduce page accesses to find row/rows
  - built on attriutes on one table
- location mechanism

  - algorithm + data structure

- Btree, good for equaliy and range searches
- Hash index, hash table
- **Extendable Hashing**

  - Eliminates chains of pages caused by collisions
  - range of hash function has to be extended to accommodate additional buckets
  - h(v) mod 2^k
    - h2, look at last 2 bits to determine bucket
  - uses directory (level of indirection)
  - **If buckets are full**
    - switch to h+1
    - concatenate copy of old directory to new directory
    - split overflowed bucket B, into B and B' dividing entries in B between the using h3
    - Pointer to B in directory copy replaced by point B'

- Directories
  - depend on number of bits using
  - 1 -> 2
  - 2 -> 4
  - 3 -> 8
- Each bucket

  - needs to keep track of how many bits it's using

- **If bucket is full and using the max amount of bits the directory provides hv value**
  - double directory
    - new directories should point to existing buckets (don't create new buckets)
    - then split the overfilled bucket
      - update the associated directory (2x)
- **If buccket is full but not full bit size**

  - split bucket
    - create new associated bucket (2x)
    - update directory
    - update bucket size too

- h1, bucket size 2
  - 2 directories, 2 sized buckets

# Homework3

- **indexes on unique values**
- DBTTable class implements basic databse table
  - every method will use ExtHash
- ExtHash implements extendable hash index
- File
  - numOtherFields (at least 1)
  - length 1 (for each fixed length field)
  - length 2 ()
  - free (pointer value to next address that is free)
    - added and removing, always do it at head of list
- Bucket file
  - 0, bucket size
  - n bits
  - n keys (in the bucket)
  - keys
  - DB table address for that value
- Directory file
  - hash bits
  - addr, bucket address
- use hash index, never search

# Query Processing and Relational Algebra 1

- Basic Process

  - System catalog
    - counts of rows, columns
    - range, distribution, etc
  - Sql query
  - Sql parser (uses catalog)
  - relation algebra expression
  - query optimizer (uses catalog)
    - query plan generator
    - cost estimator
      - how many times to go to secondard memory
  - query execution plan
  - query plan interpreter (uses catalog)
  - query result

- Relational Algebra
  - set, no duplicates
  - query processing allow duplicates
  - Operators
    - Select (sigma(condition))
    - Project (pi(attribute list))
      - select only attribute lists as output
    - Union
    - Set difference
      - \-
    - Intersection
    - Certesian product
      - x
    - Joins
      - natural join
        - match up based on same matching column values
      - equi join and theta join
      - based on condition
    - Division
      - /
      - if every value is found in other table, then take that table's column value as a result
        - **every** a good candidate to use this
    - Renaming
      - expression[a1, a2, a3]
  - Result size
    - R Union S
      - Max = Size(R) + Size(S)
      - Min = Max(Size(R), Size(S))
    - R Intersection S
      - Max = Min(Size(R), Size(S))
      - Min = 0
    - R difference S
      - Max = Size(R)
      - Min = 0
      - if everything in S is in R = Size(R) - Size(S)
    - Project A from R
      - Max = Size(R)
      - Min = 1
    - R x S
      - Size(R) x Size(S)
    - Join
      - Max = Size(R) x Size(S)
      - Min = 0
      - if primary key in R, foreign key is S
        - no join attributes are null in S, Max = Size(R)
    - Divide
      - Max = Size(R) / Size(S)
      - Min = 0
- Quiz 8 solution fixes

  - 1
    - project libnum (select first = 'George' and last = 'Saunders' (A join W join B join Co))
      - other solution: (A join W join Co)
  - 2
    - wrong logic
    - needed to take set difference of pages > 200
  - join Lo, Co, W, A

- External Sorting
  - large amount of data in secondary storage
  - limited number of in memory space (memory buffers)
  - partial sorting
  - k-way merge sorted results
  - simple example
    - memory space, 4, 5 word (each int needs one word) memory buffers
      - only room for 20 integers in memory
    - 4 memory buffers, page holds 5 integers
    - 223 integers to sort
    - cieling(223/5) = 45 pages to sort with 4 mem buffers
    - read 4 pages, sort them, write 4 sorted pages to secondary storage
      - ceiling(45/4) = 12 sorted sequences
    - merge sorted sequences
      - 3 input pages, 1 output page
      - ceiling(12/3) = 4 sorted sequences (1st pass)
      - ceiling (4/3) = 2 sorted sequences (2nd pass)
      - ceiling(2/3) = 1 sorted sequence (3rd pass and stop)
  - sorting cost
    - dominated by I/O
    - suppose table with F pages, M memory page buffers
    - partial sort cost
      - 2F pages operations (F reads and F writes)
      - produces ceiling(F/M) sorted sequences
  - K-way merge cost
    - ceiling(F/(M-1)) sorted sequences after partial sort
      - 1 because need to have 1 page to write out to
      - usually recursive, so have to keep going until 1 sequence
    - multiple passes (usually)
  - total costs
    - partial sort costs + merge costs
  - formula estima
    - 2F x ceiling(log(M-1)F)
      - X \* F page accesses
- NoSQL
  - address areas of concern where relational db wouldn't be so applicable
  - motivation
    - scaling
    - distributed data sources
    - high costs of joins
    - great variation in data
    - design focuses on query needs of application
    - RDBMS not always match needs to application
    - RDBMS not going away
  - Characteristics
    - no predefined schema
    - limited or no support for declarative query language
    - focus on scalability, availability, and performance
  - SQL vs NoSQL
    - Transactions
    - ACID
      - Atomicity
      - Consistency
      - Isolation
      - Durability
    - BASE
      - Basically Available
      - Soft state
      - Eventually consistent
    - CAP
      - Consistent
        - all replicas contain same view of the data
        - client always see the same view of the data
      - Available
        - systems remain operational in presence of failures
        - all clients can always read and write
      - Partition Tolerance
        - system remains operational in presence of communication failures or network partition
    - Cap "Theorem"
      - Systems can only support 2 or 3
      - ideas debated
    - Scalability
      - Horizontal
        - distribute data and load over many servers
        - servers don't share RAM or Disks
        - **Better for scaling**
      - Vertical
        - distribute load of many cores or processors
        - cores or processors share RAM and disks
        - **Relies on machines, bottleneck**
    - Partitioning
      - Horizontal (Sharding)
        - storing records on different servers
      - Vertical
        - Storing parts of a record on different servers
    - Replication
      - Storing multiple copies of the same data
    - Taxonomy of NoSQL
      - Key-Value (map-like)
      - Column Based
      - Document (like mongo)
      - Graph Database (like neo4j)
- Query Processing Basics
  - file scan
    - linear
  - sorting
    - k-way sort
  - indexes
  - computing projection
    - duplicates allowed
      - scan table keep attributes
      - if F pages in table then F reads + F or less writes
    - duplicates not allowed
      - sort-based projections
        - sort and remove duplicates at write of last merge phase
        - cost same as sorting
      - Hash-based projections
        - hash into buckets, remove duplicates in each bucket
        - cost is 4F assume the bucket fits in memory
  - Computing selection
    - selection with simple conditions
    - select attribute op value R
      - no index, file scan
      - B+ tree index
        - search for B+ tree node where attr = value and scan leaves based on operator
      - hash index
        - only works for attr = value
    - selection with complex conditions
      - selections with conjunctive conditions (and)
        - use most selective access path
          - scan tuples returned by that acess path
          - access path chosen depends on indexes available
        - use multiple access paths (intersect results)
          - use intersection of tuples returned by all access paths
      - selections with disjunctive conditions (or)
        - if all disjuncts have better acess path than scan, use them otherwise scan
      - selection problem
        - 5,000 tuples with 10 tuples per page
          - 500 pages
        - a 2-level B+ tree index on attribute A with up to 100 index entries per page
        - attribute A is a candidate key of R
        - values of A are uniformly distributed in range of 1 to 100,000
  - Computing Joins
    - simple nested loops
      - for each row algorithm
      - F pages in R and S
      - N rows in R and S
      - cost = Fr + Nr x Fs
      - order of loop matters
    - block nested loops
      - for each page algorithm
      - cost Fr + Fr x Fs
      - improvement
        - assume M page buffers available
        - read M-2 page form R and join with page from S
          - Fr + Fs x ceiling(Fr / (M-2))
    - index nested loops
      - index to work with
      - use index on attribute B to find all tuples
      - B+tree (height h) index on B in S
        - Fr + ((h+1) + 1) x Nr
          - +1 because btree of height h is actuall level 0 + height
          - +1 for access
- Neo4J

  - graph oriented
  - terminology
    - node
      - can have properties
    - properties (attributes)
    - labels
      - type information
    - relationships
  - supports transactions

  ```neo4j
  //Nodes
  //label: Movie
  CREATE (TheMatrix:Movie {title:'The Matrix',released:1999, tagline:'Welcome to the Real World'})
  CREATE (Keanu:Person {name:'Keanu reeves'.born:1964})


  //relationships
  CREATE (Keanu)-[:ACTED_IN {roles:['Neo']}]->(TheMatrix)
  CREATE (AndyW)-[:DIRECTED]->(TheMatrix)
  CREATE (TomH)-[:ACTED_IN {roles:['Joe Fox']}]->(YouveGotMail)

  //queries
  MATCH (movie:Movie) RETURN movie
  MATCH (person:Person) RETURN person
  MATCH (person:Person) RETURN person.name
  MATCH (m:Movie)<-[:ACTED_IN]-(a:Person) WHERE a.name = 'Tom Hanks' or a.name = 'Keanu Reeves' Return m.title as movie

  MATCH (m:Movie)<-[:ACTED_IN]-(a:Person) WHERE a.name = 'Tom Hanks' or a.name = 'Keanu Reeves' Return m, a
  ```

## Book

- Database Systems by MIchael Kifer
