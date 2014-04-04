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
		<title>增加权限</title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
		<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
		<script type="text/javascript">
			function next(event) {
				$("#sysactionerror").remove();
				var isOk = true;
				if(event=="saveAddAuth") {
					isOk = $().requireds();
				}
				if(isOk) {
					var frm = document.myform;
					frm.action="<%=basePath%>qxgl/listauth!"+event+".action";
					frm.submit();
				}
			}
		</script>
	</head>
	<body onload="tablecloth('AUTH_2');">
		<s:form id="myform" name="myform" action="listauth" method="post">
			<div id="man_zone">
				<s:actionerror id="sysactionerror" />
				<s:actionmessage id="sysactionmessage" />
				<div class="span-24">
				 <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;增加权限
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
                            <s:textfield cssClass="text" id="auth_dm" name="auth.AUTH_DM"
								requireds="true" labels="权限代码"></s:textfield>
						</td>
                		<td class="tdtitle">
                            <label for="auth_lj">
								权限路径
							</label>
						</td>
						<td>
							<s:textfield cssClass="text" id="auth_lj" name="auth.AUTH_LJ"
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
							<s:textfield cssClass="text" id="auth_mc" name="auth.AUTH_MC"
								requireds="true" labels="权限名称"></s:textfield>
						</td>
                		<td class="tdtitle">
							<label for="target">
								打开目标
							</label>
						</td>
						<td>
							<s:select name="auth.TARGET"
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
							<s:textfield cssClass="text" id="auth_ms" name="auth.AUTH_MS"></s:textfield>&nbsp;&nbsp;
						</td>
                		<td class="tdtitle">
							<label for="xs_sx">
								显示顺序
							</label>
						</td>
						<td>
							<s:textfield cssClass="text" id="xs_sx" name="auth.XS_SX" labels="显示顺序" requiredsType="number"></s:textfield>&nbsp;&nbsp;
						</td>
					</tr>
                	<tr>
                		<td class="tdtitle">
							<label for="yx_bj">
								有效标记
							</label>
						</td>
						<td>
							<s:checkboxlist name="auth.YX_BJ" list="#{1:''}"></s:checkboxlist>
						</td>
                		<td class="tdtitle"><label for="yx_bj">
								仅顶级使用
							</label>
						</td>
						<td>
							<s:checkboxlist name="auth.SFDJ_BJ" list="#{1:''}"></s:checkboxlist>
						</td>
					</tr>
				</table>
					
				</div>
                 <div class="tablediv2">
				<table class="record-list" id="AUTH_2" name="AUTH_2">
					<thead>
						<tr>
							<th>
								选择
							</th>
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
									<INPUT type="radio" name='radio'
										value='<s:property value="AUTH_DM" />' />
								</td>
								<td>
									<s:textfield name="%{'auths['+#stat.index+'].AUTH_DM'}"
										size="10" labels="权限代码" />
								</td>
								<td>
									<s:textfield name="%{'auths['+#stat.index+'].AUTH_MC'}"
										labels="权限名称" />
								</td>
								<td>
									<s:textfield name="%{'auths['+#stat.index+'].AUTH_MS'}" />
								</td>
								<td>
									<s:textfield name="%{'auths['+#stat.index+'].AUTH_LJ'}"
										labels="权限路径" />
								</td>
								<td>
									<s:checkboxlist name="%{'auths['+#stat.index+'].YX_BJ'}"
										list="#{1:''}" />
								</td>
								<td>
									<s:checkboxlist name="%{'auths['+#stat.index+'].SFDJ_BJ'}"
										list="#{1:''}" />
								</td>
								<td>
									<s:textfield name="%{'auths['+#stat.index+'].XS_SX'}" size="2" />
								</td>
								<td>
									<s:select name="%{'auths['+#stat.index+'].TARGET'}"
										list="#{'_self':'_self','_blank':'_blank','_parent':'_parent','_top':'_top'}"></s:select>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				</div>
				<div align="right">
					<input class="buttonface" type="button" value="增加行"
						onClick="addNewRow('AUTH_2');tablecloth('AUTH_2');" />
					<input class="buttonface" type="button" value="删除行"
						onClick="deleteRow('AUTH_2');tablecloth('AUTH_2');" />
				</div>
				<div align="center">
					<input class="buttonface" type="button" value="保存"
						onClick="next('saveAddAuth')" />
					<input class="buttonface" type="button" value="返回"
						onClick="next('backAuth')" />
				</div>
			</div>
		</s:form>
	</body>
</html>