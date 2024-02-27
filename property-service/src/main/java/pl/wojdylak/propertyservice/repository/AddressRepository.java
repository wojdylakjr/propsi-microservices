package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.propertyservice.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
