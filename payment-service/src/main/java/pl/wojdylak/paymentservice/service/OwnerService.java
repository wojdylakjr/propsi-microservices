package pl.wojdylak.paymentservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.paymentservice.domain.Owner;
import pl.wojdylak.paymentservice.repository.OwnerRepository;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

}
