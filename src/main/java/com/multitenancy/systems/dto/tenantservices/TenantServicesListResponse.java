package com.multitenancy.systems.dto.tenantservices;

import com.multitenancy.systems.dto.join.ServicesByTenant;
import com.multitenancy.systems.model.TenantServices;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TenantServicesListResponse extends DefaultResponseFormat {
    private Pages pages;
    private List<TenantServices> data;

    public TenantServicesListResponse(boolean success, String message, Pages pages, String errorId, List<TenantServices> content) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setPages(pages);
        this.setErrorId(errorId);
        this.data = content;
    }
}
