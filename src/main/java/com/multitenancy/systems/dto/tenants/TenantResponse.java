package com.multitenancy.systems.dto.tenants;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"isSuccess", "message", "Id"})
public class TenantResponse {

    private Boolean isSuccess;
    private String message;
    private String Id;

    public TenantResponse(Boolean isSuccess, String message, String Id) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.Id = Id;
    }

}
