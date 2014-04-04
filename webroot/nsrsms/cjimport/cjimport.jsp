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
        <title>增加角色</title>

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
            	$("#progressbar").css("display","none");
            });
            function next(event) {
            }
            function importFile() {
                var frm = document.myform;
                frm.action="<%=basePath%>nsrsms/cjimport_importfile.html";
            	$("#progressbar").css("display","");
            	$("#importFileBtn").attr("disabled","true");
            	$("#exportFileBtn").attr("disabled","true");
                frm.submit();
            }
            function exportFile() {
            	 var frm = document.myform;
                 frm.action="<%=basePath%>nsrsms/cjexport_export.html";
                 frm.submit();
            }
        </script>
    </head>
    <body  onload="tablecloth('TABLEMX_1');initReadOnly();">
        <form id="myform" name="myform" action="" method="post" enctype="multipart/form-data">
            <div class="container" id="mainframe">
            <s:hidden id="fileName" name="fileName" value="催缴信息模板.xls"></s:hidden>
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;导入催缴信息
                </ul>
                 <div id="tablediv">
                 <table width="100%" class="tablepanel" cellspacing="0">
					<tr>						
						<td class="tdtitle">
                            <label for="ORG_MC">
                                	机关名称
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="ORG_MC" name="ORG_MC"
                                readonly="true"  requiredsLength="1,100" labels="机关名称"  cssStyle="width:250px"></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="NOW_DATE">
                                	当前日期
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="NOW_DATE" name="NOW_DATE"
                                requireds="false" readonly="true" requiredsLength="1,100" labels="当前日期"  cssStyle="width:250px"></s:textfield>&nbsp;
						</td>
					</tr>
					<tr>						
						<td class="tdtitle">
                            <label for="IMPORTFILE">
                                	催缴文件
                            </label>
						</td>
						<td>
							<s:file id="IMPORTFILE" name="myfile" label="导入催缴文件" cssStyle="width:250px"></s:file>
						</td>
						<td class="tdtitle">
                            <label for="title">
                                	短信标题
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="title" name="title"
                                requireds="true" requiredsLength="1,100" labels="短信标题" cssStyle="width:250px"></s:textfield>
						</td>
					</tr>                    
                </table>
                </div>
                <div align="center">
                	<br>
                	<span id="progressbar">请稍候...<img alt="" src="<%=basePath%>/images/progressbar.gif" width="300px" height="20px"/></span>
                    <br><input id="importFileBtn" type="button" value="导入催缴" onClick="importFile()"  class="buttonface"/>&nbsp;&nbsp;
                    <input id="exportFileBtn" type="button" value="下载模板" onClick="exportFile()"  class="buttonface"/>
                </div>
            </div>
            </div>
        </form>
    </body>
</html>
<%if(request.getAttribute("message")!=null && !"".equals(request.getAttribute("message"))){%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script><%}%>