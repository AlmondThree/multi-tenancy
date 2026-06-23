package com.multitenancy.systems.services.tenants;

import com.multitenancy.systems.dto.tenants.TenantResponse;
import com.multitenancy.systems.dto.tenants.TenantRequest;
import com.multitenancy.systems.model.Tenants;
import com.multitenancy.systems.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
                request.getOrganizationLogo(),
                request.getOrganizationWebsite(),
                request.getAdminAccount(),
                request.getLdap(),
                request.getIsActive()
        );

        tenant.setCreatedDate(LocalDateTime.now());

        tenantRepository.save(tenant);

        return new TenantResponse(true, "OK!", paramId);

    }

    public TenantResponse updateTenant(TenantRequest request) {

        Tenants tenantData = tenantRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(request.getName() != null && !request.getName().isBlank()) {tenantData.setName(request.getName());}
        if(request.getShortName() != null && !request.getShortName().isBlank()) {tenantData.setShortName(request.getShortName());}
        if(request.getAddress() != null && !request.getAddress().isBlank()) {tenantData.setAddress(request.getAddress());}
        if(request.getCountry() != null && !request.getCountry().isBlank()) {tenantData.setCountry(request.getCountry());}
        if(request.getIndustrySector() != null && !request.getIndustrySector().isBlank()) {tenantData.setIndustrySector(request.getIndustrySector());}
        if(request.getEmail() != null && !request.getEmail().isBlank()) {tenantData.setEmail(request.getEmail());}
        if(request.getContact() != null && !request.getContact().isBlank()) {tenantData.setContact(request.getContact());}
        if(request.getOrganizationLogo() != null && !request.getOrganizationLogo().isBlank()) {tenantData.setOrganizationLogo(request.getOrganizationLogo());}
        if(request.getOrganizationWebsite() != null && !request.getOrganizationWebsite().isBlank()) {tenantData.setOrganizationWebsite(request.getOrganizationWebsite());}
        if(request.getAdminAccount() != null && !request.getAdminAccount().isBlank()) {tenantData.setAdminAccount(request.getAdminAccount());}
        if(request.getLdap() != null && !request.getLdap().isBlank()) {tenantData.setLdap(request.getLdap());}
        if(request.getIsActive() != null) {tenantData.setIsActive(request.getIsActive());}

        tenantData.setUpdatedDate(LocalDateTime.now());

        tenantRepository.save(tenantData);

        return new TenantResponse(true, "OK!", tenantData.getId());
    }

    public List<Tenants> getListTenant() {
        return tenantRepository.findAll();
    }

    public Tenants getDetailTenant(String id) {
        return tenantRepository.getReferenceById(id);
    }

}
