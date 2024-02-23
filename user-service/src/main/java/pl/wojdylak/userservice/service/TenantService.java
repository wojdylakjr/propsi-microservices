package pl.wojdylak.userservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.userservice.domain.Tenant;
import pl.wojdylak.userservice.domain.User;
import pl.wojdylak.userservice.repository.TenantRepository;

@Service
@AllArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;

    public Tenant createDefaultTenantProfileForUser(User user) {

        Tenant tenant = new Tenant(user.getFirstName() + "-TENANT-PROFILE");

        user.getTenants().add(tenant);
        tenant.getUsers().add(user);

        return tenantRepository.saveAndFlush(tenant);
    }
}
