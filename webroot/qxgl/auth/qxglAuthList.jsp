<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>权限列表</title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css"
            media="screen">
		<link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
		<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
		<script type="text/javascript">
			function next(event) {
				var osType = getOs();
				if("Firefox"==osType || "Chrome"==osType) {
					var obj = document.getElementById("AUTH_TABLE").rows;
					var ifSelected = false;
					for ( var i = 1; i < obj.length; i++) {
						if (obj[i].cells[0].childNodes[1].checked) {
							document.getElementById("authdm").value=obj[i].cells[0].childNodes[1].value;
							ifSelected = true;
							break;
						}
					}
					if(!ifSelected && event!='addAuth') {
						alert("必须选择一条!");
						return false;
					}
					if(event=="delAuth") {
						if(!window.confirm("确认删除吗？")) {
							return false;
						}
					}
					var frm = document.myform;
					frm.action="<%=basePath%>qxgl/listauth!"+event+".action";
					frm.submit();
				} else {
					var obj = document.all.AUTH_TABLE.rows;
					var ifSelected = false;
					for ( var i = 1; i < obj.length; i++) {
						if (obj[i].cells[0].children[0].checked) {
							document.all.authdm.value=obj[i].cells[0].children[0].value;
							ifSelected = true;
							break;
						}
					}
					if(!ifSelected && event!='addAuth') {
						alert("必须选择一条!");
						return false;
					}
					if(event=="delAuth") {
						if(!window.confirm("确认删除吗？")) {
							return false;
						}
					}
					var frm = document.forms(0);
					frm.action="<%=basePath%>qxgl/listauth!"+event+".action";
					frm.submit();
				}
			}
		</script>
	</head>
	<body onload="tablecloth('AUTH_TABLE');">
		<form id="myform" name="myform" action="" method="post">
			<s:hidden id="authdm" name="authdm"></s:hidden>
			<div id="man_zone">
				<div class="span-24">
					<ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;权限列表
					</ul>
					<table class="record-list" id="AUTH_TABLE" name="AUTH_TABLE">
						<thead>
							<tr>
								<th>
									选择
								</th>
								<th>
									行
								</th>
								<th>
									权限代码
								</th>
								<th>
									权限名称
								</th>
								<th>
									层次
								</th>
								<th>
									权限描述
								</th>
								<th>
									权限路径
								</th>
								<th>
									有效标记
								</th>
								<th>
									显示顺序
								</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td colspan="9" align="right">
									<div align="right">
										<!-- 
									<span class="buttontext"><a href="javascript:next('add');">增加</a></span>
									<span class="buttontext"><a href="javascript:next('del');">删除</a></span>
									<span class="buttontext"><a href="javascript:next('mod');">修改</a></span>
									<span class="buttontext"><a href="javascript:next('see');">查看</a></span>
									 -->
										<input class="buttonface" type="button" value="增加" onClick="next('addAuth')" />
										<input class="buttonface" type="button" value="删除" onClick="next('delAuth')" />
										<input class="buttonface" type="button" value="修改" onClick="next('modAuth')" />
										<input class="buttonface" type="button" value="查看" onClick="next('seeAuth')" />
									</div>
								</td>
							</tr>
						</tfoot>
						<tbody>
							<s:iterator value="auths" status="stat">
								<tr class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
									<td>
										<INPUT type="radio" name='radio'
											value='<s:property value="AUTH_DM" />' />
									</td>
									<td>
										<s:property value="#stat.index+1" />
									</td>
									<td>
										<s:property value="AUTH_DM" />
									</td>
									<td>
										<s:property value="AUTH_MC" />
									</td>
									<td>
										<s:property value="AUTH_CC" />
									</td>
									<td>
										<s:property value="AUTH_MS" />
									</td>
									<td>
										<s:property value="AUTH_LJ" />
									</td>
									<td>
										<s:checkboxlist disabled="true"
											name="%{'auths['+#stat.index+'].YX_BJ'}" list="#{1:'选中有效'}" />
									</td>
									<td>
										<s:property value="XS_SX" />
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