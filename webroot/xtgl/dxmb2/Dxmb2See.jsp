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
        <title>查看短信模板</title>

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
                frm.action="<%=basePath%>xtgl/dxmb2_"+event+".html";
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
                    &nbsp;&nbsp;&nbsp;&nbsp;查看短信模板
                </ul>
                <div id="tablediv">
                <table width="100%" class="tablepanel" cellspacing="0">
					<tr>
						<td class="tdtitle">
                            <label for="DXMB_MC">
                                	模板名称
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="MBMC" name="dxmb.MBMC"
                                 readonly="true"></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="SMSTYPE_DM">
                                	模板类型
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="DXLX_DM" name="dxmb.DXLX_DM" list="smstypemap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
					</tr>
					<tr>			
						<td class="tdtitle">
                            <label for="INFO">
                                	模板内容
                            </label>
						</td>
						<td>
                             <s:textarea readonly="true" cssClass="text" id="INFO" name="dxmb.INFO" rows="5" cols="20" cssStyle="width:300px;height:200px"
                                requiredsLength="1,200" labels="模板内容" ></s:textarea>
						</td>			<td class="tdtitle">
                            <label for="BZ">
                                	备注
                            </label>
						</td>
						<td>
                            <s:textarea readonly="true" cssClass="text" id="BZ" name="dxmb.BZ" rows="5" cols="20" cssStyle="width:300px;height:200px"
                                requiredsLength="0,200" labels="备注" ></s:textarea>&nbsp;
						</td>
					</tr>
					<tr>		
						<td class="tdtitle">
                            <label for="XGRY_DM">
                                	修改人员
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="XGRY_DM" name="dxmb.XGRY_DM" readonly="true"></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="XG_SJ">
                                	修改时间
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="XG_SJ" name="dxmb.XG_SJ" readonly="true"></s:textfield>
						</td>
					</tr>
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
