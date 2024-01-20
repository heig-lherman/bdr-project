package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.repository.data.UserProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface UserProgressRepository extends Repository<UserProgress, String> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT user_email,
                           st_simplify(segments, 0.00005) AS segments,
                           travelled_count,
                           total_count
                    FROM user_progress
                    WHERE user_email = :userEmail
                    """
    )
    UserProgress getUserProgress(String userEmail);
}
