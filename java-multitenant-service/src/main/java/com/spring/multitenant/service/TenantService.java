package com.spring.multitenant.service;

import com.spring.multitenant.model.TenantInfo;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TenantService {

  @Autowired
  private DynamoDbClient dynamoDbClient;

  public boolean isValidTenant(String tenantId) {
    return getTenantInfo(tenantId) != null;
  }

  public TenantInfo getTenantInfo(String tenantId) {
    Map<String, AttributeValue> key = new HashMap<>();
    key.put("TenantId", AttributeValue.builder().s(tenantId).build());

    GetItemRequest request = GetItemRequest.builder()
        .tableName("TenantRegistry")
        .key(key)
        .build();

    var response = dynamoDbClient.getItem(request);
    if (response.hasItem()) {
      Map<String, AttributeValue> item = response.item();
      TenantInfo tenantInfo = new TenantInfo();
      tenantInfo.setTenantId(item.get("TenantId").s());
      tenantInfo.setDbUrl(item.get("DbUrl").s());
      tenantInfo.setUserName(item.get("userName").s());
      tenantInfo.setPassword(item.get("password").s());
      return tenantInfo;
    }
    return null;
  }

  public List<TenantInfo> getAllTenants() {
    List<TenantInfo> tenants = new ArrayList<>();
    ScanRequest scanRequest = ScanRequest.builder()
        .tableName("TenantRegistry")
        .build();

    ScanResponse response = dynamoDbClient.scan(scanRequest);
    for (Map<String, AttributeValue> item : response.items()) {
      TenantInfo tenantInfo = new TenantInfo();
      tenantInfo.setTenantId(item.get("TenantId").s());
      tenantInfo.setDbUrl(item.get("DbUrl").s());
      tenantInfo.setUserName(item.get("userName").s());
      tenantInfo.setPassword(item.get("password").s());
      tenants.add(tenantInfo);
    }
    return tenants;
  }
}