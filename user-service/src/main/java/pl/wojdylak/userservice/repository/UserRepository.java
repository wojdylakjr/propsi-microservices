package pl.wojdylak.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.userservice.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
