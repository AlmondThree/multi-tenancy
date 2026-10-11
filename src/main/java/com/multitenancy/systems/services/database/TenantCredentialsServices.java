package com.multitenancy.systems.services.database;

import com.multitenancy.systems.dto.api.ResponseTenantCredentialsDB;
import com.multitenancy.systems.dto.api.TenantCredentialsDB;
import com.multitenancy.systems.logging.TransactionLogListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class TenantCredentialsServices {
    private static final Logger log = LoggerFactory.getLogger(TransactionLogListener.class);

    @Value("${URL_GET_CREDENTIALS}")
    private String urlAPI;

    @Value("${API_KEY_AUTH}")
    private String apiKey;

    public TenantCredentialsServices() {}

    @Async
    public void sendTenantCredentialsAsync(String tenantId, String username, String password, String schema) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        TenantCredentialsDB request = new TenantCredentialsDB(
                tenantId,
                username,
                password,
                schema,
                true
        );

        headers.add("x-api-key", apiKey);
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<TenantCredentialsDB> entity = new HttpEntity<>(request, headers);

        try {
            ResponseTenantCredentialsDB response = restTemplate.postForObject(
                    urlAPI+"/tenant/credentials",
                    entity,
                    ResponseTenantCredentialsDB.class
            );

            if (response.getStatus()) {
                log.info("Success push data to DB Tenant Credentials API: "+tenantId);
            } else {
                log.error("Error send data: "+response.getMessage());
            }
        } catch (Exception error) {
            throw new RuntimeException("Error push data to Tenant Credentials API: " + tenantId + " || " + error.getMessage());
        }
    }

}
