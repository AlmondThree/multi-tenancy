package com.multitenancy.systems.repository;

import com.multitenancy.systems.model.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceTraxifyRepository extends JpaRepository<Services, UUID> {
    Page<Services> findByName(String name, Pageable page);
    Page<Services> findByCode(String code, Pageable page);
    Page<Services> findByPricingMethods(String pricingMethods, Pageable page);
}
