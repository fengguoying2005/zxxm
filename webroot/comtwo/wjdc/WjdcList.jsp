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
<title>问卷调查列表</title>
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
                document.forms[0].action="<%=basePath%>comtwo/wjdc_"+event+".html";
                document.forms[0].submit();
            }
        </script>
</head>
<body onload="tablecloth('wjdcList');">
	<form id="myform" name="myform" action="wjdc" method="post">
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
							<td class="tdtitle"><label for="FIND_INFO"> 问卷内容 </label></td>
							<td><s:textfield cssClass="text" id="FIND_INFO"
									name="FIND_INFO"></s:textfield></td>
							<td class="tdtitle"><label for="FIND_BZ"> 特征码 </label></td>
							<td><s:textfield cssClass="text" id="FIND_BZ" name="FIND_BZ"></s:textfield>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_SFJS"> 是否结束 </label></td>
							<td>
								<s:select cssStyle="width:210px;" id="FIND_SFJS" name="FIND_SFJS" list="#{'':'--全部--','1':'已结束','0':'已录入','2':'已发送短信'}"></s:select>
								<!-- <s:checkboxlist id="FIND_SFJS" name="FIND_SFJS"
									list="#{1:''}" cssStyle="width:260px;"></s:checkboxlist> -->
							</td>
							<td class="tdtitle"><label for="FIND_DCJL"> 调查结论 </label></td>
							<td><s:textfield cssClass="text" id="FIND_DCJL"
									name="FIND_DCJL"></s:textfield></td>
						</tr>
					</table>
				</div>
				<div style="margin-top: 10px;" align="right">
					<input class="buttonface" type="button" value="查询"
						onclick="next('list')"></input>
				</div>
				<ul class="bg_image_onclick"
					style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;问卷调查列表
				</ul>
				<table class="record-list" id="wjdcList" name="wjdcList">
					<thead>
						<tr>
							<th>选择</th>
							<th>行</th>
							<th>问卷内容</th>
							<th>特征码</th>
							<th>是否结束</th>
							<th>修改人员</th>
							<th>修改时间</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="7">
								<div align="left">
									<gwintag:page pageBeanName="pageBean"
										includes="FIND_INFO,FIND_BZ,FIND_SFJS,FIND_DCJL"
										styleClass="aStyle" theme="number" />
								</div>
								<div align="right">
									<input class="buttonface" type="button" value="增加问卷"
										onClick="next('toAdd')" /> <input class="buttonface"
										type="button" value="删除问卷" onClick="next('del')" /> <input
										class="buttonface" type="button" value="修改问卷"
										onClick="next('toMod')" /> <input class="buttonface"
										type="button" value="查看问卷" onClick="next('see')" />
										<input class="buttonface"
										type="button" value="短信发送" onClick="next('send')" />
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<s:iterator value="wjdcs" status="stat">
							<tr
								class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
								<td><INPUT type="radio" name='radio'
									value='<s:property value="LSH" />' /></td>
								<td><s:property value="#stat.index+1" /></td>
								<td><s:property value="INFO" /></td>
								<td><s:property value="BZ" /></td>
								<td><s:property value="SFJS" /></td>
								<td><s:property value="XGRY_DM" /></td>
								<td><s:property value="XGSJ" /></td>
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
	if (request.getAttribute("message") != null && !"".equals(request.getAttribute("message"))) {
%>
	<script
		type="text/javascript">window.alert("<%=request.getAttribute("message")%>");
	</script>
<%
	}
%>