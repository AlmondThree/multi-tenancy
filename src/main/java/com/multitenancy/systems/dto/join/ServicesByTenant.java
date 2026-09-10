package com.multitenancy.systems.dto.join;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id", "name", "description", "code", "price", "pricingMethods", "validityPeriod", "unit", "serviceUrl", "serviceLogo", "isActive", "createdDate", "createdBy", "updatedDate", "updatedBy"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ServicesByTenant {
    UUID getId();
    String getName();
    String getDescription();
    String getCode();
    Long getPrice();
    String getPricingMethods();
    Long getValidityPeriod();
    String getUnit();
    String getServiceUrl();
    String getServiceLogo();
    Boolean getIsActive();
    LocalDateTime getCreatedDate();
    String getCreatedBy();
    LocalDateTime getUpdatedDate();
    String getUpdatedBy();
}
