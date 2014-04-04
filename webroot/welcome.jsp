<%@ page language="java"
	import="java.util.*,com.gwinsoft.components.qxgl.user.User,com.gwinsoft.components.common.MessageUtil"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">
        <link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
        <link rel="stylesheet" href="resource/css/component.css" type="text/css" media="screen">
        <link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
        <link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
        
<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/locale/easyui-lang-zh_CN.js"></script>
<SCRIPT type="text/javascript" src="js/json2.js"></SCRIPT>
<script type="text/javascript">
$(function() {
	$("#datagrid").datagrid(
		{
			url:"<%=basePath%>ajax/msggetter!sendermsg.action",
			"fitColumns":true
		}
	);
	$("#datagrid2").datagrid(
		{
			url:"<%=basePath%>ajax/msggetter!auditormsg.action",
			"fitColumns":true
		}
	);
});
function msgformat(val,row) {
	if(val) {
		return "<span title='"+val+"'>"+val+"</span>";
	} else {
		return "";
	}
}
function expformat(val,row) {
	var type = "";
	if(row.SMSTYPE_DM=="催报短信") {
		type = "01";
	} else if(row.SMSTYPE_DM=="催缴短信") {
		type = "02";
	} else if(row.SMSTYPE_DM=="内部短信") {
		type = "03";
	} else if(row.SMSTYPE_DM=="税法宣传") {
		type = "04";
	}
	if(row.NOPASS_NUM=="0") {
		return "无。";
		//return "<a href='#' onClick='javaScript:exprot(\""+val+"\",\""+type+"\")'>未发送数据导出</a>";
	} else {
		return "<a href='#' onClick='javaScript:exprot(\""+val+"\",\""+type+"\")'>未发送数据导出</a>";
	}
}
function shformat(val,row) {
	return "<a href='#' onClick='javaScript:sh(\""+val+"\")'>点击进入审核</a>";
}
function exprot(data,type) {
	window.open("<%=basePath%>ajax/msggetter!exportnopass.action?NSRDATA_LSH="+data+"&SMSTYPE_DM="+type);
}
function sh(data) {
	parent.tab.addTabItem({ tabid : "短信审核",text: "短信审核", url: "smssh/sh!list.action" });
}
</script>
</head>
<body style="background: white; font-size: 12px;">
<div align="left">
	<font>
		欢迎您登陆辽宁省地方税务局短信服务平台
		<% Object o = request.getSession().getAttribute("GWINSOFT_USER_KEY"); %>
	</font>
		<BR>
		<BR>
<div style="height:150px">
	<table id="datagrid" width="600px" title='发送短信工作提醒' checkOnSelect=false fitColumns=false singleSelect=false fit=false>  
	    <thead>  
	        <tr>
	        	<th data-options="field:'SMSTYPE_DM',width:100,formatter:msgformat">发送类型</th>
				<th data-options="field:'GROUP_NAME',width:200,formatter:msgformat">发送标题</th>
				<th data-options="field:'ORG_DM_JG',width:100,formatter:msgformat">税务机关</th>
				<th data-options="field:'SMSZT_DM',width:100,formatter:msgformat">短信状态</th>
				<th data-options="field:'LR_SJ',width:160,formatter:msgformat">发送时间</th>
				<th data-options="field:'PASS_NUM',width:80,formatter:msgformat">已发送条数</th>
				<th data-options="field:'NOPASS_NUM',width:80,formatter:msgformat">未发送条数</th>
				<th data-options="field:'NSRDATA_LSH',width:100,formatter:expformat">未发送数据导出</th>
	        </tr>  
	    </thead>  
	</table>
</div>
	<BR>
<div style="height:150px">
	<table id="datagrid2" class="easyui-datagrid" width="600px" title='审核短信工作提醒' checkOnSelect=false fitColumns=false singleSelect=false fit=false>  
	    <thead>  
	        <tr>
	        	<th data-options="field:'SMSTYPE_DM',width:100,formatter:msgformat">发送类型</th>
				<th data-options="field:'GROUP_NAME',width:200,formatter:msgformat">发送标题</th>
				<th data-options="field:'ORG_DM_JG',width:100,formatter:msgformat">税务机关</th>
				<th data-options="field:'SMSZT_DM',width:100,formatter:msgformat">短信状态</th>
				<th data-options="field:'LR_SJ',width:160,formatter:msgformat">发送时间</th>
				<th data-options="field:'NSRDATA_LSH',width:100,formatter:shformat">点击进入审核</th>
	        </tr>  
	    </thead>  
	</table>
</div>
</div>
</body>
</html>
