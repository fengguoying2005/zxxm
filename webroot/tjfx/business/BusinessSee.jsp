<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>查看短信记录</title>

<link rel="stylesheet" href="resource/css/layout.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/framecommon.css"
	type="text/css" />
<link rel="stylesheet" href="resource/css/component.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
	media="screen">
<style type="text/css">
@import "resource/css/jquery.datepick.css";
</style>
<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jeasyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jeasyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jeasyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
    function next(event) {
        var frm = document.myform;
        frm.action="<%=basePath%>tjfx/business_" + event + ".html";
		frm.submit();
	}
	function initReadOnly() {
		$("[readonly='readonly']").each(function(i) {
			$(this).css("background-color", "#D4D0C8");
		});
		
		var turl = "<%=basePath%>"+'tjfx/business!listmx.action';
    	$('#datagrid').datagrid({
    		title:'明细信息',
    		url:turl,
    		iconCls:'icon-save',
    		striped: true,
    		pageNumber : 1,
    		pageSize : 10,
    		collapsible:true,
    		remoteSort: false,
    		idField:'code',
    		pagination:true,
    		//查询条件
    		queryParams: {LSH:$("#NSRDATA_LSH").val(),SMSTYPE_DM:$("#SMSTYPE_DM").val(),MODTYPE:"1"},
    		rownumbers:true,
    		onLoadSuccess : function(data){
    		}
    	});
	}

	function msgformat(val,row) {
		if(val) {
    		return "<span title='"+val+"'>"+val+"</span>";
		} else {
			return "";
		}
	}
	function exportfilenosend() {
		var data = $("#NSRDATA_LSH").val();
		var type = $("#HID_SMSTYPE_DM").val();
		window.open("<%=basePath%>tjfx/business!exportfilenosend.action?NSRDATA_LSH="+data+"&SMSTYPE_DM="+type);
	}
</script>
</head>
<body onload="initReadOnly();">
<form id="myform" name="myform" action="" method="post">
		<s:hidden id="NSRDATA_LSH" name="business.NSRDATA_LSH"></s:hidden>
		<s:hidden id="HID_SMSTYPE_DM" name="business.SMSTYPE_DM"></s:hidden>
<div class="container" id="mainframe">
<div class="span-24"><s:actionerror id="sysactionerror" />
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;查看短信记录
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="ORG_DM_JG"> 税务机关 </label></td>
		<td><s:select disabled="true" id="ORG_DM_JG"
			name="business.ORG_DM_JG" list="orgmap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
		<td class="tdtitle"><label for="SMSTYPE_DM"> 短信类型 </label></td>
		<td><s:select disabled="true" id="SMSTYPE_DM"
			name="business.SMSTYPE_DM" list="smstypemap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="GROUP_NAME"> 短信标题 </label></td>
		<td><s:textfield cssClass="text" id="GROUP_NAME"
			name="business.GROUP_NAME" readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="SMSZT_DM"> 短信状态 </label></td>
		<td><s:select disabled="true" id="SMSZT_DM"
			name="business.SMSZT_DM" list="smsztmap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="LRRY_DM"> 录入人员 </label></td>
		<td><s:textfield cssClass="text" id="LRRY_DM"
			name="business.LRRY_DM" readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="LR_SJ"> 录入时间 </label></td>
		<td><s:textfield cssClass="text" id="LR_SJ" name="business.LR_SJ"
			readonly="true"></s:textfield></td>
		</div>
	</tr>
	</div>
</table>
</div>
<div align="center"><br>
<input type="button" value="返回" onClick="next('list')" class="buttonface" />
<input type="button" value="未发送信息导出" onClick="exportfilenosend();" class="buttonface" />
</div>
	
	<br>
	<div style="height:420px">
	<table id="datagrid" style="width:768px;height:420px"  title='明细信息' iconCls='icon-save' 
       fitColumns=true singleSelect=false fit=true>  
	    <thead>  
	        <tr>
				<s:if test="business.SMSTYPE_DM=='03'">
					<th data-options="field:'ORG_MC_JG',width:100,formatter:msgformat" editor='text'>税务机关</th>
					<th data-options="field:'ORG_MC_BM',width:150,formatter:msgformat" editor='text'>部门</th>
					<th data-options="field:'USER_MC',width:100,formatter:msgformat" editor='text'>用户名称</th>
					<th data-options="field:'SJHM',width:80,formatter:msgformat" editor='text'>手机号码</th>
					<th data-options="field:'MSG',width:80,formatter:msgformat" editor='text'>短信</th>
					<th data-options="field:'SMSTYPE_DM',width:50,formatter:msgformat" editor='text'>发送状态</th>
					<th data-options="field:'FS_SJ',width:50,formatter:msgformat" editor='text'>发送时间</th>
					<th data-options="field:'FSCS',width:50,formatter:msgformat" editor='text'>发送次数</th>
				</s:if>
				<s:if test="business.SMSTYPE_DM=='05'">
					<th data-options="field:'ORG_MC_JG',width:100,formatter:msgformat" editor='text'>税务机关</th>
					<th data-options="field:'ORG_MC_BM',width:150,formatter:msgformat" editor='text'>部门</th>
					<th data-options="field:'USER_MC',width:100,formatter:msgformat" editor='text'>用户名称</th>
					<th data-options="field:'SJHM',width:80,formatter:msgformat" editor='text'>手机号码</th>
					<th data-options="field:'MSG',width:80,formatter:msgformat" editor='text'>短信</th>
					<th data-options="field:'SMSTYPE_DM',width:50,formatter:msgformat" editor='text'>发送状态</th>
					<th data-options="field:'FS_SJ',width:50,formatter:msgformat" editor='text'>发送时间</th>
					<th data-options="field:'FSCS',width:50,formatter:msgformat" editor='text'>发送次数</th>
				</s:if>
				<s:elseif test="business.SMSTYPE_DM=='04'">
					<th data-options="field:'NSRBM',width:100,formatter:msgformat" editor='text'>纳税人编码</th>
					<th data-options="field:'NSRMC',width:150,formatter:msgformat" editor='text'>纳税人名称</th>
					<th data-options="field:'SJHM',width:100,formatter:msgformat" editor='text'>手机号码</th>
					<th data-options="field:'MSG',width:80,formatter:msgformat" editor='text'>短信</th>
					<th data-options="field:'SMSTYPE_DM',width:50,formatter:msgformat" editor='text'>发送状态</th>
					<th data-options="field:'FS_SJ',width:50,formatter:msgformat" editor='text'>发送时间</th>
					<th data-options="field:'FSCS',width:50,formatter:msgformat" editor='text'>发送次数</th>
				</s:elseif>
				<s:else>
					<th data-options="field:'NSRBM',width:100,formatter:msgformat" editor='text'>纳税人编码</th>
					<th data-options="field:'NSRMC',width:150,formatter:msgformat" editor='text'>纳税人名称</th>
					<th data-options="field:'SJHM',width:100,formatter:msgformat" editor='text'>手机号码</th>
					<th data-options="field:'CBRQQ',width:80,formatter:msgformat" editor='text'>有效日期起</th>
					<th data-options="field:'CBRQZ',width:80,formatter:msgformat" editor='text'>有效日期止</th>
					<th data-options="field:'MSG',width:80,formatter:msgformat" editor='text'>短信</th>
					<th data-options="field:'JE',width:80,formatter:msgformat" editor='text'>金额</th>
					<th data-options="field:'SMSTYPE_DM',width:50,formatter:msgformat" editor='text'>发送状态</th>
					<th data-options="field:'FS_SJ',width:50,formatter:msgformat" editor='text'>发送时间</th>
					<th data-options="field:'FSCS',width:50,formatter:msgformat" editor='text'>发送次数</th>
				</s:else>
	        </tr>
	    </thead>  
	</table>
	</div>
</div>
</div>
</form>
</body>
</html>
