package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.reference.StationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationTypeRepository extends JpaRepository<StationType, String> {
}
