package pl.wojdylak.rentalservice.property.client;

import java.math.BigDecimal;
import java.time.Instant;

public record PremisesCostsDetailsResponseDto(
        BigDecimal value,
        String unit,
        Instant date,
        String costType
) {
}