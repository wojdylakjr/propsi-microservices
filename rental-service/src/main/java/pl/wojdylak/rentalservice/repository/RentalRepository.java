package pl.wojdylak.rentalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.rentalservice.domain.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
