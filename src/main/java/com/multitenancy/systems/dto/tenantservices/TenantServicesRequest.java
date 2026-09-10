package com.multitenancy.systems.dto.tenantservices;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TenantServicesRequest {
    private String id;

    private String tenantId;

    private UUID serviceId;

    private LocalDateTime expiredDate;

    private Boolean isActive;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime updatedDate;

    private String updatedBy;
}
