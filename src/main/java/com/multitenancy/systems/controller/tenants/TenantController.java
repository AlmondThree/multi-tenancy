package com.multitenancy.systems.controller.tenants;


import com.multitenancy.systems.dto.tenants.TenantDetailsResponse;
import com.multitenancy.systems.dto.tenants.TenantListResponse;
import com.multitenancy.systems.dto.tenants.TenantResponse;
import com.multitenancy.systems.dto.tenants.TenantRequest;
import com.multitenancy.systems.model.Tenants;
import com.multitenancy.systems.services.tenants.TenantsServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping
    public ResponseEntity<TenantResponse> updateTenant(@RequestBody TenantRequest request) {
        TenantResponse response = tenantsServices.updateTenant(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<TenantListResponse> getListTenant() {

        List<Tenants> responseData = tenantsServices.getListTenant();

        TenantListResponse response = new TenantListResponse();

        response.setSuccess(true);
        response.setMessage("OK!");
        response.setData(responseData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDetailsResponse> getDetailTenant(@PathVariable String id){
        Tenants responseData = tenantsServices.getDetailTenant(id);
        TenantDetailsResponse response = new TenantDetailsResponse();

        response.setSuccess(true);
        response.setMessage("OK");
        response.setData(responseData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
