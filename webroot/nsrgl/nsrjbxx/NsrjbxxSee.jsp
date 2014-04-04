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
        <title>查看纳税人</title>

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
                frm.action="<%=basePath%>nsrgl/nsrjbxx_"+event+".html";
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
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看纳税人
                </ul>
                <div id="tablediv">
                <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="NSRBM">
                                	纳税人编码
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="NSRBM" name="nsrjbxx.NSRBM"
                                 readonly="true"></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="NSRMC">
                                	纳税人名称
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="NSRMC" name="nsrjbxx.NSRMC"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="HYDM">
                                	所属行业
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="HYDM" name="nsrjbxx.HYDM" list="hymap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="SSS">
                                	所属市
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="SSS" name="nsrjbxx.SSS"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="ORG_DM">
                                	主管税务机关
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="ORG_DM" name="nsrjbxx.ORG_DM" list="orgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="SSZGY">
                                	税收专管员
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="SSZGY" name="nsrjbxx.SSZGY"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="SBFS">
                                	申报方式
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="SBFS" name="nsrjbxx.SBFS" list="sbfsmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="DJZT">
                                	登记状态
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="DJZT" name="nsrjbxx.DJZT" list="djztmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="DJLX">
                                	登记类型
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="DJLX" name="nsrjbxx.DJLX" list="djlxmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="ZCDZ">
                                	注册地址
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="ZCDZ" name="nsrjbxx.ZCDZ"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="ZCLX">
                                	注册类型
                            </label>
						</td>
						<td>
                            <s:select disabled="true" id="ZCLX" name="nsrjbxx.ZCLX" list="zclxmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="FR">
                                	法人
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="FR" name="nsrjbxx.FR"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="FRSJH">
                                	法人手机号码
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="FRSJH" name="nsrjbxx.FRSJH"
                                 readonly="true"></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="CWJL">
                                	财务经理
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="CWJL" name="nsrjbxx.CWJL"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="CWJLSJH">
                                	财务经理手机号码
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="CWJLSJH" name="nsrjbxx.CWJLSJH"
                                 readonly="true"></s:textfield>
						</td>
						<td class="tdtitle">
                            <label for="BSRY">
                                	办税人员
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="BSRY" name="nsrjbxx.BSRY"
                                 readonly="true"></s:textfield>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="BSRYSJH">
                                	办税人员手机号码
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="BSRYSJH" name="nsrjbxx.BSRYSJH"
                                 readonly="true"></s:textfield>
						</td>
                    </div>
</tr>                    </div>
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
