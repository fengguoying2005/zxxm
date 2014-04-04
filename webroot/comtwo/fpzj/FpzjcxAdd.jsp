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
        <title>增加发票中奖查询</title>

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
                $('#LR_SJ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
                $('#LR_SJ').css("width","235px");                $('#FS_SJ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
                $('#FS_SJ').css("width","235px");            });
            function next(event) {
                var isOk = true;
                if(event=="add") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>comtwo/fpzjcx_"+event+".html";
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
                    &nbsp;&nbsp;增加发票中奖查询
                </ul>
                 <div id="tablediv">
                 <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="SSX">
                                	所属市
                            </label>
						</td>
						<td>
                            <s:select id="SSX" name="fpzjcx.SSX" list="sjsxmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="false" requiredsLength="0,50" labels="所属市" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="KJNY">
                                	开奖年月
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="KJNY" name="fpzjcx.KJNY"
                                requireds="false" requiredsLength="0,50" labels="开奖年月" ></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="SJHM">
                                	手机号码
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="SJHM" name="fpzjcx.SJHM"
                                requireds="false" requiredsLength="0,50" labels="手机号码" ></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="LR_SJ">
                                	接收时间
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="LR_SJ" name="fpzjcx.LR_SJ"
                                requireds="false" requiredsLength="0,20" labels="接收时间"  requiredsType="date" ></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="FS_SJ">
                                	发送时间
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="FS_SJ" name="fpzjcx.FS_SJ"
                                requireds="false" requiredsLength="0," labels="发送时间"  requiredsType="date" ></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="FSZT_DM">
                                	发送状态
                            </label>
						</td>
						<td>
                            <s:select id="FSZT_DM" name="fpzjcx.FSZT_DM" list="fsztmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="false" requiredsLength="0,50" labels="发送状态" ></s:select>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="SMSINFO">
                                	短信内容
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="SMSINFO" name="fpzjcx.SMSINFO"
                                requireds="false" requiredsLength="0,500" labels="短信内容" ></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="SMSINFO2">
                                	回复短信
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="SMSINFO2" name="fpzjcx.SMSINFO2"
                                requireds="false" requiredsLength="0,500" labels="回复短信" ></s:textfield>
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