package com.multitenancy.systems.context.database;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DynamicFlywayEngine {
    public void migrateTenantSchema(DataSource dataSource, String schemaName, String dbName) {
        String location = "classpath:db/migration/" + dbName;

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemaName)
                .locations(location)
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }
}
