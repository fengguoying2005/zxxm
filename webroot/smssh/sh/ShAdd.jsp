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
        <title>增加纳税人群组</title>

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
                    frm.action="<%=basePath%>nsrsms/smssend_"+event+".html";
                    frm.submit();
                }
            }
        </script>
    </head>
    <body>
        <form id="myform" name="myform" action="" method="post">
            <div class="container" id="mainframe">
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;增加纳税人群组
                </ul>
                 <div id="tablediv">
                 <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="SMSTYPE_DM">
                                	短信类型
                            </label>
						</td>
						<td>
                            <s:select id="SMSTYPE_DM" name="group.SMSTYPE_DM" list="smstypemap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="true" requiredsLength="1,2" labels="短信类型" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="GROUP_NAME">
                                	短信标题
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="GROUP_NAME" name="group.GROUP_NAME"
                                requireds="true" requiredsLength="1,100" labels="短信标题" ></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="ORG_DM_JG">
                                	税务机关
                            </label>
						</td>
						<td>
                            <s:select id="ORG_DM_JG" name="group.ORG_DM_JG" list="orgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="true" requiredsLength="1,20" labels="税务机关" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="SMSZT_DM">
                                	短信状态
                            </label>
						</td>
						<td>
                            <s:select id="SMSZT_DM" name="group.SMSZT_DM" list="smsztmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="false" requiredsLength="0,2" labels="短信状态" ></s:select>
						</td>
                    </div>
</tr>                    </div>
                </table>
                </div>
                 <div class="tablediv2">
                 <!-- mx -->
                 <table class="record-list" id="TABLEMX_1" name="TABLEMX_1">
<thead><tr><th>选择</th><th>纳税人编码</th>
<th>纳税人名称</th>
<th>手机号码</th>
<th>有效日期起</th>
<th>有效日期止</th>
<th>征收项目</th>
<th>金额</th>
<th>征收品目</th>
<th>税款所属期起</th>
<th>税款所属期止</th>
</tr>
</thead>
<tbody id="tbody"><s:iterator value="tablemx" status="stat"><tr class='<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>'>
<td><INPUT type="radio" name='radio' value='<s:property value="NSRBM" />' /></td>
<td><s:textfield cssStyle="width:120px" cssClass="text" name="%{'tablemx['+#stat.index+'].NSRBM'}"requireds="true" requiredsLength="1,20" labels="纳税人编码" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:80px" cssClass="text" name="%{'tablemx['+#stat.index+'].NSRMC'}"requireds="true" requiredsLength="1,100" labels="纳税人名称" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:50px" cssClass="text" name="%{'tablemx['+#stat.index+'].SJHM'}"requireds="true" requiredsLength="1,20" labels="手机号码" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:100px" cssClass="text" name="%{'tablemx['+#stat.index+'].CBRQQ'}"requireds="true" requiredsLength="1," labels="有效日期起"  requiredsType="date" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:100px" cssClass="text" name="%{'tablemx['+#stat.index+'].CBRQZ'}"requireds="true" requiredsLength="1," labels="有效日期止"  requiredsType="date" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:50px" cssClass="text" name="%{'tablemx['+#stat.index+'].ZSXM'}"requireds="true" requiredsLength="1,50" labels="征收项目" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:50px" cssClass="text" name="%{'tablemx['+#stat.index+'].JE'}"requireds="true" requiredsLength="1,18" labels="金额"  requiredsType="decimal" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:50px" cssClass="text" name="%{'tablemx['+#stat.index+'].ZSPM'}"requireds="true" requiredsLength="1,50" labels="征收品目" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:50px" cssClass="text" name="%{'tablemx['+#stat.index+'].SKSSQ_Q'}"requireds="true" requiredsLength="1," labels="税款所属期起"  requiredsType="date" ></s:textfield>
</td>
<td><s:textfield cssStyle="width:79px" cssClass="text" name="%{'tablemx['+#stat.index+'].SKSSQ_Z'}"requireds="true" requiredsLength="1," labels="税款所属期止"  requiredsType="date" ></s:textfield>
</td>
</tr></s:iterator>
</tbody>
                 </table>
                 </div>
<div align="right">
	<input class="buttonface" type="button" value="增加行" onClick="addNewRow('TABLEMX_1');tablecloth('TABLEMX_1');" />
	<input class="buttonface" type="button" value="删除行" onClick="deleteRow('TABLEMX_1');tablecloth('TABLEMX_1');" />
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