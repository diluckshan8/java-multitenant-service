package com.spring.multitenant.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantDataSource extends AbstractRoutingDataSource {
  @Override
  protected Object determineCurrentLookupKey() {
    return TenantContext.getCurrentTenant();
  }
}