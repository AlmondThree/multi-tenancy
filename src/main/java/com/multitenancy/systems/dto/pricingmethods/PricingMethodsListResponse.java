package com.multitenancy.systems.dto.pricingmethods;

import com.multitenancy.systems.model.PricingMethods;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class PricingMethodsListResponse extends DefaultResponseFormat {
    private Pages pages;
    private List<PricingMethods> data;

    public PricingMethodsListResponse(Boolean success, String message, Pages pages, List<PricingMethods> data) {
        super(success, message);
        this.pages = pages;
        this.data = data;
    }
}
