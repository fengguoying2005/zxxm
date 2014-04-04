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
        <title>纳税人导入</title>

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
            	if($("#ORG_DM").val()=='') {
            		alert("请选择要导入的税务机关。");
            		return;
            	}
                var frm = document.myform;
                frm.action="<%=basePath%>nsrgl/nsrimport_importfile.html";
            	$("#progressbar").css("display","");
            	$("#importFileBtn").attr("disabled","true");
            	$("#exportFileBtn").attr("disabled","true");
                frm.submit();
            }
            function exportFile() {
            	 var frm = document.myform;
                 frm.action="<%=basePath%>nsrgl/nsrexport_export.html";
                 frm.submit();
            }
        </script>
    </head>
    <body  onload="tablecloth('TABLEMX_1');initReadOnly();">
        <form id="myform" name="myform" action="" method="post" enctype="multipart/form-data">
            <div class="container" id="mainframe">
            <s:hidden id="fileName" name="fileName" value="催报信息模板.xls"></s:hidden>
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;批量导入纳税人
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
                            <s:select id="ORG_DM" name="ORG_DM" list="orgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="false" requiredsLength="0,50" labels="主管税务机关" ></s:select>
						</td>
												
						<td class="tdtitle">
                            <label for="IMPORTFILE">
                                	纳税人文件
                            </label>
						</td>
						<td>
							<s:file id="IMPORTFILE" name="myfile" label="导入催报文件" cssStyle="width:250px"></s:file>
						</td>
					</tr>
                </table>
                </div>
                <div align="center">
                	<br>
                	<span id="progressbar">请稍候...<img alt="" src="<%=basePath%>/images/progressbar.gif" width="300px" height="20px"/></span>
                    <br><input id="importFileBtn" type="button" value="导入纳税人" onClick="importFile()"  class="buttonface"/>&nbsp;&nbsp;
                    <input id="exportFileBtn" type="button" value="下载模板" onClick="exportFile()"  class="buttonface"/>
                </div>
            </div>
            </div>
        </form>
    </body>
</html>
<%if(request.getAttribute("message")!=null && !"".equals(request.getAttribute("message"))){%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script><%}%>