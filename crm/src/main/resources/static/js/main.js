function cmainFrame(){
    var hmain = document.getElementById("mainFrame");
    var bheight = document.documentElement.clientHeight;
    hmain .style.width = '100%';
    hmain .style.height = (bheight - 50 - 75) + 'px';
    var bkbgjz = document.getElementById("bkbgjz");
    bkbgjz .style.height = (bheight  - 41) + 'px';
}
cmainFrame();
window.onresize=function(){
    cmainFrame();
};

function siMenu(id,MENU_NAME,MENU_URL){
    top.mainFrame.tabAddHandler(id,MENU_NAME,MENU_URL);
    showMask();
}

//清除加载进度
function hideMask(){
    $("#jzts").hide();
}

//显示加载进度
function showMask(){
    $("#jzts").show();
}

$(document).ready(function() {
	rewriteProfileModify();
	initPasswordModify();
});

function rewriteProfileModify() {
	var object = $("[href='profile.html']")[0];
	$(object).attr("href", "#");
	$(object).on("click", function() {
		editUser($(this).find("input").val(), function(doc) {
			initModalTableReadOnly(doc);
			initModalTableSave(doc);
		});
	});
}

function initPasswordModify() {
	$(".fa-asterisk").parent().on("click", function() {
		constructPasswordModal();
		var diag = new top.Dialog();
		diag.Drag=true;
	    diag.Title="修改密码";
	    diag.ShowMaxButton = true;	//最大化按钮
	    diag.ShowMinButton = true;
		diag.Width = 300;
		diag.Height = 150;
		diag.InvokeElementId='passwordModal';
		diag.OKEvent = function(){
			if (checkPassword()) {
				var parameters = new Object();
				parameters["userId"] = $(".fa-asterisk").parent().find("input").val();
				parameters["password"] = $("#passwordModal #password").val();
				$.ajax({
					url : '/user',
					async : false,
					data : parameters,
					dataType : 'json',
					type : 'put',
					success : function(data, textStatus, jqXHR) {
						
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						if (XMLHttpRequest.status != '200') {
							console.log(XMLHttpRequest.status);
						}
					}
				});
				setTimeout(function() {
					diag.close();
				}, 2000);
			}
		};//点击确定后调用的方法
		diag.CancelEvent = function(){ //关闭事件
	        top.hideMask();
	        setTimeout("self.location.reload()",100);
	        diag.close();
	    };
		diag.show();
	});
}

function constructPasswordModal() {
	$("passwordModal").remove();
	var modal = document.createElement("div");
	$("body").append(modal);
	modal.setAttribute("id", "passwordModal");
	modal.setAttribute("style", "display:none;background-color:white");
	var group0 = document.createElement("div");
	modal.appendChild(group0);
	group0.setAttribute("class", "form-group");
	var label0 = document.createElement("label");
	group0.appendChild(label0);
	label0.setAttribute("for", "password");
	label0.setAttribute("class", "col-md-4");
	label0.innerHTML="密码：";
	var input0 = document.createElement("input");
	group0.appendChild(input0);
	input0.setAttribute("type", "password");
	input0.setAttribute("id", "password");
	input0.setAttribute("class", "col-md-7");
	input0.setAttribute("placeholder", "请输入密码");
	var group1 = document.createElement("div");
	modal.appendChild(group1);
	group1.setAttribute("class", "form-group");
	var label1 = document.createElement("label");
	group1.appendChild(label1);
	label1.setAttribute("for", "password_");
	label1.setAttribute("class", "col-md-4");
	label1.innerHTML="确认：";
	var input1 = document.createElement("input");
	group1.appendChild(input1);
	input1.setAttribute("type", "password");
	input1.setAttribute("id", "password_");
	input1.setAttribute("class", "col-md-7");
	input1.setAttribute("placeholder", "请输入确认密码");
	var warning = document.createElement("div");
	modal.appendChild(warning);
	warning.setAttribute("id", "passwordWarning");
	warning.setAttribute("style", "display:none");
	var span=document.createElement("span");
	warning.append(span);
}

function checkPassword() {
	$("#passwordModal #passwordWarning").css("display", "none");
	if ($("#passwordModal #password").val() == "") {
		$("#passwordModal #passwordWarning").css("display", "block");
		$("#passwordModal #passwordWarning span").text("密码不能为空");
		return false;
	} else if ($("#passwordModal #password").val() != $(
			"#passwordModal #password_").val()) {
		$("#passwordModal #passwordWarning").css("display", "block");
		$("#passwordModal #passwordWarning span").text("您输入两次密码不一致");
		return false;
	}
	return true;
}

function initModalTableReadOnly(doc) {
	var depName = $(doc.document).find("#deps #selectTree2_input").val();
	var rolesName = $(doc.document).find("#selectTree3_input").val();
	$(doc.document).find("#selectTree").remove();
	$(doc.document).find("#selectRoleTree").remove();
	var dep = document.createElement("span");
	$(doc.document).find("#depId").parent().append(dep);
	dep.innerHTML = depName;
	var roles = document.createElement("span");
	$(doc.document).find("#roleIds").parent().append(roles);
	roles.innerHTML = rolesName;
}

function initModalTableSave(doc) {
	var button = document.createElement("a");
	$(doc.document).find(".btn-primary").remove();
	$(doc.document).find(".btn-danger").parent().append(button);
	button.setAttribute("class", "btn btn-mini btn-primary");
	button.innerHTML = "保存";
	$(button).on("click", function() {
		var flag = inputVaildatorBeta($(doc.document).find(
				"[name='name']:eq(0)"), '', '请输入姓名');
		flag &= inputVaildatorBeta(
				$(doc.document).find("[name='email']:eq(0)"),
				/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
				'请正确的输入邮箱');
		flag &= inputVaildatorBeta($(doc.document).find(
				"[name='telephone']:eq(0)"), /^1[0-9]{10}$/,
				'请正确的输入电话号码');
		if (flag) {
			$.ajax({
				url : '/user',
				async : false,
				data : $(doc.document).find(".form-horizontal")
						.serialize(),
				dataType : 'json',
				type : 'put',
				success : function(data, textStatus, jqXHR) {
					
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					if (XMLHttpRequest.status != '200') {
						console.log(XMLHttpRequest.status);
					}
				}
			});
		}
	});
}

function editUser(id, callback) {
	top.showMask();
	var diag = new top.Dialog();
	diag.Drag = true;
	diag.Title = "编辑用户";
	diag.ShowMaxButton = true; //最大化按钮
	diag.ShowMinButton = true;
	diag.URL = "/user/" + id;
	diag.Width = 800;
	diag.Height = 500;
	diag.CancelEvent = function() { //关闭事件
		top.hideMask();
		setTimeout("self.location.reload()", 100);
		diag.close();
	};
	diag.show();
	var doc = diag.innerFrame.contentWindow;
	diag.OnLoad = function() {
		callback(doc);
	};
}
