package com.multitenancy.systems.logging;

import com.multitenancy.systems.dto.logging.TransactionLogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class TransactionLogListener {

    private static final Logger log = LoggerFactory.getLogger(TransactionLogListener.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${LOGS_API}")
    private String EXTERNAL_LOG_API_URL;

    @Value("${API_KEY_LOGS}")
    private String API_KEY_LOGS;

    public TransactionLogListener(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Async("restAsyncExecutor")
    @EventListener
    public void handleTransactionLog(TransactionLogEvent event) {
        try {
            Object reqJson = objectMapper.readValue(event.getRequestPayload(), Object.class);
            Object resJson;

            if (event.getErrorMessage() != null) {
                Map<String, String> errorMap = new LinkedHashMap<>();
                errorMap.put("status", "FAILED");
                errorMap.put("message", event.getErrorMessage());
                errorMap.put("details", event.getResponsePayload());

                resJson = errorMap;
            } else {
                resJson = objectMapper.readValue(event.getResponsePayload(), Object.class);
            }

            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("id_activity", event.getIdActivity());
            payload.put("timestamp", null);
            payload.put("user_id", event.getUserId());
            payload.put("access_token", event.getAccessToken());
            payload.put("request_payload", reqJson);
            payload.put("response_payload", resJson);
            payload.put("response_time", event.getResponseTime());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.set("x-api-key", API_KEY_LOGS);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

            restTemplate.postForObject(EXTERNAL_LOG_API_URL, requestEntity, String.class);

            log.info("[API-Logging] Success sent logs with ID: {}", event.getIdLogsSpring());
        } catch (Exception e) {
            log.error("[API-Logging] Failed sent logs. Error: {}", e.getMessage());
        }
    }
}
