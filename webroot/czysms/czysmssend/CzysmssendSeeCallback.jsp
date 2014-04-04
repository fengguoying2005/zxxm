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
<title>查看短信发送</title>

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
<script type="text/javascript">
    function next(event) {
        var frm = document.myform;
        frm.action="<%=basePath%>czysms/czysmssend_" + event + ".html";
		frm.submit();
	}
	function initReadOnly() {
		$("[readonly='readonly']").each(function(i) {
			$(this).css("background-color", "#D4D0C8");
		});
	}
</script>
</head>
<body onload="initReadOnly();">
<form id="myform" name="myform" action="" method="post">
<div class="container" id="mainframe">
<div class="span-24"><s:actionerror id="sysactionerror" />
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;查看短信回复
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="SMSTYPE_DM"> 短信类型 </label></td>
		<td><s:select disabled="true" id="SMSTYPE_DM"
			name="orgsmssend.SMSTYPE_DM" list="smstypemap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
		<td class="tdtitle"><label for="GROUP_NAME"> 短信标题 </label></td>
		<td><s:textfield cssClass="text" id="GROUP_NAME"
			name="orgsmssend.GROUP_NAME" readonly="true"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="ORG_DM_JG"> 税务机关 </label></td>
		<td><s:select disabled="true" id="ORG_DM_JG"
			name="orgsmssend.ORG_DM_JG" list="orgmap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
		<td class="tdtitle"><label for="SMSZT_DM"> 短信状态 </label></td>
		<td><s:select disabled="true" id="SMSZT_DM"
			name="orgsmssend.SMSZT_DM" list="smsztmap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
		</div>
	</tr>
	</div>
</table>
</div>
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人员列表
</ul>
<div class="tablediv2"><!-- mx -->
<table class="record-list" id="TABLEMX_1" name="TABLEMX_1">
	<thead>
		<tr>
			<th>序号</th>
			<th>机构</th>
			<th>部门</th>
			<th>人员</th>
			<th>手机号码</th>
			<th>短信</th>
			<th>发送时间</th>
			<th>回复短信</th>
			<th>回复时间</th>
		</tr>
	</thead>
	<tbody id="tbody">
		<s:iterator value="tablemx" status="stat">
			<tr
				class='<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>'>
				<td><s:text name="#stat.index+1"></s:text></td>
				<td><s:text name="%{'tablemx['+#stat.index+'].ORG_MC_JG'}"></s:text>
				</td>
				<td><s:text name="%{'tablemx['+#stat.index+'].ORG_MC_BM'}"></s:text>
				</td>
				<td><s:text name="%{'tablemx['+#stat.index+'].USER_MC'}"></s:text>
				</td>
				<td><s:text name="%{'tablemx['+#stat.index+'].SJHM'}"></s:text>
				</td>
				<td><s:text name="%{'tablemx['+#stat.index+'].MSG'}"></s:text>
				</td>
				<td><s:text name="%{'tablemx['+#stat.index+'].FS_SJ'}"></s:text></td>
				<td><s:text name="%{'tablemx['+#stat.index+'].MSG2'}"></s:text></td>
				<td><s:text name="%{'tablemx['+#stat.index+'].RECEIVETIME'}"></s:text></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
<div align="center"><br>
<input type="button" value="返回" onClick="next('list')"
	class="buttonface" /></div>
</div>
</div>
</form>
</body>
</html>
