package com.ubuntuvim.framework.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ubuntuvim.framework.dao.impl.JdbcUtils;
import com.ubuntuvim.framework.model.SvnLog;
import com.ubuntuvim.framework.service.ServiceInterface;
import com.ubuntuvim.framework.util.PageBean;

public class SVNLogService implements ServiceInterface<SvnLog> {
	
	private JdbcUtils jdbcUtils = null;
	
	public SVNLogService() {
		this.jdbcUtils = new JdbcUtils();
	}
	
	@Override
	public boolean add(SvnLog s) throws Exception {
		if (null == s) {
			throw new Exception("新增的数据为空。");
		}
		
		String sql = "insert into svn_log(changeFilePath, revision, author, logMsg, changeDate, opType, functionName) " +
				" values (?, ?, ?, ?, ?, ?, ?)";
		
		List<Object> params = new ArrayList<Object>();
		params.add(s.getChangeFilePath());
		params.add(s.getRevision());
		params.add(s.getAuthor());
		params.add(s.getLogMsg());
		params.add(s.getChangeDate());
		params.add(s.getOpType());
		params.add(s.getFunctionName());
		
		return jdbcUtils.updateByPreparedStatement(sql, params);
	}


	@Override
	public boolean delete(int id) throws SQLException {
		return false;
	}

	@Override
	public boolean delete(SvnLog entity) throws Exception {
		return false;
	}

	@Override
	public boolean deleteAll(List<SvnLog> list) throws SQLException {
		return true;
	}

	@Override
	public boolean update(SvnLog entity) throws Exception {
		return false;
	}

	@Override
	public boolean update(Map<String, Object> param, int id) throws SQLException {
		return true;   
	}

	@Override
	public SvnLog findById(int id) throws Exception {
		return null;
	}

	@Override
	public SvnLog findByKeyAndValue(String key, Object value) throws Exception {
		return null;
	}

	@Override
	public List<SvnLog> findAll() throws Exception {
		String sql = "select * from svn_log";
		return jdbcUtils.findMoreRefResult(sql, null, SvnLog.class);
	}
	
	@Override
	public List<Map<String, Object>> findByPaging(PageBean page,
			Map<String, Object> params) throws Exception {
		return null;
	}


	/**
	 * 保存一个对象列表数据
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public boolean saveAll(List<SvnLog> list) throws Exception {
		if (null == list || list.size() <= 0) {
			throw new Exception("新增的数据为空。");
		}
		
		String sql = "";
		List<Object> params = new ArrayList<Object>();
		for (SvnLog s : list) {
			sql = "insert into svn_log(changeFilePath, revision, author, logMsg, changeDate, opType) " +
					" values(?, ?, ?, ?, ?, ?)";
			
			params.add(s.getChangeFilePath());
			params.add(s.getRevision());
			params.add(s.getAuthor());
			params.add(s.getLogMsg());
			params.add(s.getChangeDate());
			params.add(s.getOpType());
			
			//数据太多，无法一次性插入，一条条插入
			jdbcUtils.updateByPreparedStatement(sql, params);
			params.clear();
		}
		// 去掉最后一个逗号和空格
//		tmpSql = tmpSql.substring(0, tmpSql.length()-2);
//		sql += tmpSql;
//		return jdbcUtils.updateByPreparedStatement(sql, params);
		return true;
	}

	/**
	 * 查询所有数据
	 * @param revision 版本号
	 * @param author 提交人
	 * @param opType 更改类型
	 * @param changeDateStart 提交日期：查询开始时间
	 * @param changeDateEnd 提交的日志：查询截止时间
	 * @return
	 */
	public List<Map<String, Object>> findAll(String revision, String author, String[] opType,
			String changeDateStart, String changeDateEnd, PageBean page) {
		String sql = "select * from svn_log where 1 = 1 ";
		String sql2 = "select count(*) as aa from svn_log where 1 = 1 ";
		List<Object> params = new ArrayList<Object>();
		String tmpStr = "";
		if (StringUtils.isNotBlank(revision)) {
			tmpStr = " and revision = ?";
			sql += tmpStr;
			sql2 += tmpStr;
			params.add(revision);
		}
		if (StringUtils.isNotBlank(author)) {
			tmpStr = " and author = ?";
			sql += tmpStr;
			sql2 += tmpStr;
			params.add(author);
		}
		if (null != opType && opType.length > 0) {
//			tmpStr = " and opType in(?)";
			String ts = "and (";
			for (String type : opType) {
				ts += "opType = ? or ";
				params.add(type);
			}
			ts = ts.substring(0, ts.length() - 4);
			ts += ")";
			sql += ts;
			sql2 += ts;
		}
		//  日期区间查询
		// select * from tb where c> date('2007-07-07') and c< date('2007-07-09')
		if (StringUtils.isNotBlank(changeDateStart)) {
			tmpStr = " and changeDate > ?";
			sql += tmpStr;
			sql2 += tmpStr;
			params.add(changeDateStart);
		}
		if (StringUtils.isNotBlank(changeDateEnd)) {
			tmpStr = " and changeDate < ?";
			sql += tmpStr;
			sql2 += tmpStr;
			params.add(changeDateEnd);
		}
		
		page.setTotalCount(jdbcUtils.count(sql2, params));
		sql += "   order by id desc";
		
		return jdbcUtils.findByPaging(sql, "svn_log", page, params);
	}

	/**
	 * 获取数据库中最大的版本号
	 * @return
	 * @throws Exception 
	 */
	public int getLastVersion() throws Exception {
		String sql = "select revision from svn_log where id = (select max(id) from svn_log)";
		List<Object> params = new ArrayList<Object>();
		SvnLog slog = jdbcUtils.findSimpleRefResult(sql, params, SvnLog.class);
		if (null == slog)
			return 0;
		
		return StringUtils.isNotBlank(slog.getRevision()) ? Integer.parseInt(slog.getRevision()) : 0;
	}
}
