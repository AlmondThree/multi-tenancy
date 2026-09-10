package com.multitenancy.systems.controller.v1.tenants;


import com.multitenancy.systems.dto.tenants.TenantDetailsResponse;
import com.multitenancy.systems.dto.tenants.TenantListResponse;
import com.multitenancy.systems.dto.tenants.TenantResponse;
import com.multitenancy.systems.dto.tenants.TenantRequest;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.Tenants;
import com.multitenancy.systems.services.tenants.TenantsServices;
import com.multitenancy.systems.util.format.Pages;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tenants")
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
    public ResponseEntity<TenantListResponse> getListTenant(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String ordered,
            @RequestParam (defaultValue = "") String country
    ) {

        Integer targetPage = (page < 1) ? 0 : page - 1;

        Page<Tenants> responseData = tenantsServices.getListTenant(
                targetPage,
                size,
                country,
                ordered
        );

        if(responseData.isEmpty()) {
            throw new DataNotFoundException("Data not found");
        }

        Pages pages = new Pages(
                responseData.getNumber() + 1,
                responseData.getNumberOfElements(),
                (long) responseData.getTotalPages(),
                responseData.getTotalElements(),
                null,
                null
        );

        TenantListResponse response = new TenantListResponse(
                true,
                "OK!",
                pages,
                responseData.getContent()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDetailsResponse> getDetailTenant(@PathVariable String id){
        Tenants responseData = tenantsServices.getDetailTenant(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Data with Id: %s is not found", id)));
        TenantDetailsResponse response = new TenantDetailsResponse();

        response.setSuccess(true);
        response.setMessage("OK");
        response.setData(responseData);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
