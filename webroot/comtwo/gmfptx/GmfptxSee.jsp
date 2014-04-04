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
<title>查看购买发票提醒</title>

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
        frm.action="<%=basePath%>comtwo/gmfptx_" + event + ".html";
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
					&nbsp;&nbsp;查看购买发票提醒
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="SWJGBM"> 税务机关 </label></td>
							<td><s:select disabled="true" id="SWJGBM"
									name="gmfptx.SWJGBM" list="orgmap" headerKey=""
									headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
							<td class="tdtitle"><label for="FP_RQ"> 发票日期 </label></td>
							<td><s:textfield cssClass="text" id="TY_RQ"
									name="gmfptx.TY_RQ" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="NSRSBM"> 纳税人编码 </label></td>
							<td><s:textfield cssClass="text" id="NSRSBM"
									name="gmfptx.NSRSBM" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="NSRMC"> 纳税人名称 </label></td>
							<td><s:textfield cssClass="text" id="NSRMC"
									name="gmfptx.NSRMC" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td rowspan="7" class="tdtitle"><label for="SMSINFO"> 短信内容 </label></td>
							<td rowspan="7">
								<s:textarea cssClass="text" id="SMSINFO" name="gmfptx.SMSINFO" rows="5" cols="30" cssStyle="width:300px;height:150px"
                                 readonly="true"></s:textarea>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="SJLX"> 手机类型 </label></td>
							<td><s:select disabled="true" id="SJLX" name="gmfptx.SJLX"
									list="jsrlxmap" headerKey="" headerValue="--选择--"
									cssStyle="width:210px;"></s:select></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="SJHM"> 手机号码 </label></td>
							<td><s:textfield cssClass="text" id="SJHM"
									name="gmfptx.SJHM" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="BZ"> 备注 </label></td>
							<td><s:textfield cssClass="text" id="BZ" name="gmfptx.BZ"
									readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FSZT_DM"> 发送状态 </label></td>
							<td><s:textfield cssClass="text" id="FSZT_DM"
									name="gmfptx.FSZT_DM" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="LR_SJ"> 数据接收时间 </label></td>
							<td><s:textfield cssClass="text" id="LR_SJ"
									name="gmfptx.LR_SJ" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FS_SJ"> 发送时间 </label></td>
							<td><s:textfield cssClass="text" id="FS_SJ"
									name="gmfptx.FS_SJ" readonly="true"></s:textfield></td>
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
