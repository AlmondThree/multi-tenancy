package com.multitenancy.systems.repository;

import com.multitenancy.systems.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionRepository extends JpaRepository<Subscription, String>, JpaSpecificationExecutor<Subscription> {

}
