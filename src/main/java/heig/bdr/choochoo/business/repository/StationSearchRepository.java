package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.repository.data.StationSearch;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface StationSearchRepository extends Repository<StationSearch, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT s.opuic,
                           s.name,
                           s.abbreviated_name,
                           s.position
                    FROM station s
                    WHERE UNACCENT(LOWER(s.name)) LIKE UNACCENT(LOWER(CONCAT('%', :query, '%')))
                       OR UNACCENT(LOWER(s.abbreviated_name)) LIKE UNACCENT(LOWER(CONCAT('%', :query, '%')))
                    LIMIT :amount
                    """
    )
    List<StationSearch> searchFuzzy(String query, int amount);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT s.opuic,
                           s.name,
                           s.abbreviated_name,
                           s.position
                    FROM station s
                    ORDER BY s.position <-> :point
                    LIMIT :amount
                    """
    )
    List<StationSearch> findClosest(Point point, int amount);
}
