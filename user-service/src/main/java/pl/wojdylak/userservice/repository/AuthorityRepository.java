package pl.wojdylak.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.userservice.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
