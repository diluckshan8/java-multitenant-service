package com.spring.multitenant.filter;

import com.spring.multitenant.config.TenantContext;
import com.spring.multitenant.config.TenantValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

  @Autowired
  private TenantValidator tenantValidator;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String tenantId = request.getHeader("X-Property-Id");
    if (tenantId != null && !tenantId.isEmpty() && tenantValidator.isValidTenant(tenantId)) {
      TenantContext.setCurrentTenant(tenantId);
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("Invalid or missing Tenant ID");
      return;
    }
    try {
      filterChain.doFilter(request, response);
    } finally {
      TenantContext.clear();
    }
  }
}