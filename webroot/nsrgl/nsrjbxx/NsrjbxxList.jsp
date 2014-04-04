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
<title>纳税人列表</title>
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
                      $("#LSH").val($(":radio:checked").val());
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
                document.forms[0].action="<%=basePath%>nsrgl/nsrjbxx_"+event+".html";
                document.forms[0].submit();
            }
            function clearfiter() {
            	$("#FIND_NSRBM").val("");
            	$("#FIND_NSRMC").val("");
            	$("#FIND_HYDM").val("");
            	$("#FIND_ORG_DM").val("");
            	$("#FIND_SSZGY").val("");
            	$("#FIND_SBFS").val("");
            	$("#FIND_DJLX").val("");
            	$("#FIND_ZCLX").val("");
            }
        </script>
</head>
<body onload="tablecloth('nsrjbxxList');">
<form id="myform" name="myform" action="nsrjbxx" method="post">
<div class="container" id="mainframe"><s:hidden id="LSH"
	name="LSH"></s:hidden>
<div class="span-24">

<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="FIND_NSRBM"> 纳税人编码 </label></td>
		<td><s:textfield cssClass="text" id="FIND_NSRBM"
			name="FIND_NSRBM"></s:textfield></td>
		<td class="tdtitle"><label for="FIND_NSRMC"> 纳税人名称 </label></td>
		<td><s:textfield cssClass="text" id="FIND_NSRMC"
			name="FIND_NSRMC"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_HYDM"> 所属行业 </label></td>
		<td><s:select id="FIND_HYDM" name="FIND_HYDM" list="hymap"
			headerKey="" headerValue="--选择--" cssStyle="width:210px;"></s:select>
		</td>
		<td class="tdtitle"><label for="FIND_ORG_DM"> 主管税务机关 </label></td>
		<td><s:select id="FIND_ORG_DM" name="FIND_ORG_DM" list="orgmap"
			headerKey="" headerValue="--选择--" cssStyle="width:210px;"></s:select>
		</td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_SSZGY"> 税收专管员 </label></td>
		<td><s:textfield cssClass="text" id="FIND_SSZGY"
			name="FIND_SSZGY"></s:textfield></td>
		<td class="tdtitle"><label for="FIND_SBFS"> 申报方式 </label></td>
		<td><s:select id="FIND_SBFS" name="FIND_SBFS" list="sbfsmap"
			headerKey="" headerValue="--选择--" cssStyle="width:210px;"></s:select>
		</td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_DJLX"> 登记类型 </label></td>
		<td><s:select id="FIND_DJLX" name="FIND_DJLX" list="djlxmap"
			headerKey="" headerValue="--选择--" cssStyle="width:210px;"></s:select>
		</td>
		<td class="tdtitle"><label for="FIND_ZCLX"> 注册类型 </label></td>
		<td><s:select id="FIND_ZCLX" name="FIND_ZCLX" list="zclxmap"
			headerKey="" headerValue="--选择--" cssStyle="width:210px;"></s:select>
		</td>
	</tr>
</table>
</div>
<div style="margin-top: 10px;" align="right">
<input class="buttonface" type="button" value="查询" onclick="next('list')"></input>
<input class="buttonface" type="button" value="清空条件" onclick="clearfiter()"></input>
</div>
<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;纳税人列表
</ul>
<table class="record-list" id="nsrjbxxList" name="nsrjbxxList">
	<thead>
		<tr>
			<th>选择</th>
			<th>行</th>
			<th>纳税人编码</th>
			<th>纳税人名称</th>
			<th>所属行业</th>
			<th>主管税务机关</th>
			<th>税收专管员</th>
			<th>申报方式</th>
			<th>登记类型</th>
			<th>注册类型</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="10">
			<div align="left"><gwintag:page pageBeanName="pageBean"
				includes="FIND_NSRBM,FIND_NSRMC,FIND_HYDM,FIND_ORG_DM,FIND_SSZGY,FIND_SBFS,FIND_DJLX,FIND_ZCLX"
				styleClass="aStyle" theme="number" /></div>
			<div align="right"><input class="buttonface" type="button"
				value="增加" onClick="next('toAdd')" /> <input class="buttonface"
				type="button" value="删除" onClick="next('del')" /> <input
				class="buttonface" type="button" value="修改" onClick="next('toMod')" />
			<input class="buttonface" type="button" value="查看"
				onClick="next('see')" /> <input class="buttonface" type="button"
				value="导出" onClick="next('printExcel')" /></div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<s:iterator value="nsrjbxxs" status="stat">
			<tr
				class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
				<td><INPUT type="radio" name='radio'
					value='<s:property value="LSH" />' /></td>
				<td><s:property value="#stat.index+1" /></td>
				<td><s:property value="NSRBM" /></td>
				<td><s:property value="NSRMC" /></td>
				<td><s:property value="HYDM" /></td>
				<td><s:property value="ORG_DM" /></td>
				<td><s:property value="SSZGY" /></td>
				<td><s:property value="SBFS" /></td>
				<td><s:property value="DJLX" /></td>
				<td><s:property value="ZCLX" /></td>
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
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script>
<%
	}
%>