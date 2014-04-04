<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>辽宁省地方税务局短信服务平台</title>

<LINK href="resource/login/images/Default.css" type=text/css rel=stylesheet>
<LINK href="resource/login/images/xtree.css" type=text/css rel=stylesheet>
<LINK href="resource/login/images/User_Login.css" type=text/css rel=stylesheet>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<META content="MSHTML 6.00.6000.16674" name=GENERATOR>
<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript"><!--
	function reloadImg(){ 
		var i = Math.random(); 
		document.getElementById("imgCode").src="qxgl/validateimage.action?a="+i; 
	} 
	function next(event) {
		var isOk = true;
		if(event=="login") {
			isOk = $().requireds();
		}
		if(isOk==true) {
			var frm = document.myform;
			frm.action="<%=basePath%>login!"+event+".action";
			frm.submit();
		}
	}
	$().ready(
		function(){
			if($("#errortip").text()=="") {
				$("#errortip").remove();
			}
			$("#yzm").keydown(function(ev){
				var curKey = (ev.keyCode) || (ev.which) || (ev.charCode);
	           if(curKey == 13){ 
	        	   next('login');
	           }
		   });
			
			//
		    var canSubmit;//标志
			$("#yzmBtn").click(function () {
				if($("#TxtUserName").val()=='') {
					return false;
				}
		        if (!canSubmit) {
		        	$("#yzmBtn").attr({ "disabled": "disabled" });
		            {
		            	$.ajax({
		            		url :"<%=basePath%>ajax/loginyzm!sendsms.action",
		            		type : "post",
		            		dataType : "json",
		            		data : "USER_DM="+$("#TxtUserName").val(),
		            		async : false,
		            		error : function() {
		            			alert("error");
		            		},
		            		success : function(data) {
		            			alert(data.result);
		            			if(data.result&&data.result.indexOf("已发送验证码到")>-1) {
		            				updateTimeLabel(45);
		            			} else {
		            				updateTimeLabel(3);
		            			}
		            			return true;
		            		}
		            	});
		            }
		        }
		    });
		    function updateTimeLabel(time) {
		        var btn = $("#yzmBtn");
		        btn.fadeIn(1000);
		        btn.val(time <= 0 ? "发送验证码" : ("发送验证码(" + (time) + ")"));
		        var hander = setInterval(function () {
		            if (time <= 0) {
		                canSubmit = false;
		                clearInterval(hander);
		                btn.val("发送验证码");
		                btn.removeAttr("disabled");
		            }
		            else {
		                canSubmit = true;
		                btn.attr({ "disabled": "disabled" });
		                btn.val("发送验证码(" + (time--) + ")");
		            }
		        }, 1000);
		        
		    }


		}
	);
	function validateJSP() {
		if(window.top && window.top.frames['manFrame']) {
			window.top.location = "login!logout.action";
		}
	}
</script>
</HEAD>
<BODY id=userlogin_body onload="validateJSP();">
<form id="loginForm" name="myform" action="" method="post">
	<div style="width: 100%;margin: 1em;padding: 1em;" align="center">
		<font color="white" size="5" style="margin: 1em;padding: 1em;font-weight: bold;">辽宁省地方税务局短信服务平台&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
	</div>
	<DIV style="width: 100%" align="center">

	<DIV id=user_login align="center">
		<DL>
			<DD id=user_top>
				<UL>
					<LI class=user_top_l></LI>
					<LI class=user_top_c></LI>
					<LI class=user_top_r></LI>
				</UL>
			<DD id=user_main>
				<UL>
					<LI class=user_main_l></LI>
					<LI class=user_main_c>
						<DIV class=user_main_box>
							<UL>
								<LI class=user_main_text>用户名：</LI>
								<LI class=user_main_input>
								<s:textfield cssClass="TxtUserNameCssClass" id="TxtUserName"  name="user.USER_DM" requireds="true" labels="帐号" requiredsLength="3,30" ></s:textfield>
								</LI>
							</UL>
							<UL>
								<LI class=user_main_text>密 码：</LI>
								<LI class=user_main_input>
								<s:password cssClass="TxtPasswordCssClass" id="TxtPassword" name="user.PASSWORD" requireds="true" labels="密码" requiredsLength="3,30" ></s:password>
								</LI>
							</UL>
							<UL>
								<LI class=user_main_text>手机验证：</LI>
								<LI class=user_main_input>
								<input id="yzmBtn" type="button" value="发送验证码" style="width:160px;"></input>
								</LI>
							</UL>
							<UL>
								<LI class=user_main_text>验证码：</LI>
								<LI class=user_main_input>
								<s:textfield  cssClass="TxtPasswordCssClass" id="yzm" name="imageCode" requireds="true" labels="验证码" ></s:textfield>
								</LI>
							</UL>
						</DIV>
					</LI>
					<LI class=user_main_r><INPUT class=IbtnEnterCssClass
						id=IbtnEnter
						style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
						onclick="next('login');"
						type=image src="resource/login/images/user_botton.gif" name=IbtnEnter></LI>
				</UL>
			<DD id=user_bottom>
				<UL>
					<LI class=user_bottom_l></LI>
					<LI class=user_bottom_c></LI>
					<LI class=user_bottom_r></LI>
				</UL>
			</DD>
		</DL>
	</DIV>
	</DIV>
	<SPAN id=ValrUserName style="DISPLAY: none; COLOR: red"></SPAN>
	<SPAN id=ValrPassword style="DISPLAY: none; COLOR: red"></SPAN>
	<SPAN id=ValrValidateCode style="DISPLAY: none; COLOR: red"></SPAN>
	<DIV id=ValidationSummary1 style="DISPLAY: none; COLOR: red"></DIV>

	<DIV></DIV>

	</form>
</BODY>
</HTML>
<%
	if (request.getAttribute("message") != null
			&& !"".equals(request.getAttribute("message"))) {
%><script
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>");
	</script>
<%
	}
%>