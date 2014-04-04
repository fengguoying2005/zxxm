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
        <title>查看纳税人群组</title>

        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">
        <link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
        <link rel="stylesheet" href="resource/css/component.css" type="text/css" media="screen">
        <link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
        <link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
        <style type="text/css">
             @import "resource/css/jquery.datepick.css";
        </style>
        <SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="js/jquery.gwinsoft.js"></SCRIPT>
        <script type="text/javascript" src="js/jquery.datepick.js"></script>
        
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jeasyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jeasyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jeasyui/locale/easyui-lang-zh_CN.js"></script>
        
        <script type="text/javascript">
	        $(function() {
	        	$("#progressbar").css("display","none");

	        	var turl = "<%=basePath%>"+'nsrsms/group!listmx.action';
	        	$('#datagrid').datagrid({
	        		title:'明细信息',
	        		url:turl,
	        		iconCls:'icon-save',
	        		striped: true,
	        		pageNumber : 1,
	        		pageSize : 10,
	        		collapsible:true,
	        		remoteSort: false,
	        		idField:'code',
	        		pagination:true,
	        		//查询条件
	        		queryParams: {LSH:$("#LSH").val()},
	        		rownumbers:true,
	        		onLoadSuccess : function(data){
	        		}
	        	});
	        });
            function next(event) {
                var frm = document.myform;
                frm.action="<%=basePath%>nsrsms/group_"+event+".html";
                frm.submit();
            }
            function initReadOnly() {
                $("[readonly='readonly']").each(function(i){
                    $(this).css("background-color","#D4D0C8");
                });
            }
        	function msgformat(val,row) {
        		if(val) {
            		return "<span title='"+val+"'>"+val+"</span>";
        		} else {
        			return "";
        		}
        	}
        </script>
    </head>
    <body  onload="initReadOnly();">
        <form id="myform" name="myform" action="" method="post">
			<s:hidden id="LSH" name="group.NSRDATA_LSH"></s:hidden>
            <div class="container" id="mainframe">
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;查看纳税人群组
                </ul>
                <div id="tablediv">
                <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="SMSTYPE_DM">
                                	短信类型
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="SMSTYPE_DM" name="group.SMSTYPE_DM" list="smstypemap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="GROUP_NAME">
                                	短信标题
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="GROUP_NAME" name="group.GROUP_NAME"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="ORG_DM_JG">
                                	税务机关
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="ORG_DM_JG" name="group.ORG_DM_JG" list="orgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="SMSZT_DM">
                                	短信状态
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="SMSZT_DM" name="group.SMSZT_DM" list="smsztmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
                    </div>
</tr>                    </div>
                </table>
                </div>
                <div align="center">
                	<span id="progressbar">请稍候...<img alt="" src="<%=basePath%>/images/progressbar.gif" width="300px" height="20px"/></span>
                    <br><input type="button" value="返回" onClick="next('list')"  class="buttonface"/>
                </div>
                
	<br>
	<div style="height:420px">
	<table id="datagrid" style="width:768px;height:420px"  title='明细信息' iconCls='icon-save' 
       fitColumns=true singleSelect=false fit=true>  
	    <thead>  
	        <tr>  									
				<th data-options="field:'NSRBM',width:100,formatter:msgformat" editor='text'>纳税人编码</th>
				<th data-options="field:'NSRMC',width:150,formatter:msgformat" editor='text'>纳税人名称</th>
				<th data-options="field:'SJHM',width:100,formatter:msgformat" editor='text'>手机号码</th>
				<th data-options="field:'STR_CBRQQ',width:80,formatter:msgformat" editor='text'>有效日期起</th>
				<th data-options="field:'STR_CBRQZ',width:80,formatter:msgformat" editor='text'>有效日期止</th>
				<th data-options="field:'ZSXM',width:80,formatter:msgformat" editor='text'>征收项目</th>
				<th data-options="field:'JE',width:80,formatter:msgformat" editor='text'>金额</th>
				<th data-options="field:'ZSPM',width:80,formatter:msgformat" editor='text'>征收品目</th>
				<th data-options="field:'STR_SKSSQ_Q',width:80,formatter:msgformat" editor='text'>税款所属期起</th>
				<th data-options="field:'STR_SKSSQ_Z',width:80,formatter:msgformat" editor='text'>税款所属期止</th>
	        </tr>  
	    </thead>  
	</table>
	</div>
                <!-- 
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明细信息
                </ul>
                 <div class="tablediv2">
                 <table class="record-list" id="TABLEMX_1" name="TABLEMX_1">
<thead><tr>
								<th>序号</th><th>纳税人编码</th>
<th>纳税人名称</th>
<th>手机号码</th>
<th>有效日期起</th>
<th>有效日期止</th>
<th>征收项目</th>
<th>金额</th>
<th>征收品目</th>
<th>税款所属期起</th>
<th>税款所属期止</th>
</tr>
</thead>
<tbody id="tbody"><s:iterator value="tablemx" status="stat"><tr class='<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>'>
<td><s:text name="#stat.index+1"></s:text></td>
<td>
<s:text name="%{'tablemx['+#stat.index+'].NSRBM'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].NSRMC'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].SJHM'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].CBRQQ'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].CBRQZ'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].ZSXM'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].JE'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].ZSPM'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].SKSSQ_Q'}"></s:text>
</td>
<td><s:text name="%{'tablemx['+#stat.index+'].SKSSQ_Z'}"></s:text>
</td>
</tr></s:iterator>
</tbody>
                 </table>
                 </div>
                  -->
            </div>
            </div>
        </form>
    </body>
</html>
