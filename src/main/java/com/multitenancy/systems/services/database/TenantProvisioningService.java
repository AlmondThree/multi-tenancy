package com.multitenancy.systems.services.database;

import com.multitenancy.systems.config.DynamicRoutingDataSource;
import com.multitenancy.systems.context.database.DynamicFlywayEngine;
import com.multitenancy.systems.context.tenant.TenantContext;
import com.multitenancy.systems.logging.TransactionLogListener;
import com.multitenancy.systems.util.generator.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class TenantProvisioningService {
    private final DataSource dataSource;
    private final DynamicFlywayEngine flywayEngine;
    private static final Logger log = LoggerFactory.getLogger(TransactionLogListener.class);
    private final TenantCredentialsServices tenantCredentialsServices;

    public TenantProvisioningService(DataSource dataSource, DynamicFlywayEngine flywayEngine, TenantCredentialsServices tenantCredentialsServices) {
        this.dataSource = dataSource;
        this.flywayEngine = flywayEngine;
        this.tenantCredentialsServices = tenantCredentialsServices;
    }

    @Async
    public void provisionNewTenantSchema(String tenantId) {

        tenantId = tenantId.replace("-", "");
        String schemaName = "tenant_" + tenantId.toLowerCase();

        DynamicRoutingDataSource router = (DynamicRoutingDataSource) dataSource;

        Map<Object, DataSource> resolvedDataSources = router.getResolvedDataSources();

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();

        String username = "usrtnt" + tenantId.toLowerCase();
        String generatedPassword = randomStringGenerator.generateString(10);

        tenantCredentialsServices.sendTenantCredentialsAsync(tenantId, username, generatedPassword, schemaName);

        for (Map.Entry<Object, DataSource> entry : resolvedDataSources.entrySet()) {

            String engineName = entry.getKey().toString();

            if ("default".equalsIgnoreCase(engineName)) {
                continue;
            }

            TenantContext.setContext(tenantId, engineName);

            DataSource targetPhysicalDataSource = entry.getValue();

            try {
                java.sql.Connection conn = targetPhysicalDataSource.getConnection();
                java.sql.Statement stmt = conn.createStatement();

                String databaseName = conn.getCatalog();

                stmt.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);

                stmt.execute(String.format("CREATE USER %s WITH PASSWORD '%s';", username, generatedPassword));
                stmt.execute(String.format("GRANT CONNECT ON DATABASE %s TO %s;", databaseName, username));

                stmt.execute(String.format("GRANT USAGE ON SCHEMA %s TO %s;", schemaName, username));
                stmt.execute(String.format("GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA %s TO %s;", schemaName, username));

                flywayEngine.migrateTenantSchema(targetPhysicalDataSource, schemaName, engineName);

            } catch (Exception e) {
                log.info(tenantId, e);
                throw new RuntimeException("Error Creating Tenant: " + tenantId + " on engine: " + engineName, e);
            }
        }

        TenantContext.clear();
    }
}
