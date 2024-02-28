package pl.wojdylak.propertyservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojdylak.propertyservice.domain.dto.CreateMeterRequestDto;
import pl.wojdylak.propertyservice.service.MeterService;

@RestController
@RequestMapping("/meters")
@AllArgsConstructor
public class MeterResource {
    private final MeterService meterService;

    public void createMeterForPremises(CreateMeterRequestDto createMeterRequestDto) {

    }

}
