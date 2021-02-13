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
