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
- **\d**
  - show available tables
- **\conninfo**
  - current db info
- **\cd**
- **\h**
  - help sql command
- **\?**
  - help meta commands
- **\!**
  - execute command in shell
- **\q**

  - quit

- Loading tables from file
  - **copy table_name from 'file_path' delimiter '|';**

#### Postgres operations

- create database db_name
