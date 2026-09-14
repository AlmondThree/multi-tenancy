package com.multitenancy.systems.dto.subscription;

import com.multitenancy.systems.model.Subscription;
import com.multitenancy.systems.util.format.DefaultResponseFormat;
import com.multitenancy.systems.util.format.Pages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionResponse extends DefaultResponseFormat {
    private Subscription data;

    public SubscriptionResponse(boolean success, String message, String errorId, Subscription content) {
        this.setSuccess(success);
        this.setMessage(message);
        this.setErrorId(errorId);
        this.data = content;
    }
}
