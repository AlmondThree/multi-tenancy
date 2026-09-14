package com.multitenancy.systems.dto.pricingmethods;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class PricingMethodsRequest {
    private String code;

    private String name;

    private String description;

    private Integer logic;

    private Integer counter;

    private String unit;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime updatedDate;

    private String updatedBy;

    public PricingMethodsRequest(String code, String name, String description, Integer logic, Integer counter, String unit, LocalDateTime createdDate, String createdBy, LocalDateTime updatedDate, String updatedBy) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.logic = logic;
        this.counter = counter;
        this.unit = unit;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
    }
}
