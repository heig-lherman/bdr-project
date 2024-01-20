-- View 01: Get the data to build a heatmap of the most viewed segments
CREATE OR REPLACE VIEW global_heatmap AS
SELECT s.line_fk::BIGINT                                                               AS segment_id,
       (SELECT st_multi(st_linemerge(st_snaptogrid(st_collect(s.geometry), 0.00001)))) AS segment_geometry,
       count(js.segment_fk)                                                            AS amount,
       max(count(js.segment_fk)) OVER ()                                               as total
FROM segment s
         LEFT JOIN journey_segment js ON js.segment_fk = s.id
GROUP BY s.line_fk
ORDER BY amount DESC;

-- View 02: Get a per-user map construction of the visited segments and global percentage
CREATE OR REPLACE VIEW user_progress AS
SELECT j.user_fk                                                                       AS user_email,
       st_multi(st_linemerge(st_snaptogrid(st_collect(DISTINCT s.geometry), 0.00001))) AS segments,
       COUNT(DISTINCT js.segment_fk)                                                   AS travelled_count,
       (SELECT COUNT(*) FROM segment)                                                  AS total_count
FROM journey j
         JOIN journey_segment js ON j.id = js.journey_fk
         JOIN segment s ON js.segment_fk = s.id
GROUP BY j.user_fk;

-- View 03: For every canton, get the progression per user
CREATE OR REPLACE VIEW completion_by_canton AS
WITH segments_by_canton AS (SELECT segment.id                                   AS segment_id,
                                   c.code                                       AS canton_code,
                                   c.name                                       AS canton_name,
                                   COUNT(segment.id) OVER (PARTITION BY c.code) AS total_count
                            FROM segment
                                     JOIN station s1 ON segment.station_start_fk = s1.opuic
                                     JOIN station s2 ON segment.station_end_fk = s2.opuic
                                     JOIN swiss_locality sl1 ON s1.locality_fk = sl1.id
                                     JOIN swiss_locality sl2
                                          ON s2.locality_fk = sl2.id AND sl1.canton_fk = sl2.canton_fk
                                     JOIN canton c ON sl1.canton_fk = c.code)
SELECT t.email                       AS user_email,
       t.team_fk                     AS team_fk,
       fs.canton_code                AS canton_code,
       fs.canton_name                AS canton_name,
       COUNT(DISTINCT js.segment_fk) AS travelled_count,
       fs.total_count                AS total_count
FROM segments_by_canton fs
         CROSS JOIN traveller t
         LEFT JOIN journey j ON t.email = j.user_fk
         LEFT JOIN journey_segment js ON j.id = js.journey_fk AND js.segment_fk = fs.segment_id
GROUP BY fs.canton_code, fs.canton_name, fs.total_count, t.email;
