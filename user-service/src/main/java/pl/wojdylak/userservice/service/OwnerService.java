package pl.wojdylak.userservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.userservice.domain.Owner;
import pl.wojdylak.userservice.domain.User;
import pl.wojdylak.userservice.domain.dto.OwnerResponseDto;
import pl.wojdylak.userservice.domain.dto.PayUCredentials;
import pl.wojdylak.userservice.repository.OwnerRepository;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner createDefaultOwnerProfileForUser(User user) {
        Owner owner = new Owner(user.getFirstName() + "-OWNER-PROFILE");

        user.getOwners().add(owner);
        owner.getUsers().add(user);

        return ownerRepository.saveAndFlush(owner);
    }

    public OwnerResponseDto findById(Long id) {
        return ownerRepository.findDtoById(id)
                .orElseThrow();
    }

    public void addPayUCredentials(Long id, PayUCredentials payUCredentials) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow();

        owner.setPayUClientId(payUCredentials.payUClientId());
        owner.setPayUClientSecret(payUCredentials.payUClientSecret());

        ownerRepository.save(owner);
    }
}
