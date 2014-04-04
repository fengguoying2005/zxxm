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
		<title>查看用户</title>
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
				var frm = document.myform;
				frm.action="<%=basePath%>qxgl/user!"+event+".action";
				frm.submit();
			}
		</script>
	</head>
	<body onload="initReadOnly();">
		<form id="myform" name="myform" action="" method="post">
			<div class="container">
				<s:actionerror id="sysactionerror" />
				<fieldset>
					<legend>查看用户</legend>
					<div class="span-11">
						<div>
							<label class="width2" for="ACCOUNT">
								帐号
							</label><span class="append-char"></span><span class="append-char"></span>
							<s:textfield cssClass="text" id="ACCOUNT" name="user.ACCOUNT" readonly="true"></s:textfield>
						</div>
						<div>
							<label class="width2" for="PASSWORD">
								密码
							</label>
							<s:textfield cssClass="text" id="PASSWORD" name="user.PASSWORD" readonly="true"></s:textfield>
						</div>
						<div>
							<label for="EMAIL">
								邮件地址
							</label>
							<s:textfield cssClass="text" id="EMAIL" name="user.EMAIL" readonly="true"></s:textfield>
						</div>
						<div>
							<label for="PHONE">
								电话号码
							</label>
							<s:textfield cssClass="text" id="PHONE" name="user.PHONE" readonly="true"></s:textfield>
						</div>
						<div>
							<label class="width1" for="SFZHM">
								身份证
							</label>
							<s:textfield cssClass="text" id="SFZHM" name="user.SFZHM" readonly="true"></s:textfield>
						</div>
					</div>
					<div class="span-11 last">
						<div>
							<label class="width2" for="TRUENAME">
								姓名
							</label>
							<s:textfield cssClass="text" id="TRUENAME" name="user.TRUENAME" readonly="true"></s:textfield>
						</div>
						<div>
							<label class="width2" for="MZ">
								民族
							</label>
							<s:select disabled="true" id="MZ" name="user.MZ" list="#{1:'汉族',2:'蒙古族',3:'回族',4:'藏族',5:'维吾尔族',6:'苗族',7:'彝族',8:'壮族',9:'布依族',10:'朝鲜族',11:'满族',12:'侗族',13:'瑶族',14:'白族',15:'土家族',16:'哈尼族',17:'哈萨克族',18:'傣族',19:'黎族',20:'傈僳族',21:'佤族',22:'畲族',23:'高山族',24:'拉祜族',25:'水族',26:'东乡族',27:'纳西族',28:'景颇族',29:'柯尔克孜族',30:'土族',31:'达斡尔族',32:'仫佬族',33:'羌族',34:'布朗族',35:'撒拉族',36:'毛南族',37:'仡佬族',38:'锡伯族',39:'阿昌族',40:'普米族',41:'塔吉克族',42:'怒族',43:'乌孜别克族',44:'俄罗斯族',45:'鄂温克族',46:'德昂族',47:'保安族',48:'裕固族',49:'京族',50:'塔塔尔族',51:'独龙族',52:'鄂伦春族',53:'赫哲族',54:'门巴族',55:'珞巴族',56:'基诺族'}"></s:select>
						</div>
						<div>
							<label for="CS_RQ">
								出生日期
							</label>
							<s:textfield cssClass="text" id="CS_RQ" name="user.CS_RQ" readonly="true"></s:textfield>
						</div>
						<div>
							<label for="YZBM">
								邮政编码
							</label>
							<s:textfield cssClass="text" id="YZBM" name="user.YZBM" readonly="true"></s:textfield>
						</div>
						<div>
							<label class="width2" for="XB">
								性别
							</label>
							<s:radio list="#{0:'男',1:'女'}" name="user.XB" disabled="true"></s:radio>
						</div>
						<div>
							<label for=YX_BJ>
								有效标记
							</label>
							<s:checkboxlist list="#{1:''}" name="user.YX_BJ" disabled="true"></s:checkboxlist>
						</div>
					</div>
					<div class="span-22 last">
						<span>
							<label for="LXDZ" cssClass="span-4">
								联系地址
							</label>
							<s:textfield cssClass="addr" id="LXDZ" name="user.LXDZ" readonly="true"></s:textfield>
						</span>
					</div>
				<div class="span-22 last" align="right">
					<input class="buttonface" type="button" value="返回"
						onClick="next('backUser')" />
				</div>
				</fieldset>
			</div>
		</form>
	</body>
</html>