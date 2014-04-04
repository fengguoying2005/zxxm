
//鼠标位置
var mousePosX;
var mousePosY;

//鼠标监听事件
function mouseMove(ev) 
{ 
	ev= ev || window.event;
	if(ev.pageX || ev.pageY){ 
		mousePosX = ev.pageX;
		mousePosY = ev.pageY;
	} else {
		mousePosX = ev.clientX + document.body.scrollLeft - document.body.clientLeft;
		mousePosY = ev.clientY + document.body.scrollTop - document.body.clientTop;
	}
}
//给鼠标注册监听事件
document.onmousemove = mouseMove; 