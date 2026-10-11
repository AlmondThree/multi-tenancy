package com.multitenancy.systems.config;

import com.multitenancy.systems.context.tenant.TenantContext;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    private final DataSource dataSource;

    public MultiTenantConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        Connection connection = getAnyConnection();
        String tenant = TenantContext.getTenantId();

        if (tenant != null && !"default".equals(TenantContext.getTargetDb())) {
            try {
                connection.createStatement().execute("SET SCHEMA 'tenant_" + tenant + "'");
            } catch (SQLException e) {
                connection.close();
                throw e;
            }
        }
        return connection;
    }

    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        if (!"default".equals(TenantContext.getTargetDb())) {
            try {
                connection.createStatement().execute("SET SCHEMA 'public'");
            } catch (SQLException ignored) {}
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}

