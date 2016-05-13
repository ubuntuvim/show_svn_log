package com.ubuntuvim.framework.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ubuntuvim.framework.dao.impl.JdbcUtils;
import com.ubuntuvim.framework.model.SVNSettings;
import com.ubuntuvim.framework.model.SvnLog;
import com.ubuntuvim.framework.service.ServiceInterface;
import com.ubuntuvim.framework.util.PageBean;

public class SVNSettingsService implements ServiceInterface<SVNSettings> {
	
	private JdbcUtils jdbcUtils = null;
	
	public SVNSettingsService() {
		this.jdbcUtils = new JdbcUtils();
	}
	
	@Override
	public boolean add(SVNSettings entity) throws Exception {
		
		if (null == entity)
			throw new Exception("新增的数据为空。");
		
		String sql = "insert into svn_settings(svnBasePath, username, password) values (?, ?, ?)";
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getSvnBasePath());
		params.add(entity.getUsername());
		params.add(entity.getPassword());
		return jdbcUtils.updateByPreparedStatement(sql, params);
	}


	@Override
	public boolean delete(int id) throws SQLException {
		return false;
	}

	@Override
	public boolean delete(SVNSettings entity) throws Exception {
		return false;
	}

	@Override
	public boolean deleteAll(List<SVNSettings> list) throws SQLException {
		return true;
	}

	@Override
	public boolean update(SVNSettings entity) throws Exception {
		if (null == entity)
			throw new Exception("更新的数据为空。");
		String sql = "update svn_settings set svnBasePath = ?, username = ?, password = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(entity.getSvnBasePath());
		params.add(entity.getUsername());
		params.add(entity.getPassword());
		
		return jdbcUtils.updateByPreparedStatement(sql, params);
	}

	@Override
	public boolean update(Map<String, Object> param, int id) throws SQLException {
		
		return true;  //如果更新字段为空，直接返回true（更新成功）
	}

	@Override
	public SVNSettings findById(int id) throws Exception {
		return null;
	}

	@Override
	public SVNSettings findByKeyAndValue(String key, Object value) throws Exception {
		return null;
	}

	@Override
	public List<SVNSettings> findAll() throws Exception {
		String sql = "select * from svn_settings";
		return jdbcUtils.findMoreRefResult(sql, null, SVNSettings.class);
	}
	
	@Override
	public List<Map<String, Object>> findByPaging(PageBean page,
			Map<String, Object> params) throws Exception {
		return null;
	}

	/**
	 * 保存或者修改
	 * 1. 设置只有一条数据，除了第一次是新增之外其他都是修改
	 * @param setting
	 * @return
	 * @throws Exception 
	 */
	public boolean saveOrUpdate(SVNSettings setting) throws Exception {
		List<SVNSettings> ls = this.findAll();
		if (null == ls || ls.size() <= 0) {
			return this.add(setting);
		} else {
			return this.update(setting);
		}
	}

	@Override
	public boolean saveAll(List<SvnLog> list) throws Exception {
		return false;
	}

}
