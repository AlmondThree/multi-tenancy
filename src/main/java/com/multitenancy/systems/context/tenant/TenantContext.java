package com.multitenancy.systems.context.tenant;

public class TenantContext {
    private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> TARGET_DB = new ThreadLocal<>();

    public static void setContext(String tenantId, String targetDb) {
        TENANT_ID.set(tenantId);
        TARGET_DB.set(targetDb);
    }

    public static String getTenantId() { return TENANT_ID.get(); }
    public static String getTargetDb() { return TARGET_DB.get(); }

    public static void clear() {
        TENANT_ID.remove();
        TARGET_DB.remove();
    }
}
