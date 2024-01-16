package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    Optional<UserToken> findByToken(String token);

    @Query(
            value = """
                    UPDATE traveller_token
                    SET expired = true,
                        revoked = true
                    WHERE traveller_fk = ?1
                      AND expired = false
                      AND revoked = false
                     """,
            nativeQuery = true
    )
    @Modifying
    void expireEveryActiveTokenForUser(String userEmail);
}
