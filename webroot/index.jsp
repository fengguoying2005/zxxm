<%@ page language="java" import="java.util.*,com.gwinsoft.components.qxgl.user.User" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>管理系统</title>
    <link href="lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
    <script src="lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>    
    <script src="lib/ligerUI/js/ligerui.min.js" type="text/javascript"></script> 
        <script type="text/javascript">
            var tab = null;
            var accordion = null;
            var tree = null;
            $(function ()
            {
                //布局
                $("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged });

                var height = $(".l-layout-center").height();

                //Tab
                $("#framecenter").ligerTab({ height: height });

                //面板
                $("#accordion1").ligerAccordion({ height: height - 24, speed: null });

                $(".l-link").hover(function ()
                {
                    $(this).addClass("l-link-over");
                }, function ()
                {
                    $(this).removeClass("l-link-over");
                });


                tab = $("#framecenter").ligerGetTabManager();
                accordion = $("#accordion1").ligerGetAccordionManager();
                $("#pageloading").hide();

            });
            function f_heightChanged(options)
            {
                if (tab)
                    tab.addHeight(options.diff);
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
            }
            function f_addTab(tabid,text, url)
            { 
            	if(url=='#') {
            		alert('功能正在建设中，敬请关注。');
            	} else {
                    tab.addTabItem({ tabid : tabid,text: text, url: url });
            	}
            } 
            function moidfypwd() {
            	f_addTab("1234567890","修改密码", "login!toMoidfyPwd.action");
            }
            
     </script> 
<style type="text/css"> 
    body,html{height:100%;}
    body{ padding:0px; margin:0;   overflow:hidden;}  
    .l-link{ display:block; height:26px; line-height:26px; padding-left:10px; text-decoration:underline; color:#333;}
    .l-link2{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
    .l-layout-top{background:#102A49; color:White;}
    .l-layout-bottom{ background:#E5EDEF; text-align:center;}
    #pageloading{position:absolute; left:0px; top:0px; background:white url('loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
    .l-link{ display:block; line-height:22px; height:22px; padding-left:16px;border:1px solid white; margin:4px;}
    .l-link-over{ background:#FFEEAC; border:1px solid #DB9F00;} 
    .l-winbar{ background:#2B5A76; height:30px; position:absolute; left:0px; bottom:0px; width:100%; z-index:99999;}
    .space{ color:#E7E7E7;}
    /* 顶部 */ 
    .l-topmenu{ margin:0; padding:0; height:31px; line-height:31px; background:url('lib/images/top.jpg') repeat-x bottom;  position:relative; border-top:1px solid #1D438B;  }
    .l-topmenu-logo{ color:#E7E7E7; padding-left:35px; line-height:26px;background:url('lib/images/topicon.gif') no-repeat 10px 5px;}
    .l-topmenu-welcome{  position:absolute; height:24px; line-height:24px;  right:30px; top:2px;color:#070A0C;}
    .l-topmenu-welcome a{ color:#E7E7E7; text-decoration:underline} 

 </style>
</head>
<body style="padding:0px;background:#EAEEF5;">  

<div id="pageloading"></div>  
<div id="topmenu" class="l-topmenu">
    <div class="l-topmenu-logo">辽宁省地方税务局短信服务平台</div>
    <div class="l-topmenu-welcome">
    	<%Object o = request.getSession().getAttribute("GWINSOFT_USER_KEY");
		if(o != null) {
		%>
     	<font color="white">欢迎您，<%=((User)o).getLRRY_DM()%>&nbsp;&nbsp;&nbsp;<%=((User)o).getUSER_MC() %>&nbsp;|&nbsp;</font>
     	<%	
		}
		%>
    	<a href="#" onclick="javascript:moidfypwd();return false;">修改密码</a>&nbsp;<font color="white">|</font>
        <a href="login!logout.action" class="l-link2" target="_self">退出</a>
    </div> 
</div>
  <div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; "> 
        <div position="left"  title="权限菜单" id="accordion1"> 
        			<div title="我具有的功能列表" class="l-scroll">
                         <ul id="myauths" style="margin-top:3px;">
                    </div>
        </div>
        
                    <script type="text/javascript">
                    $.ajax({
                    	url :"<%=basePath%>ajax/ajax_menu.action",
                    	type : "post",
                    	dataType : "json",
                    	data : "para1=myauths",
                    	//timeout : 20000,
                    	async : false,
                    	error : function() {
                    		alert("erwror");
                    	},
                    	success : function(data) {
                    		$("#myauths").ligerTree({
                                data : eval(data.result),
                                checkbox: false,
                                slide: false,
                                nodeWidth: 120,
                                attribute: ['nodename', 'url'],
                                onSelect: function (node)
                                {
                                    if (!node.data.url) return;
                                    var tabid = $(node.target).attr("tabid");
                                    if (!tabid)
                                    {
                                        //tabid = new Date().getTime();
                                        tabid = node.data.text;
                                        $(node.target).attr("tabid", tabid);
                                    } 
                                    f_addTab(tabid, node.data.text, node.data.url);
                                }
                            });
                    		return true;
                    	}
                    });
                    </script>
        <div position="center" id="framecenter"> 
            <div tabid="home" title="首页" style="height:300px" >
                <iframe frameborder="0" name="home" id="home" src="welcome.jsp"></iframe>
            </div> 
        </div> 
        
    </div>
    <div  style="height:32px; line-height:32px; text-align:center;">
            Copyright © 2012-2012 辽宁省地方税务局 同泽
    </div>
    <div style="display:none"></div>
</body>
</html>
