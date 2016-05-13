var tipMsgonfig = {handle:false, multiple: false, showIcon: false, closable: false, showTime: false, width: 100};


//  转换文件被操作的类型
function transformType(type) {
	var retStr = "";
	//<span class=\"label label-default\">"+arr[i].opType+"</span>
	if ('A' === type) {
		retStr = "<span class=\"label label-success\">新增</span>";
	} else if ('M' === type) {
		retStr = "<span class=\"label label-info\">修改</span>";
	} else if ('C' === type) {
		retStr = "<span class=\"label label-warning\">冲突</span>";
	} else if ('G' === type) {
		retStr = "<span class=\"label label-warning\">合并</span>";
	} else if ('D' === type) {
		retStr = "<span class=\"label label-danger\">删除</span>";
	} else if ('U' === type) {
		retStr = "<span class=\"label label-default\">更新</span>";
	} else if ('R' === type) {
		retStr = "<span class=\"label label-info\">替换</span>";
	} else {
		retStr = "<span class=\"label label-danger\">未知操作</span>";
	}  
	
	return retStr;
}

function transformDate(dataStr) {
    var f = dataStr.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
            parseInt(d[0], 10) || null,
            (parseInt(d[1], 10) || 1) - 1,
            parseInt(d[2], 10) || null,
            parseInt(t[0], 10) || null,
            parseInt(t[1], 10) || null,
            parseInt(t[2], 10) || null
            )).getTime() / 1000;
}

/** 
 * @param {String} divName 分页导航渲染到的dom对象ID 
 * @param {String} funName 点击页码需要执行后台查询数据的JS函数 
 * @param {Object} params 后台查询数据函数的参数，参数顺序就是该对象的顺序，当前页面一定要设置在里面的 
 * @param {String} curPage 当然页码 
 * @param {String} totalPage 后台返回的总记录数 
 * @param {Boolean} pageSize 每页显示的记录数，默认是20 
 */  
function supage(divId, funName, params, curPage, total, totalPage, pageSize) {  
    var output = '<div class="container"><nav><ul class="pagination">';  
    var pageSize = parseInt(pageSize)>0 ? parseInt(pageSize) : 20;  
    if(parseInt(total) == 0 || parseInt(total) == 'NaN') return;  
    //var totalPage = Math.ceil(total/pageSize);  
    var curPage = parseInt(curPage)>0 ? parseInt(curPage) : 1;  
      
    //从参数对象中解析出来各个参数  
    var param_str = '';  
    if(typeof params == 'object'){  
        for(o in params){  
            if(typeof params[o] == 'string'){  
               param_str += '\'' + params[o] + '\',';  
            }  
            else{  
               param_str += params[o] + ',';  
            }  
        }  
        //alert(111);  
    }  
    console.log("totalPage = " + totalPage);
    //设置起始页码  
    if (totalPage > 5) {  
        if ((curPage - 5) > 0 && curPage < totalPage - 5) {  
            var start = curPage;  
            var end = curPage + 5; 
        }  else if (curPage >= (totalPage - 5)) {  
            var start = totalPage - 5;  
            var end = totalPage;  
        }  else {  
            var start = 1;  
            var end = 5;  
        }  
    } else {  
        var start = 1;  
        var end = totalPage;  
    }  
    
    
    //首页控制  
   // if(curPage>1){  
    //    output += '<a href="javascript:'+funName+'(' + param_str + '1);" title="第一页" class="page-first">«</a>';  
   // } else   {  
    //    output += '<li><span aria-hidden="true">&laquo;</span><span aria-hidden="true">&laquo;</span></li>';  
   // }  
    //上一页菜单控制  
    if(curPage>1){  
        output += '<li><a href="javascript:'+funName+'(' + param_str + (curPage-1)+');" title="上一页" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>';  
    }  
    else{  
        output += '<li><span aria-hidden="true">&laquo;</span></li>';  
    }  
      
    //页码展示   <li><a href="#">5</a></li>
    for (var i = start; i <= end; i++) {  
        if (i == curPage) {  
            output += '<li class="active"><a href="javascript:;" class="page-cur">' + curPage + '</a></li>';  
        }  
        else {  
            output += '<li><a href="javascript:'+funName+'(' + param_str + i + ');">' + i + '</a></li>';  
        }  
    }  
    //下一页菜单控制  
    if(totalPage>1 && curPage<totalPage){  
        output += '<li><a title="下一页" href="javascript:'+funName+'('+param_str + (curPage+1)+');" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>';  
    }  
    else{  
        output += '<li><span aria-hidden="true">&raquo;</span></li>';  
    }  
  //最后页控制  
    //if(curPage<totalPage){  
        //output += '<a title="最后页" href="javascript:'+funName+'('+param_str + totalPage+');" class="page-end">»</a>';  
    //} else{  
        //output += '<span class="page-disabled">»</span>';  
    //}   
      
    output += '</ul></nav></div>';  
    //渲染到dom中  
    document.getElementById(divId).innerHTML = output;  	
}; 

function loadSvnLog(currentPage, page, pageSize) {
	
	$.ajax({  
        type : "POST",  //提交方式  
        url : __bastPath__ + "SVNLogServlet?functionCode=svnlog_show_all&pageSize=20&page="+page,//路径  
        data: $("#queryForm").serialize(),//数据，这里使用的是Json格式进行传输  
        success : function(result) {//返回数据根据结果进行相应的处理
        	var data =  eval('(' + result + ')');    
        	console.log("data = " + data);
            if ( data.flag ) { 
            	// 清空原有数据
            	$("#dataList").html("");
				//  渲染数据到表格上
            	var trStr = "";
            	// 遍历数据
            	var arr = data.data;
            	var len = data.data.length;
            	if (len === 0) {
            		Messenger.success(data.msg, jQuery.extend(tipMsgonfig, {timeout: 1000}));//
            		return;
            	}
            	// 设置分页
            	/** 
            	 * @param {String} divName 分页导航渲染到的dom对象ID 
            	 * @param {String} funName 点击页码需要执行后台查询数据的JS函数 
            	 * @param {Object} params 后台查询数据函数的参数，参数顺序就是该对象的顺序，当前页面一定要设置在里面的 
            	 * @param {String} curPage 当前页 
            	 * @param {String} total 后台返回的总记录数 
            	 * @param {Boolean} pageSize 每页显示的记录数，默认是10 
            	 */  
            	supage('pageNav','loadSvnLog',{currentPage:currentPage},page,data.totalCount,data.totalPage, pageSize);
            	
            	for (var i = 0; i < len; i++) {
            		// 生成一个唯一的id
            		trStr += "<tr onclick=\"showMsg('collapse__"+arr[i].id+"')\" class='tr-cursor'>\
			          <th>"+arr[i].revision+"</th>\
			          <td>"+arr[i].author+"</td>\
			          <td>"+transformType(arr[i].opType)+"</td>\
			          <td>"+arr[i].changeDate+"</td>\
			          <td>"+arr[i].changeFilePath+"</td>\
			          <td>"+arr[i].functionName+"</td>\
			        </tr>\
			        <tr>\
					  <td colspan='5' class='margin0-padding0'>\
						<div class='collapse' id='collapse__"+arr[i].id+"'>\
						  <p class='text-info'>\
							   <b>提交备注：</b>"+arr[i].logMsg+"\
							</p>\
						</div>\
					  </td>\
				    </tr>";
            	}
            	$("#dataList").html(trStr);
            } else {  
            	Messenger.error(data.msg, jQuery.extend(tipMsgonfig, {timeout: 5000}));//
            }  
        },
        error: function(err) {
        	Messenger.error("服务器出错，请重试！", jQuery.extend(tipMsgonfig, {timeout: 5000}));
        }
    });  
}

function showMsg(ids) {
	var id = "#"+ids;
	$(id).collapse('toggle');
}


$(function(){

	
	// 加载数据
	console.log("加载列表数据……");
	loadSvnLog(1, 1, 20);  //页面加载的时候加载全部数据
	
	// 点击“查询”手动加载数据
	$("#querySvnLogBtn").click(function() {
		loadSvnLog(1, 1, 20);
	});

	// 时间插件
	var datepickerConfig = {format:'yyyy-mm-dd', autoclose: true, language: 'zh-CN'};
	$('#changeDateStart').datepicker(datepickerConfig);
	$('#changeDateEnd').datepicker(datepickerConfig);

	
	var validate = $("#settingForm").validate({
        //debug: false, //调试模式取消submit的默认提交功能   
        errorClass: "label.error", //默认为错误的样式类为：error   
        focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
        onkeyup: false,  
        invalidHandler: function(form, validator) {
        	return false;
        },
        submitHandler: function(form) {   //表单提交句柄,为一回调函数，带一个参数：form   
        
            $.ajax({  
                type : "POST",  //提交方式  
                url : __bastPath__ + "SVNSettingsServlet?functionCode=setting_save",//路径  
                data: $("#settingForm").serialize(),//数据，这里使用的是Json格式进行传输  
                success : function(result) {//返回数据根据结果进行相应的处理
                	var data =  eval('(' + result + ')');    
                    if ( data.flag ) { 
                    	Messenger.success(data.msg, jQuery.extend(tipMsgonfig, {timeout: 1000}));  //使用插件jquery-messager提示方法
                    	//隐藏模态框	
                    	$('#settingModal').modal("hide");
                    } else {  
                    	Messenger.error(data.msg, jQuery.extend(tipMsgonfig, {timeout: 5000}));//
                    }  
                },
                error: function(err) {
                	Messenger.error("服务器出错，请重试！", jQuery.extend(tipMsgonfig, {timeout: 5000}));
                }
            });  
        
            //form.submit();   //提交表单 
            return false;
        },   
        
        rules:{
            username:{
                required:true
            },
            password:{
                required:true 
            },
            svnBasePath:{
                required:true
            }                    
        },
        messages:{
        	username:{
                required:"必填"
            },
            password:{
                required:"必填"
            },
            svnBasePath:{
                required: "必填"
            }                                    
        }
    });    
});

$("#showSettingModal").click(function() {
	$(".spinner").show();  //loading
	$(".syn").html("加载数据中……");
	$("#settingModal").modal("show");
	// 初始化表单数据
	$.ajax({  
        type : "POST",  //提交方式  
        url : __bastPath__ + "SVNSettingsServlet?functionCode=setting_find_one",//路径  
        data: $("#settingForm").serialize(),//数据，这里使用的是Json格式进行传输  
        success : function(result) {//返回数据根据结果进行相应的处理
        	var data =  eval('(' + result + ')');    
        	//console.log("data = " + data);
            if ( data.flag ) { 
            	$("#svnUser").val(data.data[0].username);
            	$("#svnPwd").val(data.data[0].password);
            	$("#svnPath").val(data.data[0].svnBasePath);
            } else {  
            	Messenger.error("加载数据出错。"+data.msg, jQuery.extend(config, {timeout: 5000}));//
            }  
            $(".spinner").hide();
        },
        error: function(err) {
        	Messenger.error("服务器出错，请重试！", jQuery.extend(config, {timeout: 5000}));
        }
    });
});


// 同步数据
$("#syncData2Local").click(function() {
	$('#DivLocker').css({
	    "position": "absolute",
	    "margin-left": "1px",
	    "margin-top": "1px",
	    "background-color": "#000000",
	    "height": function () { return $(document).height(); },
	    "filter": "alpha(opacity=30)",
	    "opacity": "0.3",
	    "overflow": "hidden",
	    "width": function () { return $(document).width(); },
	    "z-index": "999"
	});
	$(".spinner").show();
	// 执行后台程序，同步数据
	$.ajax({  
        type : "POST",  //提交方式  
        url : __bastPath__ + "SVNLogServlet?functionCode=svnlog_synchronous_data",//路径  
        //data: ,//数据，这里使用的是Json格式进行传输  
        success : function(result) {//返回数据根据结果进行相应的处理
        	var data =  eval('(' + result + ')');    
        	//console.log("data = " + data);
            if ( data.flag ) { 
            	// 同步完成刷新页面
            	location.reload();
            } else {  
            	Messenger.error("同步数据出错。"+data.msg, jQuery.extend(config, {timeout: 5000}));//
            }  
        },
        error: function(err) {
        	Messenger.error("服务器出错，请重试！", jQuery.extend(config, {timeout: 5000}));
        }
	});
});