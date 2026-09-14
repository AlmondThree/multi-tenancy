package com.multitenancy.systems.repository;

import com.multitenancy.systems.model.PricingMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PricingMethodsRepository extends JpaRepository<PricingMethods, String>, JpaSpecificationExecutor<PricingMethods> {
}
