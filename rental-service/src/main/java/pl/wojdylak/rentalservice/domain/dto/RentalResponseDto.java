package pl.wojdylak.rentalservice.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record RentalResponseDto(
        Long rentalId,
        Long tenantId,
        Long ownerId,
        Long premisesId,
        String name,
        BigDecimal rentPrice,
        Instant rentalStartDate,
        Instant rentalEndDate,
        Integer paymentDay,
        Double costsPart
) {
}