package com.spring.multitenant.config;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TenantValidator {

  @Autowired
  private DynamoDbClient dynamoDbClient;

  public boolean isValidTenant(String tenantId) {
    Map<String, AttributeValue> key = new HashMap<>();
    key.put("TenantId", AttributeValue.builder().s(tenantId).build());

    GetItemRequest request = GetItemRequest.builder()
        .tableName("TenantRegistry")
        .key(key)
        .build();

    return dynamoDbClient.getItem(request).hasItem();
  }
}