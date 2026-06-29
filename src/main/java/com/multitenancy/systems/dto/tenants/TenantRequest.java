package com.multitenancy.systems.dto.tenants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TenantRequest {

    private String id;

    private String name;

    private String shortName;

    private String address;

    private String country;

    private String industrySector;

    private String email;

    private String contact;

    private String organizationLogo;

    private String organizationWebsite;

    private String adminAccount;

    private String ldap;

    @JsonProperty("isActive")
    private Boolean isActive;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
