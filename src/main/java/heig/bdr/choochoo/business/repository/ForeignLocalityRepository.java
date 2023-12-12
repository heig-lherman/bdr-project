package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.locality.ForeignLocality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForeignLocalityRepository extends JpaRepository<ForeignLocality, Long> {

    Optional<ForeignLocality> findByName(String name);
}
