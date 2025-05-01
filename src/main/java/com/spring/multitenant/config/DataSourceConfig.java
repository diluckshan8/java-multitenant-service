package com.spring.multitenant.config;

import com.spring.multitenant.model.TenantInfo;
import com.spring.multitenant.service.TenantService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    private final TenantService tenantService;

    public DataSourceConfig(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    public DataSource dataSource() throws SQLException {
        Map<Object, Object> targetDataSources = new HashMap<>();

        // Fetch all tenants from DynamoDB
        for (TenantInfo tenant : tenantService.getAllTenants()) {
            logger.info("Configuring datasource for tenant : {}", tenant.getTenantId());
            PoolDataSource poolDataSource = getPoolDataSource(tenant);
            targetDataSources.put(tenant.getTenantId(), poolDataSource);
            TenantContext.setCurrentTenant(tenant.getTenantId());
        }

        MultiTenantDataSource routingDatasource = new MultiTenantDataSource();
        routingDatasource.setTargetDataSources(targetDataSources);
        return routingDatasource;
    }

    private PoolDataSource getPoolDataSource(TenantInfo tenant) throws SQLException {
        PoolDataSource poolDataSource = PoolDataSourceFactory.getPoolDataSource();
        poolDataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        poolDataSource.setUser(tenant.getUserName());
        poolDataSource.setPassword(tenant.getPassword());
        poolDataSource.setURL(tenant.getDbUrl());
        poolDataSource.setValidateConnectionOnBorrow(true);
        poolDataSource.setSecondsToTrustIdleConnection(15);
        poolDataSource.setConnectionPoolName("TENANT" + tenant.getTenantId());
        return poolDataSource;
    }
}