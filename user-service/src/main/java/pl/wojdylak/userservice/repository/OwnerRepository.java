package pl.wojdylak.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.userservice.domain.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
