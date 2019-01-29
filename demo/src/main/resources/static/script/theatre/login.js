
$(function() {
	initHintPopoverListener();
	initSignInListener();
});

function initHintPopoverListener() {
	setPopoverBeta($("label[for='username'] strong")[0], "user name is empty...", "left");
	setPopoverBeta($("label[for='password'] strong")[0], "password is empty...", "left");
}

function validator() {
	var flag = true;
	flag &= vaildatorBeta($("[name='username']")[0], "", $("label[for='username'] div[data-toggle='popover']")[0]);
	flag &= vaildatorBeta($("[name='password']")[0], "", $("label[for='password'] div[data-toggle='popover']")[0]);
	return flag;
}

function initSignInListener() {
	$("#signIn").on("click", function() {
		if (validator()) {
			var parameters = $("form").serialize();
			var pathSignIn = window.parameters["contextPath"] + "/theatre/login/signIn";
			var pathIndex =  window.parameters["contextPath"] + "/index";
			$.ajax({
				url : pathSignIn,
				contentType : "application/x-www-form-urlencoded;charset=utf-8",
				async : false,
				data : parameters,
				dataType : 'json',
				type : 'post'
			}).done(function(data) {
				if (data["message"] != "") {
					alert(JSON.stringify(data));
				} else {
						window.location.href = pathIndex;
				}
			});
		}
	});
}