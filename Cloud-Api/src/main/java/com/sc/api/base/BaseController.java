package com.sc.api.base;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sc.api.utils.JsonResult;

public class BaseController<M extends BaseService<T>, T extends BaseEntity> {

	@Autowired
	protected M service;

	/**
	 * 保存对象 save
	 * 
	 * @param entity
	 * @return boolean
	 * @author dy 2020年7月13日
	 */
	@PostMapping
	public JsonResult<Boolean> save(T entity) {
		if (entity == null) {
			return new JsonResult<>(1001);
		}
		boolean result = false;
		if (entity.getId() != null) {
			result = service.updateById(entity);
		} else {
			result = service.save(entity);
		}
		return new JsonResult<>(result);
	}

	/**
	 * 根据id删除 delete
	 * 
	 * @param id
	 * @return
	 * @author dy 2020年7月13日
	 */
	@DeleteMapping("{id}")
	public JsonResult<Boolean> delete(@PathVariable Serializable id) {
		return new JsonResult<>(service.removeById(id));
	}

	/**
	 * 根据id修改 update
	 * 
	 * @param id
	 * @return
	 * @author dy 2020年7月13日
	 */
	@PutMapping
	public JsonResult<Boolean> updateById(T entity) {
		if (entity.getId() == null) {
			return new JsonResult<>(1001);
		}
		return new JsonResult<>(service.updateById(entity));
	}

	/**
	 * 根据id查询 getById
	 * 
	 * @param id
	 * @return
	 * @author dy 2020年7月13日
	 */
	@GetMapping("{id}")
	public JsonResult<T> getById(@PathVariable Serializable id) {
		if (id == null) {
			return new JsonResult<>(null);
		}
		return new JsonResult<T>(service.getById(id));
	}

	/**
	 * 翻页查询全部 page
	 * 
	 * @param page
	 * @return
	 * @author dy 2020年7月13日
	 */
	@GetMapping("page")
	public JsonResult<IPage<T>> page(Page<T> page) {
		return new JsonResult<>(service.page(page));
	}

}
