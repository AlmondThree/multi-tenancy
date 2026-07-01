package com.multitenancy.systems.dto.logging;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionLogEvent {
    private final String idLogsSpring;
    private final String idActivity;
    private final String userId;
    private final String accessToken;
    private final String requestPayload;
    private final String responsePayload;
    private final long responseTime;
    private final String errorMessage;

    public TransactionLogEvent(String idLogsSpring, String idActivity, String userId, String accessToken,
                               String requestPayload, String responsePayload, long responseTime, String errorMessage) {
        this.idLogsSpring = idLogsSpring;
        this.idActivity = idActivity;
        this.userId = userId;
        this.accessToken = accessToken;
        this.requestPayload = requestPayload;
        this.responsePayload = responsePayload;
        this.responseTime = responseTime;
        this.errorMessage = errorMessage;
    }
}
