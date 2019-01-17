var socket = null;

$(function() {
	$("#mainId").text($(".layoutHidden #JSESSIONID").val());
	initSimpleSocket($(".testHidden #socketPath").val(), function(message) {
		if (message["data"] != undefined && message["data"] != null) {
			var message = JSON.parse(message["data"]);
			if (message != null) {
				var object = JSON.parse(message["data"]);
				$("#oppositeId").text(message["sender"]);
				$("#oppositeMessage").val(object["info"]);
			}
		}
	});
	initConfirmListener();
});

function initConfirmListener() {
	$("#confirm").on("click", function() {
		var item = new Object();
		var data = new Object();
		item["sender"] = $(".testHidden #socketPath").val();
		item["receiver"] = $("#receiver").val();
		data["info"] = $("#mainMessage").val()
		item["data"] = data;
		socket.send(JSON.stringify(item));
	});
}

function initSimpleSocket(pathVal, onmessage) {
	var rewritePath = function(path) {
		path = path.replace("[sid]", $(".layoutHidden #JSESSIONID").val());
		path = "ws://" + location.hostname + ":" + location.port + path;
		return path;
	};
	if (typeof (WebSocket) != undefined) {
		var path = rewritePath($(".testHidden #socketPath").val());
		socket = new WebSocket(path);
		// 打开事件
		socket.onopen = function() {
			console.log("socket was been opened...");
			// socket.send("这是来自客户端的消息" + location.href + new Date());
		};
		// 获得消息事件
		socket.onmessage = function(msg) {
			// console.log(msg.data);
			onmessage(msg);
			// 发现消息进入 开始处理前端触发逻辑
		};
		// 关闭事件
		socket.onclose = function() {
			console.log("socket was been closed...");
		};
		// 发生了错误事件
		socket.onerror = function() {
			alert("socket has error occurred...");
			// 此时可以尝试刷新页面
		}
	}
}
