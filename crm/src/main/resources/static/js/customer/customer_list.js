$(document).ready(function() {
	initModelListener();
	initImportListener();
	initExportListener();
});

function initModelListener() {
	$("#model").on("click", function() {
		setModalDialog("模板", "./cust/export/model", function() {
		}, function() {
		}, function(diag) {
			diag.AutoClose = 2;
		});
	});
}

function initExportListener() {
	$("#export").on("click", function() {
		setModalDialog("导出", "./cust/export", function() {
		}, function() {
		}, function(diag) {
			diag.AutoClose = 2;
		});
	});
}

function initImportListener() {
	var checkData = function(doc) {
		var fileDir = doc.getElementById("file").value;
		var suffix = fileDir.substr(fileDir.lastIndexOf("."));
		doc.getElementById("hint").setAttribute("style","display:none");
		doc.getElementById("hint").innerHTML="";
		if ("" == fileDir) {
			doc.getElementById("hint").setAttribute("style","display:block");
			doc.getElementById("hint").innerHTML="选择需要导入的Excel文件！";
			return false;
		}
		if (".xls" != suffix && ".xlsx" != suffix) {
			doc.getElementById("hint").setAttribute("style","display:block");
			doc.getElementById("hint").innerHTML="选择Excel格式的文件导入！";
			return false;
		}
		return true;
	}
	$("#import").on("click", function() {
		var html = "<style>";
		html += "#importForm {"
		html += "display: block;width: 90%; height: 180px; background: white; border-radius: 5px;text-align: center;";
		html += "position: absolute; margin: auto; top: 0px; bottom: 0px; left: 0px; right: 0px; }";
		html += "#importForm input {padding-top: 5px; width: 90%;}";
		html += "#importForm div{float: left; color: dimgray; text-align: center; width: 90%; height: 20px;";
		html += "background-color: orange; border-radius: 5px; position: absolute; margin: auto; left: 0px; right: 0px; top: 20%;}";
		html += "</style>"; 
		html += "<form id='importForm' method='post' enctype='multipart/form-data'>";
		html += "<input type='file' id='file' name='file'>";
		html += "<div id='hint' class='alert alert-danger' style='display:none'></div>";
		html += "</form>";
		setModalDialog('导入', "test.html", function(diag, doc) {
			if (checkData(doc)) {
				var formData = new FormData();
				var fileDir = doc.getElementById("file").value;
				var suffix = fileDir.substr(fileDir.lastIndexOf("."));
				formData.append('suffix', suffix);
	            formData.append("file", doc.getElementById("file").files[0]);
	            $.ajax({
	                url: "./cust/import",
	                type: "post",
	                data: formData,
	                contentType: false,
	                processData: false,
	                success: function (data) {
	                	if (data!=false) {
	                		setTimeout("self.location.reload()",100);
	                		diag.close();
						} else {
							alert("可能格式有误");
						}
	                }
	            });
			}
		}, function() {
		}, function() {
		}, html);
	});
}

function setModalDialog(title, url, ok, cancel, execute, html) {
	var doc = null;
	var diag = new top.Dialog();
	diag.Drag = true;
	diag.title = title;
	diag.ShowMaxButton = true;
	diag.ShowMinButton = true;
	diag.URL = url;
	diag.Width = 300;
	diag.Height = 200;
	diag.OKEvent = function() {
		ok(diag, doc);
	};
	diag.CancelEvent = function() {
		top.hideMask();
		cancel(diag, doc);
		diag.close();
	};
	execute(diag, doc);
	diag.show();
	if (html != undefined && html != "") {
		doc = diag.innerFrame.contentWindow.document;
		doc.open();
		doc.write(html);
		doc.close();
	}
}