package com.multitenancy.systems.controller.tenants;


import com.multitenancy.systems.dto.tenants.TenantResponse;
import com.multitenancy.systems.dto.tenants.TenantRequest;
import com.multitenancy.systems.services.tenants.TenantsServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    private final TenantsServices tenantsServices;

    public TenantController(TenantsServices tenantsServices) {
        this.tenantsServices = tenantsServices;
    }

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(@RequestBody TenantRequest request) {
        TenantResponse response = tenantsServices.createTenant(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
