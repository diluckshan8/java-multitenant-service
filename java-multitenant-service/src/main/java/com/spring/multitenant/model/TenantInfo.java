package com.spring.multitenant.model;

import lombok.Data;

@Data
public class TenantInfo {
  private String tenantId;
  private String dbUrl;
  private String userName;
  private String password;
}
