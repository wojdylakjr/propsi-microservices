package pl.wojdylak.rentalservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.wojdylak.rentalservice.domain.Bill;
import pl.wojdylak.rentalservice.domain.dto.BillResponseDto;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT new pl.wojdylak.rentalservice.domain.dto.BillResponseDto(b.id, r.tenantId, r.ownerId, r.id, r.premisesId, b.totalPrice, b.date) " +
            "FROM Rental r " +
            "JOIN r.bills b " +
            "WHERE b.id = :billId ")
    Optional<BillResponseDto> findBillDtoById(@Param("billId") Long billId);

    @Query("SELECT new pl.wojdylak.rentalservice.domain.dto.BillResponseDto(b.id, r.tenantId,r.ownerId, r.id, r.premisesId, b.totalPrice, b.date) " +
            "FROM Rental r " +
            "JOIN r.bills b ")
    List<BillResponseDto> findAllBillDto();
}

