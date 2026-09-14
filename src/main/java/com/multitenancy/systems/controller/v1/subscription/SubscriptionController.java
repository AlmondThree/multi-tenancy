package com.multitenancy.systems.controller.v1.subscription;

import com.multitenancy.systems.dto.subscription.SubscriptionListResponse;
import com.multitenancy.systems.dto.subscription.SubscriptionRequest;
import com.multitenancy.systems.dto.subscription.SubscriptionResponse;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.Subscription;
import com.multitenancy.systems.services.subscription.SubscriptionServices;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/subscription")
public class SubscriptionController {
    private final SubscriptionServices subscriptionServices;

    public SubscriptionController(SubscriptionServices subscriptionServices) {
        this.subscriptionServices = subscriptionServices;
    }

    @PostMapping
    public ResponseEntity<DefaultResponseFormat> createSubscription(@RequestBody SubscriptionRequest request){
        DefaultResponseFormat response = subscriptionServices.createSubscription(request);
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponseFormat> updateSubscription(@RequestBody SubscriptionRequest request, @PathVariable String id) {
        DefaultResponseFormat response = subscriptionServices.updateSubscription(request, id);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/list")
    public ResponseEntity<SubscriptionListResponse> getListSubscription(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String ordered,
            @RequestParam(required = false) String tenantServices,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String paymentInfo
    ) {
        Integer targetPage = (page < 1) ? 0 : page - 1;

        Page<Subscription> responseData = subscriptionServices.getListSubscription(
                targetPage,
                size,
                ordered,
                tenantServices,
                status,
                paymentInfo
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

        SubscriptionListResponse response = new SubscriptionListResponse(
                true,
                "OK",
                pages,
                null,
                responseData.getContent()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> getDetailSubscription(
            @PathVariable String id
    ) {
        Subscription responseData = subscriptionServices.getDetailSubscription(id)
                .orElseThrow(() -> new DataNotFoundException(String.format("Data with Id: %s is not found", id)));

        SubscriptionResponse response = new SubscriptionResponse(
                true,
                "Success found data!",
                null,
                responseData
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

}
