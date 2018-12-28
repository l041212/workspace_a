$(document).ready(function() {
	initDatetimePicker();
	initFiles();
	initMembers();
});

function initDatetimePicker() {
	$("[name='timeFrom']").val($("[name='timeFrom']").val() != 0 ? dateToStr($("[name='timeFrom']").val()) : "");
	$("[name='timeTo']").val($("[name='timeTo']").val() != 0 ? dateToStr($("[name='timeTo']").val()) : "");
	$("[name='timeFrom']").datetimepicker({
		format : 'yyyy-mm-dd hh:ii:ss',
		startView : 2,
		minView : 2,
		todayBtn : true,
		autoclose : true
	});
	$("[name='timeTo']").datetimepicker({
		format : 'yyyy-mm-dd hh:ii:ss',
		startView : 2,
		minView : 0,
		todayBtn : true,
		autoclose : true
	});
	$(".datetimepicker .icon-arrow-left").addClass("fa fa-arrow-left");
	$(".datetimepicker .icon-arrow-right").addClass("fa fa-arrow-right");
}

function initFiles() {
	var accept1 = "application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	var regex1 = /(\/|\\)[^\n^\t^\/^\\^:^,]+\.docx?$/ig;
	var hint1 = "请选择doc,docx格式的文件";
	var accept2 = ".zip,.rar,.7z";
	var regex2 = /(\/|\\)[^\n^\t^\/^\\^:^,]+\.(rar|zip|7z)$/ig;
	var hint2 = "请选择rar,zip,7z格式的文件";
	initSingleFile("content", accept1, regex1, hint1);
	initSingleFile("photo", accept2, regex2, hint2);
}

function initMembers() {
	var valMap = new Map();
	var findVal = function(treeNode, val) {
		if (treeNode.id.indexOf("t_") > -1) {
			if (treeNode.children != null) {
				for(var item of treeNode.children) {
					findVal(item, val);
				}
			}
		} else {
			if (treeNode.id != undefined && treeNode.id != null) {
				val.set(treeNode.id, treeNode.text);
			}
		}
	};
	var setting = {
		data : {
			key : {
				name : "text"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId",
				rootPId : null
			}
		},
		check : {
			enable : true,
			chkable : "checkbox"
		},
		callback : {
			onCheck : function(event, treeId, treeNode) {
				var pool = $("#membersModal #treePool").val();
				var val = new Map();
				findVal(treeNode, val);
				val.forEach(function(value, key, map) {
					!treeNode.checked ? valMap.delete(key) : valMap.set(key, value);
				});
			}
		}
	};
	var read = function(name) {
		var value = "";
		$("[name='" + name + "']").css("display", "none");
		$("[name='" + name + "']").attr("value", "");
		$("[name='" + name + "']").parent().find("li").each(function() {
			value += $(this).attr("value") + ",";
		});
		$("[name='" + name + "']").attr("value", value != "" ? value.substr(0, value.length - 1) : "");
	};
	var load = function(id, path) {
		$.ajax({
			async : false,
			contentType : "application/x-www-form-urlencoded",
			type : 'get',
			url : path,
			dataType : 'json',
			success : function(data, textStatus, jqXHR) {
				$.fn.zTree.init($("#" + id + "Tree"), setting, data);
			}
		});
	};
	var modal = function(name, path) {
		$("[name='" + name + "']").parent().find("a").on("click", function() {
			valMap = new Map();
			initModal(name + "Modal", "编辑", function(tbody) {
				var tree = document.createElement("div");
				$(tbody).append(tree);
				tree.setAttribute("id", name + "Tree");
				tree.setAttribute("class", "ztree");
				load(name, path);
				var pool = document.createElement("input");
				$(tbody).append(pool);
				pool.setAttribute("type", "hidden");
				pool.setAttribute("id", "treePool");
			}, function(tfooter) {
				$(tfooter).find(".btn-confirm").on("click", function() {
					var val=new Array();
					Array.from(valMap.keys()).forEach(item => val.push(item.replace("e_","")));
					$("[name='" + name + "']").attr("value", val.join(","));
					$("#"+name+"List ul li").remove("");
					$("#"+name+"List ul").length <= 0 ? $("#"+name+"List").append(document.createElement("ul")) : null;			
					for([key,value] of valMap) {
						var li = document.createElement("li");
						$("#"+name+"List ul").append(li);
						li.setAttribute("id", value);
						li.innerHTML=value;
					}
					$("#" + name + "Modal").modal("hide");
				});
			});
		});
	};
	read("members");
	modal("members", "/friendMemberTypes/member/true/115");
}

function validate() {
	var flag = vaildatorBeta($("[name='title']:eq(0)"), '', '请输入标题');
	flag &= vaildatorBeta($("[name='members']:eq(0)"), '', '请输入参与成员', $(".fa-pencil:eq(0)"));
	flag &= vaildatorBeta($("[name='timeFrom']:eq(0)"), '', '请输入开始时间');
	flag &= vaildatorBeta($("[name='timeTo']:eq(0)"), '', '请输入结束时间');
	flag &= vaildatorBeta($("[name='location']:eq(0)"), '', '请输入地点');
	flag &= vaildatorBeta($("[name='content']:eq(0)"), '', '请上传内容', $("#contentUploadBtn"));
	flag &= vaildatorBeta($("[name='photo']:eq(0)"), '', '请上传图片', $("#photoUploadBtn"));
	flag &= vaildatorBeta($("[name='summary']:eq(0)"), '', '请输入概述');
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
		top.showMask();
		$.ajax({
			async : true,
			contentType : "application/x-www-form-urlencoded",
			type : method,
			url : path,
			data : parameters,
			dataType : 'json',
			success : function(data, textStatus, jqXHR) {
				top.hideMask();
				setTimeout("self.location.reload()", 100);
				top.Dialog.close();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				top.hideMask();
			}
		});
	}
}
