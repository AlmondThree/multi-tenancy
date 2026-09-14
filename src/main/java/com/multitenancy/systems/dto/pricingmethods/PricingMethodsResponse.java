package com.multitenancy.systems.dto.pricingmethods;

import com.multitenancy.systems.model.PricingMethods;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PricingMethodsResponse extends DefaultResponseFormat {
    private PricingMethods data;

    public PricingMethodsResponse(Boolean success, String message,PricingMethods data) {
        super(success, message);
        this.data = data;
    }
}
