package com.multitenancy.systems.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name= "tenants", schema = "public")
public class Tenants {

    @Id
    private String id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "short_name", nullable = true, length = 100)
    private String shortName;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(name = "industry_sector", nullable = true, length = 200)
    private String industrySector;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String contact;

    @Column(name = "organization_logo", nullable = true, length = 200)
    private String organizationLogo;

    @Column(name = "organization_website", nullable = true, length = 200)
    private String organizationWebsite;

    @Column(name = "admin_account", nullable = false, length = 200)
    private String adminAccount;

    @Column(nullable = true, length = 200)
    private String ldap;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    public Tenants(){};

    public Tenants(String Id, String name, String shortName, String address, String country, String industrySector, String email, String contact, String contact1, String organizationLogo, String organizationWebsite, String adminAccount, String ldap, Boolean active, LocalDateTime createdDate) {
        this.id = Id;
        this.name = name;
        this.shortName = shortName;
        this.address = address;
        this.country = country;
        this.industrySector = industrySector;
        this.email = email;
        this.contact = contact;
        this.organizationLogo = organizationLogo;
        this.organizationWebsite = organizationWebsite;
        this.adminAccount = adminAccount;
        this.ldap = ldap;
        this.isActive = active;
        this.createdDate = createdDate;
    }
}
