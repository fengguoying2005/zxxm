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
		<title>修改组</title>
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
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
			var isOk = true;
			if(event=="saveModGroup") {
			isOk = $().requireds();
			}
			if(isOk) {
				var frm = document.myform;
				frm.action="<%=basePath%>qxgl/group!"+event+".action";
				frm.submit();
			}
		}
		</script>
	</head>
	<body>
		<form id="myform" name="myform" action="" method="post">
			<div class="container">
				<s:actionerror id="sysactionerror" />
				<s:actionmessage id="sysactionmessage" />
				<fieldset>
					<legend>修改组</legend>
				
					<div class="span-11">
						<div>
							<label for="GROUP_DM" class="width1">
								组代码
							</label>
							<s:textfield cssClass="text" id="GROUP_DM" name="group.GROUP_DM"
								readonly="true"></s:textfield>
						</div>
						<div>
							<label for="GROUP_NAME" class="width1">
								组名称
							</label>
							<s:textfield cssClass="text" id="GROUP_NAME"
								name="group.GROUP_NAME" labels="组名称" requireds="true"></s:textfield>
						</div>
						<div>
							<label for="YX_BJ">
								有效标记
							</label>
							<s:checkboxlist list="#{1:''}" name="group.YX_BJ"></s:checkboxlist>
						</div>
					</div>
					<div class="span-12 last">
						<div>
							<label for="GROUP_LX" class="width1">
								组类型
							</label>
							<s:textfield cssClass="text" id="GROUP_LX" name="group.GROUP_LX"
								labels="组类型" requireds="true"></s:textfield>
						</div>
						<div>
							<label for="SJ_GROUP_DM" class="width1">
								上级组
							</label>
							<s:textfield cssClass="text" id="SJ_GROUP_DM"
								name="group.SJ_GROUP_DM" labels="上级组" requireds="true"></s:textfield>
						</div>
					</div>
				<div class="span-22 last" align="right">
					<input class="buttonface" type="button" value="保存"
						onClick="next('saveModGroup')" />
					<input class="buttonface" type="button" value="返回"
						onClick="next('backGroup')" />
				</div>
				</fieldset>
			</div>
		</form>
	</body>
</html>
