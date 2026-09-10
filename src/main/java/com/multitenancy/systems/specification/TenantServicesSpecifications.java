package com.multitenancy.systems.specification;

import com.multitenancy.systems.model.TenantServices;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class TenantServicesSpecifications {

    public static Specification<TenantServices> hasTenantId(String tenantId) {
        return (root, query, cb) -> tenantId == null ? cb.conjunction() : cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<TenantServices> hasServiceId(UUID serviceId) {
        return (root, query, cb) -> serviceId == null ? cb.conjunction() : cb.equal(root.get("serviceId"), serviceId);
    }

    public static Specification<TenantServices> hasIsActive(Boolean isActive) {
        return (root, query, cb) -> isActive == null ? cb.conjunction() : cb.equal(root.get("isActive"), isActive);
    }

}
