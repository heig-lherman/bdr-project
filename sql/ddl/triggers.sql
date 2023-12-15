-- Trigger 01: Check CI for line_assessment
CREATE OR REPLACE FUNCTION check_line_assessment() RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    IF NEW.line_fk NOT IN (SELECT DISTINCT line_fk
                           FROM traveller
                                    JOIN journey ON journey.user_fk = traveller.email
                                    JOIN journey_segment ON journey_segment.journey_fk = journey.id
                                    JOIN segment on journey_segment.segment_fk = segment.id
                           WHERE traveller.email = NEW.user_fk) THEN
            RAISE EXCEPTION 'CI ERROR: Line assessment on line that has not been visited';
    END IF;

    RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER line_assessment_checks
    BEFORE INSERT OR UPDATE
    ON line_assessment
    FOR EACH ROW
EXECUTE FUNCTION check_line_assessment();
