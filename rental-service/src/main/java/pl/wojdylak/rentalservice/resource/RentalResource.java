package pl.wojdylak.rentalservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.rentalservice.domain.Rental;
import pl.wojdylak.rentalservice.domain.dto.RentalResponseDto;
import pl.wojdylak.rentalservice.service.RentalService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rentals")
public class RentalResource {
    private final RentalService rentalService;

    @PostMapping()
    public Rental addRental(@RequestBody Rental rental) {
        return rentalService.save(rental);
    }

    @GetMapping
    public List<RentalResponseDto> getAll() {
        return rentalService.findAllRentals();
    }

    @GetMapping("/{id}")
    public Rental getById(@PathVariable Long id) {
        return rentalService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        rentalService.deleteById(id);
    }

    @DeleteMapping()
    public void deleteAll() {
        rentalService.deleteAll();
    }
}