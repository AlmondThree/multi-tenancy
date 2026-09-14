package com.multitenancy.systems.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name= "subscription", schema = "public")
public class Subscription {

    @Id
    private String id;

    @Column(name = "tenant_services", nullable = false, length = 50)
    private String tenantServices;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "last_payment", nullable = true)
    private LocalDateTime lastPayment;

    @Column(name = "next_payment", nullable = true)
    private LocalDateTime nextPayment;

    @Column(name = "payment_info", nullable = true, length = 100)
    private String paymentInfo;

    @Column(name = "created_date", nullable = true)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    public Subscription(String id, String tenantServices, String status, LocalDateTime lastPayment, LocalDateTime nextPayment, String paymentInfo) {
        this.id = id;
        this.tenantServices = tenantServices;
        this.status = status;
        this.lastPayment = lastPayment;
        this.nextPayment = nextPayment;
        this.paymentInfo = paymentInfo;
    }

    public Subscription() {

    }
}
