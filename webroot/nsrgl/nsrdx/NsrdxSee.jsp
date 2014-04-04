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

<style type="text/css">
.demo {
	float: left;
	width: 260px;
}

.docs {
	margin-left: 265px;
}

a.button {
	font-size: 1em;
	color: blue;
	margin-top: 4px;
	margin-bottom: 0px;
	margin-right: 4px;
	margin-left: 170px;
	color: -webkit-link;
	text-decoration: underline;
	cursor: auto;
}
</style>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jeasyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jeasyui/locale/easyui-lang-zh_CN.js"></script>

<link href="js/wdTree/css/tree.css" rel="stylesheet" type="text/css" />
<script src="js/wdTree/src/Plugins/jquery.tree.js"
	type="text/javascript"></script>
<script src="js/wdTree/src/Plugins/json2.js" type="text/javascript"></script>
<script src="js/wdTree/data/tree1.js" type="text/javascript"></script>

<script type="text/javascript">
    	function msgformat(val,row) {
    		if(val) {
        		return "<span title='"+val+"'>"+val+"</span>";
    		} else {
    			return "";
    		}
    	}
    	function noneformat(val,row) {
    		return "";
    	}
    	function queryList() {
        	var turl = "<%=basePath%>"+'nsrgl/nsrdx!listmx.action';
        	$("#progressbar").css("display","none");
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
        		queryParams: {LSH:$("#NSRDATA_LSH").val()},
        		rownumbers:true,
        		onLoadSuccess : function(data){
        		}
        	});
    	}
        $(function() {
        	queryList();
            var SJTYPE = $("#SJTYPE").val();
            if(SJTYPE=='1') {
            	$("#rylx1").attr("checked","true");
            } else if(SJTYPE=='2') {
            	$("#rylx2").attr("checked","true");
            } if(SJTYPE=='3') {
            	$("#rylx3").attr("checked","true");
            } if(SJTYPE=='4') {
            	$("#rylx4").attr("checked","true");
            }
        });
        function select() {
        	var s=$("#tree").getCheckedNodes();
        	      if(s !=null)
        	      alert(s.join(","));
        }
        function next(event) {
            var isOk = true;
            var xzfs;
            if(event=="mod") {
                isOk = $().requireds();
            	xzfs = $("input[name='xzfs']:checked").val();
            	if(xzfs=='1') {
                    var s=$("#tree2").getCheckedNodes();
          	        if(s !=null) {
          	        	$("#datas").val(s.join(","));
          	        }
            	} else {
                    var s=$("#tree").getCheckedNodes();
          	        if(s !=null) {
              	        $("#datas").val(s.join(","));
          	        }
            	}
            }
            if(isOk) {
                var frm = document.myform;
                frm.action="<%=basePath%>nsrgl/nsrdx_"+event+".html?rylx="+$("input[name='rylx']:checked").val()+"&xzfs="+xzfs;
                frm.submit();
                //alert($("#datas").val());
            }
        }

		function seemx() {
			var rows = $('#datagrid').datagrid('getChecked');
			var ids = [];
			var LSH;
			for(var i=0; i<rows.length; i++){
			    ids.push(rows[i].SFXCMSG_LSH);
			    LSH = (rows[i].SFXCMSG_LSH+"").split(",")[1];
			}
			if(ids.length==0) {
				$.messager.alert("[提示信息]","请选择记录。");
			} else if(ids.length>1) {
				$.messager.alert("[提示信息]","请选择一条记录。");
			} else {
				var url = "<%=basePath%>"+'nsrgl/nsrdx!seemx.action?LSH='+LSH;
				var content ='<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%; height:100%"></iframe>';
				$("#see_dialog").dialog({
				    title: "查看纳税人",   
				    'width': 700,   
				    'height': 380,   
				    content: content,
				    collapsible: true,
				    resizable: true,
				    maximizable: true,
				    modal: true,
				    closed: false,
				    cache: false
				}).dialog('open'); 
			}
		}
        </script>
</head>
<body>
<form id="myform" name="myform" action="" method="post">
<s:hidden id="NSRDATA_LSH" name="NSRDATA_LSH"></s:hidden>
<s:hidden id="SJTYPE" name="nsrdx.DXQM"></s:hidden>

<div class="container" id="mainframe">

<div class="span-24"><s:actionerror id="sysactionerror" />
<s:hidden id="datas" name="datas"></s:hidden>
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;增加短信发送
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="ORG_DM_JG"> 税务机关 </label></td>
		<td>
			<s:textfield cssClass="text" id="ORG_DM_JG"
			name="nsrdx.ORG_DM_JG" readonly="true"
			labels="税务机关" cssStyle="width:152px;"></s:textfield>
		</td>
		
		<td class="tdtitle" rowspan="5"><label for="DXMBINFO"> 短信内容 </label></td>
		<td rowspan="5"><s:textarea cssClass="text" id="DXMBINFO" cssStyle="width:210px;height:150px"
			name="nsrdx.DXMBINFO" requireds="true" requiredsLength="1,600"
			labels="短信内容"></s:textarea>
		</td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="SMSZT_DM"> 短信状态 </label></td>
		<td><s:select id="SMSZT_DM" name="nsrdx.SMSZT_DM" list="smsztmap"
			headerKey="" headerValue="--选择--" disabled="true"
			labels="短信状态" cssStyle="width:160px;"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="SMSTYPE_DM"> 短信类型 </label></td>
		<td><s:select id="SMSTYPE_DM" name="nsrdx.SMSTYPE_DM"
			list="smstypemap" headerKey="" headerValue="--选择--" disabled="true"
			labels="短信类型" cssStyle="width:160px;"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="GROUP_NAME"> 短信标题 </label></td>
		<td><s:textfield cssClass="text" id="GROUP_NAME"
			name="nsrdx.GROUP_NAME" requireds="true" requiredsLength="1,100"
			labels="短信标题" cssStyle="width:144px;"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="GROUP_NAME"> 手机类型(发短信) </label></td>
		<td>
			<input type="radio" id="rylx1" name="rylx" value="1" checked="checked">办税人员</input>
			<input type="radio" id="rylx2" name="rylx" value="2">财务经理</input>
			<input type="radio" id="rylx3" name="rylx" value="3">法人</input>
			<input type="radio" id="rylx4" name="rylx" value="4">以上所有</input>
		</td>
	</tr>
	</div>
</table>
</div>

<div align="center"><br>
<input type="button" value="返回" onClick="next('list')"
	class="buttonface" /></div>
	
<br>
	<div style="height:420px">
		<div id="tb">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="seemx()">查看</a>
		</div>
		<table id="datagrid" title='纳税人明细信息' iconCls='icon-save' fitColumns=true singleSelect=false fit=true toolbar="#tb">  
		    <thead>  
		        <tr>
		        	<th field="SFXCMSG_LSH" checkbox="true"></th>			
					<th data-options="field:'NSRBM',width:100,formatter:msgformat" editor='text'>纳税人编码</th>
					<th data-options="field:'NSRMC',width:150,formatter:msgformat" editor='text'>纳税人名称</th>
					<th data-options="field:'PHONETYPE',width:70,formatter:msgformat" editor='text'>手机类型</th>
					<th data-options="field:'SJHM',width:80,formatter:msgformat" editor='text'>手机号码</th>
					<th data-options="field:'MSG',width:200,formatter:msgformat" editor='text'>短信内容</th>
					<th data-options="field:'SMSTYPE_DM',width:50,formatter:msgformat" editor='text'>发送状态</th>
					<th data-options="field:'FS_SJ',width:50,formatter:msgformat" editor='text'>发送时间</th>
					<th data-options="field:'FSCS',width:50,formatter:msgformat" editor='text'>发送次数</th>
					<!-- 
					<th data-options="field:'FR',width:100,formatter:msgformat" editor='text'>法人</th>
					<th data-options="field:'FRSJH',width:100,formatter:msgformat" editor='text'>法人手机</th>
					<th data-options="field:'CWJL',width:100,formatter:msgformat" editor='text'>财务经理</th>
					<th data-options="field:'CWJLSJH',width:100,formatter:msgformat" editor='text'>财务经理手机</th>
					<th data-options="field:'HYDM',width:100,formatter:msgformat" editor='text'>所属行业</th>
					<th data-options="field:'SBFS',width:100,formatter:msgformat" editor='text'>申报方式</th>
					<th data-options="field:'DJZT',width:100,formatter:msgformat" editor='text'>登记状态</th>
					<th data-options="field:'DJLX',width:100,formatter:msgformat" editor='text'>登记类型</th>
					<th data-options="field:'ZCLX',width:100,formatter:msgformat" editor='text'>注册类型</th>
					 -->
		        </tr>  
		    </thead>  
		</table>
	</div>

</div>
</div>
<div id="see_dialog"></div>
</form>
</body>
</html>
<%
	if (request.getAttribute("message") != null
			&& !"".equals(request.getAttribute("message"))) {
%><script
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script>
<%
	}
%>