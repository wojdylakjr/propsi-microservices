package pl.wojdylak.userservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.wojdylak.userservice.domain.Authority;
import pl.wojdylak.userservice.domain.AuthorityEnum;
import pl.wojdylak.userservice.domain.User;
import pl.wojdylak.userservice.domain.dto.UserResponseDto;
import pl.wojdylak.userservice.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final TenantService tenantService;
    private final OwnerService ownerService;

    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAllUsers();
    }

    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with ID: " + id));

    }

    @Transactional
    public User save(User user) {

        if (user.getAuthorities() != null) {
            authorityService.saveAuthorities(user.getAuthorities());

            if (user.getAuthorities().contains(new Authority(AuthorityEnum.ROLE_TENANT))) {
                tenantService.createDefaultTenantProfileForUser(user);
            }

            if (user.getAuthorities().contains(new Authority(AuthorityEnum.ROLE_OWNER))) {
                ownerService.createDefaultOwnerProfileForUser(user);
            }
        }

        return userRepository.save(user);
    }

    public void deleteById(Long id) {

        userRepository.deleteById(id);
    }

    public void deleteAll() {

        userRepository.deleteAll();
    }
}
