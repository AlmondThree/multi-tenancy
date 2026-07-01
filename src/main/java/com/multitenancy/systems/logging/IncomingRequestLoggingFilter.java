package com.multitenancy.systems.logging;

import com.multitenancy.systems.dto.logging.TransactionLogEvent;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class IncomingRequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(IncomingRequestLoggingFilter.class);
    private final ApplicationEventPublisher eventPublisher;

    public IncomingRequestLoggingFilter(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId.substring(0, 8));

        String authHeader = request.getHeader("Authorization");
        String accessToken = (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;

        String activityHeader = request.getHeader("x-activity-id");

        String userId = request.getHeader("x-user-id") != null ? request.getHeader("x-user-id") : "SYSTEM";

        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
        ContentCachingResponseWrapper cachedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(cachedRequest, cachedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            try {
                byte[] reqBytes = StreamUtils.copyToByteArray(cachedRequest.getInputStream());
                String reqBody = new String(reqBytes, StandardCharsets.UTF_8).trim();
                reqBody = reqBody.isEmpty() ? "{}" : reqBody;

                byte[] resBytes = cachedResponse.getContentAsByteArray();
                String resBody = new String(resBytes, StandardCharsets.UTF_8).trim();
                resBody = resBody.isEmpty() ? "{}" : resBody;

                String idErrorFromAdvice = (String) cachedRequest.getAttribute("AdviceIDError");
                String messageFromAdvice = (String) cachedRequest.getAttribute("messageError");

                String finalErrorMessage = null;
                if (idErrorFromAdvice != null) {
                    finalErrorMessage = "[" + idErrorFromAdvice + "] " + messageFromAdvice;
                } else if (response.getStatus() >= 400) {
                    finalErrorMessage = "HTTP Error Status: " + response.getStatus();
                }

                TransactionLogEvent logEvent = new TransactionLogEvent(
                        traceId, activityHeader, userId, accessToken, reqBody, resBody, duration, finalErrorMessage
                );
                eventPublisher.publishEvent(logEvent);

                cachedResponse.copyBodyToResponse();

            } catch (Exception e) {
                log.error("Error Extract Payload from Servlet", e);
            } finally {
                MDC.clear();
            }
        }
    }
}