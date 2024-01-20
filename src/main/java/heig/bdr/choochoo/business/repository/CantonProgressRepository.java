package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.repository.data.CantonProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CantonProgressRepository extends Repository<CantonProgress, String> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT canton_code,
                           canton_name,
                           travelled_count,
                           total_count
                    FROM completion_by_canton
                    WHERE user_email = :userEmail
                    """
    )
    List<CantonProgress> getUserCantonProgress(String userEmail);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT canton_code,
                           canton_name,
                           SUM(travelled_count) AS travelled_count,
                           total_count
                    FROM completion_by_canton cc
                             JOIN team t ON cc.team_fk = t.id
                    WHERE cc.team_fk = :teamId
                    GROUP BY canton_code, canton_name, total_count
                    """
    )
    List<CantonProgress> getTeamCantonProgress(long teamId);
}
