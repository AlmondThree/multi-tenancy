package com.multitenancy.systems.config;

import com.multitenancy.systems.context.tenant.TenantContext;
import com.multitenancy.systems.dto.api.CredentialsDB;
import com.multitenancy.systems.dto.api.ListCredentialsResponse;
import com.multitenancy.systems.logging.TransactionLogListener;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SharedJpaConfig {

    private static final Logger log = LoggerFactory.getLogger(TransactionLogListener.class);

    @Value("${URL_GET_CREDENTIALS}")
    private String urlAPI;

    @Value("${API_KEY_AUTH}")
    private String apiKey;

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties mainProps) {
        Map<Object, Object> targetDataSources = new HashMap<>();

        DataSource mainDs = mainProps.initializeDataSourceBuilder().build();
        targetDataSources.put("default", mainDs);

        try{
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();

            headers.add("x-api-key", apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ListCredentialsResponse> response = restTemplate.exchange(
                    urlAPI+"/database/credentials/list?format=jdbc&isSupabase=true&isActive=true",
                    HttpMethod.GET,
                    entity,
                    ListCredentialsResponse.class
            );

            ListCredentialsResponse listCred = response.getBody();

            assert listCred != null;
            if(listCred.getStatus()) {
                for (CredentialsDB dbInfo : listCred.getData()) {
                    DataSource ds = DataSourceBuilder.create()
                            .url(dbInfo.getJdbcUrl())
                            .username(dbInfo.getUsername())
                            .password(dbInfo.getPassword())
                            .build();
                    targetDataSources.put(dbInfo.getAppName(), ds);
                    log.info("Complete sign Datasource: " + dbInfo.getDbName());
                }
            }
        } catch (Exception e) {
            log.info("Error: {}", e.toString());
        }

        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(mainDs);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.multitenancy.systems.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<>();

        jpaProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, new MultiTenantConnectionProviderImpl(dataSource));
        jpaProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, new CurrentTenantIdentifierResolver() {
            @Override public String resolveCurrentTenantIdentifier() {
                String tenant = TenantContext.getTenantId();
                return tenant != null ? tenant : "public";
            }
            @Override public boolean validateExistingCurrentSessions() { return true; }
        });

        em.setJpaPropertyMap(jpaProperties);
        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}
