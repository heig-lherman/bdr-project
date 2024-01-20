package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.api.review.model.ReviewListViewModel;
import heig.bdr.choochoo.business.model.Line;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReviewRepository extends Repository<Line, Integer> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT DISTINCT l.line_number AS line,
                                    la.id         AS assessmentId,
                                    la.grade      AS grade,
                                    la.review     AS comment,
                                    l.name        AS lineName,
                                    s1.name       AS stationStart,
                                    s2.name       AS stationEnd
                    FROM line l
                             JOIN station s1 ON s1.opuic = l.station_start_fk
                             JOIN station s2 ON s2.opuic = l.station_end_fk
                             JOIN segment s ON l.line_number = s.line_fk
                             JOIN journey_segment js ON s.id = js.segment_fk
                             JOIN journey j ON j.id = js.journey_fk
                             JOIN traveller t ON t.email = j.user_fk
                             LEFT JOIN line_assessment la ON l.line_number = la.line_fk
                    WHERE t.email = :user
                    """
    )
    List<ReviewListViewModel> findVisitedLines(String user);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO line_assessment (grade, review, line_fk, user_fk)
                    VALUES (:grade, :review, :line, :email)
                    """
    )
    void create(long line, int grade, String review, String email);
}
