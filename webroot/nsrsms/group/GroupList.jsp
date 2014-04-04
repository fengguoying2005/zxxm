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
<title>纳税人群组列表</title>
<link rel="stylesheet" href="resource/css/layout.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/framecommon.css"
	type="text/css" />
<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
<style type="text/css">
@import "resource/css/jquery.datepick.css";
</style>
<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type=text/javascript src="js/common.js"></script>
<script type=text/javascript src="cssmenu2/menu.js"></script>
<script type="text/javascript">
            function next(event) {
                var ifSelected = false;
                if("toAdd"!=event && "list"!=event && "printExcel"!=event &&"printWord"!=event) {
                    var item = $(":radio:checked");
                    var len=item.length;
                    if(len>0){
                      $("#NSRDATA_LSH").val($(":radio:checked").val());
                      if(event=="del" && "初始"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()) {
                    	  alert("只有‘初始’状态才能删除。");
                    	  return;
                      } else if(event=="toMod" && "初始"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()) {
                    	  alert("只有‘初始’状态才能修改。");
                    	  return;
                      }
                    } else {
                        alert("请选择一条！");
                        return false;
                    }
                }
                if("del"==event) {
                    if(!window.confirm("确定删除吗？")) {
                        return false;
                    }
                }
                document.forms[0].action="<%=basePath%>nsrsms/group_"+event+".html";
                document.forms[0].submit();
            }
			$(function() {
			    $('#FIND_KS_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
			    $('#FIND_KS_RQ').css("width","185px");   
			    $('#FIND_JZ_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
			    $('#FIND_JZ_RQ').css("width","185px");   
			});
        </script>
</head>
<body onload="tablecloth('groupList');">
<form id="myform" name="myform" action="group" method="post">
<div class="container" id="mainframe"><s:hidden id="NSRDATA_LSH"
	name="NSRDATA_LSH"></s:hidden>
<div class="span-24">

<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="FIND_SMSTYPE_DM"> 短信类型 </label></td>
		<td><s:select id="FIND_SMSTYPE_DM" name="FIND_SMSTYPE_DM"
			list="smstypemap" headerKey="" headerValue="--选择--"
			cssStyle="width:210px;"></s:select></td>
		<td class="tdtitle"><label for="FIND_GROUP_NAME"> 短信标题 </label></td>
		<td><s:textfield cssClass="text" id="FIND_GROUP_NAME"
			name="FIND_GROUP_NAME"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_KS_RQ"> 开始日期 </label></td>
		<td><s:textfield cssClass="text" id="FIND_KS_RQ"
			name="FIND_KS_RQ"></s:textfield></td>
		<td class="tdtitle"><label for="FIND_JZ_RQ"> 截止日期 </label></td>
		<td><s:textfield cssClass="text" id="FIND_JZ_RQ"
			name="FIND_JZ_RQ"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_SMSZT_DM"> 短信状态 </label></td>
		<td><s:select id="FIND_SMSZT_DM" name="FIND_SMSZT_DM"
			list="smsztmap" headerKey="" headerValue="--选择--"
			cssStyle="width:210px;"></s:select></td>
	</tr>
</table>
</div>
<div style="margin-top: 10px;" align="right"><input
	class="buttonface" type="button" value="查询" onclick="next('list')"></input></div>
<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;纳税人群组列表
</ul>
<table class="record-list" id="groupList" name="groupList">
	<thead>
		<tr>
			<th>选择</th>
			<th>行</th>
			<th>短信类型</th>
			<th>短信标题</th>
			<th>税务机关</th>
			<th>短信状态</th>
			<th>日期</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="7">
			<div align="left"><gwintag:page pageBeanName="pageBean"
				includes="FIND_SMSTYPE_DM,FIND_GROUP_NAME,FIND_SMSZT_DM,FIND_KS_RQ,FIND_JZ_RQ"
				styleClass="aStyle" theme="number" /></div>
			<div align="right"><input class="buttonface" type="button"
				value="删除" onClick="next('del')" /> <input class="buttonface"
				type="button" value="修改" onClick="next('toMod')" /> <input
				class="buttonface" type="button" value="查看" onClick="next('see')" />
			<input class="buttonface" type="button" value="导出"
				onClick="next('printExcel')" /></div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<s:iterator value="groups" status="stat">
			<tr
				class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
				<td><INPUT type="radio" name='radio'
					value='<s:property value="NSRDATA_LSH" />' /></td>
				<td><s:property value="#stat.index+1" /></td>
				<td><s:property value="SMSTYPE_DM" /></td>
				<td><s:property value="GROUP_NAME" /></td>
				<td><s:property value="ORG_DM_JG" /></td>
				<td><s:property value="SMSZT_DM" /></td>
				<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{LR_SJ})}" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
</div>
</form>
</body>
</html>
