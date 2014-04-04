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
		<title>用户列表</title>
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
			media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
			media="screen">
		<link rel="stylesheet" href="resource/css/component.css"
			type="text/css" />
		<style type="text/css">
@import "resource/css/jquery.datepick.css";
</style>
		<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<script type="text/javascript" src="js/jquery.datepick.js"></script>
		<script type=text/javascript src="js/common.js"></script>
		<script type=text/javascript src="js/TreePanel.js"></script>
		<script type=text/javascript src="cssmenu2/menu.js"></script>
		<script type="text/javascript">
			function next(event) {
				var ifSelected = false;
				if("addUser"!=event && "listUsers"!=event) {
					var item = $(":radio:checked");
					var len=item.length;
					if(len>0){
					  $("#account").val($(":radio:checked").val());
					} else {
						alert("请选择一条！");
						return false;
					}
				}
				if("delUser"==event) {
					if(!window.confirm("确定删除吗？")) {
						return false;
					}
				}
				document.forms[0].action="<%=basePath%>qxgl/user!"+event+".action";
				document.forms[0].submit();
			}
			function assginRole() {
				var item = $(":radio:checked");
				var len=item.length;
				if(len>0){
				  $("#account").val($(":radio:checked").val());
				} else {
					alert("请选择一条！");
					return false;
				}
				$("#roleTree").html("");
				$("#useraccount").text($(":radio:checked").val());
				$.ajax({	
					url :"<%=basePath%>ajax/roletree!test.action",
					type : "post",
					dataType : "json",
					data : "name="+$("#account").val(),
					//timeout : 20000,
					async : false,
					error : function() {
						alert("erwror");
					},
					success : function(data) {
						tree = new TreePanel({
							'root' : eval(data.result)[0],
							'renderTo':"roleTree"
						});
						tree.render();
						tree.expandAll();
						return true;
					}
				});
				$("#rolerole").text($(":radio:checked").val());
				var W = screen.width;//取得屏幕分辨率宽度 
				var H = screen.height;//取得屏幕分辨率高度 
				var yScroll;//取滚动条高度 
				if (self.pageYOffset) { 
					yScroll = self.pageYOffset; 
				} else if (document.documentElement && document.documentElement.scrollTop){ 
					yScroll = document.documentElement.scrollTop; 
				} else if (document.body) {
					yScroll = document.body.scrollTop; 
				} 
				$("#popupbg").css( {
					display : "block",
					height : $(document).height()
				});
				var yscroll = document.documentElement.scrollTop;
				
				$("#popupdiv").css("top", (H/2 - 325 + yscroll) + "px");
				$("#popupdiv").css("left", (W/2 - 200) + "px");
				 //$("#11").css({position:"absolute","top": e.pageY,"left": e.pageX,"backgroundColor":"yellow"});
				
				$("#popupdiv").css("display", "block");
				document.documentElement.scrollTop = 0;
			}
			function saveAssginRole() {
				if(tree) {
					var a='';
					for(var i = 0, count=tree.getChecked().length; i < count; i ++){
						a=a+","+(tree.getChecked()[i]);
					}
					$("#assignRole").val(a);
				}
			    $("#account").val($(":radio:checked").val());
				document.forms[0].action="<%=basePath%>qxgl/user!saveAssginRole.action";
				document.forms[0].submit();
			}
			function close() {
				$("#popupbg").css("display", "none");
				$("#popupdiv").css("display", "none");
			}
		</script>
	</head>
	<body onload="tablecloth('userList');">
		<form id="myform" name="myform" action="user" method="post">
			<div class="container">
				<s:hidden id="account" name="account"></s:hidden>
				<s:hidden id="assignRole" name="assignRole"></s:hidden>
				<div class="span-24">

					<fieldset>
						<legend>
							设置条件
						</legend>
						<label for="account">
							帐号
						</label>
						<s:textfield cssClass="text" id="account" name="user.ACCOUNT"></s:textfield>
						<span class="append-1"></span>
						<label for="email">
							Email
						</label>
						<s:textfield cssClass="text" id="email" name="user.EMAIL"></s:textfield>
						<span class="append-1"></span>
						<input class="buttonface" type="button" value="查询"
							onclick="next('listUsers')"></input>
					</fieldset>
					<table class="record-list" id="userList" name="userList">
						<thead>
							<tr>
								<th>
									选择
								</th>
								<th>
									行
								</th>
								<th>
									帐号
								</th>
								<th>
									邮件
								</th>
								<th>
									姓名
								</th>
								<th>
									电话
								</th>
								<th>
									身份证号码
								</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td colspan="7">
									<div align="left">
										<gwintag:page pageBeanName="pageBean"
											includes="user.ACCOUNT,user.EMAIL" styleClass="aStyle"
											theme="number" />
									</div>
									<div align="right">
										<input class="buttonface" type="button" value="增加"
											onClick="next('addUser')" />
										<input class="buttonface" type="button" value="删除"
											onClick="next('delUser')" />
										<input class="buttonface" type="button" value="修改"
											onClick="next('modUser')" />
										<input class="buttonface" type="button" value="查看"
											onClick="next('seeUser')" />
										<input class="buttonface" type="button" value="指派角色"
											onClick="assginRole()" />
									</div>
								</td>
							</tr>
						</tfoot>
						<tbody>
							<s:iterator value="users" status="stat">
								<tr
									class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
									<td>
										<INPUT type="radio" name='radio'
											value='<s:property value="ACCOUNT" />' />
									</td>
									<td>
										<s:property value="#stat.index+1" />
									</td>
									<td>
										<s:property value="ACCOUNT" />
									</td>
									<td>
										<s:property value="EMAIL" />
									</td>
									<td>
										<s:property value="TRUENAME" />
									</td>
									<td>
										<s:property value="PHONE" />
									</td>
									<td>
										<s:property value="SFZHM" />
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div id="popupbg"></div>
				<div id="popupdiv" style="display: none">
					<h2>
						指派角色
						<a href="javascript: close();" class="close">关闭</a>
					</h2>
					<h5>
						给用户<
						<span id="useraccount"></span>>指派角色
					</h5>
					<div id="roleTree" class="Tree">
					</div>
					<div align="right">
						<input class="buttonface" type="button" value="保存"
							onClick="saveAssginRole();" />
						<input class="buttonface" type="button" value="返回"
							onClick="next('backUser')" />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>