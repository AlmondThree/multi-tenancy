package com.multitenancy.systems.dto.tenants;

import com.multitenancy.systems.model.Tenants;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantDetailsResponse extends DefaultResponseFormat {
    private Tenants data;
}
