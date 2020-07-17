package com.sc.sys.consumer.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sc.BaseController;
import com.sc.api.entity.AdminLog;
import com.sc.api.service.AdminLogService;
import com.sc.api.utils.Constant;
import com.sc.api.utils.JsonResult;
import com.sc.redis.RebloomUtil;

@RestController
@RequestMapping("admin/log")
@Validated
public class AdminLogController extends BaseController<AdminLogService, AdminLog> {

	@Autowired
	private RebloomUtil rebloomUtil;

	@Override
	public JsonResult<AdminLog> getById(@PathVariable Serializable id) {		
		if (id == null) {
			return new JsonResult<>(new AdminLog());
		}
		if (rebloomUtil != null) {
			if (rebloomUtil.exists(AdminLog.class, Constant.REBLOOM_PRE_SYS, id.toString())) {
				return new JsonResult<>(service.getById(id));
			}
			return new JsonResult<>(new AdminLog());
		} else {
			return new JsonResult<>(service.getById(id));
		}
	}

}
