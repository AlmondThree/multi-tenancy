package com.multitenancy.systems.services.tenants;

import com.multitenancy.systems.dto.tenants.TenantResponse;
import com.multitenancy.systems.dto.tenants.TenantRequest;
import com.multitenancy.systems.model.Tenants;
import com.multitenancy.systems.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TenantsServices {

    private final TenantRepository tenantRepository;


    public TenantsServices(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantResponse createTenant(TenantRequest request) {
        String paramId = String.format("%s-%d", request.getShortName(), new Random().nextInt(100)) ;

        Tenants tenant = new Tenants(
                paramId,
                request.getName(),
                request.getShortName(),
                request.getAddress(),
                request.getCountry(),
                request.getIndustrySector(),
                request.getEmail(),
                request.getContact(),
                request.getContact(),
                request.getOrganizationLogo(),
                request.getOrganizationWebsite(),
                request.getAdminAccount(),
                request.getLdap(),
                request.getActive(),
                LocalDateTime.now()
        );

        tenantRepository.save(tenant);

        return new TenantResponse(true, "OK!", paramId);

    }

}
