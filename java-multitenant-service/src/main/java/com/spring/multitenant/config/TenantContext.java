package com.spring.multitenant.config;

public class TenantContext {

  private static final ThreadLocal CURRENT_TENANT = new ThreadLocal<>();

  public static String getCurrentTenant() {
    return (String) CURRENT_TENANT.get();
  }

  public static void setCurrentTenant(String tenant) {
    CURRENT_TENANT.set(tenant);
  }

  public static void clear() {
    CURRENT_TENANT.remove();
  }
}
