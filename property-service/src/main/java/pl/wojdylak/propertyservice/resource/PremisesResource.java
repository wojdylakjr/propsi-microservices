package pl.wojdylak.propertyservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.propertyservice.domain.dto.PremisesCostsDetailsResponseDto;
import pl.wojdylak.propertyservice.service.PremisesService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/premises")
public class PremisesResource {
    private final PremisesService premisesService;

    @GetMapping("/{premisesId}/costs")
    public List<PremisesCostsDetailsResponseDto> getPremisesCostsInMonth(@PathVariable Long premisesId,
                                                                         @RequestParam Integer monthValue) {

        return premisesService.getPremisesCostsInMonth(premisesId, monthValue);
    }

}
