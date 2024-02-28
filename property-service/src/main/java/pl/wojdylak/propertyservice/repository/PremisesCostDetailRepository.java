package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojdylak.propertyservice.domain.PremisesCostDetail;

public interface PremisesCostDetailRepository extends JpaRepository<PremisesCostDetail, Long> {
}
