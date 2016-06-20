<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!-- 引入bootstrap库 --> 
<%@ include file="libs.jsp" %>   
<%
	response.sendRedirect(basePath+"SVNLogServlet?functionCode=svnlog_show_all");
%>