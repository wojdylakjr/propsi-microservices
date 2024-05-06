package pl.wojdylak.rentalservice.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.wojdylak.rentalservice.domain.Rental;
import pl.wojdylak.rentalservice.domain.dto.RentalResponseDto;
import pl.wojdylak.rentalservice.repository.RentalRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Rental findById(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rental not found with ID: " + rentalId));
    }

    public List<RentalResponseDto> findAllRentals() {
        return rentalRepository.findAllRentals();
    }

    public void deleteById(Long id) {
        rentalRepository.deleteById(id);
    }

    public void deleteAll() {
        rentalRepository.deleteAll();
    }
}
