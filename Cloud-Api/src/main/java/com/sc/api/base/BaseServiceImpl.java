package com.sc.api.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> implements BaseService<T> {

	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	protected M baseMapper;

	/**
	 * @param result
	 * @return boolean
	 */
	protected boolean retBool(Integer result) {
		return SqlHelper.retBool(result);
	}

	@SuppressWarnings("unchecked")
	protected Class<T> currentModelClass() {
		return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
	}

	/**
	 * SqlSession
	 */
	protected SqlSession sqlSessionBatch() {
		return SqlHelper.sqlSessionBatch(currentModelClass());
	}

	/**
	 * sqlSession
	 *
	 * @param sqlSession session
	 */
	protected void closeSqlSession(SqlSession sqlSession) {
		SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
	}

	/**
	 * SqlStatement
	 *
	 * @param sqlMethod ignore
	 * @return ignore
	 */
	protected String sqlStatement(SqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}

	@Override
	public boolean save(T entity) {
		return retBool(baseMapper.insert(entity));
	}

	@Override
	public boolean saveBatch(Collection<T> entityList, int batchSize) {
		String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int i = 0;
			for (T anEntityList : entityList) {
				batchSqlSession.insert(sqlStatement, anEntityList);
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
				i++;
			}
			batchSqlSession.flushStatements();
		}
		return true;
	}

	@Override
	public boolean removeById(Serializable id) {
		return SqlHelper.retBool(baseMapper.deleteById(id));
	}

	@Override
	public boolean removeByMap(Map<String, Object> columnMap) {
		Assert.notEmpty(columnMap, "error: columnMap must not be empty");
		return SqlHelper.retBool(baseMapper.deleteByMap(columnMap));
	}

	@Override
	public boolean remove(Wrapper<T> wrapper) {
		return SqlHelper.retBool(baseMapper.delete(wrapper));
	}

	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		return SqlHelper.retBool(baseMapper.deleteBatchIds(idList));
	}

	@Override
	public boolean updateById(T entity) {
		return retBool(baseMapper.updateById(entity));
	}
	
	@Override
	public boolean update(Wrapper<T> updateWrapper) {
		return retBool(baseMapper.update(null, updateWrapper));
	}

	@Override
	public boolean updateBatchById(Collection<T> entityList, int batchSize) {
		Assert.notEmpty(entityList, "error: entityList must not be empty");
		String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int i = 0;
			for (T anEntityList : entityList) {
				MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
				param.put(Constants.ENTITY, anEntityList);
				batchSqlSession.update(sqlStatement, param);
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
				i++;
			}
			batchSqlSession.flushStatements();
		}
		return true;
	}

	@Override
	public T getById(Serializable id) {
		return baseMapper.selectById(id);
	}

	@Override
	public Collection<T> listByIds(Collection<? extends Serializable> idList) {
		return baseMapper.selectBatchIds(idList);
	}

	@Override
	public Collection<T> listByMap(Map<String, Object> columnMap) {
		return baseMapper.selectByMap(columnMap);
	}

	@Override
	public T getOne(Wrapper<T> queryWrapper) {
		return SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
	}

	@Override
	public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
		return SqlHelper.getObject(log, baseMapper.selectMaps(queryWrapper));
	}

	@Override
	public int count(Wrapper<T> queryWrapper) {
		return SqlHelper.retCount(baseMapper.selectCount(queryWrapper));
	}

	@Override
	public List<T> list(Wrapper<T> queryWrapper) {
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public IPage<T> page(long current, long size, Wrapper<T> queryWrapper) {
		return baseMapper.selectPage(new Page<>(current, size), queryWrapper);
	}
	
	@Override
	public IPage<T> page(IPage<T> page) {
		IPage<T> selectPage = baseMapper.selectPage(page,Wrappers.emptyWrapper());
		return (Page<T>) selectPage;
	}		

	@Override
	public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
		return baseMapper.selectMaps(queryWrapper);
	}

	@Override
	public IPage<Map<String, Object>> pageMaps(long current, long size, Wrapper<T> queryWrapper) {
		return baseMapper.selectMapsPage(new Page<>(current, size), queryWrapper);
	}

	@Override
	public M getBaseMapper() {
		return baseMapper;
	}

}
