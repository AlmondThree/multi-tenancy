package com.multitenancy.systems.dto.services;

import com.multitenancy.systems.model.Services;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter


public class ServiceTraxifyListResponse extends DefaultResponseFormat {
    private Pages pages;
    private List<Services> data;

    public ServiceTraxifyListResponse(boolean success, String message, Pages pages, String errorId, List<Services> content) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setPages(pages);
        this.setErrorId(errorId);
        this.data = content;
    }
}
