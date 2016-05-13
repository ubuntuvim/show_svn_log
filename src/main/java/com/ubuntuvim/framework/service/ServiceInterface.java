package com.ubuntuvim.framework.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ubuntuvim.framework.model.SvnLog;
import com.ubuntuvim.framework.util.PageBean;

public interface ServiceInterface<T> {
	
	/**
	 * 新增记录
	 * @param entity
	 * @return true-新增成功
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public boolean add(T entity) throws SQLException, Exception;
	
	/**
	 * 根据ID删除数据
	 * @param id
	 * @throws SQLException 
	 */
	public boolean delete(int id) throws SQLException;
	
	/**
	 * 删除某个实体
	 * @param entity
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public boolean delete(T entity) throws SQLException, Exception;
	
	/**
	 * 删除一组数据
	 * @param list
	 * @throws SQLException 
	 * @return true-更新成功
	 */
	public boolean deleteAll(List<T> list) throws SQLException;
	
	/**
	 * 更新实体
	 * @param entity
	 * @return true-删除成功
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public boolean update(T entity) throws SQLException, Exception;
	
	/**
	 * 根据ID更新某个些字段
	 * @param param 字段值
	 * @param id ID
	 * @return true-更新成功
	 * @throws SQLException 
	 */
	public boolean update(Map<String, Object> param, int id) throws SQLException;
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public T findById(int id) throws Exception;
	
	/**
	 * 根据某个字段查询
	 * @param key 字段代码
	 * @param value 字段值
	 * @return
	 */
	public T findByKeyAndValue(String key, Object value) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll() throws Exception;
	
	/**
	 * 分页查询
	 * @param page
	 * @param params 查询参数
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findByPaging(PageBean page, Map<String, Object> params) throws Exception; 
	
	/**
	 * 保存一个列表
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public boolean saveAll(List<SvnLog> list) throws Exception;
}
