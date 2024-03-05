package pl.wojdylak.userservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.userservice.domain.dto.OwnerResponseDto;
import pl.wojdylak.userservice.domain.dto.PayUCredentials;
import pl.wojdylak.userservice.service.OwnerService;

@RestController
@AllArgsConstructor
@RequestMapping("/owners")
public class OwnerResource {
    private final OwnerService ownerService;

    @GetMapping("/{id}")
    public OwnerResponseDto getUserDtoById(@PathVariable Long id) {
        return ownerService.findById(id);
    }

    @PutMapping("/{id}")
    public void addPayUCredentials(@PathVariable Long id, @RequestBody PayUCredentials payUCredentials) {
        ownerService.addPayUCredentials(id, payUCredentials);
    }
}