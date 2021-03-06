CREATE OR REPLACE FUNCTION get_latest_completed(course_num varchar, student_id integer) 
RETURNS enroll AS $$ 
DECLARE 
    latested_graded_completed enroll;
BEGIN 
    SELECT * INTO latested_graded_completed FROM enroll WHERE student_id = sid AND course_num = cnum AND grade <> 'I'
    ORDER BY year DESC, 
        CASE semester 
            WHEN 'Fall' THEN 1
            WHEN 'Summer' THEN 2 
            WHEN 'Spring' THEN 3
            else 4
        END
    LIMIT 1;
    return latested_graded_completed;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_numeric_grade(grade varchar)
RETURNS numeric AS $$
DECLARE 
    grade_points numeric;
BEGIN 
    CASE grade 
        WHEN 'A' THEN  
            grade_points = 4.0;
        WHEN 'AB' THEN
            grade_points = 3.5;
        WHEN 'B' THEN 
            grade_points = 3.0;
        WHEN 'BC' THEN 
            grade_points = 2.5;
        WHEN 'C' THEN 
            grade_points = 2.0;
        WHEN 'D' THEN 
            grade_points = 1.0;
        ELSE 
            grade_points = 0.0;
    END CASE;
    RETURN grade_points;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION calc_student_gpa(student_id integer)
RETURNS numeric AS $$
DECLARE
    total_points numeric := 0.0;
    total_credits numeric := 0.0;
    course_credit numeric := 0.0;
    completed_course enroll;
    course_num varchar;
BEGIN
    FOR course_num IN (SELECT distinct(cnum) FROM enroll WHERE student_id = sid)
    LOOP
        completed_course := get_latest_completed(course_num, student_id);
        IF completed_course IS NOT NULL THEN
            SELECT credits INTO course_credit FROM course WHERE completed_course.cnum = cnum AND completed_course.year = year AND completed_course.semester = semester;
            total_points := total_points + (get_numeric_grade(completed_course.grade) * course_credit);
            total_credits := total_credits + course_credit;
        END IF;
    END LOOP;

    IF (total_credits = 0) THEN 
        return 0.0;
    END IF;
    RETURN total_points / total_credits;
END;
$$ LANGUAGE plpgsql;