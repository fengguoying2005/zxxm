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
<title>修改短信发送</title>

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
            	tablecloth('TABLEMX_1');
            });
            function next(event) {
                var isOk = true;
                if(event=="mod") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>orgsms/orgsmssend_"+event+".html";
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
            $("#showchecked").click(function(e){
                var s=$("#tree").getCheckedNodes();
                if(s !=null) {
                	for(var i = 0; i < s.length; i ++) {
                		var rowcell = s[i].split("~");
                		if(rowcell.length==10) {//add row
                			//judge have
                			var have = false;
                    		var len = document.getElementById("TABLEMX_1").rows.length;
                			for(var j = 1; j < len; j ++) {
                				with(document.getElementById("TABLEMX_1").rows[j].cells[5].children[0]) {
                					if(value==rowcell[0]) {
                						have = true;
                						break;
                					}
                				}
                			}
                			if(!have) {
                        		if(document.getElementById("TABLEMX_1").rows[1].cells[1].children[0].value!='') {
                        			addNewRow('TABLEMX_1');tablecloth('TABLEMX_1');
                        		}
                        		len = document.getElementById("TABLEMX_1").rows.length;
                        		document.getElementById("TABLEMX_1").rows[len-1].cells[1].children[0].value=rowcell[5];
                        		document.getElementById("TABLEMX_1").rows[len-1].cells[2].children[0].value=rowcell[6];
                        		document.getElementById("TABLEMX_1").rows[len-1].cells[3].children[0].value=rowcell[1];
                        		document.getElementById("TABLEMX_1").rows[len-1].cells[4].children[0].value=rowcell[2];
                        		document.getElementById("TABLEMX_1").rows[len-1].cells[5].children[0].value=rowcell[0];
                        		document.getElementById("TABLEMX_1").rows[len-1].cells[6].children[0].value=rowcell[3];
                        		document.getElementById("TABLEMX_1").rows[len-1].cells[7].children[0].value=rowcell[4];
                			}
                		}
                	}
                } else {
                	alert("NULL");
                }
            });
             $("#showcurrent").click(function(e){
                var s=$("#tree").getCurrentNode();
                if(s !=null)
                    alert(s.text);
                else
                    alert("NULL");
             });
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
<form id="myform" name="myform" action="" method="post"><s:hidden
	id="NSRDATA_LSH" name="NSRDATA_LSH"></s:hidden>
<div class="container" id="mainframe">
<div class="demo">
    <div style="margin-bottom:5px;">
      <a class="button" href="javascript:void(0);" id="showchecked">增加所选人员</a>
  </div>
  <div style="border-bottom: #c3daf9 1px solid; border-left: #c3daf9 1px solid; width: 250px; height: 450px; overflow: auto; border-top: #c3daf9 1px solid; border-right: #c3daf9 1px solid;">
      <div id="tree" class="bbit-tree"></div>
      
  </div>
</div>
<div class="docs">
<s:actionerror id="sysactionerror" />
<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
	&nbsp;&nbsp;&nbsp;&nbsp;修改短信发送
</ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0">
	<tr>
		<td class="tdtitle"><label for="ORG_DM_JG"> 税务机关 </label></td>
		<td><s:select disabled="true" id="ORG_DM_JG" name="orgsmssend.ORG_DM_JG"
			list="orgmap" headerKey="" headerValue="--选择--"
			cssStyle="width:160px;" requireds="true" requiredsLength="1,20"
			labels="税务机关"></s:select></td>
		<td class="tdtitle" rowspan="5"><label for="DXMBINFO"> 短信内容 </label></td>
		<td rowspan="5"><s:textarea cssClass="text" id="DXMBINFO" cssStyle="width:210px;height:150px"
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
</table>
</div>
<ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;待发短信人员列表
                </ul>
<div class="tablediv2"><!-- mx -->
<table class="record-list" id="TABLEMX_1" name="TABLEMX_1">
	<thead>
		<tr>
			<th>选择</th>
			<th>机构</th>
			<th>部门</th>
			<th>人员</th>
			<th>手机号码</th>
			<th></th>
			<th></th>
			<th></th>
		</tr>
	</thead>
	<tbody id="tbody">
		<s:iterator value="tablemx" status="stat">
			<tr
				class='<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>'>
				<td><INPUT type="checkbox" name='radio'
					value='<s:property value="ORG_DM_JG" />' /></td>
				<td><s:textfield readonly="true" cssStyle="width:150px" cssClass="text"
					name="%{'tablemx['+#stat.index+'].ORG_MC_JG'}" requireds="true"
					requiredsLength="1,20" labels="机构"></s:textfield></td>
				<td><s:textfield readonly="true" cssStyle="width:100px" cssClass="text"
					name="%{'tablemx['+#stat.index+'].ORG_MC_BM'}" requireds="true"
					requiredsLength="1,100" labels="部门"></s:textfield></td>
				<td><s:textfield readonly="true" cssStyle="width:70px" cssClass="text"
					name="%{'tablemx['+#stat.index+'].USER_MC'}" requireds="true"
					requiredsLength="1,20" labels="人员"></s:textfield></td>
				<td><s:textfield cssStyle="width:70px" cssClass="text"
					name="%{'tablemx['+#stat.index+'].SJHM'}" requireds="true"
					requiredsLength="1,20" labels="手机号码"></s:textfield></td>
				<td width="0px" style="display: none">
					<s:hidden style="display: none" name="%{'tablemx['+#stat.index+'].USER_DM'}"></s:hidden>
				</td>
				<td width="0px" style="display: none">
					<s:hidden style="display: none" name="%{'tablemx['+#stat.index+'].ORG_DM_JG'}"></s:hidden>
				</td>
				<td width="0px" style="display: none">
					<s:hidden style="display: none" name="%{'tablemx['+#stat.index+'].ORG_DM_BM'}"></s:hidden>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
<div align="right">
<input class="buttonface" type="button" value="删除行"
	onClick="deleteRow('TABLEMX_1');tablecloth('TABLEMX_1');" /></div>
<div align="center"><br>
<input type="button" value="保存" onClick="next('mod')" class="buttonface" />
<input type="button" value="返回" onClick="next('list')"
	class="buttonface" /></div>
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