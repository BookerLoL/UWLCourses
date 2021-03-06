CREATE OR REPLACE FUNCTION valid_student_advisor()
RETURNS trigger AS $$
BEGIN
    IF ((NEW.fid IS NOT NULL) AND (NOT EXISTS (SELECT * FROM (SELECT dcode from major WHERE NEW.mid = mid) AS m NATURAL JOIN (SELECT dcode from faculty WHERE NEW.fid = fid) AS f))) THEN
        RAISE EXCEPTION 'The advisor does not belong to the same department as the students major';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE trigger check_valid_student_advisor BEFORE INSERT OR UPDATE ON student
    FOR EACH ROW EXECUTE PROCEDURE valid_student_advisor();