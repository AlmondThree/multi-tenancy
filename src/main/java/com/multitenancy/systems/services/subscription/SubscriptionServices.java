package com.multitenancy.systems.services.subscription;

import com.multitenancy.systems.dto.subscription.SubscriptionRequest;
import com.multitenancy.systems.model.Subscription;
import com.multitenancy.systems.repository.SubscriptionRepository;
import com.multitenancy.systems.specification.SubscriptionSpecifications;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubscriptionServices {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServices(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public DefaultResponseFormat createSubscription(SubscriptionRequest request) {
        Subscription data = new Subscription(
                request.getId(),
                request.getTenantServices(),
                request.getStatus(),
                request.getLastPayment(),
                request.getNextPayment(),
                request.getPaymentInfo()
        );

        data.setCreatedDate(LocalDateTime.now());

        subscriptionRepository.save(data);

        return new DefaultResponseFormat(
                true,
                "OK",
                request.getId()
        );

    }

    public DefaultResponseFormat updateSubscription(SubscriptionRequest request, String id) {

        Subscription data = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getTenantServices() != null && !request.getTenantServices().isBlank()) {data.setTenantServices(request.getTenantServices());}
        if (request.getStatus() != null && !request.getStatus().isBlank()) {data.setStatus(request.getStatus());}
        if (request.getLastPayment() != null) {data.setLastPayment(request.getLastPayment());}
        if (request.getNextPayment() != null) {data.setNextPayment(request.getNextPayment());}
        if (request.getPaymentInfo() != null && !request.getPaymentInfo().isBlank()) {data.setPaymentInfo(request.getPaymentInfo());}

        data.setUpdatedDate(LocalDateTime.now());

        subscriptionRepository.save(data);

        return new DefaultResponseFormat(
                true,
                "OK",
                id
        );
    }

    public Page<Subscription> getListSubscription(
            Integer page,
            Integer size,
            String ordered,
            String tenantServices,
            String status,
            String paymentInfo
    ) {
        Sort sort = ordered.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by("createdDate").ascending() : Sort.by("createdDate").descending();
        Pageable pages = PageRequest.of(page, size, sort);

        return subscriptionRepository.findAll(
                Specification.where(SubscriptionSpecifications.hasTenantServices(tenantServices)
                        .and(SubscriptionSpecifications.hasStatus(status))
                        .and(SubscriptionSpecifications.hasPaymentInfo(paymentInfo))
                ),
                pages
        );
    }

    public Optional<Subscription> getDetailSubscription(String id) {
        return subscriptionRepository.findById(id);
    }

}
