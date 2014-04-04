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
<title>增加投诉举报</title>

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
                $('#TS_SJ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
                $('#TS_SJ').css("width","235px");            });
            function next(event) {
                var isOk = true;
                if(event=="add") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>comtwo/tsjb_"+event+".html";
                    frm.submit();
                }
            }
        </script>
</head>
<body>
	<form id="myform" name="myform" action="" method="post">
		<div class="container" id="mainframe">
			<div class="span-24">
				<s:actionerror id="sysactionerror" />
				<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;&nbsp;&nbsp;增加投诉举报
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="SJHM"> 手机号码 </label></td>
							<td><s:textfield cssClass="text" id="SJHM" name="tsjb.SJHM"
									requireds="true" requiredsLength="1,20" labels="手机号码"></s:textfield>
							</td>
							<td class="tdtitle"><label for="TSINFO"> 投诉内容 </label></td>
							<td><s:textfield cssClass="text" id="TSINFO"
									name="tsjb.TSINFO" requireds="true" requiredsLength="1,200"
									labels="投诉内容"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="TS_SJ"> 投诉时间 </label></td>
							<td><s:textfield cssClass="text" id="TS_SJ"
									name="tsjb.TS_SJ" requireds="false" requiredsLength="0,"
									labels="投诉时间" requiredsType="date"></s:textfield></td>
							<td class="tdtitle"><label for="HF_BJ"> 回复标记 </label></td>
							<td><s:textfield cssClass="text" id="HF_BJ"
									name="tsjb.HF_BJ" requireds="false" requiredsLength="0,1"
									labels="回复标记"></s:textfield></td>
							</div>
						</tr>
						</div>
					</table>
				</div>
				<div class="tablediv2">
					<!-- mx -->
					<table class="record-list" id="TABLEMX_1" name="TABLEMX_1">
						<thead>
							<tr>
								<th>选择</th>
								<th>回复内容</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<s:iterator value="tablemx" status="stat">
								<tr
									class='<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>'>
									<td><INPUT type="radio" name='radio'
										value='<s:property value="HFINFO" />' /></td>
									<td><s:textfield cssStyle="width:120px" cssClass="text"
											name="%{'tablemx['+#stat.index+'].HFINFO'}" requireds="true"
											requiredsLength="1,200" labels="回复内容"></s:textfield></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div align="right">
					<input class="buttonface" type="button" value="增加行"
						onClick="addNewRow('TABLEMX_1');tablecloth('TABLEMX_1');" /> <input
						class="buttonface" type="button" value="删除行"
						onClick="deleteRow('TABLEMX_1');tablecloth('TABLEMX_1');" />
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
<%
	if (request.getAttribute("message") != null && !"".equals(request.getAttribute("message"))) {
%>
		<script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script>
<%
	}
%>