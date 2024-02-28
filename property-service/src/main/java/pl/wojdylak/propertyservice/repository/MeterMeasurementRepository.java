package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojdylak.propertyservice.domain.MeterMeasurement;

public interface MeterMeasurementRepository extends JpaRepository<MeterMeasurement, Long> {
}
