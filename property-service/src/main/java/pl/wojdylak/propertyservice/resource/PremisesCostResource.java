package pl.wojdylak.propertyservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wojdylak.propertyservice.domain.PremisesCostDetail;
import pl.wojdylak.propertyservice.domain.dto.CreatePremisesCostDetailRequestDto;
import pl.wojdylak.propertyservice.service.PremisesCostService;

import java.util.List;

@RestController
@AllArgsConstructor
public class PremisesCostResource {
    private final PremisesCostService premisesCostService;

    @PostMapping("/premises-costs-details")
    public void createPremisesCostDetail(@RequestBody CreatePremisesCostDetailRequestDto createPremisesCostDetailRequestDto) {
        premisesCostService.save(createPremisesCostDetailRequestDto);
    }

    @GetMapping("/premises-costs-details")
    public List<PremisesCostDetail> findAllPremisesCostDetail() {
        return premisesCostService.findAllCostDetails();
    }
}
