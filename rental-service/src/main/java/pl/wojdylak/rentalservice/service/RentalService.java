package pl.wojdylak.rentalservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.rentalservice.domain.Rental;
import pl.wojdylak.rentalservice.repository.RentalRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    public void save(Rental rental) {
        rentalRepository.save(rental);
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }
}
