package com.multitenancy.systems.services.services;

import com.multitenancy.systems.dto.services.ServicesTraxifyRequest;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.Services;
import com.multitenancy.systems.repository.ServiceTraxifyRepository;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ServiceTraxifyServices {
    private final ServiceTraxifyRepository serviceTraxifyRepository;

    public ServiceTraxifyServices(ServiceTraxifyRepository serviceTraxifyRepository) {
        this.serviceTraxifyRepository = serviceTraxifyRepository;
    }

    public DefaultResponseFormat insertService(ServicesTraxifyRequest request) {
        Services service = new Services(
                request.getName(),
                request.getDescription(),
                request.getCode(),
                request.getPrice(),
                request.getPricingMethods(),
                request.getValidityPeriod(),
                request.getServiceUrl(),
                request.getServiceLogo(),
                request.getUnit(),
                request.getIsActive()
        );

        service.setCreatedDate(LocalDateTime.now());
        service.setCreatedBy("System");

        Services data = serviceTraxifyRepository.save(service);

        UUID paramId = data.getId();

        return new DefaultResponseFormat(true, "Success insert Data", String.valueOf(paramId));
    }

    public DefaultResponseFormat updateService(ServicesTraxifyRequest request, String paramId) {
        Services data = serviceTraxifyRepository.findById(UUID.fromString(paramId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {data.setName(request.getName());}
        if (request.getDescription() != null && !request.getDescription().isBlank()) {data.setName(request.getDescription());}
        if (request.getCode() != null && !request.getCode().isBlank()) {data.setCode(request.getCode());}
        if (request.getPrice() != null) {data.setPrice(request.getPrice());}
        if (request.getPricingMethods() != null && !request.getPricingMethods().isBlank()) {data.setPricingMethods(request.getPricingMethods());}
        if (request.getValidityPeriod() != null) {data.setValidityPeriod(request.getValidityPeriod());}
        if (request.getServiceUrl() != null && !request.getServiceUrl().isBlank()) {data.setServiceUrl(request.getServiceUrl());}
        if (request.getServiceLogo() != null && !request.getServiceLogo().isBlank()) {data.setServiceLogo(request.getServiceLogo());}
        if (request.getUnit() != null && !request.getUnit().isBlank()) {data.setUnit(request.getUnit());}
        if (request.getIsActive() != null) {data.setIsActive(request.getIsActive());}

        serviceTraxifyRepository.save(data);

        return new DefaultResponseFormat(true, "Success update Data", paramId);
    }

    public Page<Services> getListServices(
            Integer page,
            Integer size,
            String name,
            String code,
            String pricingMethods,
            String ordered
    ) {
        Sort sort = ordered.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by("createdDate").ascending() : Sort.by("createdDate").descending();
        Pageable pages = PageRequest.of(page, size, sort);

        if(name != null && !name.isBlank()){
            return serviceTraxifyRepository.findByName(name, pages);
        } else if (code != null && !code.isBlank()) {
            return serviceTraxifyRepository.findByCode(code, pages);
        } else if (pricingMethods != null && !pricingMethods.isBlank()) {
            return serviceTraxifyRepository.findByPricingMethods(pricingMethods, pages);
        }

        return serviceTraxifyRepository.findAll(pages);
    }

    public Services getDetailServices(String id) {
        UUID paramId = UUID.fromString(id);

        return serviceTraxifyRepository.findById(paramId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Data with Id: %s is not found", id)));
    }

}
