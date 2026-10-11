package com.multitenancy.systems.config;

import com.multitenancy.systems.context.tenant.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    private Map<Object, Object> currentTargetDataSources;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.currentTargetDataSources = targetDataSources;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String targetDb = TenantContext.getTargetDb();
        return (targetDb != null) ? targetDb : "default";
    }

    @Override
    public Map<Object, DataSource> getResolvedDataSources() {
        return super.getResolvedDataSources();
    }

    public synchronized void registerNewDataSourceLive(String engineName, DataSource newDataSource) {
        this.currentTargetDataSources.put(engineName, newDataSource);

        super.setTargetDataSources(this.currentTargetDataSources);
        super.afterPropertiesSet(); // Re-initialize map internal Spring
    }
}
