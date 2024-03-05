package pl.wojdylak.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.paymentservice.domain.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
