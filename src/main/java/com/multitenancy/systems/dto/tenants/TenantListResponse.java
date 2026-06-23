package com.multitenancy.systems.dto.tenants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.multitenancy.systems.model.Tenants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"isSuccess", "message", "data"})
public class TenantListResponse {
    @JsonProperty("isSuccess")
    private boolean success;
    private String message;
    private List<Tenants> data;

}
