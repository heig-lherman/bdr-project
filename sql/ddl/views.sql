-- View 01: Get the data to build a heatmap of the most viewed segments
CREATE OR REPLACE VIEW heatmap AS
SELECT id, count(nullif(segment_fk, 0)) AS number, count(*) OVER () AS total
FROM journey_segment
         RIGHT JOIN public.segment s ON s.id = journey_segment.segment_fk
GROUP BY id
ORDER BY number DESC;
