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
<title>短信发送列表</title>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
            function next(event) {
                var ifSelected = false;
                if("toAdd"!=event && "list"!=event && "printExcel"!=event &&"printWord"!=event) {
                    var item = $(":radio:checked");
                    var len=item.length;
                    if(len>0){
                      $("#NSRDATA_LSH").val($(":radio:checked").val());
                      if(event=="del") {
                    	  if("审核不通过"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim() 
                    			  && "初始"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()
                    			  && "已生成短信"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()) {
                        	  alert("只有‘初始’、‘审核不通过’、‘已生成短信’状态下才能进行删除。");
                        	  return;
                    	  }
                      } else if(event=="toMod") {
                    	  if("审核不通过"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim() 
                    			  && "初始"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()
                    			  && "已生成短信"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()) {
                        	  alert("只有‘初始’、‘审核不通过’、‘已生成短信’状态才能修改。");
                        	  return;
                    	  }
                      } else if(event=="send") {
                    	  if("审核不通过"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim() 
                    			  && "已生成短信"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()) {
                        	  alert("只有在‘已生成短信’或‘审核不通过’状态下才能发送短信。");
                        	  return;
                    	  }
	                      	if($('#SHY').combobox('getValue')=='') {
	                      		alert("请选择审核人，再提交审核。");
	                      		return;
	                      	}
                          if(!window.confirm("确定提交审核吗？")) {
                              return false;
                          }
                      } else if(event=="seecallback") {
                    	  if("已发送"!=$(":radio:checked").parent().parent().children('td').eq(5).html().trim()) {
                        	  alert("只有在‘已发送’状态下才能查看回复短信。");
                        	  return;
                      		}
                      }
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
                if(event!='printExcel') {
                	setDisabledBtn(true, ['shBtn','delBtn']);
                }
                document.forms[0].action="<%=basePath%>orgsms/orgsmssend_"+event+".html";
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
<body onload="tablecloth('orgsmssendList');">
<form id="myform" name="myform" action="orgsmssend" method="post">
<div class="container" id="mainframe"><s:hidden id="NSRDATA_LSH"
	name="NSRDATA_LSH"></s:hidden>
<div class="span-24">

<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="FIND_GROUP_NAME"> 短信标题 </label></td>
		<td><s:textfield cssClass="text" id="FIND_GROUP_NAME"
			name="FIND_GROUP_NAME"></s:textfield></td>
		<td class="tdtitle"><label for="FIND_SMSZT_DM"> 短信状态 </label></td>
		<td><s:select id="FIND_SMSZT_DM" name="FIND_SMSZT_DM"
			list="smsztmap" headerKey="" headerValue="--选择--"
			cssStyle="width:210px;"></s:select></td>
	</tr>
	
	<tr>
		<td class="tdtitle"><label for="FIND_KS_RQ"> 开始日期 </label></td>
		<td><s:textfield cssClass="text" id="FIND_KS_RQ"
			name="FIND_KS_RQ"></s:textfield></td>
		<td class="tdtitle"><label for="FIND_JZ_RQ"> 截止日期 </label></td>
		<td><s:textfield cssClass="text" id="FIND_JZ_RQ"
			name="FIND_JZ_RQ"></s:textfield></td>
	</tr>
</table>
</div>
<div style="margin-top: 10px;" align="right"><input
	class="buttonface" type="button" value="查询" onclick="next('list')"></input></div>
<ul class="bg_image_onclick"
	style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;短信发送列表
</ul>
<table class="record-list" id="orgsmssendList" name="orgsmssendList">
	<thead>
		<tr>
			<th>选择</th>
			<th>行</th>
			<th>短信类型</th>
			<th>短信标题</th>
			<th>税务机关</th>
			<th>短信状态</th>
			<th>审核信息</th>
			<th>日期</th>
			<th>需要回复</th>
			<th>审核人员</th>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="10">
			<div align="left"><gwintag:page pageBeanName="pageBean"
				includes="FIND_GROUP_NAME,FIND_SMSZT_DM,FIND_KS_RQ,FIND_JZ_RQ"
				styleClass="aStyle" theme="number" /></div>
			<div align="right">
             审核人<input id="SHY" name="SHY" type="text" class="easyui-combobox" data-options="editable:false,valueField:'DM',textField:'MC',url:'<%=basePath%>ajax/usertree!shycombox.action'">
             
				<input id="shBtn" class="buttonface" type="button" value="提交审核"
                 onClick="next('send')" />
			<input class="buttonface" type="button"
				value="编辑短信" onClick="next('toAdd')" /> 
				<input id="delBtn" class="buttonface"
				type="button" value="删除" onClick="next('del')" /> <input
				class="buttonface" type="button" value="修改" onClick="next('toMod')" />
			<input class="buttonface" type="button" value="查看"
				onClick="next('see')" /> 
			<input class="buttonface" type="button" value="查看回复短信" onClick="next('seecallback')" />
			
				<input class="buttonface" type="button"
				value="导出" onClick="next('printExcel')" /></div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<s:iterator value="orgsmssends" status="stat">
			<tr
				class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
				<td><INPUT type="radio" name='radio'
					value='<s:property value="NSRDATA_LSH" />' /></td>
				<td><s:property value="#stat.index+1" /></td>
				<td><s:property value="SMSTYPE_DM" /></td>
				<td><s:property value="GROUP_NAME" /></td>
				<td><s:property value="ORG_DM_JG" /></td>
				<td><s:property value="SMSZT_DM" /></td>
				<td><s:property value="THYY" /></td>
				<td><s:property value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{LR_SJ})}" /></td>
				<td><s:property value="CALLBACK" /></td>
				<td><s:property value="SHY" /></td>
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
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>");
	setDisabledBtn(false, ['shBtn','delBtn']);
	</script>
<%
	}
%>