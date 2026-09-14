package com.multitenancy.systems.specification;

import com.multitenancy.systems.model.Subscription;
import org.springframework.data.jpa.domain.Specification;

public class SubscriptionSpecifications {
    public static Specification<Subscription> hasTenantServices(String tenantServices) {
        return (root, query, cb) -> tenantServices == null ? cb.conjunction() : cb.equal(root.get("tenantServices"), tenantServices);
    }

    public static Specification<Subscription> hasStatus(String status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Subscription> hasPaymentInfo(String paymentInfo) {
        return (root, query, cb) -> paymentInfo == null ? cb.conjunction() : cb.equal(root.get("paymentInfo"), paymentInfo);
    }
}
