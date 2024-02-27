package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.propertyservice.domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
