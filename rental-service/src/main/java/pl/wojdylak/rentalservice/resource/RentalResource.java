package pl.wojdylak.rentalservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.rentalservice.domain.Rental;
import pl.wojdylak.rentalservice.service.RentalService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rentals")
public class RentalResource {
    private final RentalService rentalService;

    @PostMapping()
    public void addRental(@RequestBody Rental rental) {
        rentalService.save(rental);
    }

    @GetMapping
    public List<Rental> getAll() {
        return rentalService.findAll();
    }
}