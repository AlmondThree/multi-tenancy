package com.multitenancy.systems.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";

    @Value("${security.api.key}")
    private String configuredAuthToken;

    public Authentication getAuthentication(HttpServletRequest request) {
        String requestApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (requestApiKey == null || !requestApiKey.equals(configuredAuthToken)) {
            throw new BadCredentialsException("Invalid API Key");
        }
        return new ApiKeyAuthentication(
                requestApiKey,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")
        );
    }
}
