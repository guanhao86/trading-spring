package com.spring.free.base;



import com.spring.free.common.conf.TradingMapper;

import java.util.List;

/**
 * DAO支持类实现
 * @author Memory
 * @version 1.0
 */
public interface BaseMapper<T> extends TradingMapper<T> {

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(Long id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	List<T> findList(T entity);
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	List<T> findAllList(T entity);
	
	/**
	 * 查询所有数据列表
	 * @see public List<T> findAllList(T entity)
	 * @return
	 */
//	@Deprecated
//	public List<T> findAllList();
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insertBs(T entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	int updateBs(T entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
//	@Deprecated
//	public int delete(Long id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public int deleteBs(T entity);
	

	
}