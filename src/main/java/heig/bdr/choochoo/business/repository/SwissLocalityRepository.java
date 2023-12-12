package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.locality.SwissLocality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwissLocalityRepository extends JpaRepository<SwissLocality, Long> {

    Optional<SwissLocality> findByFsoNumber(Integer fsoNumber);
}
