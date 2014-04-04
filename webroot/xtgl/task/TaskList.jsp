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
<title>定时任务列表</title>
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
            	if("toAdd"==event && $(":radio").length>=1) {
            		alert("执行任务只允许存在一条！");
                    return false;
            	}
                var ifSelected = false;
                if("toAdd"!=event && "list"!=event && "printExcel"!=event &&"printWord"!=event) {
                    var item = $(":radio:checked");
                    var len=item.length;
                    if(len>0){
                      $("#LSH").val($(":radio:checked").val());
                      $("#TYPE_DM2").val(item.parent().find("input[name='radioTYPE_DM2']").val());
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
                document.forms[0].action="<%=basePath%>xtgl/task_"+event+".html";
                document.forms[0].submit();
            }
        </script>
</head>
<body onload="tablecloth('taskList');">
<form id="myform" name="myform" action="task" method="post">
<div class="container" id="mainframe">
	<s:hidden id="LSH" name="LSH"></s:hidden>
	<s:hidden id="TYPE_DM2" name="TYPE_DM2"></s:hidden>
<div class="span-24">

</tr>
</table>
</div>
<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;定时任务列表
</ul>
<table class="record-list" id="taskList" name="taskList">
	<thead>
		<tr>
			<th>选择</th>
			<th>行</th>
			<th>任务名称</th>
			<th>时间表达式</th>
			<th>备注</th>
			<th>上次发生时间</th>
			<th>任务类型</th>
			<th>有效标记</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="8">
			<div align="left"><gwintag:page pageBeanName="pageBean"
				includes="" styleClass="aStyle" theme="number" /></div>
			<div align="right">
			<!-- 
			<input class="buttonface" type="button" value="增加" onClick="next('toAdd')" />
			<input class="buttonface" type="button" value="删除" onClick="next('del')" />
			 -->
			<input class="buttonface" type="button" value="修改" onClick="next('toMod')" />
			<input class="buttonface" type="button" value="查看" onClick="next('see')" />
			<!-- 
			<input class="buttonface" type="button" value="导出" onClick="next('printExcel')" />
			 -->
			</div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<s:iterator value="tasks" status="stat">
			<tr class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
				<td width="5%"><INPUT type="radio" name='radio'
					value='<s:property value="TYPE_DM" />' /><input type="hidden"  name="radioTYPE_DM2" value="<s:property value="TYPE_DM2" />"/></td>
				<td width="5%"><s:property value="#stat.index+1" /></td>
				<s:if test="%{YX_BJ=='有效'}">
					<td width="15%"><font color="green"><s:property value="TYPE_DM" /></font></td>
					<td width="15%"><font color="green"><s:property value="CRON" /></font></td>
					<td width="25%"><font color="green"><s:property value="BZ" /></font></td>
					<td width="15%"><font color="green"><s:property value="SCFS_SJ" /></font></td>
					<td width="10%"><font color="green"><s:if test="%{TYPE_DM2=='FS'}">发送任务</s:if><s:else>接收任务</s:else></font></td>
					<td width="10%"><font color="green"><s:property value="YX_BJ" /></font></td>
				</s:if>
				<s:elseif test="%{YX_BJ=='无效'}">
					<td width="15%"><font color="red"><s:property value="TYPE_DM" /></font></td>
					<td width="15%"><font color="red"><s:property value="CRON" /></font></td>
					<td width="25%"><font color="red"><s:property value="BZ" /></font></td>
					<td width="15%"><font color="red"><s:property value="SCFS_SJ" /></font></td>
					<td width="10%"><font color="red"><s:if test="%{TYPE_DM2=='FS'}">发送任务</s:if><s:else>接收任务</s:else></font></td>
					<td width="10%"><font color="red"><s:property value="YX_BJ" /></font></td>
				</s:elseif>
				<s:else>
					<td width="15%"><font color="red"><s:property value="TYPE_DM" /></font></td>
					<td width="15%"><font color="red"><s:property value="CRON" /></font></td>
					<td width="25%"><font color="red"><s:property value="BZ" /></font></td>
					<td width="15%"><font color="red"><s:property value="SCFS_SJ" /></font></td>
					<td width="10%"><font color="red"><s:if test="%{TYPE_DM2=='FS'}">发送任务</s:if><s:else>接收任务</s:else></font></td>
					<td width="10%"><font color="red">无效</font></td>
				</s:else>
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