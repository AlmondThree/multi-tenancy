package com.multitenancy.systems.controller.v1.services;

import com.multitenancy.systems.dto.services.ServiceTraxifyDetailResponse;
import com.multitenancy.systems.dto.services.ServiceTraxifyListResponse;
import com.multitenancy.systems.dto.services.ServicesTraxifyRequest;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.Services;
import com.multitenancy.systems.services.services.ServiceTraxifyServices;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/services")
public class ServiceTraxifyController {
    private final ServiceTraxifyServices serviceTraxifyServices;

    public ServiceTraxifyController(ServiceTraxifyServices serviceTraxifyServices) {this.serviceTraxifyServices = serviceTraxifyServices;}

    @PostMapping
    public ResponseEntity<DefaultResponseFormat> insertService(@RequestBody ServicesTraxifyRequest request){
        DefaultResponseFormat response = serviceTraxifyServices.insertService(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponseFormat> updateService(@RequestBody ServicesTraxifyRequest request, @PathVariable String id){
        DefaultResponseFormat response = serviceTraxifyServices.updateService(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ServiceTraxifyListResponse> getListService(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String ordered,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "") String pricingMethods
    ){
        Integer targetPage = (page < 1) ? 0 : page - 1;

        Page<Services> responseData = serviceTraxifyServices.getListServices(
                targetPage,
                size,
                name,
                code,
                pricingMethods,
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

        ServiceTraxifyListResponse response = new ServiceTraxifyListResponse(
                true,
                "OK!",
                pages,
                null,
                responseData.getContent()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTraxifyDetailResponse> getDetailServicesTraxify(@PathVariable String id) {
        Services responseData = serviceTraxifyServices.getDetailServices(id);

        ServiceTraxifyDetailResponse response = new ServiceTraxifyDetailResponse(
                true,
                "Success found data!",
                null,
                null,
                responseData
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
