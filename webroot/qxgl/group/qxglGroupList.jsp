<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>组列表</title>
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
		<style type="text/css">
@import "resource/css/jquery.datepick.css";
		</style>
		<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
		<script type="text/javascript">
		function next(event) {
			var ifSelected = false;
			if("addGroup"!=event) {
				var item = $(":radio:checked");
				var len=item.length;
				if(len>0){
					$("#GROUP_DM").val($(":radio:checked").val());
				} else {
					alert("请选择一条！");
					return false;
				}
			}
			if("delGroup"==event) {
				if(!window.confirm("确定删除吗？")) {
					return false;
				}
			}
			document.forms[0].action="<%=basePath%>qxgl/group!"+event+".action";
			document.forms[0].submit();
		}
		</script>
	</head>
	<body onload="tablecloth('groupList');">
		<form id="myform" name="myform" action="group" method="post">
			<div class="container">
				<s:hidden id="GROUP_DM" name="GROUP_DM"></s:hidden>
				<div class="span-24">
					<label>
						组列表
					</label>
					<table class="record-list" id="groupList" name="groupList">
						<thead>
							<tr>
								<th>
									选择
								</th>
								<th>
									行
								</th>
								<th>
									组代码
								</th>
								<th>
									组名称
								</th>
								<th>
									组类型
								</th>
								<th>
									上级组
								</th>
								<th>
									有效标记
								</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td colspan="7">
									<div align="right">
										<input class="buttonface" type="button" value="增加"
											onClick="next('addGroup')" />
										<input class="buttonface" type="button" value="删除"
											onClick="next('delGroup')" />
										<input class="buttonface" type="button" value="修改"
											onClick="next('modGroup')" />
										<input class="buttonface" type="button" value="查看"
											onClick="next('seeGroup')" />
									</div>
								</td>
							</tr>
						</tfoot>
						<tbody>
							<s:iterator value="groups" status="stat">
								<tr
									class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
									<td>
										<INPUT type="radio" name='radio'
											value='<s:property value="GROUP_DM" />' />
									</td>
									<td>
										<s:property value="#stat.index+1" />
									</td>
									<td>
										<s:property value="GROUP_DM" />
									</td>
									<td>
										<s:property value="GROUP_NAME" />
									</td>
									<td>
										<s:property value="GROUP_LX" />
									</td>
									<td>
										<s:property value="SJ_GROUP_DM" />
									</td>
									<td>
										<s:property value="YX_BJ" />
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</body>
</html>
