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

-- A faculty member can only be assigned to teach courses that are offered by the faculty member's department [TRIGGER]
   -- the first three characters of a Cnum determine the department (Dcode) that offers the course.
CREATE TABLE faculty (
    fid SERIAL PRIMARY KEY,
    fname varchar(40),
    dcode varchar(3) REFERENCES department(dcode)
);


--If a student has an advisor, the advisor must be a member of the department that offers the student's major. [TRIGGER]
CREATE TABLE student (
    sid SERIAL PRIMARY KEY,
    sname varchar(40),
    mid integer REFERENCES major(mid) NOT NULL,
    fid integer REFERENCES faculty(fid)
);

-- The value of Year must be in the range 1900 to 2099 inclusive [done]
-- The value for credits must be in the range 1 to 12 inclusive [done]
-- The first three characters of a Cnum must match some Dcode [trigger]
-- The values of semester must be one of Fall, Spring and Summer [done]
-- The combination of Cnum, Semester and Year uniquely identity a row in course [done]
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

-- The possible values for grade are A, AB, B, BC, C, D, F, I. [done]
CREATE TABLE enroll (
    cnum varchar(6),
    semester varchar(6),
    year integer,
    sid integer REFERENCES student NOT NULL,
    grade varchar(2),
    CONSTRAINT valid_grade CHECK (grade IN ('A', 'AB', 'B', 'BC', 'C', 'D', 'F', 'I')),
    CONSTRAINT course_fk FOREIGN KEY (cnum, semester, year) REFERENCES course(cnum, semester, year)
);