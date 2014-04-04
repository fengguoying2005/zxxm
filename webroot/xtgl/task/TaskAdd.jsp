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
        <title>增加定时任务</title>

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
                if(event=="add") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>xtgl/task_"+event+".html";
                    frm.submit();
                }
            }
        </script>
    </head>
    <body  onload="tablecloth('TABLEMX_1');initReadOnly();">
        <form id="myform" name="myform" action="" method="post">
            <div class="container" id="mainframe">
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;增加定时任务
                </ul>
                 <div id="tablediv">
                 <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="TASKNAME">
                                	任务名称
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="TASKNAME" name="task.TASKNAME" readonly="true"
                                requireds="true" requiredsLength="1,20" labels="任务名称" ></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="RULE1">
                                	时间表达式
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="RULE1" name="task.RULE1"
                                requireds="false" requiredsLength="0,50" labels="时间表达式" ></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="RULE2">
                                	备注
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="RULE2" name="task.RULE2"
                                requireds="false" requiredsLength="0,100" labels="备注" ></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="YX_BJ">
                                	有效标记
                            </label>
						</td>
						<td>
                            <s:checkboxlist id="YX_BJ" name="task.YX_BJ" list="#{1:''}" cssStyle="width:260px;" ></s:checkboxlist>
						</td>
                    </div>
</tr>                    </div>
                </table>
                </div>
                <div align="center">
                    <br><input type="button" value="保存" onClick="next('add')"  class="buttonface"/>
                    <input type="button" value="返回" onClick="next('list')"  class="buttonface"/>
                </div>
            </div>
            </div>
        </form>
    </body>
</html>
<%if(request.getAttribute("message")!=null && !"".equals(request.getAttribute("message"))){%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script><%}%>