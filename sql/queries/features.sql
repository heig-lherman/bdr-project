-- Find stations of a line
select distinct station.opuic, station.name
from station
         join segment on station.opuic = segment.station_end_fk or station.opuic = segment.station_start_fk
         join line l on l.line_number = segment.line_fk
where l.line_number = 140;

-- find lines of a station
select distinct line.line_number, line.name
from station
         join segment on station.opuic = segment.station_end_fk or station.opuic = segment.station_start_fk
         join line on line.line_number = segment.line_fk
where station.opuic = 8504200;

-- find segments connecting two stations
-- example: Ependes to Yverdon-Champ Pittet
WITH RECURSIVE
    reachable(fromuid, touid)
        AS (SELECT prev.station_start_fk, station.opuic
            FROM station
                     JOIN segment prev ON station.opuic = prev.station_end_fk
            UNION
            SELECT station.opuic, next.station_end_fk
            FROM station
                     JOIN segment next ON station.opuic = next.station_start_fk),
    distance(uid, distance, path)
        AS (SELECT 8504144::BIGINT, 0, ARRAY [8504144]::BIGINT[]
            UNION ALL
            SELECT a.fromuid, b.distance + 1, a.fromuid || b.path
            FROM reachable a
                     JOIN distance b ON a.touid = b.uid
            WHERE NOT (b.path @> ARRAY [a.fromuid]))
SELECT path
FROM distance
WHERE uid = 8501110;

-- Find closest station(s) to a GPS coordinate
SELECT *
FROM station
ORDER BY ST_Distance(station.position, ST_SetSRID(ST_MakePoint(6.6597877230995435, 46.77924346233183), 4326));

-- Find the total distance of all travellers' journeys
SELECT SUM(distance)
FROM traveller
         JOIN journey j ON traveller.email = j.user_fk
         JOIN journey_segment js ON j.id = js.journey_fk
         JOIN segment s ON js.segment_fk = s.id;

-- Find percentage of completion for all segments of a canton
WITH vd_segments(id) AS (SELECT segment.id
                         FROM segment
                                  JOIN station s
                                       ON segment.station_start_fk = s.opuic OR segment.station_end_fk = s.opuic
                                  JOIN locality l ON s.locality_fk = l.id
                                  JOIN swiss_locality sl ON l.id = sl.id
                         WHERE sl.canton_fk = 'VD'
                         GROUP BY segment.id)
SELECT t.email, count(*) AS travelled_count, (select count(*) from vd_segments) AS total_count
FROM vd_segments fs
         JOIN journey_segment js ON fs.id = js.segment_fk
         JOIN journey j ON j.id = js.journey_fk
         JOIN traveller t ON j.user_fk = t.email
GROUP BY t.email;
