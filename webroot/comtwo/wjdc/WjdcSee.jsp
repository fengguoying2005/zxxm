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
<title>查看问卷调查</title>

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
                frm.action="<%=basePath%>comtwo/wjdc_" + event + ".html";
		frm.submit();
	}
	function initReadOnly() {
		$("[readonly='readonly']").each(function(i) {
			$(this).css("background-color", "#D4D0C8");
		});
		queryList();
	}

	function queryList() {
    	var turl = "<%=basePath%>"+'comtwo/wjdc!listmx.action';
    	$('#datagrid').datagrid({
    		title:'已选择的参与问卷纳税人',
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
	}
	function msgformat(val,row) {
		if(val) {
    		return "<span title='"+val+"'>"+val+"</span>";
		} else {
			return "";
		}
	}
</script>
</head>
<body onload="initReadOnly();">
	<form id="myform" name="myform" action="" method="post">
		<s:hidden id="LSH" name="LSH"></s:hidden>
		<div class="container" id="mainframe">
			<div class="span-24">
				<s:actionerror id="sysactionerror" />
				<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;&nbsp;&nbsp;查看问卷调查
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="INFO"> 问卷内容 </label></td>
							<td><s:textarea cssClass="text" id="INFO" name="wjdc.INFO" rows="5" cols="30" cssStyle="width:300px;height:150px"
                                requireds="false" requiredsLength="1,200" labels="问卷内容" readonly="true"></s:textarea>
							</td>
							<td class="tdtitle"><label for="DCJL"> 调查结论 </label></td>
							<td><s:textarea cssClass="text" id="DCJL" name="wjdc.DCJL" rows="5" cols="30" cssStyle="width:300px;height:150px"
                                requireds="false" requiredsLength="0,200" labels="调查结论" readonly="true"></s:textarea>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="BZ"> 特征码 </label></td>
							<td><s:textfield cssClass="text" id="BZ" name="wjdc.BZ"
									readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="SFJS"> 是否结束 </label></td>
							<td>
								<s:select cssStyle="width:210px;" id="SFJS" name="wjdc.SFJS" list="#{'1':'已结束','0':'已录入','2':'已发送短信'}" disabled="true"></s:select>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="LRRY_DM"> 录入人员 </label></td>
							<td><s:textfield cssClass="text" id="LRRY_DM"
									name="wjdc.LRRY_DM" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="XGRY_DM"> 修改人员 </label></td>
							<td><s:textfield cssClass="text" id="XGRY_DM"
									name="wjdc.XGRY_DM" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="LR_SJ"> 录入时间 </label></td>
							<td><s:textfield cssClass="text" id="LR_SJ"
									name="wjdc.LRSJ" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="XG_SJ"> 修改时间 </label></td>
							<td><s:textfield cssClass="text" id="XG_SJ"
									name="wjdc.XGSJ" readonly="true"></s:textfield></td>
							</div>
						</tr>
						</div>
					</table>
				</div>
				<div align="center">
					<br>
					<input type="button" value="返回" onClick="next('list')"
						class="buttonface" />
				</div>
					<br>
				<div style="height: 420px">
						<div id="tb">
							<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="seemx()">查看</a> -->
						</div>
						<table id="datagrid" title='纳税人明细信息' iconCls='icon-save'
							fitColumns=true singleSelect=false fit=true toolbar="#tb">
							<thead>
								<tr>
									<th field="LSH" checkbox="true"></th>
									<th data-options="field:'NSRBM',width:100,formatter:msgformat"
										editor='text'>纳税人编码</th>
									<th data-options="field:'NSRMC',width:150,formatter:msgformat"
										editor='text'>纳税人名称</th>
									<th
										data-options="field:'PHONETYPE',width:70,formatter:msgformat"
										editor='text'>手机类型</th>
									<th data-options="field:'SJHM',width:100,formatter:msgformat"
										editor='text'>手机号码</th>
									<th data-options="field:'SMSINFO',width:100,formatter:msgformat"
										editor='text'>短信反馈内容</th>
									<th data-options="field:'FK_SJ',width:100,formatter:msgformat"
										editor='text'>反馈时间</th>
								</tr>
							</thead>
						</table>
					</div>
			</div>
		</div>
	</form>
</body>
</html>
