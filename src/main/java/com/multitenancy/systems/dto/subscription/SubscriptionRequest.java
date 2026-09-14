package com.multitenancy.systems.dto.subscription;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubscriptionRequest {
    private String id;

    private String tenantServices;

    private String status;

    private LocalDateTime lastPayment;

    private LocalDateTime nextPayment;

    private String paymentInfo;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
