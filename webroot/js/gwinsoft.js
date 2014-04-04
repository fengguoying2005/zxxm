var ISGROUPONGYS = true;
var ISGROUPONKH = true;

$(function() {
	try{
		reinitIframeHeight();
		/**初始化必填样式**/
		$("[requireds='true']").each(function(i){
			$(this).after("<font color='red'>*</font>");
		});
		$("[requireds='false']").each(function(i){
			$(this).after("<font color='red'>&nbsp;</font>");
		});
		//setInterval(reinitIframeHeight,2000);
	}
	catch(er) {;}
});

function setDisabledBtn(flag,arrs) {
	if(arrs) {
		for(var arr in arrs) {
			//alert("#"+arrs[arr]);
			if(flag) {
				//$("#"+arrs[arr]).attr("disabled",true);
				$("#"+arrs[arr]).hide();

				$loadMask = $("<div id='loadInfoBtn'><br>正在处理...请稍后...</div>");
				$win = $(window);
				var x = (($win.width() - $loadMask.outerWidth()) / 2) + $win.scrollLeft() + "px";
				var y = (($win.height() - $loadMask.outerHeight()) / 2) + $win.scrollTop() + "px";
				$loadMask.css( {
					left : x,
					top : y
				}).show();
				$loadMask.css( {
				'position' : 'absolute',
				'top' : y,
				'left' : x,
				'width' : '280px',
				'height' : '65px',
				'color':'red',
				'background-color' : '#1E4080',
				'border-radius' : '5px',
				'padding-top' : '20px',
				'opacity' : '1',
				'z-index' : '999',
				'text-align' : 'center',
				'font-size' : '100%'
				}).prependTo(document.body).show();
			} else {
				//$("#"+arrs[arr]).removeAttr("disabled");
				$("#"+arrs[arr]).show();
				
				$("#loadInfoBtn").hide();
			}
		}
	}
}
function reinitIframeHeight(){ 
	try{
		if(document.all.mainframe) {
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
			height = height - 23;
			height = height+"px";//NNNNNNNNNNNNNNNNNNNNNNNNNN
			document.all.mainframe.style.height=height;
		}
	}catch (ex){} 
}

/**只读样式**/
function initReadOnly() {
    $("[readonly='readonly']").each(function(i){
        $(this).css("background-color","#D4D0C8");
    });
    $("[readonly='true']").each(function(i){
        $(this).css("background-color","#D4D0C8");
    });
}
/** 计算 **/
Number.prototype.toFixed = function(d)
{
    var s=this+"";if(!d)d=0;
    if(s.indexOf(".")==-1)s+=".";s+=new Array(d+1).join("0");
    if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0,"+ (d+1) +"})?)\\d*$").test(s))
    {
        var s="0"+ RegExp.$2, pm=RegExp.$1, a=RegExp.$3.length, b=true;
        if (a==d+2){a=s.match(/\d/g); if (parseInt(a[a.length-1])>4)
        {
            for(var i=a.length-2; i>=0; i--) {a[i] = parseInt(a[i])+1;
            if(a[i]==10){a[i]=0; b=i!=1;} else break;}
        }
        s=a.join("").replace(new RegExp("(\\d+)(\\d{"+d+"})\\d$"),"$1.$2");
    }if(b)s=s.substr(1);return (pm+s).replace(/\.$/, "");} return this+"";
};
//取得列
function getChildForIE(tableId, rowIndex, index) {
	return tableId.rows[rowIndex].cells[index].children[0];
}
function getChildForFireFox(tableId, rowIndex, index) {
	return tableId.rows[rowIndex].cells[index].childNodes[1];
}
//计算当前行
function changeSLForIE() {
	var curParm = event.srcElement;
	var curRow = curParm.parentElement.parentElement;
	var parmA, parmB, parmC="", parmD="", parmE;
	var JECOL;
	for ( i = 0; i < curRow.cells.length; i++ ) {
		if ( curRow.cells[i].children[0].parmA )
			parmA = curRow.cells[i].children[0];
		if ( curRow.cells[i].children[0].parmB )
			parmB = curRow.cells[i].children[0];	
		if ( curRow.cells[i].children[0].parmC )
			parmC = curRow.cells[i].children[0];
		if ( curRow.cells[i].children[0].parmD )
			parmD = curRow.cells[i].children[0];
		if ( curRow.cells[i].children[0].parmE ) {
			parmE = curRow.cells[i].children[0];		
			JECOL = i;
		}
	}
	if(parmA.value!="" && parmB.value!="") {
		if(parmC=="") {
			if(parmD=="") {
				//ZK SL no
				parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value)).toFixed(2);
			} else {
				//ZK no
				
			}
		} else if(parmD=="") {
			//SL no
			
		} else {
			//数量*单价*折扣*(1-税率)
			if(parmC.value!="" && parmD.value!="") {
				parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value) * (parseInt(parmC.value)/100)*(1+parseFloat(parmD.value)/100)).toFixed(2);
			}
		}
		getJEHJ(JECOL);
	}
}
function changeSLForFireFox(ev) {
	var ev = window.event || ev;
	var curParm = ev.srcElement || ev.target;
	var curRow;
	if(curParm.parentElement) {
		curRow = curParm.parentElement.parentElement;
	} else {
		curRow = curParm.parentNode.parentNode;
	}
	var parmA, parmB, parmC="", parmD="", parmE;
	var JECOL;
	for ( i = 0; i < curRow.cells.length; i++ ) {
		if ( curRow.cells[i].children[0].parmA )
			parmA = curRow.cells[i].childNodes[1];
		if ( curRow.cells[i].children[0].parmB )
			parmB = curRow.cells[i].childNodes[1];
		if ( curRow.cells[i].children[0].parmC )
			parmC = curRow.cells[i].childNodes[1];
		if ( curRow.cells[i].children[0].parmD )
			parmD = curRow.cells[i].childNodes[1];
		if ( curRow.cells[i].children[0].parmE ) {
			parmE = curRow.cells[i].childNodes[1];
			JECOL = i;
		}
	}
	if(parmA.value!="" && parmB.value!="") {
		if(parmC=="") {
			if(parmD=="") {
				parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value)).toFixed(2);
			} else {
				
			}
		} else if(parmD=="") {
			
		} else {
			//数量*单价*折扣*(1-税率)
			if(parmC.value!="" && parmD.value!="") {
				parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value) * (parseInt(parmC.value)/100)*(1+parseFloat(parmD.value)/100)).toFixed(2);
			}
		}
		getJEHJ(JECOL);
	}
}
//整体计算一遍
function changeSL2ForIE(tableId,sl,dj,zk,slv,je) {
	var osType = getOs();
	var JECOL;
	if("Firefox"==osType||"Chrome"==osType || osType.indexOf("Firefox")!=-1 || osType.indexOf("Chrome")!=-1) {
		var list = document.getElementById(tableId);
		for (i=1; i < list.rows.length; i++ ) {
			parmA = getChildForFireFox(list, i, sl);
			parmB = getChildForFireFox(list, i, dj);
			var parmC="";
			if(zk!="XXX") {
				parmC = getChildForFireFox(list, i, zk);
			}
			var parmD="";
			if(slv!="XXX") {
				parmD = getChildForFireFox(list, i, slv);
			}
			parmE = getChildForFireFox(list, i, je);
			if(parmA.value!="" && parmB.value!="") {
				if(parmC=="") {
					if(parmD=="") {
						parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value)).toFixed(2);
					} else {
						
					}
				} else if(parmD=="") {
					
				} else {
					//数量*单价*折扣*(1-税率)
					if(parmC.value!="" && parmD.value!="") {
						parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value) * (parseInt(parmC.value)/100)*(1+parseFloat(parmD.value)/100)).toFixed(2);
					}
				}
			}
		}
	} else {
		var list = document.getElementById(tableId);
		for (i=1; i < list.rows.length; i++ ) {
			parmA = getChildForIE(list, i, sl);
			parmB = getChildForIE(list, i, dj);
			var parmC="";
			if(zk!="XXX") {
				parmC = getChildForIE(list, i, zk);
			}
			var parmD="";
			if(slv!="XXX") {
				parmD = getChildForIE(list, i, slv);
			}
			parmE = getChildForIE(list, i, je);
			if(parmA.value!="" && parmB.value!="") {
				if(parmC=="") {
					if(parmD=="") {
						//ZK SL no
						parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value)).toFixed(2);
					} else {
						//ZK no
						
					}
				} else if(parmD=="") {
					//SL no
					
				} else {
					//数量*单价*折扣*(1-税率)
					if(parmC.value!="" && parmD.value!="") {
						parmE.value=(parseInt(parmA.value) * parseFloat(parmB.value) * (parseInt(parmC.value)/100)*(1+parseFloat(parmD.value)/100)).toFixed(2);
					}
				}
			}
		}
	}
	getJEHJ(je);
}
function addEvent(el,eventType,fn){
    if(el.addEventListener){
        el.addEventListener(eventType,fn,false);
    }else if(el.attachEvent){
    	alert("attachEvent");
        el.attachEvent("on" + eventType,fn);
    }else{
    	alert("on..");
        el["on"+eventType]=fn;
    }
} 
//计算
function computeCnt(tableId,sl,dj,zk,slv,je) {
	var osType = getOs();
	if("Firefox"==osType||"Chrome"==osType || osType.indexOf("Firefox")!=-1 || osType.indexOf("Chrome")!=-1) {
		var list = document.getElementById(tableId);
		for (i=1; i < list.rows.length; i++ ) {
			addEvent(getChildForFireFox(list, i, sl), "keyup", changeSLForFireFox);
			addEvent(getChildForFireFox(list, i, dj), "keyup", changeSLForFireFox);
			if(zk!="XXX")
			addEvent(getChildForFireFox(list, i, zk), "change", changeSLForFireFox);
			if(slv!="XXX")
			addEvent(getChildForFireFox(list, i, slv), "keyup", changeSLForFireFox);

			getChildForFireFox(list, i, sl).parmA=true;
			getChildForFireFox(list, i, dj).parmB=true;
			if(zk!="XXX")
			getChildForFireFox(list, i, zk).parmC=true;
			if(slv!="XXX")
			getChildForFireFox(list, i, slv).parmD=true;
			getChildForFireFox(list, i, je).parmE=true;
		}
	} else {
		var list = document.getElementById(tableId);
		for (i=1; i < list.rows.length; i++ ) {
			getChildForIE(list, i, sl).attachEvent("onkeyup", changeSLForIE);
			getChildForIE(list, i, dj).attachEvent("onkeyup", changeSLForIE);
			if(zk!="XXX")
			getChildForIE(list, i, zk).attachEvent("onchange", changeSLForIE);
			if(slv!="XXX")
			getChildForIE(list, i, slv).attachEvent("onkeyup", changeSLForIE);

			getChildForIE(list, i, sl).parmA=true;
			getChildForIE(list, i, dj).parmB=true;
			if(zk!="XXX")
			getChildForIE(list, i, zk).parmC=true;
			if(slv!="XXX")
			getChildForIE(list, i, slv).parmD=true;
			getChildForIE(list, i, je).parmE=true;
		}
	}
}
//算合计
function getJEHJ(col) {
	var tableId = "CRKMX_2";
	//try {
		var osType = getOs();
		var JEHJ = 0;
		if("Firefox"==osType||"Chrome"==osType || osType.indexOf("Firefox")!=-1 || osType.indexOf("Chrome")!=-1) {
			var list = document.getElementById(tableId);
			for (i=1; i < list.rows.length; i++ ) {
				JEHJ=parseFloat(list.rows[i].cells[col].childNodes[1].value)+parseFloat(JEHJ);
			}
		} else {
			var list = document.getElementById(tableId);
			for (i=1; i < list.rows.length; i++ ) {
				JEHJ=parseFloat(list.rows[i].cells[col].children[0].value)+parseFloat(JEHJ);
			}
		}
		if(JEHJ)
		$("#SUMJEHJ").val(JEHJ);
	//} catch (err){}
	;
}




function getOs() {
    var OsObject = ""; 
    var Sys = {}; 
    var ua = navigator.userAgent.toLowerCase(); 
    var s; 
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 
    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : 
    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : 
    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : 
    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0; 
    //以下进行测试 
    if (Sys.ie) return "MSIE";
    if (Sys.firefox) return "Firefox";
    if (Sys.chrome) return "Chrome";
    if (Sys.opera) return('Opera: ' + Sys.opera); 
    if (Sys.safari) return "Safari";
	/*var OsObject = "";
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		return "MSIE";
	}
	if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0) {
		return "Firefox";
	}
	if (isSafari = navigator.userAgent.indexOf("Safari") > 0) {
		return "Safari";
	}
	if (isCamino = navigator.userAgent.indexOf("Camino") > 0) {
		return "Camino";
	}
	if (isMozilla = navigator.userAgent.indexOf("Gecko/") > 0) {
		return "Gecko";
	}*/
}
//添加一行数据
function addNewRow(tablename) {
	var osType = getOs();
	if("Firefox"==osType||osType.indexOf("Firefox")!=-1) {
		addNewRowFireFox(tablename);
	} else if("Chrome"==osType ||  osType.indexOf("Chrome")!=-1) {
		addNewRowChrome(tablename);
	} else {
		addNewRowIE(tablename);
	}
}
function addNewRowIE(tablename) {
	var tableObj = document.getElementById(tablename);
	var rowsObj = tableObj.rows;
	if (rowsObj != null) {
		var rows = rowsObj.length;
	}
	//末尾插入一条数据
	var len = tableObj.rows[1].cells.length;
	var oRow = tableObj.insertRow(rows);
	//插入新列
	var cell = new Array();
	var d = new Date();
	var se = d.getSeconds();
	var mi = d.getMilliseconds();
	if(se<10) {
		se = "0"+se;
	} else {
		se = ""+se;
	}
	if(mi<10) {
		mi = "00"+mi;
	} else if(mi<100) {
		mi = "0"+mi;
	} else {
		mi = ""+mi;
	}
	var ifStrutsFrame = false;
	for ( var i = 0; i < len; i++) {
		cell[i] = oRow.insertCell();
		//alert(tableObj.rows[1].cells[i].style);
		//alert(tableObj.rows[1].cells[i].outerHTML);
		if(tableObj.rows[1].cells[i].children[0]) {
			cell[i].innerHTML = tableObj.rows[1].cells[i].children[0].outerHTML;
			//alert(cell[i].style);
			//alert(tableObj.rows[1].cells[i].style);
			//cell[i].style=tableObj.rows[1].cells[i].style;
			//cell[i].outerHTML = tableObj.rows[1].cells[i].outerHTML;
			if(cell[i].children[0].type=='text') {
				cell[i].children[0].value = "";
			} else if(cell[i].children[0].type=='radio') {
				cell[i].children[0].checked=false;
			} else if(cell[i].children[0].type=='checkbox') {
				cell[i].children[0].checked=false;
			} else {
				cell[i].children[0].value = "";
			}
			if(cell[i].children[0].type!='radio') {
				var leftStart = cell[i].children[0].name.indexOf("[");
				var rightStart = cell[i].children[0].name.indexOf("]");
				if(leftStart != -1 && rightStart != -1) {
					ifStrutsFrame = true;
					cell[i].children[0].name=cell[i].children[0].name.substring(0,leftStart+1)+(rows-1)+cell[i].children[0].name.substring(rightStart);
				} else {
					cell[i].children[0].name=cell[i].children[0].name+se+mi;
				}
			}
		} else {
			
		}
		
	}
	//重新置name
	if(ifStrutsFrame) {
		var leftPrefix = new Array();
		var rightPrefix = new Array();
		for(var j = 1; j < tableObj.rows.length; j ++) {
			for ( var i = 0; i < len; i++) {
				cell = tableObj.rows[j].cells[i];
				if(cell.children[0] && cell.children[0].type!='radio') {
					if((leftPrefix[i] == null || leftPrefix[i] == "") && (rightPrefix[i] == null || rightPrefix[i] =="")) {
						var leftStart = cell.children[0].name.indexOf("[");
						var rightStart = cell.children[0].name.indexOf("]");
						if(leftStart != -1 && rightStart != -1) {
							leftPrefix[i] = cell.children[0].name.substring(0,leftStart+1);
							rightPrefix[i] = cell.children[0].name.substring(rightStart);
						}
					}
					if(leftPrefix[i]!=undefined) {
						cell.children[0].name=leftPrefix[i]+(j-1);
					}
					if(rightPrefix[i]!=undefined) {
						cell.children[0].name=cell.children[0].name+rightPrefix[i];
					}
				}
			}
		}
	}
}
function addNewRowChrome(tablename) {

	var tableObj = document.getElementById(tablename);
	var rowsObj = tableObj.rows;
	if (rowsObj != null) {
		var rows = rowsObj.length;
	}
	//末尾插入一条数据
	var len = tableObj.rows[1].cells.length;
	//插入新列
	var cell = new Array();
	var d = new Date();
	var se = d.getSeconds();
	var mi = d.getMilliseconds();
	if(se<10) {
		se = "0"+se;
	} else {
		se = ""+se;
	}
	if(mi<10) {
		mi = "00"+mi;
	} else if(mi<100) {
		mi = "0"+mi;
	} else {
		mi = ""+mi;
	}
	var ifStrutsFrame = false;
	var d = tableObj.rows[1].cloneNode(true);
	for ( var i = 0; i < len; i++) {
		try{
			if(d.cells[i].childNodes[0].type=="text") {
				d.cells[i].childNodes[0].value="";
			} else if(d.cells[i].childNodes[0].type=="radio") {
				d.cells[i].childNodes[0].checked=false;
			} else if(d.cells[i].childNodes[0].type=="checkbox") {
				d.cells[i].childNodes[0].checked=false;
			}
			
			if(d.cells[i].childNodes[0].type!='radio') {
				var leftStart = d.cells[i].childNodes[0].name.indexOf("[");
				var rightStart = d.cells[i].childNodes[0].name.indexOf("]");
				if(leftStart != -1 && rightStart != -1) {
					ifStrutsFrame = true;
					d.cells[i].childNodes[0].name=d.cells[i].childNodes[0].name.substring(0,leftStart+1)+(rows-1)+d.cells[i].childNodes[0].name.substring(rightStart);
				} else {
					d.cells[i].childNodes[0].name=d.cells[i].childNodes[0].name+se+mi;
				}
				//alert(d.cells[i].childNodes[1].name);
			}
		} catch(er) {;}
	}
	tableObj.tBodies[0].appendChild(d);
}
function addNewRowFireFox(tablename) {

	var tableObj = document.getElementById(tablename);
	var rowsObj = tableObj.rows;
	if (rowsObj != null) {
		var rows = rowsObj.length;
	}
	//末尾插入一条数据
	var len = tableObj.rows[1].cells.length;
	//插入新列
	var cell = new Array();
	var d = new Date();
	var se = d.getSeconds();
	var mi = d.getMilliseconds();
	if(se<10) {
		se = "0"+se;
	} else {
		se = ""+se;
	}
	if(mi<10) {
		mi = "00"+mi;
	} else if(mi<100) {
		mi = "0"+mi;
	} else {
		mi = ""+mi;
	}
	var ifStrutsFrame = false;
	var d = tableObj.rows[1].cloneNode(true);
	for ( var i = 0; i < len; i++) {
		try{
			if(d.cells[i].childNodes[1].type=="text") {
				d.cells[i].childNodes[1].value="";
			} else if(d.cells[i].childNodes[1].type=="radio") {
				d.cells[i].childNodes[1].checked=false;
			} else if(d.cells[i].childNodes[1].type=="checkbox") {
				d.cells[i].childNodes[1].checked=false;
			}
			
			if(d.cells[i].childNodes[1].type!='radio') {
				var leftStart = d.cells[i].childNodes[1].name.indexOf("[");
				var rightStart = d.cells[i].childNodes[1].name.indexOf("]");
				if(leftStart != -1 && rightStart != -1) {
					ifStrutsFrame = true;
					d.cells[i].childNodes[1].name=d.cells[i].childNodes[1].name.substring(0,leftStart+1)+(rows-1)+d.cells[i].childNodes[1].name.substring(rightStart);
				} else {
					d.cells[i].childNodes[1].name=d.cells[i].childNodes[1].name+se+mi;
				}
				//alert(d.cells[i].childNodes[1].name);
			}
		} catch(er) {;}
	}
	tableObj.tBodies[0].appendChild(d);
}
//删除一行数据
function deleteRow(tablename) {
	var osType = getOs();
	if("Firefox"==osType||"Chrome"==osType) {
		deleteRowFireFox(tablename);
	} else {
		deleteRowIE(tablename);
	}
}

function deleteRowIE(tablename) {
	var ifStrutsFrame = true;
	var tableObj = document.getElementById(tablename);
	var rowsObj = tableObj.rows;
	if (rowsObj != null) {
		var rows = rowsObj.length;
	}
	if(rows<=2) {
		alert('\u81f3\u5c11\u4fdd\u7559\u4e00\u884c\u8bb0\u5f55!');
		return false;
	}
	var len = tableObj.rows[1].cells.length;//循环所有列检索radio项
	var isBreak = false;
	for ( var i = rows - 1; i > 0 ; i--) {
		var isRadioRow = false;
		var isRadioRowChecked = false;
		for( var j = 0; j < len; j++) {
			if(!isRadioRow && tableObj.rows[i].cells[j].children[0].type=='radio') {
				isRadioRow = true;
				isRadioRowChecked = tableObj.rows[i].cells[j].children[0].checked;
			}
			if((isRadioRowChecked)||(!isRadioRow && (tableObj.rows[i].cells[j].children[0].type=='radio' || tableObj.rows[i].cells[j].children[0].type=='checkbox') && tableObj.rows[i].cells[j].children[0].checked)) {

				rows = rowsObj.length;
				if(rows<=2) {
					alert('\u81f3\u5c11\u4fdd\u7559\u4e00\u884c\u8bb0\u5f55!');
					return false;
				}
				tableObj.deleteRow(i);
				isBreak = true;
				break;
			}
		}
	}
	//重新置name
	if(ifStrutsFrame) {
		var leftPrefix = new Array();
		var rightPrefix = new Array();
		tableObj = document.getElementById(tablename);
		for(var j = 1; j < tableObj.rows.length; j ++) {
			for ( var i = 0; i < len; i++) {
				cell = tableObj.rows[j].cells[i];
				if(cell.children[0].type!='radio') {
					if((leftPrefix[i] == null || leftPrefix[i] == "") && (rightPrefix[i] == null || rightPrefix[i] =="")) {
						var leftStart = cell.children[0].name.indexOf("[");
						var rightStart = cell.children[0].name.indexOf("]");
						if(leftStart != -1 && rightStart != -1) {
							leftPrefix[i] = cell.children[0].name.substring(0,leftStart+1);
							rightPrefix[i] = cell.children[0].name.substring(rightStart);
						}
					}
					if(leftPrefix[i]!=undefined) {
						cell.children[0].name=leftPrefix[i]+(j-1);
					}
					if(rightPrefix[i]!=undefined) {
						cell.children[0].name=cell.children[0].name+rightPrefix[i];
					}
				}
			}
		}
	}
}
function deleteRowFireFox(tablename) {
	var ifStrutsFrame = true;
	var tableObj = document.getElementById(tablename);
	var rowsObj = tableObj.rows;
	if (rowsObj != null) {
		var rows = rowsObj.length;
	}
	if(rows<=2) {
		alert('\u81f3\u5c11\u4fdd\u7559\u4e00\u884c\u8bb0\u5f55!');
		return false;
	}
	var len = tableObj.rows[1].cells.length;//循环所有列检索radio项
	var isBreak = false;
	for ( var i = rows - 1; i > 0 ; i--) {
		var isRadioRow = false;
		var isRadioRowChecked = false;
		for( var j = 0; j < len; j++) {
			//(tableObj.rows[i].cells[j].children[0].type=='radio' || tableObj.rows[i].cells[j].children[0].type=='checkbox') && tableObj.rows[i].cells[j].children[0].checked
			if(!isRadioRow && tableObj.rows[i].cells[j].children[0].type=='radio') {
				isRadioRow = true;
				isRadioRowChecked = tableObj.rows[i].cells[j].children[0].checked;
			}
			if((isRadioRowChecked)||(!isRadioRow && (tableObj.rows[i].cells[j].children[0].type=='radio' || tableObj.rows[i].cells[j].children[0].type=='checkbox') && tableObj.rows[i].cells[j].children[0].checked)) {
				
				rows = rowsObj.length;
				if(rows<=2) {
					alert('\u81f3\u5c11\u4fdd\u7559\u4e00\u884c\u8bb0\u5f55!');
					tableObj.rows[i].cells[j].children[0].checked=false;
					return false;
				}
				tableObj.deleteRow(i);
				isBreak = true;
				break;
			}
		}
	}
	//重新置name
	if(ifStrutsFrame) {
		var leftPrefix = new Array();
		var rightPrefix = new Array();
		tableObj = document.getElementById(tablename);
		for(var j = 1; j < tableObj.rows.length; j ++) {
			for ( var i = 0; i < len; i++) {
				cell = tableObj.rows[j].cells[i];
				if(cell.children[0].type!='radio') {
					if((leftPrefix[i] == null || leftPrefix[i] == "") && (rightPrefix[i] == null || rightPrefix[i] =="")) {
						var leftStart = cell.children[0].name.indexOf("[");
						var rightStart = cell.children[0].name.indexOf("]");
						if(leftStart != -1 && rightStart != -1) {
							leftPrefix[i] = cell.children[0].name.substring(0,leftStart+1);
							rightPrefix[i] = cell.children[0].name.substring(rightStart);
						}
					}
					if(leftPrefix[i]!=undefined) {
						cell.children[0].name=leftPrefix[i]+(j-1);
					}
					if(rightPrefix[i]!=undefined) {
						cell.children[0].name=cell.children[0].name+rightPrefix[i];
					}
				}
			}
		}
	}
}

function RunGLNL(now, elementId){ 
var d=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六"); 
//var year = now.getYear()<100 ? now.getYear()+1900:now.getYear(); 
var year = now.getFullYear().toString(); 
//var yy=to_date(substr(to_char("yy",2,4))); 
year=year.substr(0,4); 
//alert(year); 
var month = now.getMonth()+1; 
var date = now.getDate(); 
if(month < 10) month = "0" + month; 
if(date < 10) date = "0" + date; 
var DDDD = year +"年"+ month +"月"+ date +"日"; 
DDDD = DDDD + " " + d[now.getDay()]; 
DDDD = DDDD+ " " + (CnDateofDateStr(now)); 
/*DDDD = DDDD+ "<a href='' target='_blank'>" + SolarTerm(now)+"</a>";*/ 
document.getElementById(elementId).innerHTML=DDDD;
} 
function DaysNumberofDate(DateGL){ 
return parseInt((Date.parse(DateGL)-Date.parse(DateGL.getFullYear()+"/1/1"))/86400000)+1; 
} 
function CnDateofDate(DateGL){ 
var CnData=new Array( 
0x16,0x2a,0xda,0x00,0x83,0x49,0xb6,0x05,0x0e,0x64,0xbb,0x00,0x19,0xb2,0x5b,0x00, 
0x87,0x6a,0x57,0x04,0x12,0x75,0x2b,0x00,0x1d,0xb6,0x95,0x00,0x8a,0xad,0x55,0x02, 
0x15,0x55,0xaa,0x00,0x82,0x55,0x6c,0x07,0x0d,0xc9,0x76,0x00,0x17,0x64,0xb7,0x00, 
0x86,0xe4,0xae,0x05,0x11,0xea,0x56,0x00,0x1b,0x6d,0x2a,0x00,0x88,0x5a,0xaa,0x04, 
0x14,0xad,0x55,0x00,0x81,0xaa,0xd5,0x09,0x0b,0x52,0xea,0x00,0x16,0xa9,0x6d,0x00, 
0x84,0xa9,0x5d,0x06,0x0f,0xd4,0xae,0x00,0x1a,0xea,0x4d,0x00,0x87,0xba,0x55,0x04 
); 
var CnMonth=new Array(); 
var CnMonthDays=new Array(); 
var CnBeginDay; 
var LeapMonth; 
var Bytes=new Array(); 
var I; 
var CnMonthData; 
var DaysCount; 
var CnDaysCount; 
var ResultMonth; 
var ResultDay; 
var yyyy=DateGL.getFullYear(); 
var mm=DateGL.getMonth()+1; 
var dd=DateGL.getDate(); 
if(yyyy<100) yyyy+=1900; 
if ((yyyy < 1997) || (yyyy > 2020)){ 
return 0; 
} 
Bytes[0] = CnData[(yyyy - 1997) * 4]; 
Bytes[1] = CnData[(yyyy - 1997) * 4 + 1]; 
Bytes[2] = CnData[(yyyy - 1997) * 4 + 2]; 
Bytes[3] = CnData[(yyyy - 1997) * 4 + 3]; 
if ((Bytes[0] & 0x80) != 0) {CnMonth[0] = 12;} 
else {CnMonth[0] = 11;} 
CnBeginDay = (Bytes[0] & 0x7f); 
CnMonthData = Bytes[1]; 
CnMonthData = CnMonthData << 8; 
CnMonthData = CnMonthData | Bytes[2]; 
LeapMonth = Bytes[3]; 
for (I=15;I>=0;I--){ 
CnMonthDays[15 - I] = 29; 
if (((1 << I) & CnMonthData) != 0 ){ 
CnMonthDays[15 - I]++;} 
if (CnMonth[15 - I] == LeapMonth ){ 
CnMonth[15 - I + 1] = - LeapMonth;} 
else{ 
if (CnMonth[15 - I] < 0 ){CnMonth[15 - I + 1] = - CnMonth[15 - I] + 1;} 
else {CnMonth[15 - I + 1] = CnMonth[15 - I] + 1;} 
if (CnMonth[15 - I + 1] > 12 ){ CnMonth[15 - I + 1] = 1;} 
} 
} 
DaysCount = DaysNumberofDate(DateGL) - 1; 
if (DaysCount <= (CnMonthDays[0] - CnBeginDay)){ 
if ((yyyy > 1901) && (CnDateofDate(new Date((yyyy - 1)+"/12/31")) < 0)){ 
ResultMonth = - CnMonth[0];} 
else {ResultMonth = CnMonth[0];} 
ResultDay = CnBeginDay + DaysCount; 
} 
else{ 
CnDaysCount = CnMonthDays[0] - CnBeginDay; 
I = 1; 
while ((CnDaysCount < DaysCount) && (CnDaysCount + CnMonthDays[I] < DaysCount)){ 
CnDaysCount+= CnMonthDays[I]; 
I++; 
} 
ResultMonth = CnMonth[I]; 
ResultDay = DaysCount - CnDaysCount; 
} 
if (ResultMonth > 0){ 
return ResultMonth * 100 + ResultDay;} 
else{return ResultMonth * 100 - ResultDay;} 
} 
function CnYearofDate(DateGL){ 
var YYYY=DateGL.getFullYear(); 
var MM=DateGL.getMonth()+1; 
var CnMM=parseInt(Math.abs(CnDateofDate(DateGL))/100); 
if(YYYY<100) YYYY+=1900; 
if(CnMM>MM) YYYY--; 
YYYY-=1864; 
return CnEra(YYYY)+"年"; 
} 
function CnMonthofDate(DateGL){ 
var CnMonthStr=new Array("零","正","二","三","四","五","六","七","八","九","十","冬","腊"); 
var Month; 
Month = parseInt(CnDateofDate(DateGL)/100); 
if (Month < 0){return "闰" + CnMonthStr[-Month] + "月";} 
else{return CnMonthStr[Month] + "月";} 
} 
function CnDayofDate(DateGL){ 
var CnDayStr=new Array("零", 
"初一", "初二", "初三", "初四", "初五", 
"初六", "初七", "初八", "初九", "初十", 
"十一", "十二", "十三", "十四", "十五", 
"十六", "十七", "十八", "十九", "二十", 
"廿一", "廿二", "廿三", "廿四", "廿五", 
"廿六", "廿七", "廿八", "廿九", "三十"); 
var Day; 
Day = (Math.abs(CnDateofDate(DateGL)))%100; 
return CnDayStr[Day]; 
} 
function DaysNumberofMonth(DateGL){ 
var MM1=DateGL.getFullYear(); 
MM1<100 ? MM1+=1900:MM1; 
var MM2=MM1; 
MM1+="/"+(DateGL.getMonth()+1); 
MM2+="/"+(DateGL.getMonth()+2); 
MM1+="/1"; 
MM2+="/1"; 
return parseInt((Date.parse(MM2)-Date.parse(MM1))/86400000); 
} 
function CnEra(YYYY){ 
var Tiangan=new Array("甲","乙","丙","丁","戊","己","庚","辛","壬","癸"); 
//var Dizhi=new Array("子(鼠)","丑(牛)","寅(虎)","卯(兔)","辰(龙)","巳(蛇)", 
//"午(马)","未(羊)","申(猴)","酉(鸡)","戌(狗)","亥(猪)"); 
var Dizhi=new Array("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"); 
return Tiangan[YYYY%10]+Dizhi[YYYY%12]; 
} 
function CnDateofDateStr(DateGL){ 
if(CnMonthofDate(DateGL)=="零月") return "　请调整您的计算机日期!"; 
else return "农历" + CnMonthofDate(DateGL) + CnDayofDate(DateGL); 
} 
function SolarTerm(DateGL){ 
var SolarTermStr=new Array( 
"小寒","大寒","立春","雨水","惊蛰","春分", 
"清明","谷雨","立夏","小满","芒种","夏至", 
"小暑","大暑","立秋","处暑","白露","秋分", 
"寒露","霜降","立冬","小雪","大雪","冬至"); 
var DifferenceInMonth=new Array( 
1272060,1275495,1281180,1289445,1299225,1310355, 
1321560,1333035,1342770,1350855,1356420,1359045, 
1358580,1355055,1348695,1340040,1329630,1318455, 
1306935,1297380,1286865,1277730,1274550,1271556); 
var DifferenceInYear=31556926; 
var BeginTime=new Date(1901/1/1); 
BeginTime.setTime(947120460000); 
for(;DateGL.getFullYear()<BeginTime.getFullYear();){ 
BeginTime.setTime(BeginTime.getTime()-DifferenceInYear*1000); 
} 
for(;DateGL.getFullYear()>BeginTime.getFullYear();){ 
BeginTime.setTime(BeginTime.getTime()+DifferenceInYear*1000); 
} 
for(var M=0;DateGL.getMonth()>BeginTime.getMonth();M++){ 
BeginTime.setTime(BeginTime.getTime()+DifferenceInMonth[M]*1000); 
} 
if(DateGL.getDate()>BeginTime.getDate()){ 
BeginTime.setTime(BeginTime.getTime()+DifferenceInMonth[M]*1000); 
M++; 
} 
if(DateGL.getDate()>BeginTime.getDate()){ 
BeginTime.setTime(BeginTime.getTime()+DifferenceInMonth[M]*1000); 
M==23?M=0:M++; 
} 
var JQ; 
if(DateGL.getDate()==BeginTime.getDate()){ 
JQ=" "+SolarTermStr[M] ; 
}else{ 
JQ=""; 
} 
return JQ; 
} 
function CAL() 
{} 

function CurentTime(){ 
var now = new Date(); 
var hh = now.getHours(); 
var mm = now.getMinutes(); 
var ss = now.getTime() % 60000; 
ss = (ss - (ss % 1000)) / 1000; 
var clock = hh+':'; 
if (mm < 10) clock += '0'; 
clock += mm+':'; 
if (ss < 10) clock += '0'; 
clock += ss; 
return(clock); 
}


Date.prototype.format = function (format) 
{
    var o = {
        "M+": this.getMonth() + 1, //month 
        "d+": this.getDate(),    //day 
        "h+": this.getHours(),   //hour 
        "m+": this.getMinutes(), //minute 
        "s+": this.getSeconds(), //second 
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter 
        "S": this.getMilliseconds() //millisecond 
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
    (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
      RegExp.$1.length == 1 ? o[k] :
        ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}