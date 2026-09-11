package com.multitenancy.systems.specification;

import com.multitenancy.systems.model.Tenants;
import org.springframework.data.jpa.domain.Specification;

public class TenantSpecifications {

    public static Specification<Tenants> hasName(String name) {
        return (root, query, cb) -> name == null ? cb.conjunction() : cb.equal(root.get("name"), name);
    }

    public static Specification<Tenants> hasShortName(String shortName) {
        return (root, query, cb) -> shortName == null ? cb.conjunction() : cb.equal(root.get("shortName"), shortName);
    }

    public static Specification<Tenants> hasAddress(String address) {
        return (root, query, cb) -> address == null ? cb.conjunction() : cb.equal(root.get("address"), address);
    }

    public static Specification<Tenants> hasCountry(String country) {
        return (root, query, cb) -> country == null ? cb.conjunction() : cb.equal(root.get("country"), country);
    }

    public static Specification<Tenants> hasIndustrySector(String industrySector) {
        return (root, query, cb) -> industrySector == null ? cb.conjunction() : cb.equal(root.get("industrySector"), industrySector);
    }

    public static Specification<Tenants> hasEmail(String email) {
        return (root, query, cb) -> email == null ? cb.conjunction() : cb.equal(root.get("email"), email);
    }

    public static Specification<Tenants> hasContact(String contact) {
        return (root, query, cb) -> contact == null ? cb.conjunction() : cb.equal(root.get("contact"), contact);
    }

    public static Specification<Tenants> hasAdminAccount(String adminAccount) {
        return (root, query, cb) -> adminAccount == null ? cb.conjunction() : cb.equal(root.get("adminAccount"), adminAccount);
    }

    public static Specification<Tenants> hasTenantType(String tenantType) {
        return (root, query, cb) -> tenantType == null ? cb.conjunction() : cb.equal(root.get("tenantType"), tenantType);
    }

    public static Specification<Tenants> hasIsActive(Boolean isActive) {
        return (root, query, cb) -> isActive == null ? cb.conjunction() : cb.equal(root.get("isActive"), isActive);
    }
}
