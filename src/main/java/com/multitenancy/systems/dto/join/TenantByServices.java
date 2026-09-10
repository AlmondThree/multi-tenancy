package com.multitenancy.systems.dto.join;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"id", "name", "shortName", "address", "country", "industrySector", "email", "contact", "organizationLogo", "organizationWebsite", "adminAccount", "tenantType", "ldap", "isActive", "createdDate", "updatedDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface TenantByServices {
    String getId();
    String getName();
    String getShortName();
    String getAddress();
    String getCountry();
    String getIndustrySector();
    String getEmail();
    String getContact();
    String getOrganizationLogo();
    String getOrganizationWebsite();
    String getAdminAccount();
    String getTenantType();
    String getLdap();
    Boolean getIsActive();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatedDate();
}
