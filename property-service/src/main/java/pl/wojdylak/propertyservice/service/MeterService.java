package pl.wojdylak.propertyservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.propertyservice.domain.Meter;
import pl.wojdylak.propertyservice.domain.MeterMeasurement;
import pl.wojdylak.propertyservice.domain.dto.CreateMeterMeasurementRequestDto;
import pl.wojdylak.propertyservice.repository.MeterMeasurementRepository;
import pl.wojdylak.propertyservice.repository.MeterRepository;
import pl.wojdylak.propertyservice.service.creator.MeterMeasurementCreator;

import java.util.List;

@Service
@AllArgsConstructor
public class MeterService {
    private final MeterMeasurementRepository meterMeasurementRepository;
    private final MeterRepository meterRepository;
    private final MeterMeasurementCreator meterMeasurementCreator;

    @Transactional
    public void save(CreateMeterMeasurementRequestDto createMeterMeasurementRequestDto) {

        Meter meter = meterRepository.findById(createMeterMeasurementRequestDto.meterId())
                .orElseThrow();

        MeterMeasurement meterMeasurement = meterMeasurementCreator.create(createMeterMeasurementRequestDto, meter);

        meterMeasurementRepository.save(meterMeasurement);
    }

    public List<MeterMeasurement> findAll() {
        return meterMeasurementRepository.findAll();
    }
}
