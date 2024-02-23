package pl.wojdylak.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojdylak.userservice.domain.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
