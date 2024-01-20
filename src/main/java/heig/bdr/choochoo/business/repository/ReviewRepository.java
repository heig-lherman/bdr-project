package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.api.review.model.ReviewListViewModel;
import heig.bdr.choochoo.business.model.Line;
import heig.bdr.choochoo.business.model.LineAssessment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReviewRepository extends Repository<Line, Integer> {

    @Query(
            nativeQuery = true,
            value = """
                    select distinct l.line_number as line, la.id as id, la.grade as grade, l.name as line_name, s.name as stationStart, s2.name as stationEnd
                    from line as l
                             inner join public.station s on s.opuic = l.station_start_fk
                             inner join public.station s2 on s2.opuic = l.station_end_fk
                             inner join public.segment s on l.line_number = s.line_fk
                             inner join public.journey_segment js on s.id = js.segment_fk
                             inner join public.journey j on j.id = js.journey_fk
                             inner join public.traveller t on t.email = j.user_fk
                             left join public.line_assessment la on l.line_number = la.line_fk
                    where t.email = :user
                            """
    )
    List<ReviewListViewModel> findAll(String user);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT la.id, la.grade, la.review, s.name as stationStart , s2.name as stationEnd
                        FROM public.line_assessment as la
                        inner join public.line l on l.line_number = la.line_fk
                        inner join public.station s on s.opuic = l.station_start_fk
                        inner join public.station s2 on s2.opuic = l.station_end_fk
                        WHERE la.id = :reviewId
                    """
    )
    LineAssessment findDetailById(Long reviewId);

    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO line_assessment (grade, review, line_fk, user_fk)
                    VALUES (:grade, :review, :line, :email)
                    RETURNING *
                    """
    )
    LineAssessment create(long line, int grade, String review, String email);
}
