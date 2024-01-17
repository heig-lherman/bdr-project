package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.api.team.model.TeamListViewModel;
import heig.bdr.choochoo.business.model.user.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TeamRepository extends Repository<Team, Long> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT
                        t.id,
                        t.nom AS name,
                        COUNT(DISTINCT u.email) as userCount
                    FROM team t
                    LEFT JOIN traveller u on t.id = u.team_fk
                    GROUP BY t.id
                    """
    )
    List<TeamListViewModel> findAll();

    @Query(
            nativeQuery = true,
            value = """
                    SELECT
                        t.id,
                        t.nom,
                        u.username,
                        u.first_name,
                        u.last_name
                    FROM team t
                    LEFT JOIN traveller u on t.id = u.team_fk
                    WHERE t.id = :id
                    """
    )
    Team findDetailById(Long id);

    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO team (nom)
                    VALUES (:name)
                    RETURNING *
                    """
    )
    Team create(String name);
}
