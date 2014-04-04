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
        <title>增加短信模板</title>

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
        	var pos = 0;
        	var isselected = false;
        	function getPosition(obj){
        		isselected = true;
        		var result = 0;
        		if(obj.selectionStart){ //非IE浏览器
        			result = obj.selectionStart
        		}else{ //IE
        			var rng;
        			if(obj.tagName == "TEXTAREA"){ //如果是文本域
        				rng = event.srcElement.createTextRange();
        				rng.moveToPoint(event.x,event.y);
        			}else{ //输入框
        				rng = document.selection.createRange();
        			}
        			rng.moveStart("character",-event.srcElement.value.length);
        			result = rng.text.length;
        		}
        		pos = result;
        	}
            $(function() {
            });
            function next(event) {
                var isOk = true;
                if(event=="add") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>xtgl/dxmb_"+event+".html";
                    frm.submit();
                }
            }
            function adddxys() {
            	if(!isselected && pos == 0) {
            		pos = $("#INFO").val().length;
            	}
            	$("#INFO").val(
            			$("#INFO").val().substr(0,pos)
            			+$("#DXYS option:selected").attr("text")
            			+$("#INFO").val().substr(pos,$("#INFO").val().length));
            	//$("#INFO").val($("#INFO").val()+$("#DXYS option:selected").attr("text"));
            	$("#DXYS").val("");
            	pos = 0;
            	isselected = false;
            }
            function validate() {
            	
            }
        </script>
    </head>
    <body  onload="tablecloth('TABLEMX_1');initReadOnly();">
        <form id="myform" name="myform" action="" method="post">
            <div class="container" id="mainframe">
            <div class="span-24">
                <s:actionerror id="sysactionerror" />
                <ul class="tabletitle" style="line-height:24px;font-weight:bold;">
                    &nbsp;&nbsp;&nbsp;&nbsp;增加短信模板
                </ul>
                 <div id="tablediv">
                 <table width="100%" class="tablepanel" cellspacing="0">
<tr>						<td class="tdtitle">
                            <label for="ORG_DM_JG">
                                	税务机关
                            </label>
						</td>
						<td>
                            <s:select id="ORG_DM_JG" disabled="true" name="dxmb.ORG_DM_JG" list="orgjgmap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="true" requiredsLength="1,20" labels="税务机关" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="SMSTYPE_DM">
                                	模板类型
                            </label>
						</td>
						<td>
                            <s:select id="SMSTYPE_DM" name="dxmb.SMSTYPE_DM" list="smstypemap" headerKey="" headerValue="--选择--" cssStyle="width:210px;" requireds="true" requiredsLength="1,2" labels="模板类型" ></s:select>
						</td>
</tr>
<tr>						<td class="tdtitle">
                            <label for="DXMB_MC">
                                	模板名称
                            </label>
						</td>
						<td>
                            <s:textfield cssClass="text" id="DXMB_MC" name="dxmb.DXMB_MC"
                                requireds="true" requiredsLength="1,50" labels="模板名称" ></s:textfield>
						</td>
						</tr>
<tr>	
						<td class="tdtitle">
                            <label for="INFO">
                                	模板内容
                            </label>
						</td>
						<td>
							<s:textarea onmouseup="getPosition(this)" cssClass="text" id="INFO" name="dxmb.INFO" rows="5" cols="30" cssStyle="width:300px;height:200px"
                                requireds="true" requiredsLength="1,600" labels="模板内容" ></s:textarea><BR>
                            可选表达式：<s:select onchange="adddxys();" id="DXYS" name="DXYS" list="dxysmap" headerKey="" headerValue="--选择--" cssStyle="width:240px;" requireds="false" requiredsLength="0,200" labels="短信要素" ></s:select>
						</td>
						<td class="tdtitle">
                            <label for="BZ">
                                	备注
                            </label>
						</td>
						<td>
                            <s:textarea cssClass="text" id="BZ" name="dxmb.BZ" rows="5" cols="20" cssStyle="width:300px;height:200px"
                                requireds="false" requiredsLength="0,200" labels="备注" ></s:textarea>&nbsp;
						</td>
                    </div>
</tr>                    </div>
                </table>
                </div>
                <div align="center">
                    <br>
                    <input type="button" value="验证短信模板" onClick="next('validatedxmb')"  class="buttonface"/>
                    <input type="button" value="保存" onClick="next('add')"  class="buttonface"/>
                    <input type="button" value="返回" onClick="next('list')"  class="buttonface"/>
                </div>
            </div>
            </div>
        </form>
    </body>
</html>
<%if(request.getAttribute("message")!=null && !"".equals(request.getAttribute("message"))){%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script><%}%>