package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojdylak.propertyservice.domain.PremisesCost;

public interface PremisesCostRepository extends JpaRepository<PremisesCost, Long> {
}
