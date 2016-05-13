package com.ubuntuvim.framework.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;

import com.ubuntuvim.framework.util.PageBean;
import com.ubuntuvim.framework.util.PropertiesUtil;


public class JdbcUtils {
	
//	//数据库用户名
//	private static final String USERNAME = "root";
//	//数据库密码
//	private static final String PASSWORD = "123123";
//	//驱动信息 
//	private static final String DRIVER = "com.mysql.jdbc.Driver";
//	//数据库地址
//	private static final String URL = "jdbc:mysql://localhost:3306/interface_mng?useUnicode=true&amp;characterEncoding=UTF-8";

	private static Log log = LogFactory.getLog(JdbcUtils.class);
	
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	
	public JdbcUtils() {
		try{
			Class.forName(PropertiesUtil.GetValueByKey("DRIVER"));
//			Class.forName(DRIVER);
//			connection = DriverManager.getConnection(PropertiesUtil.GetValueByKey("URL"), PropertiesUtil.GetValueByKey("USERNAME"), PropertiesUtil.GetValueByKey("PASSWORD"));
//			Assert.assertNotNull("连接数据库MySQL失败！请检查数据库连接配置。", connection);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得数据库的连接
	 * @return
	 */
	public Connection getConnection() {
		try{
			connection = DriverManager.getConnection(PropertiesUtil.GetValueByKey("URL"), PropertiesUtil.GetValueByKey("USERNAME"), PropertiesUtil.GetValueByKey("PASSWORD"));
//			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Assert.assertNotNull("连接数据库MySQL失败！请检查数据库连接配置。", connection);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return connection;
	}

	
	/**
	 * 增加、删除、改
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public synchronized boolean updateByPreparedStatement(String sql, List<Object>params) throws SQLException{
		boolean flag = false;
		int result = -1;
		pstmt = this.getConnection().prepareStatement(sql);
		int index = 1;
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		log.info("执行SQL："+sql);
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		
		releaseConn();
		return flag;
	}

	/**
	 * 查询单条记录
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
		Map<String, Object> map = new HashMap<String, Object>();
		int index  = 1;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			if(params != null && !params.isEmpty()){
				for(int i=0; i<params.size(); i++){
					pstmt.setObject(index++, params.get(i));
				}
			}
			log.info("执行SQL："+sql);
			resultSet = pstmt.executeQuery();//返回查询结果
			ResultSetMetaData metaData = resultSet.getMetaData();
			int col_len = metaData.getColumnCount();
			while(resultSet.next()){
				for(int i=0; i<col_len; i++ ){
					String cols_name = metaData.getColumnName(i+1);
					Object cols_value = resultSet.getObject(cols_name);
					if(cols_value == null){
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseConn();
		}
		
		return map;
	}

	/**
	 * 查询多条记录
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			if(params != null && !params.isEmpty()){
				for(int i = 0; i<params.size(); i++){
					pstmt.setObject(index++, params.get(i));
				}
			}
			log.info("执行SQL："+sql);
			resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while(resultSet.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i=0; i<cols_len; i++){
					String cols_name = metaData.getColumnName(i+1);
					Object cols_value = resultSet.getObject(cols_name);
					if(cols_value == null){
						cols_value = "";
					}
					map.put(cols_name, cols_value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseConn();
		}

		return list;
	}

	/**
	 * 通过反射机制查询单条记录
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls )throws Exception{
		T resultObject = null;
		int index = 1;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			if(params != null && !params.isEmpty()){
				for(int i = 0; i<params.size(); i++){
					pstmt.setObject(index++, params.get(i));
				}
			}
			log.info("执行SQL："+sql);
			resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData  = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while(resultSet.next()){
				//通过反射机制创建一个实例
				resultObject = cls.newInstance();
				for(int i = 0; i<cols_len; i++){
					String cols_name = metaData.getColumnName(i+1);
					Object cols_value = resultSet.getObject(cols_name);
					if(cols_value == null){
						cols_value = "";
					}
					Field field = cls.getDeclaredField(cols_name);
					field.setAccessible(true); //打开javabean的访问权限
					field.set(resultObject, cols_value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseConn();
		}
		
		return resultObject;
	}

	/**
	 * 通过反射机制查询多条记录
	 * @param sql 
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T>List<T> findMoreRefResult(String sql, List<Object> params,
			Class<T> cls ) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			if(params != null && !params.isEmpty()){
				for(int i = 0; i<params.size(); i++){
					pstmt.setObject(index++, params.get(i));
				}
			}
			log.info("执行SQL："+sql);
			resultSet = pstmt.executeQuery();
			ResultSetMetaData metaData  = resultSet.getMetaData();
			int cols_len = metaData.getColumnCount();
			while(resultSet.next()){
				//通过反射机制创建一个实例
				T resultObject = cls.newInstance();
				for(int i = 0; i<cols_len; i++){
					String cols_name = metaData.getColumnName(i+1);
					Object cols_value = resultSet.getObject(cols_name);
					if(cols_value == null){
						cols_value = "";
					}
					Field field = cls.getDeclaredField(cols_name);
					field.setAccessible(true); //打开javabean的访问权限
					field.set(resultObject, cols_value);
				}
				list.add(resultObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseConn();
		}
		
		return list;
	}

	/**
	 * 释放数据库连接
	 */
	public void releaseConn(){
		if(resultSet != null){
			try{
				resultSet.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 查询所总条数
	public int count(String sql, List<Object> params) {
		log.info("执行SQL："+sql);
		int i = 0;
		try {
			int index = 1;
			pstmt = this.getConnection().prepareStatement(sql);
			if(params != null && !params.isEmpty()){
				for(int j = 0; j<params.size(); j++){
					pstmt.setObject(index++, params.get(j));
				}
			}
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				i = resultSet.getInt("aa");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			releaseConn();
		}
		
		return i;
	}

	/**
	 * 分页查询
	 * @param sql
	 * @param tableName
	 * @param page 分页设置
	 * @param params 查询参数
	 * @return
	 */
	public List<Map<String, Object>> findByPaging(String sql, String tableName, PageBean page, List<Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (page != null) {
			sql = sql + " limit " + page.getStart() + "," + page.getPageSize();
//			page.setTotalCount(this.count("inter_info", params));  //设置总记录数
		}
		try {
			int index = 1;
			String tmp = "  参数：";
			pstmt = this.getConnection().prepareStatement(sql);
			if(params != null && !params.isEmpty()){
				for(int i = 0; i<params.size(); i++){
					pstmt.setObject(index++, params.get(i));
					tmp += params.get(i) + ", ";
				}
			}
			log.info("执行SQL："+sql + tmp);
			resultSet = pstmt.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int count = rsmd.getColumnCount();// 得到表里字段的总数
			while (resultSet.next()) {
				// System.out.println("名字是-->"+rsmd.getColumnName(i)+"/t 得到的object是-->"+rs.getObject(i)+"   "+i);
				HashMap<String, Object> map = new HashMap<String, Object>();
				String key = "";
				String value = null;
				for (int i = 0; i < count; i++) {
					key = rsmd.getColumnName(i + 1);
					value = ObjectUtils.toString(resultSet.getObject(i + 1));
					map.put(key, value);// 名字和值
				}  // end for
				list.add(map);
			}  // end while 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		JdbcUtils jdbcUtils = new JdbcUtils();
		System.out.println(jdbcUtils.getConnection());
	}

}
