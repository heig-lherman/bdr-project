package heig.bdr.choochoo.business.repository;

import heig.bdr.choochoo.business.model.locality.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
