<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <base href="<%=basePath%>">
        <title>单据列表</title>
        <link rel="stylesheet" href="resource/css/layout.css" type="text/css" media="screen">        <link rel="stylesheet" href="resource/css/framecommon.css" type="text/css" />        <link rel="stylesheet" href="resource/css/grid.css" type="text/css"
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
	        $(function() {
	            $('#FIND_ZW_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
	            $('#FIND_ZWZ_RQ').datepick({yearRange: 'c-50:c+10',showOnFocus:true,triggerImage:'images/calendar.gif',fixedWeeks:true,dateFormat: 'yyyy-mm-dd',showTrigger:'#aaaaa'});
	        });
            function next(event) {
                var ifSelected = false;
                if("toAdd"!=event && "list"!=event && "printExcel"!=event) {
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
                    if(!window.confirm("确定要核销吗？")) {
                        return false;
                    }
                }
                document.forms[0].action="<%=basePath%>qxgl/djhx!"+event+".action";
                document.forms[0].submit();
            }
        </script>
    </head>
    <body onload="tablecloth('crkzbList');">
        <form id="myform" name="myform" action="crkzb" method="post">
            <div id="man_zone">
                <s:hidden id="LSH" name="LSH"></s:hidden>
                <s:actionmessage  id="sysactionmessage" />
                <s:actionerror id="sysactionerror" />
                <div>

                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;条件查询
                    </ul>
                        <label for="FIND_ZW_RQ">
                            日期
                        </label>
                        <s:textfield cssClass="text" id="FIND_ZW_RQ" name="FIND_ZW_RQ" cssStyle="width:150px;"></s:textfield>
                        <span class="append-1"></span>
                        <label for="FIND_ZWZ_RQ">
                            日期止
                        </label>
                        <s:textfield cssClass="text" id="FIND_ZWZ_RQ" name="FIND_ZWZ_RQ" cssStyle="width:150px;"></s:textfield>
                        <span class="append-1"></span>
                        <label for="FIND_DJLX_DM">
                            单据种类
                        </label>
                        <s:select cssStyle="width:260px" name="FIND_DJLX_DM" list="#{'CD':'采购订单','CS':'采购收货','CF':'采购付款','CT':'采购退货','XD':'销售订单','XK':'销售开单','XS':'销售收款','XX':'现款销售','XT':'销售退货','NL':'领料单','TL':'退料单','CJ':'产品进仓','TJ':'存货调价','RC':'库存变动','CP':'库存盘点','DB':'库存调拨','HF':'付款核销','HS':'收款核销','BJ':'报价单','SS':'售后服务单','FY':'费用记录'}" headerKey="" headerValue="--选择--" cssStyle="width:150px;"></s:select>
                        <br>
                        <label for="FIND_DH">
                            单号
                        </label>
                        <s:textfield cssClass="text" id="FIND_DH" name="FIND_DH" cssStyle="width:150px;"></s:textfield>
                        <span class="append-1"></span>
                        <label for="FIND_YWY_DM">
                            &nbsp;&nbsp;&nbsp;&nbsp;业务员
                        </label>
                        <s:select cssStyle="width:260px" name="FIND_YWY_DM" list="usermap" headerKey="" headerValue="--选择--" cssStyle="width:150px;"></s:select>
                        <span class="append-1"></span>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="buttonface" type="button" value="查询"
                            onclick="next('list')"></input>
                    <ul class="bg_image_onclick" style="line-height:24px;font-weight:bold;">
                    	&nbsp;&nbsp;&nbsp;&nbsp;单据列表
                    </ul>
                    <table class="record-list" id="crkzbList" name="crkzbList">
                        <thead>
                            <tr>
                                <th>
                                    选择
                                </th>
                                <th>
                                    行
                                </th>
                                <th>
                                    单据类型
                                </th>
                                <th>
                                    单号
                                </th>
                                <th>
                                    账务日期
                                </th>
                                <th>
                                    金额
                                </th>
                                <th>
                                    出库单位
                                </th>
                                <th>
                                    入库单位
                                </th>
                                <th>
                                    业务员
                                </th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td colspan="12">
                                    <div align="left">
                                        <gwintag:page pageBeanName="pageBean"
                                            includes="FIND_ZW_RQ,FIND_ZWZ_RQ,FIND_DJLX_DM,FIND_DH,FIND_YWY_DM" styleClass="aStyle"
                                            theme="number" />
                                    </div>
                                    <div align="right">
                                            <!-- 
                                        <input class="buttonface" type="button" value="增加"
                                            onClick="next('toAdd')" />
                                        <input class="buttonface" type="button" value="删除"
                                            onClick="next('del')" />
                                        <input class="buttonface" type="button" value="修改"
                                            onClick="next('toMod')" />
                                             -->
                                        <input class="buttonface" type="button" value="核销"
                                            onClick="next('del')" />
                                    </div>
                                </td>
                            </tr>
                        </tfoot>
                        <tbody>
                            <s:iterator value="crkzbs" status="stat">
                                <tr
                                    class="<s:property value="%{((#stat.index+1)%2==1)?'even':'odd'}"/>">
                                    <td>
                                        <INPUT type="radio" name='radio'
                                            value='<s:property value="LSH" />' />
                                    </td>
                                    <td>
                                        <s:property value="#stat.index+1" />
                                    </td>
                                    <td>
                                        <s:property value="DJLX_DM" />
                                    </td>
                                    <td>
                                        <s:property value="DH" />
                                    </td>
                                    <td>
                                        <s:property value="ZW_RQ" />
                                    </td>
                                    <td>
                                        <s:property value="SHJE" />
                                    </td>
                                    <td>
                                        <s:property value="CKDW_DM" />
                                    </td>
                                    <td>
                                        <s:property value="RKDW_DM" />
                                    </td>
                                    <td>
                                        <s:property value="YWY_DM" />
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