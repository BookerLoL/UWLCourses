CREATE OR REPLACE FUNCTION faculty_can_teach()
RETURNS trigger AS $$ 
BEGIN
    IF NOT EXISTS (SELECT * FROM (SELECT dcode FROM department WHERE SUBSTRING(NEW.cnum, 1, 3) = dcode) AS d NATURAL JOIN (SELECT dcode from faculty WHERE NEW.fid = fid) AS f) THEN
        RAISE EXCEPTION 'The advisor cannot teach courses outside of the department';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

create trigger check_faculty_can_teach BEFORE INSERT OR UPDATE ON course
    FOR EACH ROW EXECUTE PROCEDURE faculty_can_teach();