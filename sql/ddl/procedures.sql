-- Procedure 01: duplicate a journey when a user travels with a  group
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

-- Procedure 02: find a path between two stations
CREATE OR REPLACE FUNCTION station_bfs_lookup(start_id bigint, end_id bigint, path_offset INT = 0)
    RETURNS TABLE
            (
                rank             BIGINT,
                opuic            BIGINT,
                segment_id       BIGINT,
                abbreviated_name VARCHAR,
                name             VARCHAR
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    path  BIGINT[];
    edges BIGINT[];
BEGIN
    -- Recursive BFS search
    WITH RECURSIVE
        reachable(fromuid, touid, edge_id)
            AS (SELECT station.opuic, prev.station_start_fk, prev.id
                FROM station
                         JOIN segment prev ON station.opuic = prev.station_end_fk
                UNION
                SELECT station.opuic, next.station_end_fk, next.id
                FROM station
                         JOIN segment next ON station.opuic = next.station_start_fk),
        distance(uid, distance, path, edges)
            AS (SELECT end_id::BIGINT, 0, ARRAY [end_id]::BIGINT[], ARRAY []::BIGINT[]
                UNION ALL
                SELECT a.fromuid, b.distance + 1, a.fromuid || b.path, a.edge_id || b.edges
                FROM reachable a
                         JOIN distance b ON a.touid = b.uid
                WHERE NOT (b.path @> ARRAY [a.fromuid]))
    SELECT d.path, d.edges
    INTO path, edges
    FROM distance d
    WHERE uid = start_id
    OFFSET path_offset LIMIT 1;

    IF NOT FOUND THEN
        RAISE NOTICE 'No path found from % to %', start_id, end_id;
        RETURN;
    END IF;

    RETURN QUERY (SELECT a.rank,
                         a.opuic,
                         edges[a.rank - 1]  AS segment_id,
                         s.abbreviated_name AS abbreviated_name,
                         s.name             AS name
                  FROM unnest(path) WITH ORDINALITY a(opuic, rank)
                           JOIN station s ON s.opuic = a.opuic);
END;
$$;

-- Procedure 03: find reachable stations within single lines from a station
CREATE OR REPLACE FUNCTION get_connecting_stations(start_uid BIGINT, max_distance INTEGER = 10)
    RETURNS TABLE
            (
                opuic            BIGINT,
                abbreviated_name VARCHAR,
                name             VARCHAR,
                geoposition      GEOGRAPHY,
                line             INTEGER,
                edges            BIGINT[],
                distance         INTEGER
            )
    STABLE
    LANGUAGE sql
AS
$$
WITH RECURSIVE
    reachable(fromuid, touid, edge_id, line)
        AS (SELECT station.opuic, prev.station_start_fk, prev.id, prev.line_fk
            FROM station
                     JOIN segment prev ON station.opuic = prev.station_end_fk
            UNION
            SELECT station.opuic, next.station_end_fk, next.id, next.line_fk
            FROM station
                     JOIN segment next ON station.opuic = next.station_start_fk),
    distance(lines, end_uid, edges, distance)
        AS (SELECT ARRAY(SELECT DISTINCT line FROM reachable WHERE fromuid = start_uid),
                   start_uid::BIGINT,
                   ARRAY []::BIGINT[],
                   0
            UNION ALL
            SELECT ARRAY [a.line], a.touid, a.edge_id || b.edges, b.distance + 1
            FROM reachable a
                     JOIN distance b ON a.fromuid = b.end_uid AND a.line = ANY (b.lines)
            WHERE NOT (b.edges @> ARRAY [a.edge_id])
              AND b.distance < max_distance)
SELECT d.end_uid,
       s.abbreviated_name,
       s.name,
       s.position,
       d.lines[1],
       d.edges,
       d.distance
FROM distance d
         JOIN station s ON s.opuic = d.end_uid
WHERE d.distance > 0
ORDER BY distance;
$$;

-- Procedure 4: find distance of all segements in array of ids
CREATE OR REPLACE FUNCTION get_segments_distance(segments BIGINT[])
    RETURNS INTEGER
    STABLE
    LANGUAGE sql
AS
$$
SELECT SUM(distance) as total_distance
FROM unnest(segments) AS segment_id
         JOIN segment ON segment.id = segment_id;
$$;
