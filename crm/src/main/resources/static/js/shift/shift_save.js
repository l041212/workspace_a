$(function() {
	top.hideMask();
});

function inputVaildatorBeta(selector, regex, hint) {
	var execute = function(selector, hint) {
		selector.tips({
			side : 3,
			msg : hint,
			bg : '#AE81FF',
			time : 2
		});
		selector.focus();
	}
	if (!(selector != undefined && selector.val() != undefined && selector.val() != '')) {
		execute(selector, hint);
		return false;
	}
	if (regex != undefined && regex != '' && (!regex.test(selector.val()))) {
		execute(selector, hint);
		return false;
	}
	return true;
}

function save() {
	var flag = inputVaildatorBeta($("[name='name']:eq(0)"), '', '请输入名称');
	flag &= inputVaildatorBeta($("[name='period']:eq(0)"), '', '请输入时段');
	flag &= inputVaildatorBeta($("[name='color']:eq(0)"), '', '请输入颜色');
	if (flag) {
		$("#shiftForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
}