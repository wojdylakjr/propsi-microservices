package pl.wojdylak.rentalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wojdylak.rentalservice.domain.Rental;
import pl.wojdylak.rentalservice.domain.dto.RentalResponseDto;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT NEW pl.wojdylak.rentalservice.domain.dto.RentalResponseDto(r.id, r.tenantId, r.ownerId, r.premisesId, r.name, r.rentPrice, r.rentalStartDate, r.rentalEndDate, r.paymentDay, r.costsPart) FROM Rental r")
    List<RentalResponseDto> findAllRentals();
}
