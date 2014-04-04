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
        <title>操作员列表</title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">
        <link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
        <link rel="stylesheet" href="resource/css/grid.css" type="text/css"
            media="screen">
        <link rel="stylesheet" href="resource/css/forms.css" type="text/css"
            media="screen">
        <link rel="stylesheet" href="resource/css/component.css"
            type="text/css" />
        <style type="text/css">
            @import "resource/css/jquery.datepick.css";
        </style>
		<link rel="stylesheet" href="resource/css/cssother/TreePanel.css" type="text/css">
        <SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
        <script type="text/javascript" src="js/jquery.datepick.js"></script>
        <script type=text/javascript src="js/common.js"></script>
		<script type=text/javascript src="js/TreePanel.js"></script>
        <script type=text/javascript src="cssmenu2/menu.js"></script>
        <script type="text/javascript">
            function next(event) {
                var ifSelected = false;
                if("toAdd"!=event && "list"!=event && "printExcel"!=event &&"printWord"!=event) {
                    var item = $(":radio:checked");
                    var len=item.length;
                    if(len>0){
                      $("#USER_DM").val($(":radio:checked").val());
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
                document.forms[0].action="<%=basePath%>qxgl/user_"+event+".html";
                document.forms[0].submit();
            }
            function changebm() {
				var bmvalue = $("#FIND_ORG_DM_BM").val();
            	var jg = $("#FIND_ORG_DM_JG").val();
            	$.ajax({	
					url :"<%=basePath%>ajax/liandong!swjgbm.action",
					type : "post",
					dataType : "json",
					data : "dm="+jg,
					async : false,
					error : function() {
						alert("error");
					},
					success : function(data) {
						
						$("#FIND_ORG_DM_BM").empty();
						$("#FIND_ORG_DM_BM").append("<option value=''>--请选择--</option>");
						if(data && data.result) {
							var arr = eval(data.result);
							for(var i = 0; i < arr.length; i ++) {
								var key = arr[i]['key'];
								var value = arr[i]['value'];
								if(key!='' && value!='') {
									var str = "";
									if(key==bmvalue) {
										str = " checked";
										$("#FIND_ORG_DM_BM").append("<option value='"+key+"' selected>"+value+"</option>");
									} else {
										$("#FIND_ORG_DM_BM").append("<option value='"+key+"'>"+value+"</option>");
									}
								}
							}
						}
						return true;
					}
				});
            }
            function clearfiter() {
            	$("#FIND_USER_DM").val("");
            	$("#FIND_USER_MC").val("");
            	$("#FIND_ORG_DM_JG").val("");
            	$("#FIND_ORG_DM_BM").val("");
            }
        </script>
    </head>
    <body onload="tablecloth('userList');changebm();">
        <form id="myform" name="myform" action="user" method="post">
            <div class="container" id="mainframe">
                <s:hidden id="USER_DM" name="USER_DM"></s:hidden>
				<s:hidden id="account" name="account"></s:hidden>
				<s:hidden id="orgdm" name="orgdm"></s:hidden>
				<s:hidden id="assignRole" name="assignRole"></s:hidden>
				<s:hidden id="assignAuth" name="assignAuth"></s:hidden>
                <div class="span-24">

                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
                    </ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0"><tr>                        <td  class="tdtitle">                        <label for="FIND_USER_DM">
                            用户编码
                        </label>
                        </td>                        <td>                        <s:textfield cssClass="text" id="FIND_USER_DM" name="FIND_USER_DM"></s:textfield>
                        </td>                        <td  class="tdtitle">                        <label for="FIND_USER_MC">
                            用户名称
                        </label>
                        </td>                        <td>                        <s:textfield cssClass="text" id="FIND_USER_MC" name="FIND_USER_MC"></s:textfield>
                        </td></tr><tr>                        <td  class="tdtitle">                        <label for="FIND_ORG_DM_JG">
                            机关
                        </label>
                        </td>                        <td>                            <s:select id="FIND_ORG_DM_JG" name="FIND_ORG_DM_JG" list="orgjgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" onchange="changebm();"></s:select>
                        </td>                        <td  class="tdtitle">                        <label for="FIND_ORG_DM_BM">
                            部门
                        </label>
                        </td>                        <td>                            <s:select id="FIND_ORG_DM_BM" name="FIND_ORG_DM_BM" list="orgbmmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
                        </td></tr></table></div>
                        <div style="margin-top: 10px;" align="right">
                        <input class="buttonface" type="button" value="查询" onclick="next('list')"></input>
                        <input class="buttonface" type="button" value="清空条件" onclick="clearfiter()"></input>
                        </div>
                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;操作员列表
                    </ul>
                    <table class="record-list" id="userList" name="userList">
                        <thead>
                            <tr>
                                <th>
                                    选择
                                </th>
                                <th>
                                    行
                                </th>
                                <th>
                                    用户编码
                                </th>
                                <th>
                                    用户名称
                                </th>
                                <th>
                                    性别
                                </th>
                                <th>
                                    机关
                                </th>
                                <th>
                                    部门
                                </th>
                                <th>
                                    有效标记
                                </th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td colspan="8">
                                    <div align="left">
                                        <gwintag:page pageBeanName="pageBean"
                                            includes="FIND_USER_DM,FIND_USER_MC,FIND_ORG_DM_JG,FIND_ORG_DM_BM" styleClass="aStyle"
                                            theme="number" />
                                    </div>
                                    <div align="right">
                                        <input class="buttonface" type="button" value="增加"
                                            onClick="next('toAdd')" />
                                        <input class="buttonface" type="button" value="删除"
                                            onClick="next('del')" />
                                        <input class="buttonface" type="button" value="修改"
                                            onClick="next('toMod')" />
                                        <input class="buttonface" type="button" value="查看"
                                            onClick="next('see')" />
                                        <input class="buttonface" type="button" value="导出"
                                            onClick="next('printExcel')" />
										<input class="buttonface" type="button" value="指派角色"
											onClick="assginRole()" />
										<input class="buttonface" type="button" value="指派权限"
											onClick="assginAuth()" />
                                    </div>
                                </td>
                            </tr>
                        </tfoot>
                        <tbody>
                            <s:iterator value="users" status="stat">
                                <tr
                                    class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
                                    <td>
                                        <INPUT type="radio" name='radio'
                                            value='<s:property value="USER_DM" />' />
                                    </td>
                                    <td>
                                        <s:property value="#stat.index+1" />
                                    </td>
                                    <td>
                                        <s:property value="USER_DM" />
                                    </td>
                                    <td>
                                        <s:property value="USER_MC" />
                                    </td>
                                    <td>
                                        <s:property value="XB_DM" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_DM_JG" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_DM_BM" />
                                    </td>
                                    <td>
                                        <s:property value="YX_BJ" />
                                    </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </div>
                
				<div id="popupbg2"></div>
				<div id="popupdiv2" style="display: none">
					<h2>
						指派角色
						<a href="javascript: close();" class="close">关闭</a>
					</h2>
					<h5>
						给用户<<span id="useraccount"></span>>指派角色
					</h5>
					<div id="roleTree" class="Tree">
					</div>
					<div align="right">
						<input class="buttonface" type="button" value="保存"
							onClick="saveAssginRole();" />
						<input class="buttonface" type="button" value="返回"
							onClick="next('list')" />
					</div>
				</div>
				<div id="popupbg"></div>
				<div id="popupdiv" style="display: none">
					<h2>分配权限<a href="javascript: close();" class="close">关闭</a></h2>
					<h5>给用户<<span id="useraccount2"></span>>分配权限</h5>
					<div id="authTree" class="Tree"> </div>
					<div align="right">
						<input class="buttonface" type="button" value="保存" onClick="saveAssginAuth();" />
						<input class="buttonface" type="button" value="返回" onClick="next('list')" />
					</div>
				</div>
            </div>
        </form>
    </body>
    <script type="text/javascript">

	var tree;
	var tree2;
	function assginAuth() {
		var item = $(":radio:checked");
		var len=item.length;
		if(len>0){
		  $("#account").val($(":radio:checked").val());
		} else {
			alert("请选择一条！");
			return false;
		}
		$("#authTree").html("");
		$("#useraccount2").text($(":radio:checked").parent().parent().children('td').eq(2).html().trim()+":"+$(":radio:checked").parent().parent().children('td').eq(3).html().trim());
		$.ajax({	
			url :"<%=basePath%>ajax/authtree!test.action",
			type : "post",
			dataType : "json",
			data : "name=assginUserAuth___"+$(":radio:checked").val()+"&orgdm="+$("#orgdm").val(),
			//timeout : 20000,
			async : false,
			error : function() {
				alert("erwror");
			},
			success : function(data) {
				tree2 = new TreePanel({
					'root' : eval(data.result)[0],
					'renderTo':"authTree"
				});
				tree2.render();
				tree2.expandAll();
				return true;
			}
		});
		$("#useraccount2").text($(":radio:checked").parent().parent().children('td').eq(2).html().trim()+":"+$(":radio:checked").parent().parent().children('td').eq(3).html().trim());
		var W = screen.width;//取得屏幕分辨率宽度 
		var H = screen.height;//取得屏幕分辨率高度 
		var yScroll;//取滚动条高度 
		if (self.pageYOffset) { 
			yScroll = self.pageYOffset; 
		} else if (document.documentElement && document.documentElement.scrollTop){ 
			yScroll = document.documentElement.scrollTop; 
		} else if (document.body) {
			yScroll = document.body.scrollTop; 
		} 
		$("#popupbg").css( {
			display : "block",
			height : $(document).height()
		});
		var yscroll = document.documentElement.scrollTop;
		$("#popupdiv").css("top", "0px");
		$("#popupdiv").css("left", (W/2 - 200) + "px");
		$("#popupdiv").css("display", "block");
		document.documentElement.scrollTop = 0;
	}
	function assginRole() {
		var item = $(":radio:checked");
		var len=item.length;
		if(len>0){
		  $("#account").val($(":radio:checked").val());
		} else {
			alert("请选择一条！");
			return false;
		}
		$("#roleTree").html("");
		$("#useraccount").text($(":radio:checked").parent().parent().children('td').eq(2).html().trim()+":"+$(":radio:checked").parent().parent().children('td').eq(3).html().trim());
		$.ajax({	
			url :"<%=basePath%>ajax/roletree!test.action",
			type : "post",
			dataType : "json",
			data : "name="+$("#account").val()+"&orgdm="+$("#orgdm").val(),
			//timeout : 20000,
			async : false,
			error : function() {
				alert("erwror");
			},
			success : function(data) {
				tree = new TreePanel({
					'root' : eval(data.result)[0],
					'renderTo':"roleTree"
				});
				tree.render();
				tree.expandAll();
				return true;
			}
		});
		$("#rolerole").text($(":radio:checked").val());
		var W = screen.width;//取得屏幕分辨率宽度 
		var H = screen.height;//取得屏幕分辨率高度 
		var yScroll;//取滚动条高度 
		if (self.pageYOffset) { 
			yScroll = self.pageYOffset; 
		} else if (document.documentElement && document.documentElement.scrollTop){ 
			yScroll = document.documentElement.scrollTop; 
		} else if (document.body) {
			yScroll = document.body.scrollTop; 
		} 
		$("#popupbg2").css( {
			display : "block",
			height : $(document).height()
		});
		var yscroll = document.documentElement.scrollTop;
		
		$("#popupdiv2").css("top", "0px");
		$("#popupdiv2").css("left", (W/2 - 200) + "px");
		 //$("#11").css({position:"absolute","top": e.pageY,"left": e.pageX,"backgroundColor":"yellow"});
		
		$("#popupdiv2").css("display", "block");
		document.documentElement.scrollTop = 0;
	}
	function saveAssginRole() {
		if(tree) {
			var a='';
			for(var i = 0, count=tree.getChecked().length; i < count; i ++){
				a=a+","+(tree.getChecked()[i]);
			}
			$("#assignRole").val(a);
		}
	    $("#account").val($(":radio:checked").val());
		document.forms[0].action="<%=basePath%>qxgl/user!saveAssginRole.action";
		document.forms[0].submit();
	}
	function saveAssginAuth() {
		if(tree2) {
			var a='';
			for(var i = 0, count=tree2.getChecked().length; i < count; i ++){
				a=a+","+(tree2.getChecked()[i]);
			}
			$("#assignAuth").val(a);
		}
	    $("#account").val($(":radio:checked").val());
		document.forms[0].action="<%=basePath%>qxgl/user!saveAssginAuth.action";
		document.forms[0].submit();
	}
	function close() {
		$("#popupbg2").css("display", "none");
		$("#popupdiv2").css("display", "none");
		$("#popupbg").css("display", "none");
		$("#popupdiv").css("display", "none");
	}
    </script>
</html>
<%
	if (request.getAttribute("message") != null
			&& !"".equals(request.getAttribute("message"))) {
%><script
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>");
	</script>
<%
	}
%>