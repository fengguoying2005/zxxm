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
        <title>机构列表</title>
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
                      $("#ORG_DM").val($(":radio:checked").val());
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
                document.forms[0].action="<%=basePath%>qxgl/org_"+event+".html";
                document.forms[0].submit();
            }
        </script>
    </head>
    <body onload="tablecloth('orgList');">
        <form id="myform" name="myform" action="org" method="post">
            <div class="container" id="mainframe">
                <s:hidden id="ORG_DM" name="ORG_DM"></s:hidden>
                <div class="span-24">

                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
                    </ul>
<div id="tablediv">
<table width="100%" class="tablepanel" cellspacing="0"><tr>                        <td  class="tdtitle">                        <label for="FIND_ORG_DM">
                            机构代码
                        </label>
                        </td>                        <td>                        <s:textfield cssClass="text" id="FIND_ORG_DM" name="FIND_ORG_DM"></s:textfield>
                        </td>                        <td  class="tdtitle">                        <label for="FIND_ORG_MC">
                            机构名称
                        </label>
                        </td>                        <td>                        <s:textfield cssClass="text" id="FIND_ORG_MC" name="FIND_ORG_MC"></s:textfield>
                        </td></tr><tr>                        <td  class="tdtitle">                        <label for="FIND_ORG_TYPE">
                            机构类型
                        </label>
                        </td>                        <td>                            <s:select id="FIND_ORG_TYPE" name="FIND_ORG_TYPE" list="orgtypemap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
                        </td>                        <td  class="tdtitle">                        <label for="FIND_ORG_DESC">
                            机构描述
                        </label>
                        </td>                        <td>                        <s:textfield cssClass="text" id="FIND_ORG_DESC" name="FIND_ORG_DESC"></s:textfield>
                        </td></tr><tr>                        <td  class="tdtitle">                        <label for="FIND_SJ_ORG_DM">
                            上级机构
                        </label>
                        </td>                        <td>                            <s:select id="FIND_SJ_ORG_DM" name="FIND_SJ_ORG_DM" list="sjorgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
                        </td></tr></table></div>
                        <div style="margin-top: 10px;" align="right"><input class="buttonface" type="button" value="查询"
                            onclick="next('list')"></input></div>
                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;机构列表
                    </ul>
                    <table class="record-list" id="orgList" name="orgList">
                        <thead>
                            <tr>
                                <th>
                                    选择
                                </th>
                                <th>
                                    行
                                </th>
                                <th>
                                    机构代码
                                </th>
                                <th>
                                    机构名称
                                </th>
                                <th>
                                    机构类型
                                </th>
                                <th>
                                    机构描述
                                </th>
                                <th>
                                    上级机构
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
                                            includes="FIND_ORG_DM,FIND_ORG_MC,FIND_ORG_TYPE,FIND_ORG_DESC,FIND_SJ_ORG_DM" styleClass="aStyle"
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
                            <s:iterator value="orgs" status="stat">
                                <tr
                                    class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
                                    <td>
                                        <INPUT type="radio" name='radio'
                                            value='<s:property value="ORG_DM" />' />
                                    </td>
                                    <td>
                                        <s:property value="#stat.index+1" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_DM" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_MC" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_TYPE" />
                                    </td>
                                    <td>
                                        <s:property value="ORG_DESC" />
                                    </td>
                                    <td>
                                        <s:property value="SJ_ORG_DM" />
                                    </td>
                                    <td>
                                        <s:property value="YX_BJ" />
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
