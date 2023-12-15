-- Procedure 1 : duplicate a journey when a user travels with a  group
CREATE OR REPLACE PROCEDURE clone_journey_for_team(p_user_email varchar, p_journey_id bigint)
    LANGUAGE plpgsql AS
$$
DECLARE
    team_id        bigint;
    new_user_fk    varchar;
    new_journey_id bigint;
BEGIN
    SELECT team_fk
    INTO team_id
    FROM traveller
    WHERE email = p_user_email
      AND team_fk IS NOT NULL;

    IF NOT FOUND THEN
        -- No team, nothing to do.
        RAISE NOTICE 'Mentioned user has no teams.';
        RETURN;
    END IF;

    FOR new_user_fk IN
        SELECT email
        FROM traveller
        WHERE team_fk = team_id
          AND traveller.email != p_user_email
    LOOP
        INSERT INTO journey (start_date, end_date, grade, review, user_fk)
        SELECT start_date, end_date, grade, review, new_user_fk
        FROM journey
        WHERE id = p_journey_id
          AND user_fk = p_user_email
        RETURNING id
            INTO new_journey_id;

        IF NOT FOUND THEN
            RAISE EXCEPTION 'The journey either does not exist or has not been completed by the user.';
        END IF;

        INSERT INTO journey_segment (journey_fk, segment_fk)
        SELECT new_journey_id, journey_segment.segment_fk
        FROM journey_segment
        WHERE journey_segment.journey_fk = p_journey_id;
    END LOOP;
END;
$$;
