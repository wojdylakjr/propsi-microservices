package pl.wojdylak.rentalservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wojdylak.rentalservice.domain.dto.BillResponseDto;
import pl.wojdylak.rentalservice.domain.dto.CreateBillDto;
import pl.wojdylak.rentalservice.service.BillService;

import java.util.List;

@RestController
@AllArgsConstructor
public class BillResource {
    private final BillService billService;

    @PostMapping(("create-bill"))
    public void createBill(@RequestBody CreateBillDto createBillDto) {
        billService.createBill(createBillDto);
    }

    @GetMapping(("/bills"))
    public List<BillResponseDto> getAllBills() {
        return billService.findAll();
    }

//    @GetMapping(("/bills/{id}"))
//    public List<Bill> getAllBills(@PathVariable Long id) {
//        return billService.findAll();
//    }
}