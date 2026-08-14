package com.multitenancy.systems.dto.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ServicesTraxifyRequest {
    private UUID Id;

    private String name;

    private String description;

    private String code;

    private Long price;

    private String pricingMethods;

    private Long validityPeriod;

    private String unit;

    private String serviceUrl;

    private String serviceLogo;

    @JsonProperty("isActive")
    private Boolean isActive;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime updatedDate;

    private String updatedBy;
}
