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
        <title>修改角色</title>

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
        <script type="text/javascript">
            $(function() {
            });
            function next(event) {
                var isOk = true;
                if(event=="mod") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>qxgl/role_"+event+".html";
                    frm.submit();
                }
            }
        </script>
    </head>
    <body  onload="tablecloth('TABLEMX_1');initReadOnly();">
        <form id="myform" name="myform" action="" method="post">
            <s:hidden id="ROLE_LSH" name="ROLE_LSH"></s:hidden>
            <div class="container" id="mainframe">
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改角色
                </ul>
                <div id="tablediv">
                <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="ROLE_MC">
                                	角色名称
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="ROLE_MC" name="role.ROLE_MC"
                                requireds="true" requiredsLength="1,100" labels="角色名称" ></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="ORG_DM_JG">
                                	税务机关
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="ORG_DM_JG" name="role.ORG_DM_JG" list="orgmap" headerKey="" headerValue="--选择--" cssStyle="width:260px;" requireds="false" requiredsLength="0,20" labels="税务机关" ></s:select>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="ROLE_MS">
                                	角色描述
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="ROLE_MS" name="role.ROLE_MS"
                                requireds="false" requiredsLength="0,100" labels="角色描述" ></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="YX_BJ">
                                	有效标记
                            </label>
						</td>
						<td>
                            <s:checkboxlist id="YX_BJ" name="role.YX_BJ" list="#{1:''}" cssStyle="width:260px;" ></s:checkboxlist>
						</td>
                    </div>
</tr>                    </div>
                </table>
                </div>
                <div align="center">
                    <br><input type="button" value="保存" onClick="next('mod')"  class="buttonface"/>
                    <input type="button" value="返回" onClick="next('list')"  class="buttonface"/>
                </div>
            </div>
            </div>
        </form>
    </body>
</html>
<%if(request.getAttribute("message")!=null && !"".equals(request.getAttribute("message"))){%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script><%}%>