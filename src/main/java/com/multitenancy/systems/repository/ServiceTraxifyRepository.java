package com.multitenancy.systems.repository;

import com.multitenancy.systems.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTraxifyRepository extends JpaRepository<Services, String> {

}
