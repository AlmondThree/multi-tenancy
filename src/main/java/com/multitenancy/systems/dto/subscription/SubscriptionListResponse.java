package com.multitenancy.systems.dto.subscription;

import com.multitenancy.systems.model.Subscription;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubscriptionListResponse extends DefaultResponseFormat {
    private Pages pages;
    private List<Subscription> data;

    public SubscriptionListResponse(boolean success, String message, Pages pages, String errorId, List<Subscription> content) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setPages(pages);
        this.setErrorId(errorId);
        this.data = content;
    }
}
