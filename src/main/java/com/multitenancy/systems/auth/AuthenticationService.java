package com.multitenancy.systems.auth;

import com.multitenancy.systems.dto.logging.TransactionLogEvent;
import com.multitenancy.systems.logging.TransactionLogListener;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthenticationService {

    private final ApplicationEventPublisher eventPublisher;

    private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";

    @Value("${security.api.key}")
    private String configuredAuthToken;

    public AuthenticationService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String requestApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (requestApiKey == null || !requestApiKey.equals(configuredAuthToken)) {

            TransactionLogEvent event = new TransactionLogEvent(
                    UUID.randomUUID().toString(),
                    request.getHeader("x-activity-id"),
                    request.getHeader("x-user-id"),
                    null,
                    "{}",
                    "Invalid API Key",
                    0,
                    "Invalid API Key"
            );

            eventPublisher.publishEvent(event);

            throw new BadCredentialsException("Invalid API Key");
        }
        return new ApiKeyAuthentication(
                requestApiKey,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")
        );
    }
}
