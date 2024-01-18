package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.repository.data.HeatmapView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface HeatmapRepository extends Repository<HeatmapView, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT
                        segment_id,
                        st_simplify(segment_geometry, 0.005) AS segment_geometry,
                        amount,
                        total
                    FROM global_heatmap;
                    """
    )
    List<HeatmapView> getGlobalHeatmap();

    @Query(
            nativeQuery = true,
            value = """
                    SELECT * FROM user_heatmap
                    WHERE user_fk = :userEmail;
                    """
    )
    List<HeatmapView> getUserHeatmap(String userEmail);
}
