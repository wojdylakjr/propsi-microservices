package pl.wojdylak.propertyservice.domain.dto;

import java.time.Instant;

public record CreateMeterMeasurementRequestDto(
        Double value,
        Instant date,
        String unit,
        Long meterId
) {
}
