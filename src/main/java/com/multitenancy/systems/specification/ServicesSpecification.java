package com.multitenancy.systems.specification;

import com.multitenancy.systems.model.Services;
import org.springframework.data.jpa.domain.Specification;

public class ServicesSpecification {
    public static Specification<Services> hasName(String name) {
        return (root, query, cb) -> name == null ? cb.conjunction() : cb.equal(root.get("name"), name);
    }

    public static Specification<Services> hasCode(String code) {
        return (root, query, cb) -> code == null ? cb.conjunction() : cb.equal(root.get("code"), code);
    }

    public static Specification<Services> hasPricingMethods(String pricingMethods) {
        return (root, query, cb) -> pricingMethods == null ? cb.conjunction() : cb.equal(root.get("pricingMethods"), pricingMethods);
    }

    public static Specification<Services> hasIsActive(Boolean isActive) {
        return (root, query, cb) -> isActive == null ? cb.conjunction() : cb.equal(root.get("isActive"), isActive);
    }
}
