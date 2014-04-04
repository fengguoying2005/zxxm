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
<title>修改短信参数</title>

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
            $(function() {
            });
            function next(event) {
                var isOk = true;
                if(event=="mod") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>xtgl/dxcs_"+event+".html";
                    frm.submit();
                }
            }
        </script>
</head>
<body onload="tablecloth('TABLEMX_1');initReadOnly();">
<form id="myform" name="myform" action="" method="post"><s:hidden
	id="YYS_DM" name="YYS_DM"></s:hidden>
<div class="container" id="mainframe">
<div class="span-24"><s:actionerror id="sysactionerror" />
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;修改短信参数
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<s:iterator value="dxcss" status="stat">
		<tr>
			<td class="tdtitle"><label> 运营商 </label><s:hidden name="%{'dxcss['+#stat.index+'].YYS_DM'}"></s:hidden></td>
			<td><s:textfield readonly="true" cssStyle="width:100px" cssClass="text"
				name="%{'dxcss['+#stat.index+'].YYS_MC'}" requireds="true"
				requiredsLength="1,20" labels="运营商"></s:textfield></td>
			<td class="tdtitle"><label> 短信价格 </label></td>
			<td><s:textfield cssStyle="width:70px" cssClass="text"
				name="%{'dxcss['+#stat.index+'].DXJG'}" requireds="true" requiredsType="decimal"
				requiredsLength="1,20" labels="短信价格"></s:textfield>（元）</td>
			<td class="tdtitle"><label> 备注 </label></td>
			<td><s:textfield cssStyle="width:120px" cssClass="text"
				name="%{'dxcss['+#stat.index+'].BZ'}" requireds="true"
				requiredsLength="1,20" labels="备注"></s:textfield></td>
		</tr>
	</s:iterator>
</table>
</div>
<div align="center"><br>
<input type="button" value="保存" onClick="next('mod')" class="buttonface" />
</div>
</div>
</div>
</form>
</body>
</html>
<%
	if (request.getAttribute("message") != null
			&& !"".equals(request.getAttribute("message"))) {
%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");
</script>
<%
	}
%>