package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("""
            SELECT station
                FROM Station station
                WHERE UNACCENT(LOWER(station.name)) = UNACCENT(LOWER(:name))
            """)
    Optional<Station> findByNameInsensitive(String name);

    Optional<Station> findByAbbreviatedName(String abbreviatedName);
}
