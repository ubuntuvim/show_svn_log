package com.ubuntuvim.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ubuntuvim.framework.model.SVNSettings;
import com.ubuntuvim.framework.service.impl.SVNSettingsService;
import com.ubuntuvim.framework.util.CommonUtil;


/**
 * 业务管理功能处理控制器
 */
public class SVNSettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Log log = LogFactory.getLog(SVNSettingsServlet.class);
	
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
		
		SVNSettingsService svnService = new SVNSettingsService();
		
		if ("setting_save".equals(functionCode)) {
			save(svnService, out, request);
		} else if ("setting_find_one".equals(functionCode)) {
			findOne(svnService, out, request);
		} else {
			CommonUtil.initRetInfo(out, false, "调用的功能编号[functionCode]不允许为空。", null);
		}
	}

	/**
	 * 查询，只有一条数据
	 * @param svnService
	 * @param out
	 * @param request
	 */
	private void findOne(SVNSettingsService svnService, PrintWriter out,
			HttpServletRequest request) {
		try {
			List<Object> data = new ArrayList<Object>();
			
			List<SVNSettings> ls = svnService.findAll();
			if (null != ls && ls.size() > 0) {
				data.add(ls.get(0));
				CommonUtil.initRetInfo(out, true, "加载完成。", data, 1);
			} else {
				CommonUtil.initRetInfo(out, true, "加载失败。", data, 1);
			}
		} catch (Exception e) {
			log("e.toString() = " + e.toString());
			CommonUtil.initRetInfo(out, false, "错误：" + e.toString(), null);
		}
	}


	private void save(SVNSettingsService svnService, PrintWriter out,
			HttpServletRequest request) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String svnBasePath = request.getParameter("svnBasePath");
		
		SVNSettings setting = new SVNSettings();
		setting.setPassword(password);
		setting.setUsername(username);
		setting.setSvnBasePath(svnBasePath);
		try {
			if (svnService.saveOrUpdate(setting)) {
				CommonUtil.initRetInfo(out, true, "设置完成。", null);
			} else {
				CommonUtil.initRetInfo(out, false, "设置失败。", null);
			}
		} catch (Exception e) {
			log("e.toString() = " + e.toString());
			CommonUtil.initRetInfo(out, false, "设置出错：" + e.toString(), null);
		}
	}


}
