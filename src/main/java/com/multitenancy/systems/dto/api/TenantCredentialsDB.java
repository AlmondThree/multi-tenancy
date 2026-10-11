package com.multitenancy.systems.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantCredentialsDB {

    private String tenantId;
    private String username;
    private String password;
    private String schema;
    private Boolean isActive;

    public TenantCredentialsDB(String tenantId, String username, String password, String schema, boolean isActive) {
        this.tenantId = tenantId;
        this.username = username;
        this.password = password;
        this.schema = schema;
        this.isActive = isActive;
    }
}
