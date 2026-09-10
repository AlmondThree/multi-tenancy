package com.multitenancy.systems.repository;

import com.multitenancy.systems.dto.join.ServicesByTenant;
import com.multitenancy.systems.dto.join.TenantByServices;
import com.multitenancy.systems.model.TenantServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TenantServicesRepository extends JpaRepository<TenantServices, String>, JpaSpecificationExecutor<TenantServices> {
    @Query(
            value = """
                SELECT
                    s.id AS id,
                    s.name AS name,
                    s.code AS code,
                    s.price AS price,
                    s.pricing_methods AS pricingMethods,
                    s.validity_period AS validityPeriod,
                    s.unit AS unit,
                    s.service_url AS serviceUrl,
                    s.service_logo AS serviceLogo,
                    s.is_active AS isActive,
                    s.created_date AS createdDate,
                    s.created_by AS createdBy,
                    s.updated_date AS updatedDate,
                    s.updated_by AS updatedBy
                FROM tenant_services ts
                JOIN services s ON ts.service_id = s.id
                WHERE ts.tenant_id = :tenantId
            """
            , nativeQuery = true
    )
    Page<ServicesByTenant>findServiceByTenantId(@Param("tenantId") String tenantId, Pageable page);

    @Query(
            value = """
                SELECT
                    t.id AS id,
                    t.name AS name,
                    t.short_name AS shortName,
                    t.address AS address,
                    t.country AS country,
                    t.industry_sector AS industrySector,
                    t.email AS email,
                    t.contact AS contact,
                    t.organization_logo AS organizationLogo,
                    t.organization_website AS organizationWebsite,
                    t.admin_account AS adminAccount,
                    t.tenant_type AS tenantType,
                    t.ldap AS ldap,
                    t.is_active AS isActive,
                    t.created_date AS createdDate,
                    t.updated_date AS updatedDate
                FROM tenant_services ts
                JOIN tenants t ON ts.tenant_id = t.id
                WHERE ts.service_id = :serviceId
            """
            , nativeQuery = true
    )
    Page<TenantByServices>findTenantByServiceId(@Param("serviceId") UUID serviceId, Pageable page);
}
