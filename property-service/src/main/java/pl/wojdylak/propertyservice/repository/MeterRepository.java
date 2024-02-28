package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojdylak.propertyservice.domain.Meter;

public interface MeterRepository extends JpaRepository<Meter, Long> {
}
