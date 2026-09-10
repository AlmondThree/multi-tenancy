package com.multitenancy.systems.services.tenantservices;

import com.multitenancy.systems.dto.join.TenantByServices;
import com.multitenancy.systems.dto.tenantservices.TenantServicesRequest;
import com.multitenancy.systems.dto.join.ServicesByTenant;
import com.multitenancy.systems.exceptions.BadRequestException;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.TenantServices;
import com.multitenancy.systems.repository.TenantServicesRepository;
import com.multitenancy.systems.specification.TenantServicesSpecifications;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantServicesService {
    private final TenantServicesRepository tenantServicesRepository;

    public TenantServicesService(TenantServicesRepository tenantServicesRepository) {
        this.tenantServicesRepository = tenantServicesRepository;
    }

    public DefaultResponseFormat createTenantServices(TenantServicesRequest request, String userId) {
        String id = request.getTenantId() + "-" + request.getServiceId();

        TenantServices data = new TenantServices(
                id,
                request.getTenantId(),
                request.getServiceId(),
                LocalDateTime.now().plusMonths(1),
                request.getIsActive(),
                request.getCreatedDate(),
                userId,
                null,
                null
        );

        data.setCreatedDate(LocalDateTime.now());

        if(tenantServicesRepository.existsById(id)) {
            throw new BadRequestException("Record already exist");
        }

        tenantServicesRepository.save(data);

        return new DefaultResponseFormat(
                true,
                "Success insert data!",
                id
        );
    }

    public DefaultResponseFormat updateTenantServices(TenantServicesRequest request, String id, String userId) {

        TenantServices data = tenantServicesRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Data not found!"));

        if(request.getExpiredDate()!= null) {data.setExpiredDate(request.getExpiredDate());}
        if(request.getIsActive() != null) {data.setIsActive(request.getIsActive());}

        data.setUpdatedDate(LocalDateTime.now());
        data.setUpdatedBy(userId);

        tenantServicesRepository.save(data);

        return new DefaultResponseFormat(
          true,
          "Success update data!",
          id
        );
    }

    public Page<TenantServices> getListTenantServices(
            Integer page,
            Integer size,
            String tenantId,
            String serviceId,
            Boolean isActive
    ) {
        Pageable pages = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return tenantServicesRepository.findAll(
                Specification.where(TenantServicesSpecifications.hasTenantId(tenantId)).
                        and(TenantServicesSpecifications.hasServiceId(serviceId != null ? UUID.fromString(serviceId) : null )).
                        and(TenantServicesSpecifications.hasIsActive(isActive)),
                pages
        );
    }

    public Optional<TenantServices> getDetailsTenant(String id) {
        return tenantServicesRepository.findById(id);
    }

    public Page<ServicesByTenant> getListServicesByTenantId(
            Integer page,
            Integer size,
            String tenantId
    ) {
        Pageable pages = (Pageable) PageRequest.of(page, size, Sort.by("expired_date").descending());

        return tenantServicesRepository.findServiceByTenantId(tenantId, pages);

    }

    public Page<TenantByServices> getListTenantsByServiceId(
            Integer page,
            Integer size,
            String serviceId
    ) {
        Pageable pages = (Pageable) PageRequest.of(page, size, Sort.by("created_date").descending());

        return tenantServicesRepository.findTenantByServiceId(UUID.fromString(serviceId), pages);

    }

}