package com.multitenancy.systems.context.database;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DynamicFlywayEngine {
    @Value("${FLYWAY_PATH}")
    private String locationPath;


    public void migrateTenantSchema(DataSource dataSource, String schemaName, String dbName) {
        String location = locationPath + dbName;

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemaName)
                .locations(location)
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }
}
