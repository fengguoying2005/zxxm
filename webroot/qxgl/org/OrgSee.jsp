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
        <title>查看机构</title>

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
            function next(event) {
                var frm = document.myform;
                frm.action="<%=basePath%>qxgl/org_"+event+".html";
                frm.submit();
            }
            function initReadOnly() {
                $("[readonly='readonly']").each(function(i){
                    $(this).css("background-color","#D4D0C8");
                });
            }
        </script>
    </head>
    <body  onload="initReadOnly();">
        <form id="myform" name="myform" action="" method="post">
            <div class="container" id="mainframe">
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看机构
                </ul>
                <div id="tablediv">
                <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="ORG_DM">
                                	机构代码
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="ORG_DM" name="org.ORG_DM"
                                 readonly="true"></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="ORG_MC">
                                	机构名称
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="ORG_MC" name="org.ORG_MC"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="ORG_TYPE">
                                	机构类型
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="ORG_TYPE" name="org.ORG_TYPE" list="orgtypemap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="ORG_DESC">
                                	机构描述
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="ORG_DESC" name="org.ORG_DESC"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="SJ_ORG_DM">
                                	上级机构
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="SJ_ORG_DM" name="org.SJ_ORG_DM" list="orgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="YX_BJ">
                                	有效标记
                            </label>
						</td>
						<td>
                            <s:checkboxlist disabled="true" id="YX_BJ" name="org.YX_BJ" list="#{1:''}" cssStyle="width:260px;" ></s:checkboxlist>
						</td>
</tr>         
<tr>						
						<td class="tdtitle">
                            <label for="JGPX">
                                	机关排序
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="JGPX" name="org.JGPX"
                                requireds="false" requiredsLength="0,20" labels="机关排序" readonly="true"></s:textfield>
						</td>
</tr>                   
</div>
                </table>
                </div>
                <div align="center">
                    <br><input type="button" value="返回" onClick="next('list')"  class="buttonface"/>
                </div>
            </div>
            </div>
        </form>
    </body>
</html>
