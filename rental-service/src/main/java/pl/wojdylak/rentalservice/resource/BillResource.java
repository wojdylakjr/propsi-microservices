package pl.wojdylak.rentalservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wojdylak.rentalservice.service.BillService;

@RestController
@AllArgsConstructor
public class BillResource {
    private final BillService billService;

    public void createBill(@RequestBody Long rentalId) {
        billService.createBill(rentalId);
    }
}
