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
        <title>查看短信发送</title>

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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/locale/easyui-lang-zh_CN.js"></script>
<SCRIPT type="text/javascript" src="js/json2.js"></SCRIPT>
        <script type="text/javascript">
            function next(event) {
                var frm = document.myform;
                frm.action="<%=basePath%>orgsms/orgsmssend_"+event+".html";
                frm.submit();
            }
            function initReadOnly() {
                $("[readonly='readonly']").each(function(i){
                    $(this).css("background-color","#D4D0C8");
                });
            }

            function queryList() {
            	var turl = "<%=basePath%>"+'orgsms/orgsmssend!loadgroup.action';
            	$("#progressbar").css("display","none");
            	$('#dataGridmx').datagrid({
            		title:'组内人员列表',
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
            		queryParams: {NSRDATA_LSH:$("#NSRDATA_LSH").val()},
            		rownumbers:true,
            		onLoadSuccess : function(data){
            		}
            	});
            }

            $(function() {
            	queryList();
            });

            function vtip(){}
            function orgformat(val,row) {
            	return "<span title='"+row.ORG_MC_JG+"'>"+row.ORG_MC_JG+"</span>";
            }
            function orgbmformat(val,row) {
            	return "<span title='"+row.ORG_MC_BM+"'>"+row.ORG_MC_BM+"</span>";
            }
            function zwformat(val,row) {
            	return "<span title='"+row.ZW_MC+"'>"+row.ZW_MC+"</span>";
            }
            function msgformat(val,row) {
            	//ORG_DM_JG,ORG_DM_BM,USER_DM,ZW_DM
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
        <s:hidden id="NSRDATA_LSH" name="NSRDATA_LSH"></s:hidden>
            <div class="container" id="mainframe">
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;查看短信发送
                </ul>
                <div id="tablediv">
                <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="SMSTYPE_DM">
                                	短信类型
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="SMSTYPE_DM" name="orgsmssend.SMSTYPE_DM" list="smstypemap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="GROUP_NAME">
                                	短信标题
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="GROUP_NAME" name="orgsmssend.GROUP_NAME"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="ORG_DM_JG">
                                	税务机关
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="ORG_DM_JG" name="orgsmssend.ORG_DM_JG" list="orgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="SMSZT_DM">
                                	短信状态
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="SMSZT_DM" name="orgsmssend.SMSZT_DM" list="smsztmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
                    </div>
</tr>                    </div>
                </table>
                </div>
                <div align="center">
                    <br><input type="button" value="返回" onClick="next('list')"  class="buttonface"/>
                </div><br>
<div style="height:420px;">
	<table id="dataGridmx" width="200px" title='人员列表' iconCls='icon-save' fitColumns=true singleSelect=false fit=true>  
	    <thead>  
	        <tr>
	        	<th data-options="field:'USER_DM',checkbox:true"></th>
				<th data-options="field:'ORG_DM_JG',width:100,formatter:orgformat">机构</th>
				<th data-options="field:'ORG_DM_BM',width:100,formatter:orgbmformat">部门</th>
				<th data-options="field:'ZW_DM',width:100,formatter:zwformat">职务</th>
				<th data-options="field:'USER_MC',width:100,formatter:msgformat">人员</th>
				<th editor="text" data-options="field:'SJHM',required:true,width:100,formatter:msgformat">手机号码</th>
				<th data-options="field:'MSG',width:200,formatter:msgformat">短信内容</th>
	        </tr>  
	    </thead>  
	</table>
</div>
            </div>
            </div>
        </form>
    </body>
</html>
