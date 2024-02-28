package pl.wojdylak.propertyservice.domain.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record CreatePremisesCostDetailRequestDto(BigDecimal value, Instant date, String unit, Long premisesCostId) {
}
