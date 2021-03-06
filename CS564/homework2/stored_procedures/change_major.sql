CREATE OR REPLACE FUNCTION change_major(student_id integer, major_id integer, faculty_id integer)
RETURNS void AS $$
BEGIN
    IF faculty_id IS NULL THEN
        UPDATE student SET mid = major_id WHERE student_id = sid;
    ELSE
        UPDATE student SET mid = major_id, fid = faculty_id  WHERE student_id = sid;
    END IF;
END;
$$ Language plpgsql;
