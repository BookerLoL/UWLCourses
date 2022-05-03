CREATE TABLE department (
    dcode varchar(3) PRIMARY KEY,
    building varchar(20), 
    room integer
);

CREATE TABLE major (
    mid SERIAL PRIMARY KEY,
    mname varchar(10),
    dcode varchar(3) REFERENCES department(dcode) NOT NULL
);


CREATE TABLE faculty (
    fid SERIAL PRIMARY KEY,
    fname varchar(40),
    dcode varchar(3) REFERENCES department(dcode)
);


CREATE TABLE student (
    sid SERIAL PRIMARY KEY,
    sname varchar(40),
    mid integer REFERENCES major(mid) NOT NULL,
    fid integer REFERENCES faculty(fid)
);

CREATE TABLE course (
    cnum varchar(6),
    cname varchar(30),
    semester varchar(6),
    year integer, 
    credits integer,
    fid integer REFERENCES faculty(fid) NOT NULL,
    PRIMARY KEY (cnum, semester, year),   
    CONSTRAINT valid_semester CHECK (semester IN ('Fall', 'Spring', 'Summer')), 
    CONSTRAINT valid_year CHECK (year BETWEEN 1900 AND 2099),
    CONSTRAINT valid_credits CHECK (credits BETWEEN 1 AND 12)
); 

CREATE TABLE enroll (
    cnum varchar(6),
    semester varchar(6),
    year integer,
    sid integer REFERENCES student(sid) NOT NULL,
    grade varchar(2),
    PRIMARY KEY (cnum, semester, year, sid),
    CONSTRAINT valid_grade CHECK (grade IN ('A', 'AB', 'B', 'BC', 'C', 'D', 'F', 'I')),
    CONSTRAINT course_fk FOREIGN KEY (cnum, semester, year) REFERENCES course(cnum, semester, year)
);