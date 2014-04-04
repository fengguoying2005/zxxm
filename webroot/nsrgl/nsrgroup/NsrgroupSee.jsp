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
<title>查看纳税人群组</title>

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

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
    function next(event) {
        var frm = document.myform;
        frm.action="<%=basePath%>nsrgl/nsrgroup_" + event + ".html";
		frm.submit();
	}
	function initReadOnly() {
		$("[readonly='readonly']").each(function(i) {
			$(this).css("background-color", "#D4D0C8");
		});
	}

	function msgformat(val,row) {
		if(val) {
    		return "<span title='"+val+"'>"+val+"</span>";
		} else {
			return "";
		}
	}

    $(function() {
    	var turl = "<%=basePath%>"+'nsrgl/nsrgroup!listmx.action';
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
    		queryParams: {LSH:$("#LSH").val()},
    		rownumbers:true,
    		onLoadSuccess : function(data){
    		}
    	});
    });
	function seemx() {
		var rows = $('#datagrid').datagrid('getChecked');
		var ids = [];
		var LSH;
		for(var i=0; i<rows.length; i++){
		    ids.push(rows[i].GROUPMX_LSH);
		    LSH = rows[i].GROUPMX_LSH;
		}
		if(ids.length==0) {
			$.messager.alert("[提示信息]","请选择记录。");
		} else if(ids.length>1) {
			$.messager.alert("[提示信息]","请选择一条记录。");
		} else {
			var url = "<%=basePath%>"+'nsrgl/nsrgroup!seemx.action?LSH='+LSH;
			var content ='<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%; height:100%"></iframe>';
			$("#see_dialog").dialog({
			    title: "查看纳税人",   
			    'width': 700,   
			    'height': 380,   
			    content: content,
			    collapsible: true,
			    resizable: true,
			    maximizable: true,
			    modal: true,
			    closed: false,
			    cache: false
			}).dialog('open'); 
		}
	}
</script>
</head>
<body onload="initReadOnly();">
<form id="myform" name="myform" action="" method="post">
<s:hidden id="LSH" name="nsrgroup.LSH"></s:hidden>
<div class="container" id="mainframe">
<div class="span-24"><s:actionerror id="sysactionerror" />
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;查看纳税人群组
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="ORG_DM"> 税务机关 </label></td>
		<td><s:textfield cssClass="text" id="ORG_DM"
			name="nsrgroup.ORG_DM" readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="GROUP_NAME"> 群组名称 </label></td>
		<td><s:textfield cssClass="text" id="GROUP_NAME"
			name="nsrgroup.GROUP_NAME" readonly="true"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="INFO"> 情况说明 </label></td>
		<td><s:textfield cssClass="text" id="INFO" name="nsrgroup.INFO"
			readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="GROUP_COUNT"> 群组数量 </label></td>
		<td><s:textfield cssClass="text" id="GROUP_COUNT"
			name="nsrgroup.GROUP_COUNT" readonly="true"></s:textfield></td>
		</div>
	</tr>
	</div>
</table>
</div>
<div align="center"><br>
<input type="button" value="返回" onClick="next('list')"
	class="buttonface" /></div>
	
	<br>
	<div id="tb">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="seemx()">查看</a>
	</div>
	<div style="height:420px">
		<table id="datagrid" title='纳税人明细信息' iconCls='icon-save' fitColumns=true singleSelect=false fit=true toolbar="#tb">  
		    <thead>  
		        <tr>  									
		        	<th field="GROUPMX_LSH" checkbox="true"></th>				
					<th data-options="field:'ORG_DM',width:100,formatter:msgformat" editor='text'>主管机关</th>
					<th data-options="field:'SSZGY',width:100,formatter:msgformat" editor='text'>专管员</th>
					<th data-options="field:'NSRBM',width:100,formatter:msgformat" editor='text'>纳税人编码</th>
					<th data-options="field:'NSRMC',width:150,formatter:msgformat" editor='text'>纳税人名称</th>
					<th data-options="field:'BSRY',width:100,formatter:msgformat" editor='text'>办税人</th>
					<th data-options="field:'BSRYSJH',width:100,formatter:msgformat" editor='text'>办税人手机</th>
					<th data-options="field:'FR',width:100,formatter:msgformat" editor='text'>法人</th>
					<th data-options="field:'FRSJH',width:100,formatter:msgformat" editor='text'>法人手机</th>
					<th data-options="field:'CWJL',width:100,formatter:msgformat" editor='text'>财务经理</th>
					<th data-options="field:'CWJLSJH',width:100,formatter:msgformat" editor='text'>财务经理手机</th>
					<!-- 
					<th data-options="field:'HYDM',width:100,formatter:msgformat" editor='text'>所属行业</th>
					<th data-options="field:'SBFS',width:100,formatter:msgformat" editor='text'>申报方式</th>
					<th data-options="field:'DJZT',width:100,formatter:msgformat" editor='text'>登记状态</th>
					<th data-options="field:'DJLX',width:100,formatter:msgformat" editor='text'>登记类型</th>
					<th data-options="field:'ZCLX',width:100,formatter:msgformat" editor='text'>注册类型</th>
					 -->
		        </tr>  
		    </thead>  
		</table>
	</div>
</div>
</div>
<div id="see_dialog"></div>
</form>
</body>
</html>
