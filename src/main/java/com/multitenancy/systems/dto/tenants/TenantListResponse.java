package com.multitenancy.systems.dto.tenants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.multitenancy.systems.model.Tenants;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"isSuccess", "message", "pages", "data"})
public class TenantListResponse {
    @JsonProperty("isSuccess")
    private boolean success;
    private String message;
    private Pages pages;
    private List<Tenants> data;

    public TenantListResponse() {};

    public TenantListResponse(boolean success, String message, Pages pages, List<Tenants> data) {
        this.success = success;
        this.message = message;
        this.pages = pages;
        this.data = data;
    }
}
