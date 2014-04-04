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
<title>查看协议定制</title>

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
                frm.action="<%=basePath%>comtwo/xydz_" + event + ".html";
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
			<div class="span-24">
				<s:actionerror id="sysactionerror" />
				<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;&nbsp;&nbsp;查看协议定制
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="NSRBM"> 纳税人编码 </label></td>
							<td><s:textfield cssClass="text" id="NSRBM"
									name="xydz.NSRBM" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="NSRMC"> 纳税人名称 </label></td>
							<td><s:textfield cssClass="text" id="NSRMC"
									name="xydz.NSRMC" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="CBTX"> 催报提醒 </label></td>
							<td><s:checkboxlist disabled="true" id="CBTX"
									name="xydz.CBTX" list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist>
							</td>
							<td class="tdtitle"><label for="CJTX"> 催缴提醒 </label></td>
							<td><s:checkboxlist disabled="true" id="CJTX"
									name="xydz.CJTX" list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="JKTX"> 缴款成功提醒 </label></td>
							<td><s:checkboxlist disabled="true" id="JKTX"
									name="xydz.JKTX" list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist>
							</td>
							<td class="tdtitle"><label for="JKSBTX"> 缴款失败提醒 </label></td>
							<td><s:checkboxlist disabled="true" id="JKSBTX"
									name="xydz.JKSBTX" list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="GMFPTX"> 购买发票提醒 </label></td>
							<td><s:checkboxlist disabled="true" id="GMFPTX"
									name="xydz.GMFPTX" list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist>
							</td>
							<td class="tdtitle"><label for="TYDQTX"> 停业到期提醒 </label></td>
							<td><s:checkboxlist disabled="true" id="TYDQTX"
									name="xydz.TYDQTX" list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="KTRQQ"> 开通日期起 </label></td>
							<td><s:textfield cssClass="text" id="KTRQQ"
									name="xydz.KTRQQ" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="KTRQZ"> 开通日期止 </label></td>
							<td><s:textfield cssClass="text" id="KTRQZ"
									name="xydz.KTRQZ" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="BZ"> 备注 </label></td>
							<td><s:textfield cssClass="text" id="BZ" name="xydz.BZ"
									readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="LRRY_DM"> 录入人员 </label></td>
							<td><s:textfield cssClass="text" id="LRRY_DM"
									name="xydz.LRRY_DM" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="XGRY_DM"> 修改人员 </label></td>
							<td><s:textfield cssClass="text" id="XGRY_DM"
									name="xydz.XGRY_DM" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="LR_SJ"> 录入时间 </label></td>
							<td><s:textfield cssClass="text" id="LR_SJ"
									name="xydz.LR_SJ" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="XG_SJ"> 修改时间 </label></td>
							<td><s:textfield cssClass="text" id="XG_SJ"
									name="xydz.XG_SJ" readonly="true"></s:textfield></td>
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
			</div>
		</div>
	</form>
</body>
</html>
