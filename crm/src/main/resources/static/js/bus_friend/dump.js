
function initSingleFile(name, accept, regex, hint) {
	var reset = function(object, fname) {
		var name = $(object).val().match(/(\/|\\)[^\n^\t^\/^\\^:^,]+\.[a-zA-Z0-9]{1,4}$/ig);
		var a = document.createElement("a");
		$(object).after(a);
		a.setAttribute("href", fname != "" ? "#" : $(object).val());
		a.innerHTML = name != null ? name[0].replace(/(\/|\\)/g, "") : fname;
		var i = document.createElement("i");
		$(a).after(i);
		i.setAttribute("class", "fa fa-remove");
		i.setAttribute("title", "清除");
		$(i).css("color", "indianred");
		$(i).css("margin-left", "1%");
		var fnInput = document.createElement("input");
		$(i).after(fnInput);
		fnInput.setAttribute("name", "fn_" + $(object).attr("name"));
		fnInput.setAttribute("type", "hidden");
		fnInput.setAttribute("value", name != null ? name[0].replace(/(\/|\\)/g, "") : fname);
		$("#" + $(object).attr("name") + "UploadBtn").remove();
		$(i).on("click", function() {
			$(this).parent().find("[name]").attr("value", "");
			$(this).parent().find("a").remove();
			$(this).parent().find("[name^='fn_']").remove();
			$(this).parent().find("i").remove();
			upload(object);
		});
	}
	var upload = function(object) {
		var imPath = "/friendEvent/test";
		var button = document.createElement("input");
		$(object).after(button);
		button.setAttribute("type", "button");
		button.setAttribute("id", $(object).attr("name") + "UploadBtn");
		button.setAttribute("class", "btn btn-info btn-mini");
		button.setAttribute("data-toggle", "modal");
		button.setAttribute("data-target", "#" + $(object).attr("name") + "UploadModal");
		button.setAttribute("value", "上传");
		initFileModal($(object).attr("name") + "UploadModal", accept, regex, hint, function(file, value) {
			if (file != null && file != "") {
				$("[name='" + name + "']").attr("value", value);
				reset($("[name='" + name + "']")[0], file.name);
				$("#" + $(object).attr("name") + "UploadModal").modal('hide');
			}
		});	
	}
	$("[name='" + name + "']").css("display", "none");
	if ($("[name='" + name + "']").val() != null && $("[name='" + name + "']").val() != "") {
		reset($("[name='" + name + "']")[0], "");
	} else {
		upload($("[name='" + name + "']")[0]);
	}
}

function initFileModal(id, accept, regex, hint, callback) {
	var initFileInput = function(object) {
		$(object).ace_file_input({
			no_file : '请选择文档 ...',
			btn_choose : '选择',
			btn_change : '更改',
			droppable : false,
			onchange : null,
			thumbnail : false
		});
	}
	var initConfirm = function(confirm, input) {
		$(confirm).on("click", function() {
			var flag = vaildatorBeta($(input), regex, hint);
			if (flag) {
				readFile(input.files[0], 1, function(file) {	
					callback(input.files[0], window.btoa(file));
				});
			};
		});
	}
	initModal(id, "文档上传", function(tbody) {
		var form = document.createElement("form");
		$(tbody).append(form);
		form.setAttribute("enctype", "multipart/form-data");
		form.setAttribute("method", "post");
		form.setAttribute("id", "fileUploadForm");
		form.setAttribute("name", "fileUploadForm");
		var input = document.createElement("input");
		$(form).append(input);
		input.setAttribute("type", "file");
		input.setAttribute("id", "uploadFile");
		input.setAttribute("name", "uploadFile");
		input.setAttribute("accept", accept);
		input.setAttribute("onchange", "");
		initFileInput(input);
	}, function(tfooter) {
		initConfirm($(tfooter).find(".btn-confirm")[0], $(tfooter).parent().find("#uploadFile")[0]);
	});
}

function initImport(id, imPath, spPath) {
	var initFileInput = function(object) {
		$(object).ace_file_input({
			no_file : '请选择EXCEL ...',
			btn_choose : '选择',
			btn_change : '更改',
			droppable : false,
			onchange : null,
			thumbnail : false, // | true | large
			whitelist : 'xls|xls',
			blacklist : 'gif|png|jpg|jpeg'
		});
	}
	var initConfirm = function(confirm, input) {
		$(confirm).on("click", function() {
			var flag = vaildatorBeta($(input), /(\/|\\)[^\n^\t^\/^\\^:^,]+\.xls$/ig, "请选择xls格式的文件");
			var formData = new FormData();
			formData.append("excelImportFile", input.files[0]);
			if (flag) {
				$.ajax({
					async : false,
					cache: false,
					processData: false,
					contentType : false,
					type : 'post',
					url : imPath,
					data : formData,
					dataType : 'text	',
					success : function(data, textStatus, jqXHR) {
						$("#"+id).modal('hide');
						setTimeout("self.location.reload()",100);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						if (XMLHttpRequest.status != 200) {
							vaildatorBeta($(input), /^#$/ig, "导入数据可能不正确");
						}
					}
				});
			}
		});
	}
	var initSample = function(sample) {
		$(sample).on("click", function() {
			window.open(spPath);
		});
	}
	initModal(id, "导入", function(tbody) {
		var form = document.createElement("form");
		$(tbody).append(form);
		form.setAttribute("enctype", "multipart/form-data");
		form.setAttribute("method", "post");
		form.setAttribute("id", "excelImportForm");
		form.setAttribute("name", "excelImportForm");
		var input = document.createElement("input");
		$(form).append(input);
		input.setAttribute("type", "file");
		input.setAttribute("id", "excelImportFile");
		input.setAttribute("name", "excelImportFile");
		input.setAttribute("accept", ".xls");
		input.setAttribute("onchange", "");
		initFileInput(input);
	}, function(tfooter) {
		var sample = document.createElement("button");
		$(tfooter).append(sample);
		sample.setAttribute("type", "button");
		sample.setAttribute("class", "btn btn-info btn-sample");
		sample.setAttribute("title", "导出模板");
		sample.innerHTML = "模板";
		initSample(sample);
		$(tfooter).find(".btn-confirm").html("导入");
		$(tfooter).find(".btn-confirm").attr("title", "导入Excel");
		initConfirm($(tfooter).find(".btn-confirm")[0], $(tfooter).parent().find("#excelImportFile")[0]);
	});
}

function initExport(id, exPath, prefix) {
	$("#" + id).on("click", function() {
		var query_params = JSON.parse(sessionStorage[prefix + "_query_params"]);
		var parameters = objToWebStr(query_params);
		window.open(exPath + parameters);
	});
}

function deleteEntity(id, label, path) {
	bootbox.confirm("确定要删除[" + label + "]吗?", function(result) {
		if (result) {
			$.ajax({
				url : path + id,
				async : false,
				dataType : "text",
				type : 'delete',
				success : function(data, textStatus, jqXHR) {
					setTimeout("self.location.reload()", 100);
					return false;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if (XMLHttpRequest.status != '200') {
						console.log(XMLHttpRequest.status);
						return false;
					}
				}
			});
		}
	});
}

function saveEntity(id, title, path) {
	var diag = new top.Dialog();
	diag.Drag = true;
	diag.title = title;
	diag.ShowMaxButton = true;
	diag.ShowMinButton = true;
	diag.URL = path + (id != undefined && id != '' ? id : '');
	diag.Width = 500;
	diag.Height = 470;
	diag.CancelEvent = function() {
		top.hideMask();
		setTimeout("self.location.reload()", 100);
		diag.close();
	};
	diag.show();
}

function getQueryParams(id, params, object) {
	var temp = new Object();
	temp['limit'] = params.limit;
	temp['page'] = params.offset / params.limit;
	temp['keywords'] = params.search;
	temp['sort'] = params.sort;
	temp['order'] = params.order;
	temp['pageNumber'] = object.pageNumber;
	temp['pageSize'] = object.pageSize;
	$("#"+id+" [name]").each(function(){
		temp[$(this).attr("name")] = $(this).val();
	});
	sessionStorage[id + "_query_params"] = JSON.stringify(temp);
	return temp;
}

function initModal(id, ttitle, tbody, tfooter) {
	$("#" + id).remove();
	var fade = document.createElement("div");
	$("body").append(fade);
	fade.setAttribute("class", "modal fade");
	fade.setAttribute("id", id);
	var dialog = document.createElement("div");
	$(fade).append(dialog);
	dialog.setAttribute("class", "modal-dialog modal-sm");
	var content = document.createElement("div");
	$(dialog).append(content);
	content.setAttribute("class", "modal-content");

	var header = document.createElement("div");
	$(content).append(header);
	header.setAttribute("style", "padding:5px;color:white;background-color:#76accd");
	header.setAttribute("class", "modal-header");
	var title = document.createElement("span");
	title.setAttribute("style","float:left");
	$(header).append(title);
	title.setAttribute("class", "modal-title");
	title.innerHTML = ttitle;
	var times = document.createElement("a");
	$(header).append(times);
	times.setAttribute("style","float:right;color:white;padding:2px");
	times.setAttribute("type", "button");
	times.setAttribute("class", "fa fa-remove");
	times.setAttribute("data-dismiss", "modal");
	times.setAttribute("title", "关闭");

	var body = document.createElement("div");
	$(content).append(body);
	body.setAttribute("class", "modal-body");
	tbody(body);

	var footer = document.createElement("div");
	$(content).append(footer);
	footer.setAttribute("class", "modal-footer");
	var cancel = document.createElement("button");
	$(footer).append(cancel);
	cancel.setAttribute("type", "button");
	cancel.setAttribute("class", "btn btn-secondary btn-cancel");
	cancel.setAttribute("data-dismiss", "modal");
	cancel.innerHTML = "取消";
	cancel.setAttribute("title", "取消");
	var confirm = document.createElement("button");
	$(footer).append(confirm);
	confirm.setAttribute("type", "button");
	confirm.setAttribute("class", "btn btn-info btn-confirm");
	confirm.innerHTML = "确认";
	confirm.setAttribute("title", "确认");
	tfooter(footer);
	$(footer).css("padding","10px");
	$(footer).find("button").css("border-width","2px");
	$(footer).find("button").css("padding","2px 4px 2px 4px");
}

function vaildatorBeta(selector, regex, hint, target) {
	var execute = function(selector, hint, target) {
		var object = target != undefined && target != null ? target : selector;
		object.tips({
			side : 3,
			msg : hint,
			bg : 'IndianRed',
			time : 2
		});
		object.focus();
	}
	regex.lastIndex=0;
	if (!(selector != undefined && selector.val() != undefined && selector.val() != '')) {
		execute(selector, hint, target);
		return false;
	}
	if (regex != undefined && regex != '' && (!regex.test(selector.val()))) {
		execute(selector, hint, target);
		return false;
	}
	return true;
}

function readFile(file, type, callback) {
	var reader = new FileReader();
	reader.onload = function(e) {
		callback(this.result);
	}
	switch (type) {
		case 0:
			reader.readAsText(file);
			break;
		case 1:
			reader.readAsBinaryString(file);
			break;
		default:
			reader.readAsDataURL(file);
			break;
	}
}

function objToWebStr(object) {
	var parameters = "";
	if (object != undefined && object != null) {
		for ( var key in object) {
			parameters += key + "=" + object[key] + "&";
		}
		parameters = parameters.substr(0, parameters.length - 1);
		parameters = (parameters != "") ? "?" + parameters : parameters;
	}
	return parameters;
}

function dateToStr(datetime) {
	var datetime = new Date(datetime);
	var year = datetime.getFullYear();
	var month = transNum(datetime.getMonth() + 1);
	var date = transNum(datetime.getDate());
	var hour = transNum(datetime.getHours());
	var minutes = transNum(datetime.getMinutes());
	var second = transNum(datetime.getSeconds());
	return year + "-" + month + "-" + date + " " + hour + ":" + minutes + ":" + second;
}

function correctTimeCTS(datetime) {
	var datetime = new Date(datetime);
	timestamp = datetime.getTime() - 14 * 3600 * 1000;
	return new Date(timestamp);
}

function transNum(num) {
	return String(parseInt(num / 10)) + String(num % 10);
}

function initSimpleZTree(name, id, parentId, label, path, table, hint) {
	var setting = {
		data : {
			key : {
				name : label
			},
			simpleData : {
				enable : true,
				idKey : id,
				pIdKey : parentId,
				rootPId : null
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				$("." + name + "TreeText").val($(treeNode).attr("value"));
				$("[name='" + name + "']").val($(treeNode).attr("id"));
				$("#" + table).bootstrapTable('refresh');				
			}
		}
	};
	var init = function() {
		var treeTextGroup = document.createElement("div");
		$("[name='" + name + "']").after(treeTextGroup);
		treeTextGroup.setAttribute("class", "treeTextGroup");
		var treeText = document.createElement("input");
		$(treeTextGroup).append(treeText);
		treeText.setAttribute("class", "treeText " + name + "TreeText");
		treeText.setAttribute("title", hint);
		var treeClear = document.createElement("a");
		$(treeTextGroup).append(treeClear);
		treeClear.setAttribute("class", "treeClear " + name + "TreeClear");
		treeClear.setAttribute("title", "清除");
		var treeClearIcon = document.createElement("i");
		$(treeClear).append(treeClearIcon);
		treeClearIcon.setAttribute("class", "fa fa-remove");
		var treeZone = document.createElement("div");
		$(treeTextGroup).after(treeZone);
		treeZone.setAttribute("class", "treeZone " + name + "TreeZone");
		var ztree = document.createElement("ul");
		treeZone.append(ztree);
		ztree.setAttribute("id", name + "Tree");
		ztree.setAttribute("class", "ztree");
		ztree.setAttribute("style", "overflow:auto;");
	}
	var tintage = function() {
		$("[name='" + name + "']").css("display", "none");
		$(".treeTextGroup").css("display", "inline-block");
		$(".treeTextGroup").css("text-align", "center");
		$(".treeTextGroup").css("width", "120px");
		$(".treeText").css("width", "80%");
		$(".treeText").css("height", "30px");
		$(".treeText").css("border", "1px solid lightgray");
		$(".treeText").css("padding", "4px 5px 4px");	
		$(".treeClear").css("display", "block");
		$(".treeClear").css("float", "right");
		$(".treeClear").css("width", "20%");
		$(".treeClear").css("height", "30px");
		$(".treeClear").css("line-height", "2");
		$(".treeClear").css("position", "relative");
		$(".treeClear").css("top", "2px");
		$(".treeClear").css("cursor", "pointer");
		$(".treeClear").css("text-decoration", "none");
		$(".treeClear").css("background-color", "indianred");
		$(".treeClear").css("border", "1px solid lightgray");
		$(".treeClear").css("border-left", "0px");
		$(".treeClear i").css("position", "relative");
		$(".treeClear i").css("color", "white");
		$(".treeZone").css("display", " none");
		$(".treeZone").css("z-index", "100");
		$(".treeZone").css("position", "absolute");
		$(".treeZone").css("background", "beige");
		$(".treeZone").css("border", "1px solid lightgray");
	}
	var load = function(treeNodes) {
		$.fn.zTree.init($("#" + name + "Tree"), setting, treeNodes);
		$("." + name + "TreeZone").css("top", $("." + name + "TreeText")[0].offsetTop + $("." + name + "TreeText")[0].offsetHeight - 1);
		$("." + name + "TreeZone").css("left", $("." + name + "TreeText")[0].offsetLeft);
		$("." + name + "TreeZone").on("focusout", function() {
			setTimeout(function() {
				if (document.activeElement.id.indexOf(name + "Tree") < 0) {
					$("." + name + "TreeZone").css("display", "none");
				}
			}, 100);
		});
		$("." + name + "TreeText").on("click", function() {
			$("." + name + "TreeZone").css("display", "block");
			$("." + name + "TreeZone li")[0].focus();
		});
		$("." + name + "TreeClear").on("click", function() {
			$("." + name + "TreeText").val("");
			$("[name='" + name + "']").val("");
			$("#" + table).bootstrapTable('refresh');	
		});
	}
	$.ajax({
		async : false,
		contentType : "application/x-www-form-urlencoded",
		type : 'get',
		url : path,
		dataType : 'json',
		success : function(data, textStatus, jqXHR) {
			init();
			tintage();
			load(data);
		}
	});
}
