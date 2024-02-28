package pl.wojdylak.propertyservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.propertyservice.domain.PremisesCost;
import pl.wojdylak.propertyservice.domain.PremisesCostDetail;
import pl.wojdylak.propertyservice.domain.dto.CreatePremisesCostDetailRequestDto;
import pl.wojdylak.propertyservice.repository.PremisesCostDetailRepository;
import pl.wojdylak.propertyservice.repository.PremisesCostRepository;
import pl.wojdylak.propertyservice.service.creator.PremisesCostDetailCreator;

import java.util.List;

@Service
@AllArgsConstructor
public class PremisesCostService {
    private final PremisesCostRepository premisesCostRepository;
    private final PremisesCostDetailRepository premisesCostDetailRepository;
    private final PremisesCostDetailCreator premisesCostDetailCreator;

    public void save(CreatePremisesCostDetailRequestDto createPremisesCostDetailRequestDto) {

        PremisesCost premisesCost = premisesCostRepository.findById(createPremisesCostDetailRequestDto.premisesCostId())
                .orElseThrow();

        PremisesCostDetail premisesCostDetail = premisesCostDetailCreator.create(createPremisesCostDetailRequestDto, premisesCost);

        premisesCostDetailRepository.save(premisesCostDetail);

    }

    public List<PremisesCostDetail> findAllCostDetails() {
        return premisesCostDetailRepository.findAll();
    }
}
