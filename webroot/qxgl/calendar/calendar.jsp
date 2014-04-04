<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="/gwintag" prefix="gwintag"%>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>日程表</title>
<style type="text/css">
/* 页面基本样式 */
body, td, input {
    font-family:Arial;
    font-size:12px;
}

/* 日程表格样式 */
#calTable {
    border-collapse:collapse;
    border:5px solid #C3D9FF;
}

/* 每日单元格样式 */
td.calBox {
    border:1px solid #CCDDEE;
    width:100px;
    height:40px;
    vertical-align:top;
}

/* 每日单元格内日期样式 */
td.calBox div.date {
    background:#E8EEF7;
    font-size:11px;
    padding:2px;
    cursor:pointer;
}

/* 每日单元格内周六周日样式 */
td.sat div.date, td.sun div.date{
    color:red;
}

/* 今日样式 */
td.calBox div.today {
    background:#BBCCDD;
}

/* 周标识样式 */
td.day {
    text-align:center;
    background:#C3D9FF;
    border:1px solid #CCDDEE;
    color:#112ABB;
}

/* 当前显示的年月样式 */
#dateInfo {
    font-weight:bold;
    margin:3px;
}

/* 添加任务div样式 */
#addBox {
    display:none;
    position:absolute;
    width:500px;
    border:1px solid #000;
    height:420px;
    background:#F0F0EE;
    padding:10px;
}

/* 添加任务内日期样式 */
#taskDate {
    height:30px;
    font-weight:bold;
    padding:3px;
}

/* 按钮样式 */
.taskBtn {
    margin:10px;
}

/* 编辑任务div样式 */
#editBox {
    display:none;
    position:absolute;
    width:500px;
    border:1px solid #000;
    height:400px;
    background:#F0F0EE;
    padding:10px;
}

/* 任务样式 */
div.task {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#668CB3;
    border:1px solid #FFF;
    color:#FFF;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskJSR00 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#B93F3F;
    border:1px solid #FFF;
    color:#FFF;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskJSR01 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#6E75D9;
    border:1px solid #FFF;
    color:#FFF;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskJSR10 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#59C478;
    border:1px solid #FFF;
    color:#FFF;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskCSR00 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#FFD0D0;
    border:1px solid #FFF;
    color:#000;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskCSR01 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#C1C4EE;
    border:1px solid #FFF;
    color:#000;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskCSR10 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#AEEFC1;
    border:1px solid #FFF;
    color:#000;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskFSR00 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#FF6D6D;
    border:1px solid #FFF;
    color:#FFF;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskFSR01 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#484A62;
    border:1px solid #FFF;
    color:#FFF;
    padding:1 2 1 3;
    cursor:pointer;
}
div.taskFSR10 {
    width:98px;
    overflow:hidden;
    white-space:nowrap;
    background:#4B7E5A;
    border:1px solid #FFF;
    color:#FFF;
    padding:1 2 1 3;
    cursor:pointer;
}
</style>
		<SCRIPT type="text/javascript" src="js/xheditor/jquery-1.4.4.min.js"></SCRIPT>
		<!-- 
		<SCRIPT type="text/javascript" src="js/xheditor/xheditor-1.1.11-zh-cn.min.js"></SCRIPT>
		 -->
		 <!-- kindeditor add -->
		<SCRIPT type="text/javascript" src="js/kindeditor/kindeditor-min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></SCRIPT>
<script type="text/javascript"><!--
var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);    //每月天数
var today = new Today();    //今日对象
var year = today.year;      //当前显示的年份
var month = today.month;    //当前显示的月份

//页面加载完毕后执行fillBox函数
$(function() {
    fillBox();
});

//今日对象
function Today() {
    this.now = new Date();
    this.year = this.now.getFullYear();
    this.month = this.now.getMonth();
    this.day = this.now.getDate();
}

//根据当前年月填充每日单元格
function fillBox() {
    updateDateInfo();                   //更新年月提示
    $("td.calBox").empty();             //清空每日单元格

    var dayCounter = 1;                 //设置天数计数器并初始化为1
    var cal = new Date(year, month, 1); //以当前年月第一天为参数创建日期对象
    var startDay = cal.getDay();        //计算填充开始位置
    //计算填充结束位置
    var endDay = startDay + getDays(cal.getMonth(), cal.getFullYear()) - 1;

    //如果显示的是今日所在月份的日程，设置day变量为今日日期
    var day = -1;
    if (today.year == year && today.month == month) {
        day = today.day;
    }

    //从startDay开始到endDay结束，在每日单元格内填入日期信息
    for (var i=startDay; i<=endDay; i++) {
        if (dayCounter==day) {
            $("#calBox" + i).html("<div class='date today' id='" + year + "-" + (month + 1) + "-" + dayCounter + "' onclick='openAddBox(this)'>今天</div>");
        } else {
            $("#calBox" + i).html("<div class='date' id='" + year + "-" + (month + 1) + "-" + dayCounter + "' onclick='openAddBox(this)'>" + dayCounter + "</div>");
        }
        dayCounter++;
    }
    getTasks();                         //从服务器获取任务信息
}

//从服务器获取任务信息
function getTasks() {
	$.ajax({	
    	url :"<%=basePath%>addcalendar!getTasks.action",
    	type : "post",
    	dataType : "json",
    	data : "data="+year + "-" + (month + 1),
    	//timeout : 20000,
    	async : false,
    	error : function() {
    		alert("erwror");
    	},
    	success : function(data) {
    		var tasks = eval(data.result);
    		if(tasks)
    		for(var i = 0; i < tasks.length; i ++) {
    			buildTask(tasks[i]['builddate'], tasks[i]['id'], tasks[i]['task'], tasks[i]['RYDM'], tasks[i]['ZTDM'], tasks[i]['FSRJSR'], tasks[i]['JSRDM']);
    		}
    		return true;
    	}
    });
}

//根据日期、任务编号、任务内容在页面上创建任务节点
function buildTask(buildDate, taskId, taskInfo, RYDM, ZTDM, FSRJSR,JSRDM) {
	//RYDM:JSR,FSR,CSR
	//ZTDM:00录入
	if("JSR"==RYDM) {
		$("#" + buildDate).parent().append("<div id='task" + taskId + "' class='task"+RYDM+ZTDM+"' onclick='editTaskJSR(this,\""+ZTDM+"\",\""+FSRJSR+"\",\""+JSRDM+"\")' title='"+removeImgAttr(taskInfo)+"'>" + taskInfo + "</div>");
	} else if("FSR"==RYDM) {
		$("#" + buildDate).parent().append("<div id='task" + taskId + "' class='task"+RYDM+ZTDM+"' onclick='editTaskFSR(this,\""+ZTDM+"\",\""+JSRDM+"\")' title='"+removeImgAttr(taskInfo)+"'>" + taskInfo + "</div>");
	} else if("CSR"==RYDM) {
		$("#" + buildDate).parent().append("<div id='task" + taskId + "' class='task"+RYDM+ZTDM+"' onclick='editTaskCSR(this,\""+ZTDM+"\",\""+JSRDM+"\")' title='"+removeImgAttr(taskInfo)+"'>" + taskInfo + "</div>");
	}
}

//判断是否闰年返回每月天数
function getDays(month, year) {
    if (1 == month) {
        if (((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400)) {
            return 29;
        } else {
            return 28;
        }
    } else {
        return daysInMonth[month];
    }
}

//显示上月日程
function prevMonth() {
    if ((month - 1) < 0) {
        month = 11;
        year--;
    } else {
        month--;
    }
    fillBox();              //填充每日单元格
}

//显示下月日程
function nextMonth() {
    if((month + 1) > 11) {
        month = 0;
        year++;
    } else {
        month++;
    }
    fillBox();              //填充每日单元格
}

//显示本月日程
function thisMonth() {
    year = today.year;
    month = today.month;
    fillBox();              //填充每日单元格
}

//更新年月提示
function updateDateInfo() {
    $("#dateInfo").html(year + "年" + (month + 1) + "月");
}

//打开新建任务box
function openAddBox(src) {
    $("#taskDate").html(src.id);                    //设置新建日期
    $("#taskInfo").val("");                         //初始化新建任务内容
    var left = getLeft(src) - 15;                   //设置左边距
    var top = getTop(src) - 15;                     //设置顶边距
    //$("#addBox").left(left).top(top).slideDown();   //显示新建任务box
    $("#addBox").css({position: "absolute",'top':top,'left':left,'z-index':2});
    $("#addBox").offset().left=left;
    $("#addBox").offset().top=top;
    $("#addBox").slideDown();
}

//向服务器提交新任务信息
function addTask() {
	$("#taskInfo").val(editor.html());//kindeditor add
    var taskInfo = $("#taskInfo").val();                //获取任务信息
    //检查任务信息是否为空
    if ($.trim(taskInfo)=="") {
        alert("请输入任务信息。");
    } else {
        var buildDate = $("#taskDate").html();          //获取任务日期
        var DATA_CSR_DM="";
        var SELECT_CSR_DM = document.getElementById('CSR_DM');
        for(var j=0;j<SELECT_CSR_DM.options.length;j++)  
        {
        	var tempOption=SELECT_CSR_DM.options[j];
        	if(tempOption.selected) {
        		DATA_CSR_DM += (tempOption.value+";");
        	}
        }
        $.ajax({	
        	url :"<%=basePath%>addcalendar!add.action",
        	type : "post",
        	dataType : "json",
        	data : "msg="+taskInfo+"&date="+buildDate+"&JSR_DM="+$("#JSR_DM").val()+"&CSR_DM="+DATA_CSR_DM,
        	//data : "data="+taskInfo+";"+buildDate+"&JSR_DM="+$("#JSR_DM").val()+"&CSR_DM="+DATA_CSR_DM,
        	//timeout : 20000,
        	async : false,
        	error : function() {
        		alert("erwror");
        	},
        	success : function(data) {
        		var taskId = eval(data.result)[0]['key'];
        		buildTask(buildDate, taskId, taskInfo, 'FSR', '00', '1', $("#JSR_DM").val()); //建立任务节点 TODO  可以判断是否接收人是本人
                closeAddBox();                          //关闭新建任务box
        		return true;
        	}
        });
    }
}
//更新内容
function updateINFO() {
	    
	$("#editTaskInfo").val(editor2.html());//kindeditor add
    var taskInfo = $("#editTaskInfo").val();                //获取任务信息
	var taskId = $("#taskId").val();                //任务编号
	var rcztdm = $("#rcztdm").val();
    //检查任务信息是否为空
    if ($.trim(taskInfo)=="") {
        alert("请输入任务信息。");
    } else {
        var buildDate = $("#taskDate").html();          //获取任务日期
        var DATA_CSR_DM="";
        var SELECT_CSR_DM = document.getElementById('CSR_DM');
        for(var j=0;j<SELECT_CSR_DM.options.length;j++)  
        {
        	var tempOption=SELECT_CSR_DM.options[j];
        	if(tempOption.selected) {
        		DATA_CSR_DM += (tempOption.value+";");
        	}
        }
        $.ajax({	
        	url :"<%=basePath%>addcalendar!modInfo.action",
        	type : "post",
        	dataType : "json",
        	data : "taskId="+taskId+"&taskInfo="+taskInfo+"&rcztdm="+rcztdm,
        	//timeout : 20000,
        	async : false,
        	error : function() {
        		alert("erwror");
        	},
        	success : function(data) {
        		$("#task" + taskId).html(taskInfo); //更新页面任务内容
                closeEditBox();                     //关闭编辑box
        		return true;
        	}
        });
    }
}
//关闭新建任务box
function closeAddBox() {
    $("#addBox").slideUp();
}

//打开编辑任务box
function editTaskJSR(src, ZTDM, FSRJSR, JSRDM) {
	$("#jsrdm").text(JSRDM);
	//alert(html2str(src.innerHTML));
	editor2.html(src.innerHTML);//kindeditor add
    $("#taskId").val(src.id.substr(4));             //对任务编号隐藏域赋值
    $("#editTaskInfo").val(src.innerHTML);          //设置编辑内容
    var left = getLeft(src) - 15;                   //设置左边距
    var top = getTop(src) - 15;                     //设置顶边距
    //$("#editBox").left(left).top(top).slideDown();  //显示编辑任务box
    
    $("#editBox").css({position: "absolute",'top':top,'left':left,'z-index':2});
    $("#editBox").offset().left=left;
    $("#editBox").offset().top=top;
    $("#editBox").slideDown();
    $("#rcztdm").val(ZTDM);
    //document.getElementById("updateabled").style="display:block";
    document.getElementById("updateTask").disabled="";
    if(("1"==FSRJSR)&&(("00"==ZTDM || "01"==ZTDM))) {
        document.getElementById("delTaskBtn").disabled="";
        document.getElementById("updateInfoBtn").disabled="";
    } else {
        document.getElementById("delTaskBtn").disabled="true";
        document.getElementById("updateInfoBtn").disabled="true";
    }
}
//打开编辑任务box
function editTaskFSR(src, ZTDM, JSRDM) {
	$("#jsrdm").text(JSRDM);
	//alert(html2str(src.innerHTML));
	editor2.html(src.innerHTML);//kindeditor add
    $("#taskId").val(src.id.substr(4));             //对任务编号隐藏域赋值
    $("#editTaskInfo").val(src.innerHTML);          //设置编辑内容
    var left = getLeft(src) - 15;                   //设置左边距
    var top = getTop(src) - 15;                     //设置顶边距
    //$("#editBox").left(left).top(top).slideDown();  //显示编辑任务box
    $("#editBox").css({position: "absolute",'top':top,'left':left,'z-index':2});
    $("#editBox").offset().left=left;
    $("#editBox").offset().top=top;
    $("#editBox").slideDown();
    $("#rcztdm").val(ZTDM);
    //document.getElementById("updateabled").style="display:none";
    document.getElementById("updateTask").disabled="true";
    if("00"==ZTDM || "01"==ZTDM) {
        document.getElementById("delTaskBtn").disabled="";
        document.getElementById("updateInfoBtn").disabled="";
    } else {
        document.getElementById("delTaskBtn").disabled="true";
        document.getElementById("updateInfoBtn").disabled="true";
    }
}
//打开编辑任务box
function editTaskCSR(src, ZTDM, JSRDM) {
	$("#jsrdm").text(JSRDM);
	//alert(html2str(src.innerHTML));
	editor2.html(src.innerHTML);//kindeditor add
    $("#taskId").val(src.id.substr(4));             //对任务编号隐藏域赋值
    $("#editTaskInfo").val(src.innerHTML);          //设置编辑内容
    var left = getLeft(src) - 15;                   //设置左边距
    var top = getTop(src) - 15;                     //设置顶边距
    //$("#editBox").left(left).top(top).slideDown();  //显示编辑任务box
    $("#editBox").css({position: "absolute",'top':top,'left':left,'z-index':2});
    $("#editBox").offset().left=left;
    $("#editBox").offset().top=top;
    $("#editBox").slideDown();
    $("#rcztdm").val(ZTDM);
    //document.getElementById("updateabled").style="display:none";
    document.getElementById("updateTask").disabled="true";
    document.getElementById("delTaskBtn").disabled="true";
    document.getElementById("updateInfoBtn").disabled="true";
}

//删除任务
function delTask() {
    var taskId = $("#taskId").val();                //获取任务编号
    $.ajax({	
    	url :"<%=basePath%>addcalendar!del.action",
    	type : "post",
    	dataType : "json",
    	data : "data="+taskId,
    	//timeout : 20000,
    	async : false,
    	error : function() {
    		alert("erwror");
    	},
    	success : function(data) {
    		 $("#task" + taskId).remove();           //在页面删除任务节点
             closeEditBox();                         //关闭编辑box
    		return true;
    	}
    });
}

//关闭编辑box
function closeEditBox() {
    $("#editBox").slideUp();
}

//更新任务信息
function updateTask() {
    var taskId = $("#taskId").val();                //任务编号
    var taskInfo = $("#editTaskInfo").val();        //任务内容
    var rcztdm = $("#rcztdm").val();
    //检查任务信息是否为空
    if ($.trim(taskInfo)=="") {
        alert("请输入任务信息。");
    } else {
    	$.ajax({	
        	url :"<%=basePath%>addcalendar!mod.action",
        	type : "post",
        	dataType : "json",
        	data : "data="+taskId+";"+taskInfo+"&rcztdm="+rcztdm,
        	//timeout : 20000,
        	async : false,
        	error : function() {
        		alert("erwror");
        	},
        	success : function(data) {
        		//$("#task" + taskId).html(taskInfo); //更新页面任务内容
        		//$("#task" + taskId).css("background-color","red");
        		//$("#task" + taskId).css("color","red");
        		$("#task" + taskId).removeClass();
        		$("#task" + taskId).addClass("taskJSR"+rcztdm);
        		$("#task" + taskId).unbind();
        		$("#task" + taskId).click(function(){
        			editTaskJSR(this,"\""+rcztdm+"\"", '1', $("#jsrdm").text());//TODO ??
        		});
                closeEditBox();                     //关闭编辑box
        		return true;
        	}
        });
    }
}
//返回对象对页面左边距
function getLeft(src){
    /***
    var obj = src;
    var objLeft = obj.offsetLeft;
    while(obj != null && obj.offsetParent != null && obj.offsetParent.tagName != "BODY"){
        objLeft += obj.offsetParent.offsetLeft;
        obj = obj.offsetParent;
    }
    return objLeft;
    ***/
	 try{
			var aWidth = 0;
			var bWidth = 0;
			var dWidth = 0;
			if (document.documentElement && document.documentElement.clientWidth) {
				aWidth = document.documentElement.clientWidth;
			}
			if(document.body.scrollWidth) {
				bWidth = document.body.scrollWidth; 
			}
			if(document.documentElement && document.documentElement.scrollWidth) {
				dWidth = document.documentElement.scrollWidth; 
			}
			var width = Math.max(bWidth, dWidth); 
			width = Math.max(width, aWidth); 
			width = width;
			width = width+"px";//NNNNNNNNNNNNNNNNNNNNNNNNNN
			//alert(aWidth+","+bWidth+","+dWidth+","+width);
			return width;
		}catch (ex){} 
}

//返回对象对页面上边距
function getTop(src){
    /***
	var obj = src;
    var objTop = obj.offsetTop;
    while(obj != null && obj.offsetParent != null && obj.offsetParent.tagName != "BODY"){
        objTop += obj.offsetParent.offsetTop;
        obj = obj.offsetParent;
    }
    return objTop;
    ***/
    try{
		var aHeight = 0;
		var bHeight = 0;
		var dHeight = 0;
		if (document.documentElement && document.documentElement.clientHeight) {
			aHeight = document.documentElement.clientHeight;
		}
		if(document.body.scrollHeight) {
			bHeight = document.body.scrollHeight; 
		}
		if(document.documentElement && document.documentElement.scrollHeight) {
			dHeight = document.documentElement.scrollHeight; 
		}
		var height = Math.max(bHeight, dHeight); 
		height = Math.max(height, aHeight); 
		height = height - 73;
		height = height+"px";//NNNNNNNNNNNNNNNNNNNNNNNNNN
		//alert(aHeight+","+bHeight+","+dHeight+","+height);
		return height;
	}catch (ex){} 
}
--></script>
</head>

<body>
<h1>日程表</h1>

<!-- 新建任务box -->
<div id="addBox">
    <div id="taskDate"></div>
    <table>
    	<tr>
    		<td colspan="4">内容：</td>
    	</tr>
    	<tr>
    		<td colspan="4"><textarea rows="2" cols="30" id="taskInfo" class="xheditor" style="width:480px;height:100px;visibility:hidden;"></textarea></td>
    	</tr>
    	<tr>
    		<td>接收人：</td>
    		<td><s:select list="usermap" name="JSR_DM" id="JSR_DM" cssStyle="width:100px;"></s:select></td>
    		<td>抄送人：</td>
    		<td>
    		<s:select id="CSR_DM" list="csrmap" name="CSR_DM" multiple="true" cssStyle="width:100px;" size="4" headerKey="" headerValue="--请选择--"></s:select>
    		</td>
    	</tr>
    </table>
    <div class="taskBtn">
        <input type="button" value="创建新任务" onclick="addTask()">
        <input type="button" value="取消" onclick="closeAddBox()">
    </div>
</div>

<!-- 编辑任务box -->
<div id="editBox">
    <input type="hidden" id="taskId">
    内容：               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任务办理人：(<font color="red"><span id="jsrdm"></span></font>)
    <textarea rows="4" cols="30" id="editTaskInfo"  class="xheditor" style="width:480px;height:320px;visibility:hidden;"></textarea>
    <div class="taskBtn">
    	
    	<span id="updateabled">
    	<s:select id="rcztdm" list="rcztmap"></s:select>
    	<input type="button" id="updateTask" value="更新状态" onclick="updateTask()">
    	</span>
    	<input type="button" id="updateInfoBtn" value="更新内容" onclick="updateINFO()">
        <input type="button" id="delTaskBtn" value="删除该任务" onclick="delTask()">
        <input type="button" id="closeEditBox" value="关闭" onclick="closeEditBox()">
        
    </div>
</div>

<!-- 日历表格 -->
<table id="calTable">
<tr>
    <td colspan="2" align="left">
        <input type="button" value="上月" onclick="prevMonth()">
        <input type="button" value="本月" onclick="thisMonth()">
        <input type="button" value="下月" onclick="nextMonth()">
    </td>
    <td colspan="3" align="center">
        <span id="dateInfo"></span>
    </td>
    <td colspan="2" align="right">
    <!-- 
    年<select ><option title="2011" value="2011">2011</option><option title="2012" value="2012">2012</option><option title="2013" value="2013">2013</option><option title="2014" value="2014">2014</option><option title="2015" value="2015">2015</option></select>
    月<select><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option><option>6</option><option>7</option><option>8</option><option>9</option><option>10</option><option>11</option><option>12</option></select>
     --></td>
</tr>
<tr>
    <td class="day">周日</td>
    <td class="day">周一</td>
    <td class="day">周二</td>
    <td class="day">周三</td>
    <td class="day">周四</td>
    <td class="day">周五</td>
    <td class="day">周六</td>
</tr>
<tr>
    <td class="calBox sun" id="calBox0"></td>
    <td class="calBox" id="calBox1"></td>
    <td class="calBox" id="calBox2"></td>
    <td class="calBox" id="calBox3"></td>
    <td class="calBox" id="calBox4"></td>
    <td class="calBox" id="calBox5"></td>
    <td class="calBox sat" id="calBox6"></td>
</tr>
<tr>
    <td class="calBox sun" id="calBox7"></td>
    <td class="calBox" id="calBox8"></td>
    <td class="calBox" id="calBox9"></td>
    <td class="calBox" id="calBox10"></td>
    <td class="calBox" id="calBox11"></td>
    <td class="calBox" id="calBox12"></td>
    <td class="calBox sat" id="calBox13"></td>
</tr>
<tr>
    <td class="calBox sun" id="calBox14"></td>
    <td class="calBox" id="calBox15"></td>
    <td class="calBox" id="calBox16"></td>
    <td class="calBox" id="calBox17"></td>
    <td class="calBox" id="calBox18"></td>
    <td class="calBox" id="calBox19"></td>
    <td class="calBox sat" id="calBox20"></td>
</tr>
<tr>
    <td class="calBox sun" id="calBox21"></td>
    <td class="calBox" id="calBox22"></td>
    <td class="calBox" id="calBox23"></td>
    <td class="calBox" id="calBox24"></td>
    <td class="calBox" id="calBox25"></td>
    <td class="calBox" id="calBox26"></td>
    <td class="calBox sat" id="calBox27"></td>
</tr>
<tr>
    <td class="calBox sun" id="calBox28"></td>
    <td class="calBox" id="calBox29"></td>
    <td class="calBox" id="calBox30"></td>
    <td class="calBox" id="calBox31"></td>
    <td class="calBox" id="calBox32"></td>
    <td class="calBox" id="calBox33"></td>
    <td class="calBox sat" id="calBox34"></td>
</tr>
<tr>
    <td class="calBox sun" id="calBox35"></td>
    <td class="calBox" id="calBox36"></td>
    <td class="calBox" id="calBox37"></td>
    <td class="calBox" id="calBox38"></td>
    <td class="calBox" id="calBox39"></td>
    <td class="calBox" id="calBox40"></td>
    <td class="calBox sat" id="calBox41"></td>
</tr>
</table>
<br>
<table border="1">
	<tr>
		<td colspan="4" align="center">颜色说明</td>
	</tr>
	<tr>
		<td>&nbsp;</td><td align="center">录入</td><td align="center">延期</td><td align="center">完成</td>
	</tr>
	<tr>
		<td align="center">接收人</td><td><div class="taskJSR00">&nbsp;</div></td><td><div class="taskJSR01">&nbsp;</div></td><td><div class="taskJSR10">&nbsp;</div></td>
	</tr>
	<tr>
		<td align="center">抄送人</td><td><div class="taskCSR00">&nbsp;</div></td><td><div class="taskCSR01">&nbsp;</div></td><td><div class="taskCSR10">&nbsp;</div></td>
	</tr>
	<tr>
		<td align="center">发送人</td><td><div class="taskFSR00">&nbsp;</div></td><td><div class="taskFSR01">&nbsp;</div></td><td><div class="taskFSR10">&nbsp;</div></td>
	</tr>
</table>
</body>
</html>
<!-- kindeditor add -->
<script>
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[id="taskInfo"]', {
			allowFileManager : true,
			items :
				[
			        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
			        'plainpaste', 'wordpaste', '|',  '/',
			        'justifyleft', 'justifycenter', 'justifyright',
			        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
			        'hr', 'emoticons', 'code', 'pagebreak',
			        'link', 'unlink', '|'
				]
		});
	});
	var editor2;
	KindEditor.ready(function(K) {
		editor2 = K.create('textarea[id="editTaskInfo"]', {
			allowFileManager : true,
			items :
				[
			        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
			        'plainpaste', 'wordpaste', '|',  '/',
			        'justifyleft', 'justifycenter', 'justifyright',
			        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
			        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
			        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
			        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
			        'hr', 'emoticons', 'code', 'pagebreak',
			        'link', 'unlink', '|'
				]
		});
	});
	function html2str(html) {
		return (_trim(html.replace(/<(?!img|embed).*?>/ig, '').replace(/&nbsp;/ig, ' ')));
	}
	function _trim(str) {
		return str.replace(/(?:^[ \t\n\r]+)|(?:[ \t\n\r]+$)/g, '');
	}
	function removeImgAttr(html) {
		html = html.replace(/<(img)+>/ig, '图片').replace(/&nbsp;/ig, ' ');
		return (_trim(html.replace(/<(?!embed).*?>/ig, '').replace(/&nbsp;/ig, ' ')));
	}
</script>