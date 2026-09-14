package com.multitenancy.systems.specification;

import com.multitenancy.systems.model.PricingMethods;
import org.springframework.data.jpa.domain.Specification;

public class PricingMethodsSpecification {
    public static Specification<PricingMethods> hasCode(String code) {
        return (root, query, cb) -> code == null ? cb.conjunction() : cb.equal(root.get("code"), code);
    }

    public static Specification<PricingMethods> hasName(String name) {
        return (root, query, cb) -> name == null ? cb.conjunction() : cb.equal(root.get("name"), name);
    }

    public static Specification<PricingMethods> hasDescription(String description) {
        return (root, query, cb) -> description == null ? cb.conjunction() : cb.equal(root.get("description"), description);
    }

    public static Specification<PricingMethods> hasLogic(Integer logic) {
        return (root, query, cb) -> logic == null ? cb.conjunction() : cb.equal(root.get("logic"), logic);
    }

    public static Specification<PricingMethods> hasCounter(Integer counter) {
        return (root, query, cb) -> counter == null ? cb.conjunction() : cb.equal(root.get("counter"), counter);
    }

    public static Specification<PricingMethods> hasUnit(String unit) {
        return (root, query, cb) -> unit == null ? cb.conjunction() : cb.equal(root.get("unit"), unit);
    }

}
