package com.sc.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sc.api.base.BaseService;
import com.sc.api.entity.AdminLog;

@FeignClient(value = "CLOUD-SYS-PROVIDER", fallbackFactory = AdminLogFallbackFactory.class)
@RequestMapping("adminLog")
public interface AdminLogService extends BaseService<AdminLog> {
	
}
