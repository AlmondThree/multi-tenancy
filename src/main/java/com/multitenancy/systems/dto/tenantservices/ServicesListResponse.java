package com.multitenancy.systems.dto.tenantservices;

import com.multitenancy.systems.dto.join.ServicesByTenant;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServicesListResponse extends DefaultResponseFormat {
    private Pages pages;
    private List<ServicesByTenant> data;

    public ServicesListResponse(boolean success, String message, Pages pages, String errorId, List<ServicesByTenant> content) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setPages(pages);
        this.setErrorId(errorId);
        this.data = content;
    }
}
