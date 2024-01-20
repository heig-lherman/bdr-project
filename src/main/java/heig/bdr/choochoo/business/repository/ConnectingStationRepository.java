package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.repository.data.ConnectingStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ConnectingStationRepository extends Repository<ConnectingStation, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT s.*,
                           get_segments_distance(edges) AS total_distance,
                           line.name                    as line_name
                    FROM get_connecting_stations(:opuic, :distance) s
                             JOIN line ON line.line_number = s.line
                    ORDER BY s.distance;
                    """
    )
    List<ConnectingStation> findConnectingStations(long opuic, int distance);
}
