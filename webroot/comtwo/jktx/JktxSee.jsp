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
<title>查看缴款提醒</title>

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
        frm.action="<%=basePath%>comtwo/jktx_" + event + ".html";
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
					&nbsp;&nbsp;&nbsp;&nbsp;查看缴款提醒
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="SWJGBM"> 税务机关 </label></td>
							<td><s:select disabled="true" id="SWJGBM" name="jktx.SWJGBM"
									list="orgmap" headerKey="" headerValue="--选择--"
									cssStyle="width:210px;"></s:select></td>
							<td class="tdtitle"><label for="SF_JE"> 税费金额 </label></td>
							<td><s:textfield cssClass="text" id="SF_JE"
									name="jktx.SF_JE" readonly="true"></s:textfield></td>
							</div>
						</tr>
						<tr>
							<td class="tdtitle"><label for="NSRSBM"> 纳税人编码 </label></td>
							<td><s:textfield cssClass="text" id="NSRSBM"
									name="jktx.NSRSBM" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="NSRMC"> 纳税人名称 </label></td>
							<td><s:textfield cssClass="text" id="NSRMC"
									name="jktx.NSRMC" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="SJLX"> 手机类型 </label></td>
							<td><s:select disabled="true" id="SJLX" name="jktx.SJLX"
									list="jsrlxmap" headerKey="" headerValue="--选择--"
									cssStyle="width:210px;"></s:select></td>
							<td class="tdtitle"><label for="SJHM"> 手机号码 </label></td>
							<td><s:textfield cssClass="text" id="SJHM" name="jktx.SJHM"
									readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="SKSSQ"> 税款所属起 </label></td>
							<td><s:textfield cssClass="text" id="SKSSQ"
									name="jktx.SKSSQ" readonly="true"></s:textfield></td>
							<td class="tdtitle"><label for="SKSSZ"> 税款所属止 </label></td>
							<td><s:textfield cssClass="text" id="SKSSZ"
									name="jktx.SKSSZ" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle" rowspan="10"><label for="SMSINFO"> 短信内容 </label></td>
							<td rowspan="10">
								<s:textarea cssClass="text" id="SMSINFO" name="jktx.SMSINFO" rows="5" cols="30" cssStyle="width:300px;height:150px"
                                 readonly="true"></s:textarea>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="SBQX"> 申报期限 </label></td>
							<td><s:textfield cssClass="text" id="SBQX" name="jktx.SBQX"
									readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="BZ"> 备注 </label></td>
							<td><s:textfield cssClass="text" id="BZ" name="jktx.BZ"
									readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FSZT_DM"> 发送状态 </label></td>
							<td><s:textfield cssClass="text" id="FSZT_DM"
									name="jktx.FSZT_DM" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="LR_SJ"> 数据接收时间 </label></td>
							<td><s:textfield cssClass="text" id="LR_SJ"
									name="jktx.LR_SJ" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FS_SJ"> 发送时间 </label></td>
							<td><s:textfield cssClass="text" id="FS_SJ"
									name="jktx.FS_SJ" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="XTSPHM"> 系统税票号码 </label></td>
							<td><s:textfield cssClass="text" id="XTSPHM"
									name="jktx.XTSPHM" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="JKLX_DM"> 缴款类型 </label></td>
							<td><s:select disabled="true" id="JKLX_DM"
									name="jktx.JKLX_DM" list="jklxmap" headerKey=""
									headerValue="--选择--" cssStyle="width:210px;"></s:select></td>
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
