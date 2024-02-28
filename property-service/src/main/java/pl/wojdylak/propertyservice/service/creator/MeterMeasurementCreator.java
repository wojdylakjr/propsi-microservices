package pl.wojdylak.propertyservice.service.creator;

import org.springframework.stereotype.Component;
import pl.wojdylak.propertyservice.domain.Meter;
import pl.wojdylak.propertyservice.domain.MeterMeasurement;
import pl.wojdylak.propertyservice.domain.dto.CreateMeterMeasurementRequestDto;

@Component
public class MeterMeasurementCreator {
    public MeterMeasurement create(CreateMeterMeasurementRequestDto createMeterMeasurementRequestDto,
                                   Meter meter) {

        return MeterMeasurement.builder()
                .value(createMeterMeasurementRequestDto.value())
                .unit(createMeterMeasurementRequestDto.unit())
                .date(createMeterMeasurementRequestDto.date())
                .meter(meter)
                .build();
    }
}
