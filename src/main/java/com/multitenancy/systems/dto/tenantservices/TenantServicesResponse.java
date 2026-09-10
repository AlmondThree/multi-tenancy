package com.multitenancy.systems.dto.tenantservices;

import com.multitenancy.systems.model.TenantServices;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TenantServicesResponse extends DefaultResponseFormat {

    private TenantServices data;

    public TenantServicesResponse(boolean success, String message, String errorId, TenantServices content) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setErrorId(errorId);
        this.data = content;
    }

}
