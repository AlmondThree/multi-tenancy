package com.multitenancy.systems.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "pricing_methods")
public class PricingMethods {

    @Id
    private String code;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "logic", nullable = false)
    private Integer logic;

    @Column(name = "counter", nullable = false)
    private Integer counter;

    @Column(name = "unit", nullable = false, length = 10)
    private String unit;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    public PricingMethods(String code, String name, String description, Integer logic, Integer counter, String unit, LocalDateTime createdDate, String createdBy, LocalDateTime updatedDate, String updatedBy) {
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

    public PricingMethods() {};
}
