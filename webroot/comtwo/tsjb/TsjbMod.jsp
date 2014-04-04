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
<title>修改投诉举报</title>

<link rel="stylesheet" href="resource/css/layout.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/framecommon.css"
	type="text/css" />
<link rel="stylesheet" href="resource/css/component.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/grid.css" type="text/css"
	media="screen">
<link rel="stylesheet" href="resource/css/forms.css" type="text/css"
	media="screen">
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
        		var tbl=$("#TABLEMX_1");
        	     var trlist=tbl.find("tr");
        	     for(var i=1;i<trlist.length;i++)
        	     {
        	         var tr=$(trlist[i]);
        	         var input=tr.find("INPUT[type='text']");
        	         if(input.val()=='') {
        	        	 
        	         } else {
        	        	 input.attr("readonly",true);
        	         }
        	     }
        			$("[readonly='readonly']").each(function(i) {
        				$(this).css("background-color", "#D4D0C8");
        			});
            });
            function next(event) {
                var isOk = true;
                if(event=="mod") {
                    isOk = $().requireds();
                }
                if(isOk) {
                    var frm = document.myform;
                    frm.action="<%=basePath%>comtwo/tsjb_"+event+".html";
                    frm.submit();
                }
            }
        </script>
</head>
<body>
	<form id="myform" name="myform" action="" method="post">
		<s:hidden id="LSH" name="LSH"></s:hidden>
		<div class="container" id="mainframe">
			<div class="span-24">
				<s:actionerror id="sysactionerror" />
				<ul class="tabletitle" style="line-height: 24px; font-weight: bold;">
					&nbsp;&nbsp;&nbsp;&nbsp;修改投诉举报
				</ul>
				<div id="tablediv">
					<table width="100%" class="tablepanel" cellspacing="0">
						<tr>
							<td class="tdtitle"><label for="SJHM"> 手机号码 </label></td>
							<td><s:textfield cssClass="text" id="SJHM" name="tsjb.SJHM" readonly="true"></s:textfield>
							</td>
							<td class="tdtitle" rowspan="7"><label for="TSINFO"> 投诉内容 </label></td>
							<td rowspan="7">
								<s:textarea cssClass="text" id="TSINFO" name="tsjb.TSINFO" rows="5" cols="30" cssStyle="width:300px;height:80px"
                                 readonly="true"></s:textarea>
							</td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="TS_SJ"> 投诉时间 </label></td>
							<td><s:textfield cssClass="text" id="TSSJ" readonly="true"
									name="tsjb.TSSJ"></s:textfield></td>
						</tr>
						<tr>
							<td class="tdtitle"><label for="HF_BJ"> 回复标记 </label></td>
							<td><s:textfield cssClass="text" id="HF_BJ" readonly="true"
									name="tsjb.HF_BJ" requireds="false"
									labels="回复标记"></s:textfield></td>
							</div>
						</tr>
						</div>
					</table>
				</div>
				<div class="tablediv2">
					<!-- mx -->
					<table class="record-list" id="TABLEMX_1" name="TABLEMX_1">
						<thead>
							<tr>
								<th>回复内容</th>
								<th>回复人</th>
								<th>回复时间</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<s:iterator value="tablemx" status="stat">
								<tr class='<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>'>
									<td>
										<s:textfield cssStyle="width:520px" cssClass="text"
											name="%{'tablemx['+#stat.index+'].HFINFO'}" requireds="true"
											requiredsLength="1,200" labels="回复内容" readonly="%{'tablemx['+#stat.index+'].HFINFO'}==''"></s:textfield>
									</td>
									<td><s:textfield readonly="true" cssStyle="width:90px"
											cssClass="text" name="%{'tablemx['+#stat.index+'].HFR_DM'}"></s:textfield></td>
									<td><s:textfield readonly="true" cssStyle="width:120px"
											cssClass="text" name="%{'tablemx['+#stat.index+'].HFSJ'}"></s:textfield>
										<s:hidden name="%{'tablemx['+#stat.index+'].LSH'}"></s:hidden>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div align="center">
					<br>
					<input type="button" value="保存" onClick="next('mod')"
						class="buttonface" /> <input type="button" value="返回"
						onClick="next('list')" class="buttonface" />
				</div>
			</div>
		</div>
	</form>
</body>
</html>
<%
	if (request.getAttribute("message") != null && !"".equals(request.getAttribute("message"))) {
%>
		<script type="text/javascript">window.alert("<%=request.getAttribute("message")%>");</script>
<%
	}
%>