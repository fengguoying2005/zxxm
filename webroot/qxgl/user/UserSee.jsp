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
<title>查看操作员</title>

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
                frm.action="<%=basePath%>qxgl/user_" + event + ".html";
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
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看操作员
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="USER_DM"> 用户编码 </label></td>
		<td><s:textfield cssClass="text" id="USER_DM" name="user.USER_DM"
			readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="USER_MC"> 用户名称 </label></td>
		<td><s:textfield cssClass="text" id="USER_MC" name="user.USER_MC"
			readonly="true"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="PASSWORD"> 密码 </label></td>
		<td><s:password cssClass="text" id="PASSWORD"
			name="user.PASSWORD" readonly="true"></s:password></td>
		<td class="tdtitle"><label for="XB_DM"> 性别 </label></td>
		<td><s:select disabled="true" id="XB_DM" name="user.XB_DM"
			list="xbmap" headerKey="" headerValue="--选择--"
			cssStyle="width:210px;"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="ORG_DM_JG"> 机关 </label></td>
		<td><s:select disabled="true" id="ORG_DM_JG"
			name="user.ORG_DM_JG" list="orgjgmap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
		<td class="tdtitle"><label for="ORG_DM_BM"> 部门 </label></td>
		<td><s:select disabled="true" id="ORG_DM_BM"
			name="user.ORG_DM_BM" list="orgbmmap" headerKey=""
			headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="SFZHM"> 身份证号 </label></td>
		<td><s:textfield cssClass="text" id="SFZHM" name="user.SFZHM"
			readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="TEL"> 电话 </label></td>
		<td><s:textfield cssClass="text" id="TEL" name="user.TEL"
			readonly="true"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="JG"> 籍贯 </label></td>
		<td><s:textfield cssClass="text" id="JG" name="user.JG"
			readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="YZBM"> 邮政编码 </label></td>
		<td><s:textfield cssClass="text" id="YZBM" name="user.YZBM"
			readonly="true"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="EMAIL"> EMAIL </label></td>
		<td><s:textfield cssClass="text" id="EMAIL" name="user.EMAIL"
			readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="ADDR"> 地址 </label></td>
		<td><s:textfield cssClass="text" id="ADDR" name="user.ADDR"
			readonly="true"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle">
            <label for="ZW_DM">
                	职务
            </label>
		</td>
		<td>
            <s:select id="ZW_DM" disabled="true" name="user.ZW_DM" list="zwmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="false" requiredsLength="0,20" labels="职务" ></s:select>
		</td>
		<td class="tdtitle">
            <label for="JGPX">
                	机构排序
            </label>
		</td>
		<td>
            <s:textfield cssClass="text" id="JGPX" name="user.JGPX"
			requireds="false" requiredsLength="0,20" labels="机构排序" readonly="true"></s:textfield>
		</td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="BZ"> 备注 </label></td>
		<td><s:textfield cssClass="text" id="BZ" name="user.BZ"
			readonly="true"></s:textfield></td>
		<td class="tdtitle"><label for="YX_BJ"> 有效标记 </label></td>
		<td><s:checkboxlist disabled="true" id="YX_BJ" name="user.YX_BJ"
			list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist></td>
		</div>
	</tr>
	</div>
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
