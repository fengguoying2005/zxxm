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
<title>短信统计列表</title>
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
        <SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
        
<script type="text/javascript">
			$(function() {
			    $('#FIND_KS_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
			    $('#FIND_KS_RQ').css("width","185px");   
			    $('#FIND_JZ_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
			    $('#FIND_JZ_RQ').css("width","185px");   
			});
            function next(event) {
            	if(event!='printExcel') {
            		var isOk = $().requireds();
            		if(!isOk) {
            			return false;
            		}
            		var FIND_KS_RQ = $('#FIND_KS_RQ').val();
            		var FIND_JZ_RQ = $('#FIND_JZ_RQ').val();
            		if(FIND_KS_RQ.length==10 && FIND_JZ_RQ.length==10 && FIND_KS_RQ.substr(0,7)==FIND_JZ_RQ.substr(0,7)) {
            			if(FIND_KS_RQ.substr(0,7)<'2013-07') {
                			alert("7月之前的短信不提供统计。");
                			return false;
            			}
            		} else {
            			alert("查询的日期不能跨月");
            			return false;
            		}
                	setDisabledBtn(true, ['queryBtn']);
            	}
                document.forms[0].action="<%=basePath%>tjfx/dxtj_"+event+".html";
                document.forms[0].submit();
            }
        </script>
</head>
<body onload="tablecloth('dxtjList');">
<form id="myform" name="myform" action="dxtj" method="post">
<div class="container" id="mainframe"><s:hidden id="" name=""></s:hidden>
<div class="span-24">

<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="FIND_KS_RQ"> 开始日期 </label></td>
		<td><s:textfield cssClass="text" id="FIND_KS_RQ" requireds="true" labels="开始日期"
			name="FIND_KS_RQ"></s:textfield></td>
		<td class="tdtitle"><label for="FIND_JZ_RQ"> 截止日期 </label></td>
		<td><s:textfield cssClass="text" id="FIND_JZ_RQ" requireds="true" labels="截止日期"
			name="FIND_JZ_RQ"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_YYS_DM"> 运营商 </label></td>
		<td><s:select id="FIND_YYS_DM" name="FIND_YYS_DM"
			list="yys_dmmap" headerKey="" headerValue="--选择--"
			cssStyle="width:210px;"></s:select></td>
		<td class="tdtitle"><label for="FIND_ORG_DM"> 税务机关 </label></td>
		<td><s:select id="FIND_ORG_DM" name="FIND_ORG_DM"
			list="org_dmmap" headerKey="" headerValue="--选择--"
			cssStyle="width:210px;"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_SMSTYPE_DM"> 短信类型 </label></td>
		<td><s:select id="FIND_SMSTYPE_DM" name="FIND_SMSTYPE_DM"
			list="smstype_dmmap" headerKey="" headerValue="--选择--"
			cssStyle="width:210px;"></s:select></td>
		<td class="tdtitle"><label> 分组显示 </label></td>
		<td>
			<s:checkboxlist id="YX_BJ" name="FIND_YYS" list="#{1:'运营商'}" cssStyle="width:260px;" ></s:checkboxlist>
			<s:checkboxlist id="YX_BJ" name="FIND_ORG" list="#{2:'税务机关'}" cssStyle="width:260px;" ></s:checkboxlist>
			<s:checkboxlist id="YX_BJ" name="FIND_SMSTYPE" list="#{3:'短信类型'}" cssStyle="width:260px;" ></s:checkboxlist>
		</td>
	</tr>
</table>
</div>
<div style="margin-top: 10px;" align="right"><input id="queryBtn"
	class="buttonface" type="button" value="查询" onclick="next('list')"></input></div>
<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;短信统计列表
</ul>
<table class="record-list" id="dxtjList" name="dxtjList">
	<thead>
		<tr>
			<th>行</th>
			<s:if test="FIND_ORG==2">
				<th>税务机关</th>
			</s:if>
			<s:if test="FIND_YYS==1">
				<th>运营商</th>
			</s:if>
			<s:if test="FIND_SMSTYPE==3">
				<th>短信类型</th>
			</s:if>
			<th>短信数量</th>
			<th>短信金额</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="9">
			<div align="right"><input class="buttonface" type="button" value="导出" onClick="next('printExcel')" /></div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<s:iterator value="dxtjs" status="stat">
			<tr class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
				<td><s:property value="#stat.index+1" /></td>
				
			<s:if test="FIND_ORG==2">
				<td><s:property value="ORG_DM" /></td>
			</s:if>
			<s:if test="FIND_YYS==1">
				<td><s:property value="YYS_DM" /></td>
			</s:if>
			<s:if test="FIND_SMSTYPE==3">
				<td><s:property value="SMSTYPE_DM" /></td>
			</s:if>
				<td><s:property value="DXSL" /></td>
				<td><s:property value="DXJE" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
</div>
</form>
</body>
</html>
<%
	if (request.getAttribute("message") != null
			&& !"".equals(request.getAttribute("message"))) {
%><script
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>");
	setDisabledBtn(false, ['queryBtn']);
	</script>
<%
	}
%>