var parameters = initParameters();

$(function() {
	moveFragment();
});

function moveFragment() {
	$(".style_").after($(".style_").html());
	$(".script_").after($(".script_").html());
	$(".body_").after($(".body_").html());
	$(".style_").remove();
	$(".script_").remove();
	$(".body_").remove();
}

function setPopoverBeta(target, hint, direction) {
	var popover = document.createElement("div");
	popover.setAttribute("data-container", "body");
	popover.setAttribute("data-toggle", "popover");
	popover.setAttribute("data-placement", direction);
	popover.setAttribute("data-content", hint);
	$(target).after(popover);
	$(popover).append(target);
}

function vaildatorBeta(selector, regex, popover) {
	var execute = function(popover) {
		$(popover).popover("show");
		setTimeout(function() {
			$(popover).popover("hide");
		}, 2000);
	}
	regex.lastIndex = 0;
	if (!($(selector) != undefined && $(selector).val() != undefined && $(selector).val() != '')) {
		execute(popover);
		return false;
	}
	if (regex != undefined && regex != '' && (!regex.test($(selector).val()))) {
		execute(popover);
		return false;
	}
	return true;
}

function getPathValue(type, name) {
	type = type=="url" ? window.location.search.substr(1) : type;
	type = type=="cookie" ? document.cookie : type;
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = type.match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function initContextPath(parameters) {
	var contextPath = parameters["contextPath"];
	if (contextPath != undefined) {
		parameters["contextPath"] = contextPath.substr(0, contextPath.length - 1);
	}
}

function initParameters() {
	var parameters = new Object();
	var scripts = document.getElementsByTagName("script");
	if (scripts[scripts.length - 1].innerHTML != "") {
		var elements = scripts[scripts.length - 1].innerHTML.split("#");
		for ( let index in elements) {
			if (elements[index].split("=")[1] != undefined) {
				parameters[elements[index].split("=")[0]] = elements[index].split("=")[1];
			}
		}
		initContextPath(parameters);
	}
	return parameters;
}
