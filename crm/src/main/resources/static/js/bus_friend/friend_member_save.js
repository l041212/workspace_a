$(document).ready(function() {
	initZTree();
	initSelector();
	initDatetimePicker();
});

function initZTree() {
	var path = "/friendMemberTypes/department/true/115";
	initSimpleZTree("typeId", "id", "parentId", "text", path, "", "部门");
	setTimeout(function() {
		$(".typeIdTreeText").val($("#typeText").val());
		$(".treeTextGroup").css("width", "98%");
		$(".treeClear").css("top", "0px");
		$(".treeZone").css("border-top", "1px solid beige");
		$(".treeZone").css("top", "");
		$(".treeZone").css("left", "");
	}, 200);
}

function initSelector() {
	$("#sex_").val($("#sex").val());
	$("#education_").val($("#education").val());
	$("#job_").val($("#job").val());
	$("#sex_").on("change", function() {
		$("#sex").val($("#sex_").val());
	});
	$("#education_").on("change", function() {
		$("#education").val($("#education_").val());
	});
	$("#job_").on("change", function() {
		$("#job").val($("#job_").val());
	});
}

function initDatetimePicker() {
	$("[name='birthday']").val($("[name='birthday']").val() != 0 ? dateToStr($("[name='birthday']").val()).replace(/\s+(\d{2}:){2}\d{2}/g, "") : "");
	$("[name='applyTime']").val($("[name='applyTime']").val() != 0 ? dateToStr($("[name='applyTime']").val()).replace(/\s+(\d{2}:){2}\d{2}/g, "") : "");
	$("[name='birthday']").datetimepicker({
		format : 'yyyy-mm-dd',
		startView : 2,
		minView : 2,
		todayBtn : true,
		autoclose : true
	});
	$("[name='applyTime']").datetimepicker({
		format : 'yyyy-mm-dd',
		startView : 2,
		minView : 2,
		todayBtn : true,
		autoclose : true
	});
}

function validate() {
	var flag = vaildatorBeta($("[name='name']:eq(0)"), '', '请输入名称');
	flag &= vaildatorBeta($(".typeIdTreeText"), '', '请输入部门');
	flag &= vaildatorBeta($("#sex_"), /^[1-9]\d*$/g, '请输入性别');
	flag &= vaildatorBeta($("[name='birthday']:eq(0)"), '', '请输入生日');
	flag &= vaildatorBeta($("[name='identity']:eq(0)"), /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/g, '请输入正确的身份证');
	flag &= vaildatorBeta($("[name='phone']:eq(0)"), /[0-9-()（）]{7,18}/g, '请输入正确的联络电话');
	flag &= vaildatorBeta($("[name='email']:eq(0)"), /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/g, '请输入正确的邮箱地址');
	flag &= vaildatorBeta($("[name='address']:eq(0)"), '', '请输入住宅地址');
	flag &= vaildatorBeta($("#education_"), /^[1-9]\d*$/g, '请输入教育');
	flag &= vaildatorBeta($("#job_"), /^[1-9]\d*$/g, '请输入职业');
	flag &= vaildatorBeta($("[name='company']:eq(0)"), '', '请输入公司');
	flag &= vaildatorBeta($("[name='post']:eq(0)"), '', '请输入职位');
	flag &= vaildatorBeta($("[name='line']:eq(0)"), '', '请输入常用线路');
	flag &= vaildatorBeta($("[name='applyTime']:eq(0)"), '', '请输入申请时间');
	flag &= vaildatorBeta($("[name='destination']:eq(0)"), '', '请输入申请目的');
	return flag;
}

function save() {
	if (validate()) {
		var path = document.location.href;
		var parameters = new Object();
		var method = ($("[name='id']").val() != undefined && $("[name='id']").val() != "") ? "put" : "post";
		$("[name]").each(function() {
			parameters[$(this).attr("name")] = $(this).val();
		});
		$.ajax({
			async : true,
			contentType : "application/x-www-form-urlencoded",
			type : method,
			url : path,
			data: parameters,
			dataType : 'json',
			success : function(data, textStatus, jqXHR) {
				setTimeout("self.location.reload()", 100);
				top.Dialog.close();
			}
		});
	}
}
