package pl.wojdylak.propertyservice.service.creator;

import org.springframework.stereotype.Component;
import pl.wojdylak.propertyservice.domain.PremisesCost;
import pl.wojdylak.propertyservice.domain.PremisesCostDetail;
import pl.wojdylak.propertyservice.domain.dto.CreatePremisesCostDetailRequestDto;

@Component
public class PremisesCostDetailCreator {
    public PremisesCostDetail create(CreatePremisesCostDetailRequestDto createPremisesCostDetailRequestDto,
                                     PremisesCost premisesCost) {

        return PremisesCostDetail.builder()
                .costValue(createPremisesCostDetailRequestDto.value())
                .date(createPremisesCostDetailRequestDto.date())
                .unit(createPremisesCostDetailRequestDto.unit())
                .premisesCost(premisesCost)
                .build();
    }
}
