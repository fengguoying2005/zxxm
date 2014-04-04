<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>增加协议定制</title>

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
            $(function() {
                $('#KTRQ_Q').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
                $('#KTRQ_Q').css("width","185px");                
                $('#KTRQ_Z').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
                $('#KTRQ_Z').css("width","185px");
            });
            function next(event) {
                var isOk = true;
                if(event=="add") {
                    isOk = $().requireds();
                    if(isOk && $("#HIDNSRBM").val()!=$("#NSRBM").val()) {
                    	isOk = false;
                    	alert("纳税人编码已修改，请重新提取纳税人。");
                    }
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>comtwo/xydz_"+event+".html";
                    frm.submit();
                }
            }
            function loadNsr() {
            	$.post("<%=basePath%>"+'comtwo/xydz!loadNsr.action?NSRBM='+$("#NSRBM").val(),
						function(data) {
            				if(data.message!='') {
            					alert(data.message);
            				} else {
    							$("#HIDNSRBM").val(data.NSRBM);
    							$("#ORG_DM").val(data.ORG_DM);
    							$("#NSRMC").val(data.NSRMC);
            				}
						}, 
					"json");
            	
            }
        </script>
</head>
<body onload="tablecloth('TABLEMX_1');initReadOnly();">
	<form id="myform" name="myform" action="" method="post">
		<s:hidden id="HIDNSRBM" name="xydz.HIDNSRBM"></s:hidden>
		<s:hidden id="ORG_DM" name="xydz.ORG_DM"></s:hidden>
		<div class="container" id="mainframe">
			<div class="span-24">
				<s:actionerror id="sysactionerror" />
				<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;&nbsp;&nbsp;增加协议定制
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="NSRBM"> 纳税人编码 </label></td>
							<td align="left"><s:textfield cssClass="text" id="NSRBM"
									name="xydz.NSRBM" requireds="true" requiredsLength="0,50"
									labels="纳税人编码"></s:textfield>
								<input type="button" value="提取纳税人" onClick="loadNsr()" class="buttonface" />
							</td>
							<td class="tdtitle"><label for="NSRMC"> 纳税人名称 </label></td>
							<td align="left"><s:textfield cssClass="text" id="NSRMC"
									name="xydz.NSRMC" requireds="true" requiredsLength="0,200"
									labels="纳税人名称" readonly="true"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="KTRQ_Q"> 开通日期起 </label></td>
							<td align="left"><s:textfield cssClass="text" id="KTRQ_Q"
									name="xydz.KTRQ_Q" requireds="true" requiredsLength="0,20"
									labels="开通日期起"></s:textfield></td>
							<td class="tdtitle"><label for="KTRQ_Z"> 开通日期止 </label></td>
							<td align="left"><s:textfield cssClass="text" id="KTRQ_Z"
									name="xydz.KTRQ_Z" requireds="true" requiredsLength="0,20"
									labels="开通日期止"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="CBTX"> 催报提醒 </label></td>
							<td align="left"><s:checkboxlist id="CBTX" name="xydz.CBTX"
									list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist></td>
							<td class="tdtitle"><label for="CJTX"> 催缴提醒 </label></td>
							<td align="left"><s:checkboxlist id="CJTX" name="xydz.CJTX"
									list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="JKTX"> 缴款成功提醒 </label></td>
							<td align="left"><s:checkboxlist id="JKTX" name="xydz.JKTX"
									list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist></td>
							<td class="tdtitle"><label for="JKSBTX"> 缴款失败提醒 </label></td>
							<td align="left"><s:checkboxlist id="JKSBTX" name="xydz.JKSBTX"
									list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="GMFPTX"> 购买发票提醒 </label></td>
							<td align="left"><s:checkboxlist id="GMFPTX" name="xydz.GMFPTX"
									list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist></td>
							<td class="tdtitle"><label for="TYDQTX"> 停业到期提醒 </label></td>
							<td align="left"><s:checkboxlist id="TYDQTX" name="xydz.TYDQTX"
									list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="BZ"> 备注 </label></td>
							<td colspan="3" align="left"><s:textfield cssClass="text" id="BZ" name="xydz.BZ"
									requireds="false" requiredsLength="0,200" labels="备注" cssStyle="width:550px"></s:textfield>
							</td>
						</tr>
					</table>
				</div>
				<div align="center">
					<br>
					<input type="button" value="保存" onClick="next('add')"
						class="buttonface" /> <input type="button" value="返回"
						onClick="next('list')" class="buttonface" />
				</div>
			</div>
		</div>
	</form>
</body>
</html>
<%if(request.getAttribute("message")!=null && !"".equals(request.getAttribute("message"))){%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script><%}%>