<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
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
<title>修改密码</title>

<link rel="stylesheet" href="resource/css/layout.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/framecommon.css"
	type="text/css" />
<link rel="stylesheet" href="resource/css/component.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
	media="screen">
<style type="text/css">
@import "resource/css/jquery.datepick.css";
</style>
<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
	function next() {
		if($("#LRRY_DM").val()!=$("#XGRY_DM").val()) {
			alert("两次录入密码不一致");
			$("#LRRY_DM").val("");
			$("#XGRY_DM").val("");
			return false;
		}
		var isOk = $().requireds();
		if(isOk) {
            var frm = document.myform;
            frm.action="<%=basePath%>login!moidfyPwd.action";
            frm.submit();
        }
	}
</script>
</head>
<body>
<form id="myform" name="myform" action="" method="post">
            <div id="man_zone">
                <s:actionmessage  id="sysactionmessage" />
                <s:actionerror id="sysactionerror" />
                <div class="span-24">
					<ul class="tabletitle" style="line-height:24px;font-weight:bold;">
				        &nbsp;&nbsp;&nbsp;&nbsp;修改密码
				    </ul>
				    <div id="tablediv">
				    <table width="100%" class="tablepanel" cellspacing="0">
				    	<tr>
				    		<td class="tdtitle">
				    			<label for="USER_DM">
							        	用户名
							    </label>
							</td>
							<td>
								<s:textfield cssClass="text" name="user.USER_DM" readonly="true"></s:textfield>&nbsp;&nbsp;
							</td>
				    		<td class="tdtitle">
				    			<label for="PASSWORD">
							        	旧密码
							    </label>
							</td>
							<td>
								<s:password cssClass="text" id="MM" name="user.PASSWORD" requireds="true" requiredsLength="1,100" labels="旧密码"></s:password>
							</td>
						</tr>
				    	<tr>
				    		<td class="tdtitle">
				    			<label for="LRRY_DM">
							        	新密码
							    </label>
							</td>
							<td>
								<s:password cssClass="text" id="LRRY_DM" name="user.LRRY_DM" requireds="true" requiredsLength="1,100" labels="新密码"></s:password>
							</td>
				    		<td class="tdtitle">
				    			<label for="XGRY_DM">
							        	密码确认
							    </label>
							</td>
							<td>
								<s:password cssClass="text" id="XGRY_DM" name="user.XGRY_DM" requireds="true" requiredsLength="1,100" labels="密码确认"></s:password>
							</td>
						</tr>
					</table>
				</div>
                <br>
                <div align="center">
                    <input type="button" value="修改密码" onClick="next()"  class="buttonface"/>
                </div>
            </div>
        </form>
</body>
</html>