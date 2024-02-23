package pl.wojdylak.userservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.userservice.domain.Authority;
import pl.wojdylak.userservice.repository.AuthorityRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public List<Authority> saveAuthority(Set<Authority> authorities) {
        return authorityRepository.saveAll(authorities);
    }
}
