function tablecloth(tableName) {
	var highlightRows = true;
	var selectable = true;
	this.start = function() {
		var tableobj = document.getElementById(tableName);
		rows(tableobj);
	};
	this.rows = function(table) {
		try {
		var tr = table.getElementsByTagName("tr");
		for ( var i = 0; i < tr.length; i++) {
			var arr = new Array();
			for ( var j = 0; j < tr[i].childNodes.length; j++) {
				if (tr[i].childNodes[j].nodeType == 1) {
					arr.push(tr[i].childNodes[j]);
				}
			}
			for ( var j = 0; j < arr.length; j++) {
				arr[j].row = i;
				arr[j].col = j;
				arr[j].onclick = function() {
					click(table, this, this.row, this.col);
				}
			}
		}
		} catch (err){}
		;
	};
	
	this.click = function(table, obj, row, col) {
		if(row==0 || col==0) {
			return;
		}
		if (selectable) {
			if (highlightRows)
				highlightRow(table, obj, row, true);
		}
	};

	this.highlightRow = function(table, active, row, sel) {
		var osType = getOs();
		var tr = table.getElementsByTagName("tr")[row];
		if("Chrome"==osType || osType.indexOf("Chrome")!=-1) {
			if (tr.cells[0].childNodes[0] && tr.cells[0].childNodes[0].type == 'radio') {
				tr.cells[0].childNodes[0].checked = true;
			}
			else if (tr.cells[0].childNodes[1] && tr.cells[0].childNodes[1].type == 'radio') {
				tr.cells[0].childNodes[1].checked = true;
			}
			else if (tr.cells[0].childNodes[1] && tr.cells[0].childNodes[1].type == 'checkbox') {
				tr.cells[0].childNodes[1].checked = !tr.cells[0].childNodes[1].checked;
			}
			else if (tr.cells[0].childNodes[0] && tr.cells[0].childNodes[0].type == 'checkbox') {
				tr.cells[0].childNodes[0].checked = !tr.cells[0].childNodes[0].checked;
			}
		} else if("Firefox"==osType || osType.indexOf("Firefox")!=-1) {
			if (tr.cells[0].childNodes[1] && tr.cells[0].childNodes[1].type == 'radio') {
				tr.cells[0].childNodes[1].checked = true;
			}
			else if (tr.cells[0].childNodes[1] && tr.cells[0].childNodes[1].type == 'checkbox') {
				tr.cells[0].childNodes[1].checked = !tr.cells[0].childNodes[1].checked;
			}
		} else {
			if (tr.cells[0].children[0] && tr.cells[0].children[0].type == 'radio') {
				tr.cells[0].children[0].checked = true;
			}
			else if (tr.cells[0].children[0] && tr.cells[0].children[0].type == 'checkbox') {
				tr.cells[0].children[0].checked = !tr.cells[0].children[0].checked;
			}
		}
	};
	start();
};

function getOs() 
{ 
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
    if (Sys.ie) return('IE: ' + Sys.ie); 
    if (Sys.firefox) return('Firefox: ' + Sys.firefox); 
    if (Sys.chrome) return('Chrome: ' + Sys.chrome); 
    if (Sys.opera) return('Opera: ' + Sys.opera); 
    if (Sys.safari) return('Safari: ' + Sys.safari); 
    /*
   if(navigator.userAgent.indexOf("MSIE")>0) { 
        return "MSIE"; 
   } 
   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
        return "Firefox"; 
   } 
   if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
        return "Safari"; 
   }  
   if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
        return "Camino"; 
   } 
   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){ 
        return "Gecko"; 
   } */
}