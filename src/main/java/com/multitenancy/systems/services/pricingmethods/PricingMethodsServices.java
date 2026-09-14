package com.multitenancy.systems.services.pricingmethods;

import com.multitenancy.systems.dto.pricingmethods.PricingMethodsListResponse;
import com.multitenancy.systems.dto.pricingmethods.PricingMethodsRequest;
import com.multitenancy.systems.exceptions.BadRequestException;
import com.multitenancy.systems.exceptions.DataNotFoundException;
import com.multitenancy.systems.model.PricingMethods;
import com.multitenancy.systems.model.Subscription;
import com.multitenancy.systems.repository.PricingMethodsRepository;
import com.multitenancy.systems.specification.PricingMethodsSpecification;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PricingMethodsServices {
    private final PricingMethodsRepository pricingMethodsRepository;

    public PricingMethodsServices(PricingMethodsRepository pricingMethodsRepository) {
        this.pricingMethodsRepository = pricingMethodsRepository;
    }

    public DefaultResponseFormat createPricingMethods(PricingMethodsRequest request, String userId) {
        PricingMethods data = new PricingMethods(
                request.getCode(),
                request.getName(),
                request.getDescription(),
                request.getLogic(),
                request.getCounter(),
                request.getUnit(),
                request.getCreatedDate(),
                userId,
                null,
                null
        );

        data.setCreatedDate(LocalDateTime.now());

        pricingMethodsRepository.save(data);

        return new DefaultResponseFormat(
                true,
                "Success insert data!",
                request.getCode()
        );
    }

    public DefaultResponseFormat updatePricingMethods(PricingMethodsRequest request, String userId, String code){
        PricingMethods data = pricingMethodsRepository.findById(code)
                .orElseThrow(() -> new BadRequestException(String.format("Data with Id: %s is not found", code)));

        if (request.getCode() != null && !request.getCode().isBlank()) {data.setCode(request.getCode());}
        if (request.getName() != null && !request.getName().isBlank()) {data.setName(request.getName());}
        if (request.getDescription() != null && !request.getDescription().isBlank()) {data.setDescription(request.getDescription());}
        if (request.getLogic() != null ) {data.setLogic(request.getLogic());}
        if (request.getCounter() != null ) {data.setCounter(request.getCounter());}
        if (request.getUnit() != null && !request.getUnit().isBlank()) {data.setUnit(request.getUnit());}

        data.setUpdatedBy(userId);
        data.setUpdatedDate(LocalDateTime.now());

        pricingMethodsRepository.save(data);

        return new DefaultResponseFormat(
                true,
                "Success update data",
                request.getCode()
        );

    }

    public Page<PricingMethods> getListPricingMethods(
            Integer page,
            Integer size,
            String ordered,
            String code,
            String name,
            Integer logic,
            String unit
    ) {
        Sort sort = ordered.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by("createdDate").ascending() : Sort.by("createdDate").descending();
        Pageable pages = PageRequest.of(page, size, sort);

        return pricingMethodsRepository.findAll(
                Specification.where(PricingMethodsSpecification.hasCode(code)
                        .and(PricingMethodsSpecification.hasName(name))
                        .and(PricingMethodsSpecification.hasLogic(logic))
                        .and(PricingMethodsSpecification.hasUnit(unit))
                ),
                pages
        );

    }

    public Optional<PricingMethods> getDetailPricingMethods(String code) {
        return pricingMethodsRepository.findById(code);
    }

}
