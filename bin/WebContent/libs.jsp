<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<link href="//cdn.bootcss.com/awesome-bootstrap-checkbox/0.3.7/awesome-bootstrap-checkbox.min.css" rel="stylesheet">

<link href="//cdn.bootcss.com/font-awesome/4.6.2/css/font-awesome.css" rel="stylesheet">

<link href="//cdn.bootcss.com/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.css" rel="stylesheet">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<script src="//cdn.bootcss.com/jquery-validate/1.15.0/jquery.validate.min.js"></script>

<link rel="stylesheet" href="<%=basePath%>/js/jquery-messenger/1.0.0/style/jquery-messenger.css">

<script type="text/javascript" src="<%=basePath%>/js/jquery-messenger/1.0.0/jquery-messenger.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery-messenger/1.0.0/lang/zh-CN.js"></script>
    
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.js"></script>
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.1/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script type="text/javascript">
<!--
var __bastPath__ = '<%=basePath%>';
//-->
</script>

<link rel="stylesheet" href="<%=basePath%>/css/syncLoading.css">
