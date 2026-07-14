package com.multitenancy.systems.dto.services;

import com.multitenancy.systems.model.Services;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ServiceTraxifyDetailResponse extends DefaultResponseFormat {
    private Pages pages;
    private Services data;

    public ServiceTraxifyDetailResponse(boolean success, String message, Pages pages, String errorId, Services content) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setPages(pages);
        this.setErrorId(errorId);
        this.data = content;
    }
}
