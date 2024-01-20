package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    UPDATE traveller
                    SET team_fk = :teamId
                    WHERE email = :email
                    """
    )
    void joinTeam(String email, Long teamId);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    UPDATE traveller
                    SET team_fk = NULL
                    WHERE email = :email
                    """
    )
    void leaveTeam(String email);

    // Required for authentication system
    @EntityGraph(attributePaths = {"team"})
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
