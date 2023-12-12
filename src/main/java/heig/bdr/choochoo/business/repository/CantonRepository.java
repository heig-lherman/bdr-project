package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.locality.Canton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CantonRepository extends JpaRepository<Canton, String> {
}
