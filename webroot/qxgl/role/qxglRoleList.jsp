<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>">
		<title>角色列表</title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />
		<link rel="stylesheet" href="resource/css/grid.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/forms.css" type="text/css" media="screen">
		<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
		<style type="text/css">
		@import "resource/css/jquery.datepick.css";
		</style>
		<link rel="stylesheet" href="resource/css/cssother/TreePanel.css" type="text/css">
		
		<SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
		<script type=text/javascript src="js/common.js"></script>
		<script type=text/javascript src="js/TreePanel.js"></script>
		<script type="text/javascript">
			var tree;
			function next(event) {
				var ifSelected = false;
				if("toAdd"!=event && "list"!=event && "printExcel"!=event &&"printWord"!=event) {
                    var item = $(":radio:checked");
                    var len=item.length;
                    if(len>0){
                      $("#ROLE_LSH").val($(":radio:checked").val());
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
				if("assignAuth"==event) {
					$("#authTree").html("");
					$.ajax({	
						url :"<%=basePath%>ajax/authtree!test.action",
						type : "post",
						dataType : "json",
						data : "name="+$("#ROLE_LSH").val(),
						//timeout : 20000,
						async : false,
						error : function() {
							alert("erwror");
						},
						success : function(data) {
							tree = new TreePanel({
								'root' : eval(data.result)[0],
								'renderTo':"authTree"
							});
							tree.render();
							tree.expandAll();
							//tree.expandAll();
							//tree.collapseAll();
							//tree.getChecked().length
							//tree.focusNode.appendChild(new TreeNode({text:text}));
							//tree.focusNode.attributes.text
							return true;
						}
					});
					$("#rolerole").text($(":radio:checked").parent().parent().children('td').eq(2).html());
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
				} else {
					if(tree) {
						var a='';
						for(var i = 0, count=tree.getChecked().length; i < count; i ++){
							a=a+","+(tree.getChecked()[i]);
						}
						$("#assignAuth").val(a);
					}
					document.forms[0].action="<%=basePath%>qxgl/role!"+event+".action";
					document.forms[0].submit();
				}
			}
			function close() {
				$("#popupbg").css("display", "none");
				$("#popupdiv").css("display", "none");
			}
		</script>
	</head>
	<body onload="tablecloth('roleList');">
<form id="myform" name="myform" action="role" method="post">
            <div class="container" id="mainframe">
                <s:hidden id="ROLE_LSH" name="ROLE_LSH"></s:hidden>
                <s:hidden id="assignAuth" name="assignAuth"></s:hidden>
                <div class="span-24">

                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
                    </ul>
<div id="tablediv">
			<table width="100%" class="tablepanel" cellspacing="0">
				<tr>
					<td class="tdtitle">
						<label for="FIND_ROLE_MC">
							角色名称
						</label>
					</td>
					<td>
						<s:textfield cssClass="text" id="FIND_ROLE_MC" name="FIND_ROLE_MC"></s:textfield>
					</td>
					<td class="tdtitle">
						<label for="FIND_ROLE_MS">
							角色描述
						</label>
					</td>
					<td>
						<s:textfield cssClass="text" id="FIND_ROLE_MS" name="FIND_ROLE_MS"></s:textfield>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">
						<label for="FIND_YX_BJ">
							有效标记
						</label>
					</td>
					<td>
						<s:checkboxlist id="FIND_YX_BJ" name="FIND_YX_BJ"
							list="#{1:'有效'}" cssStyle="width:260px;"></s:checkboxlist>
					</td>
				</tr>
			</table>
                        
                       </div>
                        <div style="margin-top: 10px;" align="right"><input class="buttonface" type="button" value="查询"
                            onclick="next('list')"></input></div>
                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;角色列表
                    </ul>
					<table class="record-list" id="roleList" name="roleList">
						<thead>
                            <tr>
                                <th>
                                    选择
                                </th>
                                <th>
                                    行
                                </th>
                                <th>
                                    角色名称
                                </th>
                                <th>
                                    税务机关
                                </th>
                                <th>
                                    角色描述
                                </th>
                                <th>
                                    有效标记
                                </th>
                            </tr>
                        </thead>
						<tfoot>
							<tr>
                                <td colspan="6">
                                    <div align="left">
                                        <gwintag:page pageBeanName="pageBean"
                                            includes="FIND_ROLE_MC,FIND_ROLE_MS,FIND_YX_BJ" styleClass="aStyle"
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
										<input class="buttonface" type="button" value="分配权限" onClick="next('assignAuth')" />
									</div>
								</td>
							</tr>
						</tfoot>
						<tbody>
                            <s:iterator value="roles" status="stat">
                                <tr
                                    class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
                                    <td>
                                        <INPUT type="radio" name='radio' value='<s:property value="ROLE_LSH" />' />
                                    </td>
                                    <td>
                                        <s:property value="#stat.index+1" />
                                    </td>
                                    <td>
                                        <s:property value="ROLE_MC" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_DM_JG" />
                                    </td>
                                    <td>
                                        <s:property value="ROLE_MS" />
                                    </td>
                                    <td>
                                        <s:property value="YX_BJ" />
                                    </td>
                                </tr>
                            </s:iterator>
						</tbody>
					</table>
				</div>
				<div id="popupbg"></div>
				<div id="popupdiv" style="display: none">
					<h2>分配权限<a href="javascript: close();" class="close">关闭</a></h2>
					<h5>给角色<<span id="rolerole"></span>>分配权限</h5>
					<div id="authTree" class="Tree"> </div>
					<div align="right">
						<input class="buttonface" type="button" value="保存" onClick="next('saveAssginAuth')" />
						<input class="buttonface" type="button" value="返回" onClick="next('list')" />
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
	type="text/javascript">window.alert("<%=request.getAttribute("message")%>");
	</script>
<%
	}
%>