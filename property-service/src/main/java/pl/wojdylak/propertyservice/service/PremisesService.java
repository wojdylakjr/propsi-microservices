package pl.wojdylak.propertyservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.propertyservice.domain.dto.PremisesCostsDetailsResponseDto;
import pl.wojdylak.propertyservice.repository.PremisesRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PremisesService {
    private final PremisesRepository premisesRepository;

    public List<PremisesCostsDetailsResponseDto> getPremisesCostsInMonth(Long premisesId,
                                                                         Integer monthValue) {

        return premisesRepository.findPremisesCostsWithDetailsByPremisesIdAndMonth(premisesId, monthValue);


    }
}
