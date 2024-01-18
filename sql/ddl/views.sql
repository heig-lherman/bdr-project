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

-- View 02: Get the data to build a heatmap of the most visited segments per user (note: meant to be used with a where constraint)
CREATE OR REPLACE VIEW user_heatmap AS
SELECT s.id                              AS segment_id,
       s.geometry                        AS segment_geometry,
       j.user_fk                         AS user_fk,
       count(js.segment_fk)              AS amount,
       max(count(js.segment_fk)) OVER () as total
FROM segment s
         LEFT JOIN journey_segment js ON s.id = js.segment_fk
         LEFT JOIN journey j on js.journey_fk = j.id
GROUP BY s.id, j.user_fk
ORDER BY amount DESC;
