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
<title>协议定制列表</title>
<link rel="stylesheet" href="resource/css/layout.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/framecommon.css"
	type="text/css" />
<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/component.css" type="text/css" />
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
                      $("#LSH").val($(":radio:checked").val());
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
                document.forms[0].action="<%=basePath%>comtwo/xydz_"+event+".html";
                document.forms[0].submit();
            }
            $(function() {
                $('#FIND_KTRQQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
                $('#FIND_KTRQQ').css("width","185px");
                $('#FIND_KTRQZ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
                $('#FIND_KTRQZ').css("width","185px");
            });
        </script>
</head>
<body onload="tablecloth('xydzList');">
	<form id="myform" name="myform" action="xydz" method="post">
		<div class="container" id="mainframe">
			<s:hidden id="LSH" name="LSH"></s:hidden>
			<div class="span-24">

				<ul class="bg_image_onclick"
					style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="FIND_NSRBM"> 纳税人编码 </label>
							</td>
							<td><s:textfield cssClass="text" id="FIND_NSRBM"
									name="FIND_NSRBM"></s:textfield></td>
							<td class="tdtitle"><label for="FIND_NSRMC"> 纳税人名称 </label>
							</td>
							<td><s:textfield cssClass="text" id="FIND_NSRMC"
									name="FIND_NSRMC"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_ORG_DM"> 税务机关 </label>
							</td>
							<td><s:select id="FIND_ORG_DM" name="FIND_ORG_DM"
									list="orgmap" headerKey="" headerValue="--选择--"
									cssStyle="width:210px;"></s:select></td>
							<td class="tdtitle"><label for="FIND_BZ"> 备注 </label></td>
							<td><s:textfield cssClass="text" id="FIND_BZ" name="FIND_BZ"></s:textfield>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_CBTX"> 催报提醒 </label></td>
							<td>
								<s:select cssStyle="width:210px;" id="FIND_CBTX" name="FIND_CBTX" list="#{'':'--全部--','1':'已开通','0':'未开通'}"></s:select>
							</td>
							<td class="tdtitle"><label for="FIND_CJTX"> 催缴提醒 </label></td>
							<td>
								<s:select cssStyle="width:210px;" id="FIND_CJTX" name="FIND_CJTX" list="#{'':'--全部--','1':'已开通','0':'未开通'}"></s:select>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_JKTX"> 缴款成功提醒 </label>
							</td>
							<td>
								<s:select cssStyle="width:210px;" id="FIND_JKTX" name="FIND_JKTX" list="#{'':'--全部--','1':'已开通','0':'未开通'}"></s:select>
							</td>
							<td class="tdtitle"><label for="FIND_JKSBTX"> 缴款失败提醒
							</label></td>
							<td>
								<s:select cssStyle="width:210px;" id="FIND_JKSBTX" name="FIND_JKSBTX" list="#{'':'--全部--','1':'已开通','0':'未开通'}"></s:select>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_GMFPTX"> 购买发票提醒
							</label></td>
							<td>
								<s:select cssStyle="width:210px;" id="FIND_GMFPTX" name="FIND_GMFPTX" list="#{'':'--全部--','1':'已开通','0':'未开通'}"></s:select>
							</td>
							<td class="tdtitle"><label for="FIND_TYDQTX"> 停业到期提醒
							</label></td>
							<td>
								<s:select cssStyle="width:210px;" id="FIND_TYDQTX" name="FIND_TYDQTX" list="#{'':'--全部--','1':'已开通','0':'未开通'}"></s:select>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="FIND_KTRQQ"> 开通日期起 </label>
							</td>
							<td><s:textfield cssClass="text" id="FIND_KTRQQ"
									name="FIND_KTRQQ"></s:textfield></td>
							<td class="tdtitle"><label for="FIND_KTRQZ"> 开通日期止 </label>
							</td>
							<td><s:textfield cssClass="text" id="FIND_KTRQZ"
									name="FIND_KTRQZ"></s:textfield></td>
						</tr>
					</table>
				</div>
				<div style="margin-top: 10px;" align="right">
					<input class="buttonface" type="button" value="查询"
						onclick="next('list')"></input>
				</div>
				<ul class="bg_image_onclick"
					style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;协议定制列表
				</ul>
				<table class="record-list" id="xydzList" name="xydzList">
					<thead>
						<tr>
							<th>选择</th>
							<th>行</th>
							<th>纳税人编码</th>
							<th>纳税人名称</th>
							<th>税务机关</th>
							<th>催报提醒</th>
							<th>催缴提醒</th>
							<th>缴款成功提醒</th>
							<th>缴款失败提醒</th>
							<th>购买发票提醒</th>
							<th>停业到期提醒</th>
							<th>开通日期起</th>
							<th>开通日期止</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="16">
								<div align="left">
									<gwintag:page pageBeanName="pageBean"
										includes="FIND_NSRBM,FIND_NSRMC,FIND_ORG_DM,FIND_CBTX,FIND_CJTX,FIND_JKTX,FIND_JKSBTX,FIND_GMFPTX,FIND_TYDQTX,FIND_KTRQQ,FIND_KTRQZ,FIND_BZ"
										styleClass="aStyle" theme="number" />
								</div>
								<div align="right">
									<input class="buttonface" type="button" value="增加"
										onClick="next('toAdd')" /> <input class="buttonface"
										type="button" value="删除" onClick="next('del')" /> <input
										class="buttonface" type="button" value="修改"
										onClick="next('toMod')" /> <input class="buttonface"
										type="button" value="查看" onClick="next('see')" />
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<s:iterator value="xydzs" status="stat">
							<tr
								class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
								<td><INPUT type="radio" name='radio'
									value='<s:property value="LSH" />' /></td>
								<td><s:property value="#stat.index+1" /></td>
								<td><s:property value="NSRBM" /></td>
								<td><s:property value="NSRMC" /></td>
								<td><s:property value="ORG_DM" /></td>
								<td><s:property value="CBTX" /></td>
								<td><s:property value="CJTX" /></td>
								<td><s:property value="JKTX" /></td>
								<td><s:property value="JKSBTX" /></td>
								<td><s:property value="GMFPTX" /></td>
								<td><s:property value="TYDQTX" /></td>
								<td><s:property value="KTRQQ" /></td>
								<td><s:property value="KTRQZ" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
<%if(request.getAttribute("message")!=null && !"".equals(request.getAttribute("message"))){%><script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script><%}%>