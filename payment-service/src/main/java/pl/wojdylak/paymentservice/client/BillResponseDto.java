package pl.wojdylak.paymentservice.client;

import java.math.BigDecimal;
import java.time.Instant;

public record BillResponseDto(
        Long billId,
        Long tenantId,
        Long ownerId,
        Long rentalId,
        Long premisesId,
        BigDecimal value,
        Instant date
) {
}
