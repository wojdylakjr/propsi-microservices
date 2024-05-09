package pl.wojdylak.rentalservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.rentalservice.domain.Bill;
import pl.wojdylak.rentalservice.domain.dto.BillResponseDto;
import pl.wojdylak.rentalservice.domain.dto.CreateBillDto;
import pl.wojdylak.rentalservice.service.BillService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bills")
public class BillResource {
    private final BillService billService;

    @PostMapping()
    public Bill createBill(@RequestBody CreateBillDto createBillDto) {
        return billService.createBill(createBillDto);
    }

    @GetMapping()
    public List<BillResponseDto> getAllBills() {
        return billService.findAll();
    }

    @GetMapping("/{id}")
    public BillResponseDto getBillById(@PathVariable Long id) {
        return billService.findBillById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBillById(@PathVariable Long id) {
        billService.deleteBillById(id);
    }
}
