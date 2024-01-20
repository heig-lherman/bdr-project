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
