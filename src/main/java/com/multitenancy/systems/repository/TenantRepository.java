package com.multitenancy.systems.repository;

import com.multitenancy.systems.model.Tenants;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenants, String> {
    Page<Tenants> findByCountry(String country, Pageable page);
//    List<Tenants> findByCountry(String country, Pageable page);
}
