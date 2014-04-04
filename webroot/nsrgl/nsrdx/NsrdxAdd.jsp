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
        var userAgent = window.navigator.userAgent.toLowerCase();
        $.browser.msie8 = $.browser.msie && /msie 8\.0/i.test(userAgent);
        $.browser.msie7 = $.browser.msie && /msie 7\.0/i.test(userAgent);
        $.browser.msie6 = !$.browser.msie8 && !$.browser.msie7 && $.browser.msie && /msie 6\.0/i.test(userAgent);
        
        function load() {
            var o = { showcheck: true,
	            url:"<%=basePath%>ajax/usertree!nsrtree.action?FIND_HYDM="+$("#FIND_HYDM").val()
	            		+"&FIND_SBFS="+$("#FIND_SBFS").val()
	            		+"&FIND_DJLX="+$("#FIND_DJLX").val()
	            		+"&FIND_ZCLX="+$("#FIND_ZCLX").val()
	            		+"&FIND_NSRBM="+$("#FIND_NSRBM").val()
	            		+"&FIND_NSRMC="+$("#FIND_NSRMC").val()
            };
            $.ajax({	
        		url :"<%=basePath%>ajax/usertree!nsrtree.action",
        		type : "post",
        		dataType : "json",
        		data : "name=assginUserAuth___",
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
        function init() {  
            if( $.browser.msie6)
            {
                load();
            }
            else{
                $(document).ready(load);
            }
        }
        init();
        function filter() {
        	//$("#tree").reflash("22100000000");
        	init();
        }
        function load2() {
        		var treeurl = "<%=basePath%>ajax/usertree!nsrgrouptree.action";
        		var o = { showcheck: true };
        		$.ajax({	
            		url : treeurl,
            		type : "post",
            		dataType : "json",
            		data : "name=assginUserAuth___",
            		async : false,
            		error : function() {
            			alert("erwror");
            		},
            		success : function(data) {
            			o.data = eval(data.result);
            			return true;
            		}
            	}); 
                $("#tree2").treeview(o);       
            } 
    	function msgformat(val,row) {
    		if(val) {
        		return "<span title='"+val+"'>"+val+"</span>";
    		} else {
    			return "";
    		}
    	}

        $(function() {
        	var turl = "<%=basePath%>"+'nsrgl/nsrgroup!listmx.action';
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
        		queryParams: {LSH:$("#LSH").val(),SMSZT_DM:$("#SMSZT_DM").val()},
        		rownumbers:true,
        		onLoadSuccess : function(data){
        		}
        	});

            load2();
        });
        function select() {
        	var s=$("#tree").getCheckedNodes();
        	      if(s !=null)
        	      alert(s.join(","));
        }
        function next(event) {
            var isOk = true;
            var xzfs;
            if(event=="add") {
                isOk = $().requireds();
            	xzfs = $("input[name='xzfs']:checked").val();
            	if(xzfs=='1') {
                    var s=$("#tree2").getCheckedNodes();
          	        if(s !=null) {
          	        	$("#datas").val(s.join(","));
          	        }
            	} else if(xzfs=='2') {
                    var s=$("#tree").getCheckedNodes();
          	        if(s !=null) {
              	        $("#datas").val(s.join(","));
          	        }
            	} else {
            		var s=$("#btree").getCheckedNodes();
          	        if(s !=null)
          	        $("#datas").val(s.join(","));
            	}
            	if($("#datas").val()=='') {
            		alert("请选择群组或纳税人。");
            		return;
            	}
            }
            if(isOk) {
            	setDisabledBtn(true, ['saveBtn']);
                var frm = document.myform;
                frm.action="<%=basePath%>nsrgl/nsrdx_"+event+".html?rylx="+$("input[name='rylx']:checked").val()+"&xzfs="+xzfs;
                frm.submit();
                //alert($("#datas").val());
            }
        }
        function inittree(type) {
        	//
        	if(type=='1') {
        		$("#div1").hide();
        		$("#div2").show();
        		$("#jqlist").hide();
        	} else if(type=='2') {
        		$("#div1").show();
        		$("#div2").hide();
        		$("#jqlist").hide();
        	} else {
        		$("#div1").hide();
        		$("#div2").hide();
        		$("#jqlist").show();
        	}
        }

        function filter2() {
        	//保证其一内容已填
        	
            var o = { showcheck: true,
	            url:"<%=basePath%>ajax/usertree!thensrtree.action?THE_TEL="+$("#THE_TEL").val()
        		+"&THE_NSRBM="+$("#THE_NSRBM").val()
        		+"&THE_NSRMC="+$("#THE_NSRMC").val()
            };
        	$.ajax({	
        		url :"<%=basePath%>ajax/usertree!thensrtree.action",
        		type : "post",
        		dataType : "json",
        		data :  "THE_TEL="+$("#THE_TEL").val()
		        		+"&THE_NSRBM="+$("#THE_NSRBM").val()
		        		+"&THE_NSRMC="+$("#THE_NSRMC").val(),
        		async : false,
        		error : function() {
        			alert("ERROR");
        		},
        		success : function(data) {
        			o.data = eval(data.result);
        			return true;
        		}
        	});      
            $("#btree").treeview(o);
        }
    </script>
</head>
<body>
<form id="myform" name="myform" action="" method="post">
<div class="container" id="mainframe">

	<div id="jqlist" class="demo" style="display:none">
		<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择纳税人
		</ul>
		<div id="tablediv">
		<table width="100%" class="tablepanel" cellspacing="0">
			<tr>
				<td class="tdtitle" style="width: 30%"><label for="THE_NSRBM">
				纳税人编码 </label></td>
				<td style="width: 70%">
					<s:textfield id="THE_NSRBM" name="THE_NSRBM" cssClass="text" cssStyle="width:144px;"></s:textfield>
				</td>
			</tr>
			<tr>
				<td class="tdtitle" style="width: 30%"><label for="THE_NSRMC">
				纳税人名称 </label></td>
				<td style="width: 70%">
					<s:textfield id="THE_NSRMC" name="THE_NSRMC" cssClass="text" cssStyle="width:144px;"></s:textfield>
				</td>
			</tr>
			<tr>
				<td class="tdtitle" style="width: 30%"><label for="THE_TEL">
				手机号码 </label></td>
				<td style="width: 70%">
					<s:textfield id="THE_TEL" name="THE_TEL" cssClass="text" cssStyle="width:144px;"></s:textfield>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="width: 100%;float: right;">
					<input type="button" value="查询" onClick="filter2()" class="buttonface" />
				</td>
			</tr>
		</table>
		</div>
		<div
			style="border-bottom: #c3daf9 1px solid; border-left: #c3daf9 1px solid; width: 258px; height: 450px; overflow: auto; border-top: #c3daf9 1px solid; border-right: #c3daf9 1px solid;">
		<div id="btree" class="bbit-tree"></div>
		
		</div>
	</div>
<div id="div1" class="demo" style="display:none">
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择纳税人
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle" style="width: 30%"><label for="FIND_NSRBM">
		纳税人编码 </label></td>
		<td style="width: 70%">
			<s:textfield id="FIND_NSRBM" name="FIND_NSRBM" cssClass="text" cssStyle="width:144px;" onkeyup="filter()"></s:textfield>
		</td>
	</tr>
	<tr>
		<td class="tdtitle" style="width: 30%"><label for="FIND_NSRMC">
		纳税人名称 </label></td>
		<td style="width: 70%">
			<s:textfield id="FIND_NSRMC" name="FIND_NSRMC" cssClass="text" cssStyle="width:144px;" onkeyup="filter()"></s:textfield>
		</td>
	</tr>
	
	<tr>
		<td class="tdtitle" style="width: 30%"><label for="FIND_HYDM">
		所属行业 </label></td>
		<td style="width: 70%"><s:select id="FIND_HYDM" name="FIND_HYDM"
			list="hymap" headerKey="" headerValue="--选择--"
			cssStyle="width:160px;" onchange="filter()"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_SBFS"> 申报方式 </label></td>
		<td><s:select id="FIND_SBFS" name="FIND_SBFS" list="sbfsmap"
			headerKey="" headerValue="--选择--" cssStyle="width:160px;"
			onchange="filter()"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_DJLX"> 登记类型 </label></td>
		<td><s:select id="FIND_DJLX" name="FIND_DJLX" list="djlxmap"
			headerKey="" headerValue="--选择--" cssStyle="width:160px;"
			onchange="filter()"></s:select></td>
	</tr>
	<tr>
		<td class="tdtitle"><label for="FIND_ZCLX"> 注册类型 </label></td>
		<td><s:select id="FIND_ZCLX" name="FIND_ZCLX" list="zclxmap"
			headerKey="" headerValue="--选择--" cssStyle="width:160px;"
			onchange="filter()"></s:select></td>
	</tr>
</table>
</div>
<div style="border-bottom: #c3daf9 1px solid; border-left: #c3daf9 1px solid; width: 258px; height: 450px; overflow: auto; border-top: #c3daf9 1px solid; border-right: #c3daf9 1px solid;">
<div id="tree" class="bbit-tree"></div>

</div>
</div>

<div id="div2" class="demo">
	<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;选择纳税人组
</ul>
<div style="border-bottom: #c3daf9 1px solid; border-left: #c3daf9 1px solid; width: 258px; height: 450px; overflow: auto; border-top: #c3daf9 1px solid; border-right: #c3daf9 1px solid;">
<div id="tree2" class="bbit-tree"></div>

</div>
</div>
<div class="docs">
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
		
		<td class="tdtitle" rowspan="6"><label for="DXMBINFO"> 短信内容 </label></td>
		<td rowspan="6"><s:textarea cssClass="text" id="DXMBINFO" cssStyle="width:210px;height:150px"
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
	<tr>
		<td class="tdtitle"><label for="GROUP_NAME"> 选择方式 </label></td>
		<td>
			<input type="radio" name="xzfs" value="1" checked="checked" onclick="inittree('1')">按群组</input>&nbsp;&nbsp;&nbsp;
			<input type="radio" name="xzfs" value="2" onclick="inittree('2')">按机构</input>&nbsp;&nbsp;&nbsp;
			<input type="radio" name="xzfs" value="3" onclick="inittree('3')">精确定位
			
		</td>
	</tr>
	</div>
</table>
</div>

<div align="center"><br>
<input type="button" id="saveBtn" value="保存" onClick="next('add')" class="buttonface" />
<input type="button" value="返回" onClick="next('list')"
	class="buttonface" /></div>
	
<br>
	<div style="height:420px">
		<table id="datagrid" title='纳税人明细信息' iconCls='icon-save' fitColumns=true singleSelect=false fit=true>  
		    <thead>  
		        <tr>  											
					<th data-options="field:'ORG_DM',width:100,formatter:msgformat" editor='text'>主管机关</th>
					<th data-options="field:'SSZGY',width:100,formatter:msgformat" editor='text'>专管员</th>
					<th data-options="field:'NSRBM',width:100,formatter:msgformat" editor='text'>纳税人编码</th>
					<th data-options="field:'NSRMC',width:150,formatter:msgformat" editor='text'>纳税人名称</th>
					<th data-options="field:'BSRY',width:100,formatter:msgformat" editor='text'>办税人</th>
					<th data-options="field:'BSRYSJH',width:100,formatter:msgformat" editor='text'>办税人手机</th>
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