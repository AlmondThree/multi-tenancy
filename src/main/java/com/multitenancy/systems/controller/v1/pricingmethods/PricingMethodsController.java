package com.multitenancy.systems.controller.v1.pricingmethods;

import com.multitenancy.systems.dto.pricingmethods.PricingMethodsListResponse;
import com.multitenancy.systems.dto.pricingmethods.PricingMethodsRequest;
import com.multitenancy.systems.dto.pricingmethods.PricingMethodsResponse;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.PricingMethods;
import com.multitenancy.systems.model.Subscription;
import com.multitenancy.systems.services.pricingmethods.PricingMethodsServices;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pricing-methods")
public class PricingMethodsController {
    private final PricingMethodsServices pricingMethodsServices;

    public PricingMethodsController(PricingMethodsServices pricingMethodsServices) {
        this.pricingMethodsServices = pricingMethodsServices;
    }

    @PostMapping
    public ResponseEntity<DefaultResponseFormat> createPricingMethods(@RequestBody PricingMethodsRequest request, @RequestHeader("x-user-id") String userId) {
        DefaultResponseFormat response = pricingMethodsServices.createPricingMethods(request, userId);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponseFormat> updatePricingMethods(@RequestBody PricingMethodsRequest request, @RequestHeader("x-user-id") String userId, @PathVariable String id) {
        DefaultResponseFormat response = pricingMethodsServices.updatePricingMethods(request, userId, id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/list")
    public ResponseEntity<PricingMethodsListResponse> getListPricingMethods(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String ordered,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer logic,
            @RequestParam(required = false) String unit
    ){
        Integer targetPage = (page < 1) ? 0 : page - 1;

        Page<PricingMethods> responseData = pricingMethodsServices.getListPricingMethods(
                targetPage,
                size,
                ordered,
                code,
                name,
                logic,
                unit
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

        PricingMethodsListResponse response = new PricingMethodsListResponse(
                true,
                "OK",
                pages,
                responseData.getContent()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<PricingMethodsResponse> getDetailPricingMethods(@PathVariable String id) {
        PricingMethods data = pricingMethodsServices.getDetailPricingMethods(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Data with Id: %s is not found", id)));

        PricingMethodsResponse response = new PricingMethodsResponse(
                true,
                "OK",
                data
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

}
