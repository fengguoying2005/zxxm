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
		<title>查看权限</title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
			media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
			media="screen">
		<link rel="stylesheet" href="resource/css/component.css"
			type="text/css" />
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
		<script type="text/javascript">
			function next(event) {
				var frm = document.myform;
				frm.action="<%=basePath%>qxgl/listauth!" + event + ".action";
		frm.submit();
	}
</script>
	</head>
	<body onload="initReadOnly();">
		<form id="myform" name="myform" action="" method="post">
			<div id="man_zone">
				<div class="span-24">
				 <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;权限信息
                </ul>
                <div id="tablediv">
                <table width="100%" class="tablepanel" cellspacing="0">
                	<tr>
                		<td class="tdtitle">
                            <label for="auth_dm">
								权限代码
							</label>
						</td>
						<td>
                            <s:textfield readonly="true" cssClass="text" id="auth_dm" name="auth.AUTH_DM"
								requireds="true" labels="权限代码"></s:textfield>
						</td>
                		<td class="tdtitle">
                            <label for="auth_lj">
								权限路径
							</label>
						</td>
						<td>
							<s:textfield readonly="true" cssClass="text" id="auth_lj" name="auth.AUTH_LJ"
								requireds="true" labels="权限路径"></s:textfield>
						</td>
					</tr>
                	<tr>
                		<td class="tdtitle">
							<label for="auth_mc">
								权限名称
							</label>
						</td>
						<td>
							<s:textfield readonly="true" cssClass="text" id="auth_mc" name="auth.AUTH_MC"
								requireds="true" labels="权限名称"></s:textfield>
						</td>
                		<td class="tdtitle">
							<label for="target">
								打开目标
							</label>
						</td>
						<td>
							<s:select name="auth.TARGET" disabled="true"
								list="#{'_self':'_self','_blank':'_blank','_parent':'_parent','_top':'_top'}" cssStyle="width:210px;"></s:select>&nbsp;
						</td>
					</tr>
                	<tr>
                		<td class="tdtitle">
							<label for="auth_ms">
								权限描述
							</label>
						</td>
						<td>
							<s:textfield readonly="true" cssClass="text" id="auth_ms" name="auth.AUTH_MS"></s:textfield>&nbsp;&nbsp;
						</td>
                		<td class="tdtitle">
							<label for="xs_sx">
								显示顺序
							</label>
						</td>
						<td>
							<s:textfield readonly="true" cssClass="text" id="xs_sx" name="auth.XS_SX" labels="显示顺序" requiredsType="number"></s:textfield>&nbsp;&nbsp;
						</td>
					</tr>
                	<tr>
                		<td class="tdtitle">
							<label for="yx_bj">
								有效标记
							</label>
						</td>
						<td>
							<s:checkboxlist disabled="true" name="auth.YX_BJ" list="#{1:''}"></s:checkboxlist>
						</td>
                		<td class="tdtitle">
							<label for="yx_bj">
								仅顶级使用
							</label>
						</td>
						<td>
							<s:checkboxlist disabled="true" name="auth.SFDJ_BJ" list="#{1:''}"></s:checkboxlist>
						</td>
					</tr>
				</table>
					
				</div>
				
				<table class="record-list" id="AUTH_2" name="AUTH_2">
					<thead>
						<tr>
							<th>
								权限代码
							</th>
							<th>
								权限名称
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
								仅顶级
							</th>
							<th>
								显示顺序
							</th>
							<th>
								打开目标
							</th>
						</tr>
					</thead>
					<tbody id="gtbody">
						<s:iterator value="auths" status="stat">
							<tr
								class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
								<td>
									<s:property value="AUTH_DM" />
								</td>
								<td>
									<s:property value="AUTH_MC" />
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
									<s:checkboxlist disabled="true"
										name="%{'auths['+#stat.index+'].SFDJ_BJ'}" list="#{1:'仅省级'}" />
								</td>
								<td>
									<s:property value="XS_SX" />
								</td>
								<td>
									<s:property value="TARGET" />
								</td>
							</tr>
						</s:iterator>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="10" align="center">
								<div align="center">
									<input class="buttonface" type="button" value="返回"
										onClick="next('backAuth')" />
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</form>
	</body>
</html>