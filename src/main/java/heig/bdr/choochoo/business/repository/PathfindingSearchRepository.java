package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.repository.data.PathfindingResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PathfindingSearchRepository extends Repository<PathfindingResult, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT path_offset                                                               AS rank,
                           array_agg(opuic ORDER BY rank)                                            AS station_ids,
                           array_agg(abbreviated_name ORDER BY rank)                                 AS station_abbreviated_names,
                           array_agg(name ORDER BY rank)                                             AS station_names,
                           array_agg(segment_id ORDER BY rank) FILTER (WHERE segment_id IS NOT NULL) AS edges,
                           get_segments_distance(array_agg(segment_id))                              AS total_distance
                    FROM generate_series(0, :amount - 1) AS path_offset,
                         LATERAL station_bfs_lookup(:startOpuic, :endOpuic, path_offset)
                    GROUP BY path_offset;
                    """
    )
    List<PathfindingResult> findPaths(long startOpuic, long endOpuic, int amount);
}
