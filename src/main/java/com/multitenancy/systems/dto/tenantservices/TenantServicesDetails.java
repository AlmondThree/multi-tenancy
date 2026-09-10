package com.multitenancy.systems.dto.tenantservices;

import com.multitenancy.systems.model.TenantServices;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantServicesDetails extends DefaultResponseFormat {

    private TenantServices data;
}
