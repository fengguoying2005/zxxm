$.fn.requireds = function(mode) {
	var mode = mode || 'alert';
	var isOk = true;
	$("#requiredserrormsg").remove();
	$("[requireds='true']").each(function(i){
		if($(this).val().length<1) {
			isOk = false;
			switch(mode) {
				case 'alert':
					var osType = getOs();
					if("Chrome"==osType || osType.indexOf("Chrome")!=-1) {
						alert("不能为空！");
					} else {
						alert("<"+$(this).attr("labels")+">不能为空！");
					}
					break;
				case 'error':
					$(this).after("<span id='requiredserrormsg' class='error'>"+"不能为空"+"</span>");
					break;
				default:
					$(this).after("<span id='requiredserrormsg' class='info'>"+"不能为空"+"</span>");
					break;
			}
			$(this).focus();
			return false;
		}
	});
	if(!isOk) {
		return false;
	}
	$("[requiredsType]").each(function(i){
		var type = $(this).attr("requiredsType");
		if($(this).val()!="") {
			switch(type) {
				case "number":var patrn=/^[0-9]*$/;break;
				case "email":var patrn=/^([a-z][a-z0-9_-]*\.?[a-z0-9_-]*)*[a-z0-9]@([a-z0-9-]+\.[a-z]+)+$/;break;
				case "decimal":var patrn=/^[0-9]+[\\.]?[0-9]*$/;break;
				case "date":var patrn=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;break;
				case "time":var patrn=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/;break;
					//日期
					//text
					//url[^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$]
					//mobilephone手机号码必须以数字开头，除数字外，可含有“-”[/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/]
					//telephone普通电话、传真号码：可以“+”开头，除数字外，可含有“-”[/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/]
					//postcode邮政编码[/^\\d{6}$/]
					//keycode搜索关键字[/^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,19}$/]
					//int整数[^-?\\d+$]
					//_int正整数[^[0-9]*[1-9][0-9]*$]
					//-int负整数[^-[0-9]*[1-9][0-9]*$]
					//float浮点数[^(-?\\d+)(\\.\\d+)?$]
					//+float正浮点数[^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$]
					//-float负浮点数[^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$]
			}
			if (!patrn.exec($(this).val())) {
				isOk = false;
				switch(mode) {
					case 'alert':
						var osType = getOs();
						if("Chrome"==osType || osType.indexOf("Chrome")!=-1) {
							alert("类型非法！");
						} else {
							alert("<"+$(this).attr("labels")+">类型非法！");
						}
						break;
					case 'error':$(this).after("<span id='requiredserrormsg' class='error'>类型非法</span>");break;
					default:$(this).after("<span id='requiredserrormsg' class='info'>类型非法</span>");break;
				}
				$(this).focus();
				$(this).select();
			}
		}
		if(!isOk) {
			return false;
		}
	});
	if(!isOk) {
		return false;
	}
	$("[requiredsLength]").each(function(i){
		var len = $(this).attr("requiredsLength");
		var lens = len.split(",");
		if($(this).val().length<lens[0] || $(this).val().length>lens[1]) {
			isOk = false;
			switch(mode) {
				case 'alert':
					if(lens[0]==lens[1]) {
						alert("<"+$(this).attr("labels")+">长度应该等于"+lens[0]);
					} else {
						alert("<"+$(this).attr("labels")+">长度应该介于"+lens[0]+"与"+lens[1]+"之间");
					}
					break;
				case 'error':
					if(lens[0]==lens[1]) {
						$(this).after("<span id='requiredserrormsg' class='error'>"+"长度应该等于"+lens[0]+"</span>");
					} else {
						$(this).after("<span id='requiredserrormsg' class='error'>"+"长度应该介于"+lens[0]+"与"+lens[1]+"之间</span>");
					}
					break;
				default:
					if(lens[0]==lens[1]) {
						$(this).after("<span id='requiredserrormsg' class='info'>"+"长度应该等于"+lens[0]+"</span>");
					} else {
						$(this).after("<span id='requiredserrormsg' class='info'>"+"长度应该介于"+lens[0]+"与"+lens[1]+"之间</span>");
					}
					break;
			}
			$(this).focus();
		}
		if(!isOk) {
			return false;
		}
	});
	return isOk;
};
$.fn.initHPDM = function() {
	var options = "";
	$.ajax({
		url :"<%=basePath%>ajax/dmbcommonaction!getHPDM.action",
		type : "post",
		dataType : "json",
		data : "a=a",
		async : false,
		error : function() {
			alert("error");
		},
		success : function(data) {
			options=data['result'];
			return true;
		}
	});
	$("[aaaa='hpdm']").each(
	function(i) {
		$(this).append(options);
	});
}