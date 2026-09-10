package com.multitenancy.systems.controller.v1.tenantservices;

import com.multitenancy.systems.dto.join.TenantByServices;
import com.multitenancy.systems.dto.tenantservices.*;
import com.multitenancy.systems.dto.join.ServicesByTenant;
import com.multitenancy.systems.exceptions.BadRequestException;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.TenantServices;
import com.multitenancy.systems.services.tenantservices.TenantServicesService;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tenant-services")
public class TenantServicesController {
    private final TenantServicesService tenantServicesService;

    public TenantServicesController(TenantServicesService tenantServicesService) {
        this.tenantServicesService = tenantServicesService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponseFormat> createTenant(@RequestBody TenantServicesRequest request, @RequestHeader("x-user-id") String userId) {
        DefaultResponseFormat response = tenantServicesService.createTenantServices(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponseFormat> updateTenantServices(@RequestBody TenantServicesRequest request, @PathVariable String id, @RequestHeader("x-user-id") String userId) {
        DefaultResponseFormat response = tenantServicesService.updateTenantServices(request, id, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<TenantServicesListResponse> getListTenantServices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String tenantId,
            @RequestParam(required = false) String serviceId,
            @RequestParam(required = false) Boolean isActive
    ) {
        Integer targetPage = (page < 1) ? 0 : page - 1;

        Page<TenantServices> responseData = tenantServicesService.getListTenantServices(
                targetPage,
                size,
                tenantId,
                serviceId,
                isActive
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

        TenantServicesListResponse response = new TenantServicesListResponse(
                true,
                "Ok",
                pages,
                null,
                responseData.getContent()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantServicesResponse> getDetailTenantServices(@PathVariable String id) {
        TenantServices responseData = tenantServicesService.getDetailsTenant(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Data with Id: %s is not found", id)));

        TenantServicesResponse response = new TenantServicesResponse(
                true,
                "Ok",
                null,
                responseData
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/services/list")
    public ResponseEntity<ServicesListResponse> getListServices(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String tenantId
    ) {

        if(tenantId.isEmpty()) {
            throw new BadRequestException("tenantId is empty");
        }

        Integer targetPage = (page < 1) ? 0 : page - 1;

        Page<ServicesByTenant> responseData = tenantServicesService.getListServicesByTenantId(
                targetPage,
                size,
                tenantId
        );

        if(responseData.isEmpty()) throw new DataNotFoundException("Data not found");

        Pages pages = new Pages(
                responseData.getNumber() + 1,
                responseData.getNumberOfElements(),
                (long) responseData.getTotalPages(),
                responseData.getTotalElements(),
                null,
                null
        );

        ServicesListResponse response = new ServicesListResponse(
                true,
                "Ok",
                pages,
                null,
                responseData.getContent()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/tenants/list")
    public ResponseEntity<TenantsListResponse> getListTenants(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String serviceId
    ) {

        if(serviceId.isEmpty()) {
            throw new BadRequestException("serviceId is empty");
        }

        Integer targetPage = (page < 1) ? 0 : page - 1;

        Page<TenantByServices> responseData = tenantServicesService.getListTenantsByServiceId(
                targetPage,
                size,
                serviceId
        );

        if(responseData.isEmpty()) throw new DataNotFoundException("Data not found");

        Pages pages = new Pages(
                responseData.getNumber() + 1,
                responseData.getNumberOfElements(),
                (long) responseData.getTotalPages(),
                responseData.getTotalElements(),
                null,
                null
        );

        TenantsListResponse response = new TenantsListResponse(
                true,
                "Ok",
                pages,
                null,
                responseData.getContent()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
