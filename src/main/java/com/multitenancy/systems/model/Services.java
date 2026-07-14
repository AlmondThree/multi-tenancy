package com.multitenancy.systems.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter

@JsonPropertyOrder({"Id", "name", "description", "code", "price", "pricingMethods", "validityPeriod", "unit", "isActive", "createdDate", "createdBy", "updatedDate", "updatedBy"})

@Entity
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = true, length = 255)
    private String description;

    @Column(nullable = false, length = 20)
    private String code;

    @Column(nullable = false)
    private Long price;

    @Column(name = "pricing_methods", nullable = false, length = 10)
    private String pricingMethods;

    @Column(name = "validity_period", nullable = false)
    private Long validityPeriod;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    @Column(name = "updated_by", nullable = true, length = 100)
    private String updatedBy;

    public Services() {}

    public Services(String name, String description, String code, Long price, String pricingMethods, Long validityPeriod, String unit, Boolean isActive) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.price = price;
        this.pricingMethods = pricingMethods;
        this.validityPeriod = validityPeriod;
        this.unit = unit;
        this.isActive = isActive;
    }
}
