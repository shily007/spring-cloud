package com.sc.sys.provider.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sc.api.base.BaseServiceImpl;
import com.sc.api.entity.AdminLog;
import com.sc.api.service.AdminLogService;

@Service
public class AdminLogServiceImpl extends BaseServiceImpl<BaseMapper<AdminLog>,AdminLog> implements AdminLogService { 

}
