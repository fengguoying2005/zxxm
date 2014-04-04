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
        <title>短信模板列表</title>
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
        <SCRIPT type="text/javascript" src="js/tablecloth.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="js/jquery-1.4.2.min.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="js/gwinsoft.js"></SCRIPT>
        <script type="text/javascript" src="js/jquery.datepick.js"></script>
        <script type=text/javascript src="js/common.js"></script>
        <script type=text/javascript src="cssmenu2/menu.js"></script>
        <script type="text/javascript">
            function next(event) {
                var ifSelected = false;
                if("toAdd"!=event && "list"!=event && "printExcel"!=event &&"printWord"!=event) {
                    var item = $(":radio:checked");
                    var len=item.length;
                    if(len>0){
                      $("#DXMB_LSH").val($(":radio:checked").val());
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
                document.forms[0].action="<%=basePath%>xtgl/dxmb_"+event+".html";
                document.forms[0].submit();
            }
        </script>
    </head>
    <body onload="tablecloth('dxmbList');">
        <form id="myform" name="myform" action="dxmb" method="post">
            <div class="container" id="mainframe">
                <s:hidden id="DXMB_LSH" name="DXMB_LSH"></s:hidden>
                <div class="span-24">

</tr></table></div>
                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;短信模板列表
                    </ul>
                    <table class="record-list" id="dxmbList" name="dxmbList">
                        <thead>
                            <tr>
                                <th>
                                    选择
                                </th>
                                <th>
                                    行
                                </th>
                                <th>
                                    税务机关
                                </th>
                                <th>
                                    模板类型
                                </th>
                                <th>
                                    模板名称
                                </th>
                                <th>
                                    模板内容
                                </th>
                                <th>
                                    备注
                                </th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td colspan="7">
                                    <div align="left">
                                        <gwintag:page pageBeanName="pageBean"
                                            includes="" styleClass="aStyle"
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
                                    </div>
                                </td>
                            </tr>
                        </tfoot>
                        <tbody>
                            <s:iterator value="dxmbs" status="stat">
                                <tr
                                    class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
                                    <td>
                                        <INPUT type="radio" name='radio'
                                            value='<s:property value="DXMB_LSH" />' />
                                    </td>
                                    <td>
                                        <s:property value="#stat.index+1" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_DM_JG" />
                                    </td>
                                    <td>
                                        <s:property value="SMSTYPE_DM" />
                                    </td>
                                    <td>
                                        <s:property value="DXMB_MC" />
                                    </td>
                                    <td>
                                        <s:property value="INFO" />
                                    </td>
                                    <td>
                                        <s:property value="BZ" />
                                    </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
    </body>
</html>
