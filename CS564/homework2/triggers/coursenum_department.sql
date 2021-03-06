CREATE OR REPLACE FUNCTION valid_coursenum()
RETURNS trigger AS $$
BEGIN
    IF NOT EXISTS (SELECT * FROM department WHERE SUBSTRING(NEW.cnum, 1, 3) = dcode) THEN 
        RAISE EXCEPTION 'course does not have a valid matching course name';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


create trigger check_valid_coursenum BEFORE INSERT OR UPDATE ON course
    FOR EACH ROW EXECUTE PROCEDURE valid_coursenum();