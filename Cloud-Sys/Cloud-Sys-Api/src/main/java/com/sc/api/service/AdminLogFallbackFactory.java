package com.sc.api.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sc.api.entity.AdminLog;
import com.sc.api.utils.LoggerUtil;

import feign.hystrix.FallbackFactory;

/**
 * 
 * @author dy
 *
 */
@Component
public class AdminLogFallbackFactory implements FallbackFactory<AdminLogService> {

	@Override
	public AdminLogService create(Throwable cause) {

		// 打印错误日志
		LoggerUtil.error(cause.getMessage());

		return new AdminLogService() {

			@Override
			public boolean updateById(AdminLog entity) {
				return false;
			}

			@Override
			public boolean updateBatchById(Collection<AdminLog> entityList, int batchSize) {
				return false;
			}

			@Override
			public boolean update(Wrapper<AdminLog> updateWrapper) {
				return false;
			}

			@Override
			public boolean saveBatch(Collection<AdminLog> entityList, int batchSize) {
				return false;
			}

			@Override
			public boolean save(AdminLog entity) {
				return false;
			}

			@Override
			public boolean removeByMap(Map<String, Object> columnMap) {
				return false;
			}

			@Override
			public boolean removeByIds(Collection<? extends Serializable> idList) {
				return false;
			}

			@Override
			public boolean removeById(Serializable id) {
				return false;
			}

			@Override
			public boolean remove(Wrapper<AdminLog> queryWrapper) {
				return false;
			}

			@Override
			public IPage<Map<String, Object>> pageMaps(long current, long size, Wrapper<AdminLog> queryWrapper) {
				return null;
			}

			@Override
			public IPage<AdminLog> page(long current, long size, Wrapper<AdminLog> queryWrapper) {
				return null;
			}
			
			@Override
			public IPage<AdminLog> page(IPage<AdminLog> page) {
				return null;
			}

			@Override
			public List<Map<String, Object>> listMaps(Wrapper<AdminLog> queryWrapper) {
				return null;
			}

			@Override
			public Collection<AdminLog> listByMap(Map<String, Object> columnMap) {
				return null;
			}

			@Override
			public Collection<AdminLog> listByIds(Collection<? extends Serializable> idList) {
				return null;
			}

			@Override
			public List<AdminLog> list(Wrapper<AdminLog> queryWrapper) {
				return null;
			}

			@Override
			public AdminLog getOne(Wrapper<AdminLog> queryWrapper) {
				return null;
			}

			@Override
			public Map<String, Object> getMap(Wrapper<AdminLog> queryWrapper) {
				return null;
			}

			@Override
			public AdminLog getById(Serializable id) {
				return null;
			}

			@Override
			public BaseMapper<AdminLog> getBaseMapper() {
				return null;
			}

			@Override
			public int count(Wrapper<AdminLog> queryWrapper) {
				return 0;
			}

		};
	}

}
