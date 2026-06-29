package com.multitenancy.systems.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Column(name = "validity_period", nullable = false, length = 20)
    private LocalDateTime validityPeriod;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy;

}
