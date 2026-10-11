package com.multitenancy.systems.context.database;

import com.multitenancy.systems.annotations.TargetDatabase;
import com.multitenancy.systems.context.tenant.TenantContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class DatabaseRoutingAspect {
    @Around("@annotation(targetDatabase)")
    public Object route(ProceedingJoinPoint joinPoint, TargetDatabase targetDatabase) throws Throwable {
        String currentTenant = TenantContext.getTenantId();
        String originalDb = TenantContext.getTargetDb();

        try {
            TenantContext.setContext(currentTenant, targetDatabase.value());
            return joinPoint.proceed();
        } finally {
            TenantContext.setContext(currentTenant, originalDb);
        }
    }
}
