package pl.wojdylak.propertyservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wojdylak.propertyservice.domain.MeterMeasurement;
import pl.wojdylak.propertyservice.domain.dto.CreateMeterMeasurementRequestDto;
import pl.wojdylak.propertyservice.service.MeterService;

import java.util.List;

@RestController
@AllArgsConstructor
public class MeterResource {
    private final MeterService meterService;

    @PostMapping("/meter-measurements")
    public void createMeterMeasurement(@RequestBody CreateMeterMeasurementRequestDto createMeterMeasurementRequestDto) {
        meterService.save(createMeterMeasurementRequestDto);
    }

    @GetMapping("/meter-measurements")
    public List<MeterMeasurement> getAllMeterMeasurements() {
        List<MeterMeasurement> all = meterService.findAll();
        System.out.println(all);
        return all;
    }

}
