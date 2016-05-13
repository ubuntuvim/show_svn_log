package com.ubuntuvim.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.ubuntuvim.framework.model.SVNSettings;
import com.ubuntuvim.framework.model.SvnLog;
import com.ubuntuvim.framework.service.impl.SVNLogService;
import com.ubuntuvim.framework.service.impl.SVNSettingsService;
import com.ubuntuvim.framework.util.CommonUtil;
import com.ubuntuvim.framework.util.PageBean;
import com.ubuntuvim.svnlog.SyncSVNLogHistoryToLocal;

/**
 * 业务管理功能处理控制器
 */
public class SVNLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Log log = LogFactory.getLog(SVNLogServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	

	/**
	 * 业务信息增删改查处理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("content-type","tex/html;charset=UTF-8");
		
		//页面输出JSONArray的内容  
        PrintWriter out = response.getWriter();  
        
		//  功能编号
		String functionCode = request.getParameter("functionCode");
		if (null == functionCode || StringUtils.isBlank(functionCode)) 
			functionCode = ObjectUtils.toString(request.getAttribute("functionCode"));
		
		log.info("请求功能号 functionCode = " + functionCode);
		if (StringUtils.isBlank(functionCode)) {
			CommonUtil.initRetInfo(out, false, "调用的功能编号[functionCode]不允许为空。", null);
			return;
		}
		
		SVNLogService svnLog = new SVNLogService();
		
		if ("svnlog_synchronous_data".equals(functionCode)) {
			syncData(svnLog, out, request);
		} else if ("svnlog_show_all".equals(functionCode)) {
			showSvnLogAll(svnLog, out, request);
		} else {
			CommonUtil.initRetInfo(out, false, "调用的功能编号[functionCode]不允许为空。", null);
		}
	}

	/**
	 * 显示所有日志信息
	 * @param svnLog
	 * @param out
	 * @param request
	 */
	private void showSvnLogAll(SVNLogService svnLogService, PrintWriter out,
			HttpServletRequest request) {
		log.info("展示所有数据……");
		// 读取数据库中的连接配置
		try {
			String revision = request.getParameter("revision");
			String author = request.getParameter("author");
			String[] opType = request.getParameterValues("opType");
			String changeDateStart = request.getParameter("changeDateStart");
			String changeDateEnd = request.getParameter("changeDateEnd");
			log.info("查询参数：" + "revision = "+  revision + "author = "+  author + "changeDateStart = "+  changeDateStart + "changeDateEnd = "+  changeDateEnd);
			String currentPath = request.getParameter("page");  //当前页码
			String pageSize = request.getParameter("pageSize");  //每页显示的记录数
			log.info("currentPath = " + currentPath + ", pageSize = " + pageSize);
			
			// 保存数据
			PageBean page = new PageBean();// 分页设置
			page.setPageSize(StringUtils.isNotBlank(pageSize) ? Integer.parseInt(pageSize) : 1);
			page.setPage(StringUtils.isNotBlank(currentPath) ? Integer.parseInt(currentPath) : 1);
			List<Map<String, Object>> retData = svnLogService.findAll(revision, author, opType, changeDateStart, changeDateEnd, page);
			if (retData.size() > 0) {
				Map<String, Object> retMap = new HashMap<String, Object>();
				Gson gson = new Gson();
				retMap.put("data", retData);
				retMap.put("flag", true);
				retMap.put("msg", "数据加载成功。");
				retMap.put("totalCount", page.getTotalCount());
				retMap.put("totalPage", page.getTotalPage());
				retMap.put("pageSize", page.getPageSize());
				
				
				out.print(gson.toJson(retMap));
			} else {
				CommonUtil.initRetInfo(out, true, "无数据。", retData);
			}
			
			log.info("加载完成。。。");
		} catch (Exception e) {
			System.out.println(e.toString());
			CommonUtil.initRetInfo(out, false, "数据加载失败。\n"+e.toString(), null, 1);
		}
	}


	/**
	 * 同步服务器上的svn日志到本地数据库
	 * @param svnLog
	 * @param out
	 * @param request
	 */
	private void syncData(SVNLogService svnLog, PrintWriter out,
			HttpServletRequest request) {
		log.info("开始同步数据……");
		// 读取数据库中的连接配置
		SVNSettingsService s = new SVNSettingsService();
		SVNLogService slog = new SVNLogService();
		SVNSettings setting = null;
		try {
			List<SVNSettings> setingList = s.findAll();
			if (null != setingList && setingList.size() > 0)
				setting = setingList.get(0);
			
			log.info("同步登陆账户信息： " + setting);
			
			List<SvnLog> list = null; 
			SyncSVNLogHistoryToLocal syn = new SyncSVNLogHistoryToLocal();
			// 获取svn上的日志记录，先获取最新同步的数据（数据库中ID最大的数据所对应的版本号）
			int lastVersion = slog.getLastVersion();
			log.info("开始版本号："+lastVersion);
			if (null != setting)
				list = syn.sync(setting.getUsername(), setting.getPassword(), setting.getSvnBasePath(), lastVersion);
			
			//log.info("获取svn日志记录： " + list);
			
			List<Object> data = new ArrayList<Object>();
			// 保存数据
			if (slog.saveAll(list)) {
				data.add(list);
				CommonUtil.initRetInfo(out, true, "数据同步完成。", data, 1);
			} else {
				CommonUtil.initRetInfo(out, false, "数据同步失败。", data, 1);
			}
			log.info("同步完成。。。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
