package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.api.journey.model.JourneyListViewModel;
import heig.bdr.choochoo.business.model.Journey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.ZonedDateTime;
import java.util.List;

public interface JourneyRepository extends Repository<Journey, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT
                        j.id,
                        j.start_date AS startDate,
                        j.end_date AS endDate,
                        j.grade,
                        j.review,
                        COUNT(DISTINCT js.segment_fk) as segmentCount
                    FROM journey j
                    LEFT JOIN journey_segment js on j.id = js.journey_fk
                    GROUP BY j.id
                    """
    )
    List<JourneyListViewModel> findAll();

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    CALL create_journey(
                        :userEmail,
                        :startDate,
                        :endDate,
                        :segments,
                        :grade,
                        :review,
                        :team
                    );
                    """
    )
    void createJourney(
            String userEmail,
            ZonedDateTime startDate,
            ZonedDateTime endDate,
            long[] segments,
            int grade,
            String review,
            boolean team
    );
}
