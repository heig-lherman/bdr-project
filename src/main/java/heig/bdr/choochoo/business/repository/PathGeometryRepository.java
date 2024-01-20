package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.repository.data.PathGeometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface PathGeometryRepository extends Repository<PathGeometry, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT min(l.id)                                      AS id,
                           st_multi(st_linemerge(st_collect(s.geometry))) AS geometry
                    FROM unnest(:segments) AS l(id)
                             JOIN segment s ON s.id = l.id;
                    """
    )
    PathGeometry findPathGeometry(long[] segments);
}
