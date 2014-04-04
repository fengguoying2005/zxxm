<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>------</title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
		<link rel="stylesheet" href="resource/css/cssother/TreePanel.css" type="text/css">
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<script type=text/javascript src="js/common.js"></script>
		<script type=text/javascript src="js/TreePanel.js"></script>
		<script type="text/javascript">
			var tree;
			$().ready(function(){
				$.ajax({	
					url :"<%=basePath%>ajax/testtree!test.action",
					type : "post",
					dataType : "json",
					data : "name="+$("#roledm").val(),
					//timeout : 20000,
					async : false,
					error : function() {
						alert("erwror");
					},
					success : function(data) {
						tree = new TreePanel({
							'root' : eval(data.result)[0],
							'renderTo':"authTree"
						});
						tree.render();
						tree.expandAll();
						//alert(tree.getChecked()[3]);
						//tree.expandAll();
						//tree.collapseAll();
						//tree.getChecked().length
						//tree.focusNode.appendChild(new TreeNode({text:text}));
						//tree.focusNode.attributes.text
						return true;
					}
				});
			});
			function next(event) {
				var a='';
				for(var i = 0, count=tree.getChecked().length; i < count; i ++){
					a=a+","+(tree.getChecked()[i]);
				}
				$("#assignAuth").val(a);
				var frm = document.myform;
				frm.action="<%=basePath%>qxgl/role!"+event+".action";
				frm.submit();
			}
		</script>
	</head>
	<body>
		<form id="myform" name="myform" action="" method="post">
			<s:hidden id="roledm" name="roledm"></s:hidden>
			<s:hidden id="assignAuth" name="assignAuth"></s:hidden>
			<div class="container">
				<div class="span-24 brder">
					<div class="span-12">
							<p>
								<div class="success">给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能</div>
							</p>
							<p>
								<div class="notice">
										给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能
								</div>
							</p>
							<p>
								<div class="error">
										给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能给角色分配权限功能
								</div>
							</p>
					</div>
					<div class="span-12 last">
						<div id="authTree" class="Tree"> </div>
					</div>
					
				</div>
				<div align="right">
					<input class="buttonface" type="button" value="保存"
						onClick="next('saveAssginAuth')" />
					<input class="buttonface" type="button" value="返回"
						onClick="next('backRole')" />
				</div>
			</div>
		</form>
	</body>
</html>