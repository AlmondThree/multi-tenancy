package com.multitenancy.systems.dto.services;

import com.multitenancy.systems.model.Services;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import lombok.Getter;

@Getter
public class ServiceTraxifyResponse extends DefaultResponseFormat {
    private Services data;
}
