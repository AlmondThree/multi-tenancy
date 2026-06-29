package com.multitenancy.systems.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pricing_methods")
public class PricingMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
}
