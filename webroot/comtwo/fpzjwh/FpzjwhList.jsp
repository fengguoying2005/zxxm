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
<title>中奖内容维护列表</title>
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
                document.forms[0].action="<%=basePath%>comtwo/fpzjwh_"+event+".html";
                document.forms[0].submit();
            }
        </script>
</head>
<body onload="tablecloth('fpzjwhList');">
	<form id="myform" name="myform" action="fpzjwh" method="post">
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
							<td class="tdtitle"><label for="FIND_SSX"> 所属市 </label></td>
							<td><s:select id="FIND_SSX" name="FIND_SSX" list="sjsxmap"
									headerKey="" headerValue="--选择--" cssStyle="width:210px;"></s:select>
							</td>
							<td class="tdtitle"><label for="FIND_KJNY"> 开奖年月 </label></td>
							<td><s:textfield cssClass="text" id="FIND_KJNY"
									name="FIND_KJNY"></s:textfield></td>
						</tr>
					</table>
				</div>
				<div style="margin-top: 10px;" align="right">
					<input class="buttonface" type="button" value="查询"
						onclick="next('list')"></input>
				</div>
				<ul class="bg_image_onclick"
					style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;中奖内容维护列表
				</ul>
				<table class="record-list" id="fpzjwhList" name="fpzjwhList">
					<thead>
						<tr>
							<th>选择</th>
							<th>行</th>
							<th>所属市</th>
							<th>开奖年月</th>
							<th>修改人员</th>
							<th>修改时间</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="6">
								<div align="left">
									<gwintag:page pageBeanName="pageBean"
										includes="FIND_SSX,FIND_KJNY" styleClass="aStyle"
										theme="number" />
								</div>
								<div align="right">
									<input class="buttonface" type="button" value="增加"
										onClick="next('toAdd')" /> <input class="buttonface"
										type="button" value="删除" onClick="next('del')" /> <input
										class="buttonface" type="button" value="修改"
										onClick="next('toMod')" /> <input class="buttonface"
										type="button" value="查看" onClick="next('see')" /> <!-- <input
										class="buttonface" type="button" value="导出"
										onClick="next('printExcel')" /> -->
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<s:iterator value="fpzjwhs" status="stat">
							<tr
								class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
								<td><INPUT type="radio" name='radio'
									value='<s:property value="LSH" />' /></td>
								<td><s:property value="#stat.index+1" /></td>
								<td><s:property value="SSX" /></td>
								<td><s:property value="KJNY" /></td>
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
		<script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script>
<%
	}
%>