package com.multitenancy.systems.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTenantCredentialsDB {
    private Boolean status;
    private String message;
    private String id;
}
