package com.multitenancy.systems.repository;

import com.multitenancy.systems.model.Tenants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenants, String> {

}
