<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!-- 引入库 --> 
<%@ include file="libs.jsp" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
</head>
<body>

<!-- 遮盖层 -->
<Div id="DivLocker"></Div>


<!-- 导航 -->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%=basePath%>">SVNLog</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav pull-right">
        <!-- <li class="active"><a href="#">SVN日志</a></li> -->
        <li><a href="#" id="syncData2Local">同步数据</a></li>
        <li><a href="#" id="showSettingModal"><span class="glyphicon glyphicon-cog"></span></a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<!-- 主要内容 -->
<div class="container">

<div class="row" style="margin-bottom: 30px;">
<div class="alert alert-default" role="alert">
<form class="form-inline" id="queryForm">
	<div class="row">
	  <div class="form-group col-md-3 col-sm-3">
	    <!-- <label for="exampleInputName2">版本号</label> -->
	    版本号：<input type="text" class="form-control input-sm" id="exampleInputName2" placeholder="1000" name="revision">
	  </div>
	  <div class="form-group col-md-3 col-sm-3">
	    <!-- <label for="exampleInputEmail2">提交人</label> -->
	    提交人：<input type="text" class="form-control input-sm" id="exampleInputEmail2" placeholder="chendq" name="author">
	  </div>
	  
	  <div class="form-group col-md-3 col-sm-3">
	    <!-- <label for="exampleInputName2">开始时间</label> -->
	    开始时间：<input type="text" class="form-control input-sm" id="changeDateStart" placeholder="开始时间" name="changeDateStart">
	  </div>
	  <div class="form-group col-md-3 col-sm-3">
	   <!--  <label for="exampleInputEmail2">截止时间</label> -->
	    截止时间：<input type="text" class="form-control input-sm" id="changeDateEnd" placeholder="截止时间" name="changeDateEnd">
	  </div>
  </div>
  
  <div class="row row-mtop">
	  <div class="form-group col-md-12 col-sm-12">
	      <label for="exampleInputEmail2">
	      	<span style="position: absolute;margin-top: -13px;">类型</span>
	      </label>
          <div class="checkbox checkbox-primary" style="margin-left: 30px;">
              <input id="checkbox2" type="checkbox" name="opType" value="M" checked>
              <label for="checkbox2">
                  <p class="text-primary">修改</p>
              </label>
          </div>
          <div class="checkbox checkbox-success">
              <input id="checkbox3" type="checkbox" name="opType" value="A" checked>
              <label for="checkbox3">
                  <p class="text-success">新增</p>
              </label>
          </div>
          <div class="checkbox checkbox-warning">
              <input id="checkbox4" type="checkbox" name="opType" value="C" checked>
              <label for="checkbox4">
                  <p class="text-info">冲突</p>
              </label>
          </div>
          <div class="checkbox checkbox-warning">
              <input id="checkbox5" type="checkbox" name="opType" value="G" checked>
              <label for="checkbox5">
                  <p class="text-warning">合并</p>
              </label>
          </div>
          <div class="checkbox checkbox-danger">
              <input id="checkbox6" type="checkbox" name="opType" value="D" checked>
              <label for="checkbox6">
                  <p class="text-danger">删除</p>
              </label>
          </div>
		 <div class="checkbox checkbox-primary">
              <input id="checkbox2" type="checkbox" name="opType" value="R" checked>
              <label for="checkbox2">
                  <p class="text-primary">替换</p>
              </label>
          </div>
          <div class="checkbox checkbox-primary">
              <input id="checkbox2" type="checkbox" name="opType" value="U" checked>
              <label for="checkbox2">
                  <p class="text-primary">更新</p>
              </label>
          </div>
	  </div>
  </div>
  
  <div class="row">
  	<div class="form-group col-md-12 col-sm-12">
  		<button type="button" id="querySvnLogBtn" class="btn btn-success btn-sm col-md-1 col-sm-1">查&nbsp;询</button>
  	</div>
  </div>
</form>
</div>
</div> <!-- row -->

<hr style="border:1px solid #337ab7; margin-top: -60px; margin-bottom: 40px;">

<div class="row">
	<table class="table table-hover table-condensed">
      <thead>
        <tr>
          <th>版本号</th>
          <th>提交人</th>
          <th>类型</th>
          <th>时间</th>
          <th>文件</th>
          <th>所属业务</th>
          <!-- <th>提交备注</th> -->
        </tr>
     </thead>
		<tbody id="dataList">
		</tbody>
	</table>
   </div>
</div>

<!-- 分页设置 -->
<div id="pageNav"></div>

<!-- 设置modal窗口 -->
<div class="modal fade" id="settingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
       <form method="post" id="settingForm">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">设置</h4>
	      </div>
	      <div class="modal-body">
			  <div class="form-group">
			    <input type="text" class="form-control" id="svnUser" name="username" placeholder="SVN登录用户">
			  </div>
			  <div class="form-group">
			    <input type="password" class="form-control" id="svnPwd" name="password" placeholder="SVN登录密码">
			  </div>
			  <div class="form-group">
			    <input type="text" class="form-control" id="svnPath" placeholder="SVN路径" name="svnBasePath">
			  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="submit" class="btn btn-success">保存</button>
	      </div>
		</form>
    </div>
  </div>
</div>

<!-- loading层 -->
<div class="spinner">

  <div class="spinner-container container1">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
  <div class="spinner-container container2">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
  <div class="spinner-container container3">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
  <div class="syn">正在同步数据……</div>
</div>

<script type="text/javascript" src="<%=basePath%>js/show_svn_log.js"></script>
</body>
</html>

