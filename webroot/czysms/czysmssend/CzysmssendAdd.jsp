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
<title>增加短信发送</title>

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
            $(function() {
            	tablecloth('TABLEMX_1');
            });
            function next(event) {
                var isOk = true;
                if(event=="add") {
                    isOk = $().requireds();

                	if(!isOk) {
                		return;
                	}
                	var s=$("#tree").getCheckedNodes();
          	        if(s !=null)
          	        $("#datagridstr").val(s.join(","));
                	if($("#datagridstr").val()=='') {
                		alert("请选择操作员。");
                		return;
                	}
                }
                if(isOk) {
                	setDisabledBtn(true, ['saveBtn']);
                    var frm = document.myform;
                    frm.action="<%=basePath%>czysms/czysmssend_"+event+".html";
                    frm.submit();
                }
            }
        </script>
        <style type="text/css">
        .demo {
			float: left;
			width: 260px;
		}
		.docs {
			margin-left: 265px;
		}
	    a.button{
	      font-size: 1em;
	      color:blue;
	      margin-top: 4px;
	      margin-bottom: 0px;
	      margin-right: 4px;
	      margin-left: 170px;
	      color: -webkit-link;
		  text-decoration: underline;
		  cursor: auto;
	    }
        </style>
    <link href="js/wdTree/css/tree.css" rel="stylesheet" type="text/css" />
    <script src="js/wdTree/src/Plugins/jquery.tree.js" type="text/javascript"></script>
    <script src="js/wdTree/data/tree1.js" type="text/javascript"></script>
    <script type="text/javascript">
    	function inittree(type) {
    		if(type=='1') {
    			treeurl = "<%=basePath%>ajax/usertree!grouptree.action";
    		} else {
    			treeurl = "<%=basePath%>ajax/usertree!usertree.action";
    		}
    		load();
    	}
        var userAgent = window.navigator.userAgent.toLowerCase();
        $.browser.msie8 = $.browser.msie && /msie 8\.0/i.test(userAgent);
        $.browser.msie7 = $.browser.msie && /msie 7\.0/i.test(userAgent);
        $.browser.msie6 = !$.browser.msie8 && !$.browser.msie7 && $.browser.msie && /msie 6\.0/i.test(userAgent);
        var treeurl = "<%=basePath%>ajax/usertree!grouptree.action";
        function load() {  
            var o = { showcheck: true
            //onnodeclick:function(item){alert(item.text);},        
            };
            $.ajax({	
        		url : treeurl,
        		type : "post",
        		dataType : "json",
        		data : "rytype=czy",
        		async : false,
        		error : function() {
        			alert("erwror");
        		},
        		success : function(data) {
        			o.data = eval(data.result);
        			return true;
        		}
        	}); 
        	
            $("#tree").treeview(o);            
        }   
        if( $.browser.msie6)
        {
            load();
        }
        else{
            $(document).ready(load);
        }
    </script>
</head>
<body>
<form id="myform" name="myform" action="" method="post">
<s:hidden cssClass="text" id="datagridstr" name="datagridstr"></s:hidden>
<s:hidden id="DXQM"  name="orgsmssend.DXQM"></s:hidden>
<div class="container" id="mainframe">
<div class="demo">
    <div>
    <ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择操作员
</ul>
  </div>
  <div style="border-bottom: #c3daf9 1px solid; border-left: #c3daf9 1px solid; width: 250px; height: 450px; overflow: auto; border-top: #c3daf9 1px solid; border-right: #c3daf9 1px solid;">
      <div id="tree" class="bbit-tree"></div>
      
  </div>
</div>
<div class="docs">
<s:actionerror id="sysactionerror" />
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;增加短信发送
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="ORG_DM_JG"> 税务机关 </label></td>
		<td><s:select disabled="true" id="ORG_DM_JG" name="orgsmssend.ORG_DM_JG"
			list="orgmap" headerKey="" headerValue="--选择--"
			cssStyle="width:160px;" requireds="true" requiredsLength="1,20"
			labels="税务机关"></s:select></td>
		<td class="tdtitle" rowspan="6"><label for="DXMBINFO"> 短信内容 </label></td>
		<td rowspan="6"><s:textarea cssClass="text" id="DXMBINFO" cssStyle="width:210px;height:150px"
			name="orgsmssend.DXMBINFO" requireds="true" requiredsLength="1,600"
			labels="短信内容"></s:textarea>
		</td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="SMSTYPE_DM"> 短信类型 </label></td>
		<td><s:select disabled="true" id="SMSTYPE_DM" name="orgsmssend.SMSTYPE_DM"
			list="smstypemap" headerKey="" headerValue="--选择--"
			cssStyle="width:160px;" requireds="true" requiredsLength="1,2"
			labels="短信类型"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="SMSZT_DM"> 短信状态 </label></td>
		<td><s:select disabled="true" id="SMSZT_DM" name="orgsmssend.SMSZT_DM"
			list="smsztmap" headerKey="" headerValue="--选择--"
			cssStyle="width:160px;" requireds="false" requiredsLength="0,2"
			labels="短信状态"></s:select></td>
		</div>
	</tr>
	<tr>
		<td class="tdtitle"><label for="GROUP_NAME"> 短信标题 </label></td>
		<td><s:textfield cssClass="text" id="GROUP_NAME" cssStyle="width:150px;"
			name="orgsmssend.GROUP_NAME" requireds="true" requiredsLength="1,100"
			labels="短信标题"></s:textfield></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="GROUP_NAME"> 选择方式 </label></td>
		<td>
			<input type="radio" name="xzfs" value="1" checked="checked" onclick="inittree('1')">按群组</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="xzfs" value="1" onclick="inittree('2')">按机构</input>
		</td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="CALLBACK">需要回复</label></td>
		<td><s:checkboxlist id="CALLBACK" name="orgsmssend.CALLBACK" list="#{1:''}"
			cssStyle="width:260px;"></s:checkboxlist></td>
	</tr>
</table>
</div>

<div align="center"><br>
<input id="saveBtn" type="button" value="保存" onClick="next('add')" class="buttonface" />
<input type="button" value="返回" onClick="next('list')"
	class="buttonface" /></div><br>
<div style="height:320px;">
	<table id="dataGridmx" width="200px" class="easyui-datagrid" title='待发短信人员列表' iconCls='icon-save' checkOnSelect=false fitColumns=true singleSelect=false fit=true>  
	    <thead>  
	        <tr>
	        	<th data-options="field:'USER_DM',checkbox:true"></th>
				<th data-options="field:'ORG_DM_JG',width:100,formatter:orgformat">机构</th>
				<th data-options="field:'ORG_DM_BM',width:100,formatter:orgbmformat">部门</th>
				<th data-options="field:'ZW_DM',width:100,formatter:zwformat">职务</th>
				<th data-options="field:'USER_MC',width:100,formatter:msgformat">人员</th>
				<th editor="text" data-options="field:'SJHM',required:true,width:100,formatter:msgformat">手机号码</th>
	        </tr>  
	    </thead>  
	</table>
</div>
</div>
</div>
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
<script type="text/javascript">
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