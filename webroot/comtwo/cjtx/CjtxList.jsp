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
<title>催缴提醒列表</title>
<link rel="stylesheet" href="resource/css/layout.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/framecommon.css"
	type="text/css" />
<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
<style type="text/css">
@import "resource/css/jquery.datepick.css";
</style>
<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type=text/javascript src="js/common.js"></script>
<script type=text/javascript src="cssmenu2/menu.js"></script>
<script type="text/javascript">
            function next(event) {
                var ifSelected = false;
                if("toAdd"!=event && "list"!=event && "printExcel"!=event &&"printWord"!=event) {
                    var item = $(":radio:checked");
                    var len=item.length;
                    if(len>0){
                      $("#LSH").val($(":radio:checked").val());
                    } else {
                        alert("请选择一条！");
                        return false;
                    }
                }
                if("del"==event) {
                    if(!window.confirm("确定删除吗？")) {
                        return false;
                    }
                }
                document.forms[0].action="<%=basePath%>comtwo/cjtx_"+event+".html";
                document.forms[0].submit();
            }
			$(function() {
			    $('#FIND_KS_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
			    $('#FIND_KS_RQ').css("width","185px");   
			    $('#FIND_JZ_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
			    $('#FIND_JZ_RQ').css("width","185px");   
			});
        </script>
</head>
<body onload="tablecloth('cjtxList');">
	<form id="myform" name="myform" action="cjtx" method="post">
		<div class="container" id="mainframe">
			<s:hidden id="LSH" name="LSH"></s:hidden>
			<div class="span-24">

				<ul class="bg_image_onclick"
					style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="FIND_SWJGBM"> 税务机关 </label>
							</td>
							<td><s:select id="FIND_SWJGBM" name="FIND_SWJGBM"
									list="orgmap" headerKey="" headerValue="--选择--"
									cssStyle="width:210px;"></s:select></td>
							<td class="tdtitle"><label for="FIND_NSRSBM"> 纳税人编码
							</label></td>
							<td><s:textfield cssClass="text" id="FIND_NSRSBM"
									name="FIND_NSRSBM"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_NSRMC"> 纳税人名称 </label>
							</td>
							<td><s:textfield cssClass="text" id="FIND_NSRMC"
									name="FIND_NSRMC"></s:textfield></td>
							<td class="tdtitle"><label for="FIND_SJLX"> 手机类型 </label></td>
							<td><s:select id="FIND_SJLX" name="FIND_SJLX"
									list="jsrlxmap" headerKey="" headerValue="--选择--"
									cssStyle="width:210px;"></s:select></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_KS_RQ"> 缴款期限起 </label></td>
							<td><s:textfield cssClass="text" id="FIND_KS_RQ"
									name="FIND_KS_RQ"></s:textfield></td>
							<td class="tdtitle"><label for="FIND_JZ_RQ"> 缴款期限止 </label></td>
							<td><s:textfield cssClass="text" id="FIND_JZ_RQ"
									name="FIND_JZ_RQ"></s:textfield></td>
						</tr>
					</table>
				</div>
				<div style="margin-top: 10px;" align="right">
					<input class="buttonface" type="button" value="查询"
						onclick="next('list')"></input>
				</div>
				<ul class="bg_image_onclick"
					style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;催缴提醒列表
				</ul>
				<table class="record-list" id="cjtxList" name="cjtxList">
					<thead>
						<tr>
							<th>选择</th>
							<th>行</th>
							<th>税务机关</th>
							<th>纳税人编码</th>
							<th>纳税人名称</th>
							<th>手机类型</th>
							<th>手机号码</th>
							<th>缴款期限</th>
							<th>税费金额</th>
							<th>发送状态</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="10">
								<div align="left">
									<gwintag:page pageBeanName="pageBean"
										includes="FIND_SWJGBM,FIND_NSRSBM,FIND_NSRMC,FIND_SJLX,FIND_JKQX_RQ"
										styleClass="aStyle" theme="number" />
								</div>
								<div align="right">
									<input class="buttonface"
										type="button" value="查看" onClick="next('see')" />
									<!-- <input
										class="buttonface" type="button" value="导出"
										onClick="next('printExcel')" /> -->
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<s:iterator value="cjtxs" status="stat">
							<tr
								class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
								<td><INPUT type="radio" name='radio'
									value='<s:property value="LSH" />' /></td>
								<td><s:property value="#stat.index+1" /></td>
								<td><s:property value="SWJGBM" /></td>
								<td><s:property value="NSRSBM" /></td>
								<td><s:property value="NSRMC" /></td>
								<td><s:property value="SJLX" /></td>
								<td><s:property value="SJHM" /></td>
								<td><s:property value="JKQX_RQ" /></td>
								<td><s:property value="SF_JE" /></td>
								<td><s:property value="FSZT_DM" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
<%
	if (request.getAttribute("message") != null
			&& !"".equals(request.getAttribute("message"))) {
%><script
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>
		");
	</script>
<%
	}
%>