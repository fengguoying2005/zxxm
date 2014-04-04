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
		<title>缓存管理</title>
		<link rel="stylesheet" type="text/css" href="css/menu.css">
		<link rel="stylesheet" type="text/css" href="css/screen.css">
		<style type="text/css">
@import "resource/css/jquery.datepick.css";
</style>
		<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
		<script type="text/javascript" src="js/jquery.datepick.js">
</script>
		<script type="text/javascript">
function clearcache(event) {
	if ("all" != event) {
		var item = $(":radio:checked");
		var len = item.length;
		if (len > 0) {
			$("#name").val($(":radio:checked").val());
		} else {
			alert("请选择一条！");
			return false;
		}
	} else {
		$("#name").val("");
	}
	document.forms[0].action = "<%=basePath%>qxgl/cache_clearCache.html";
	document.forms[0].submit();
}
</script>
	</head>
	<body onload="tablecloth('cacheList');">
		<form id="myform" name="myform" action="" method="post">
			<s:hidden id="name" name="name"></s:hidden>
			<div class="container">
				<div class="span-24">
					<table class="record-list" id="cacheList" name="userList">
						<thead>
							<tr>
								<th>
									选择
								</th>
								<th>
									行
								</th>
								<th>
									代码
								</th>
								<th>
									名称
								</th>
								<th>
									大小
								</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td colspan="5">
									<div align="right">
										<input class="buttonface" type="button" value="清除所有缓存"
											onClick="clearcache('all');" />
										<input class="buttonface" type="button" value="清除所选缓存"
											onClick="clearcache('one');" />
									</div>
								</td>
							</tr>
						</tfoot>
						<tbody>
							<s:iterator value="caches" status="stat">
								<tr
									class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
									<td>
										<INPUT type="radio" name='radio'
											value='<s:property value="name" />' />
									</td>
									<td>
										<s:property value="#stat.index+1" />
									</td>
									<td>
										<s:property value="name" />
									</td>
									<td>
										<s:property value="info" />
									</td>
									<td>
										<s:property value="size" />
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