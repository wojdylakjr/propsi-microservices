package pl.wojdylak.userservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wojdylak.userservice.domain.Authority;
import pl.wojdylak.userservice.domain.AuthorityEnum;
import pl.wojdylak.userservice.domain.User;
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

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public User save(User user) {

        if (user.getAuthorities() != null) {
            authorityService.saveAuthority(user.getAuthorities());

            if (user.getAuthorities().contains(new Authority(AuthorityEnum.ROLE_TENANT))) {
                tenantService.createDefaultTenantProfileForUser(user);
            }

            if (user.getAuthorities().contains(new Authority(AuthorityEnum.ROLE_OWNER))) {
                ownerService.createDefaultOwnerProfileForUser(user);
            }
        }

        return userRepository.save(user);
    }


}
