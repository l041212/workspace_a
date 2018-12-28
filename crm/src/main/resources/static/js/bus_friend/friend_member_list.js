$(document).ready(function() {
	initTable();
	initZTree();
	initImport("impModal", "/friendMember/excel/import", "/friendMember/excel/model");
	initExport("expBtn","/friendMember/excel/export", "friendMemberToolbar");
});

function initTable() {
	var setColumns = function() {
		return [ {
			field : 'name',
			title : "姓名",
			align : 'center',
			valign : 'middle',
			sortable : true
		}, {
			field : 'type',
			title : "部门",
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				if (value != undefined && value != null) {
					return value.text;
				}
				return "";
			}
		}, {
			field : 'identity',
			title : "身份证",
			align : 'center',
			valign : 'middle',
			sortable : false
		}, {
			field : 'birthday',
			title : "出生日期",
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				if (value != undefined && value != null) {
					return dateToStr(value).replace(/\s+(\d{2}:){2}\d{2}/g, "");
				}
				return "";
			}
		}, {
			field : 'phone',
			title : "联络电话",
			align : 'center',
			valign : 'middle',
			sortable : false
		}, {
			field : 'email',
			title : "邮箱地址",
			align : 'center',
			valign : 'middle',
			sortable : false
		}, {
			field : 'applyTime',
			title : "申请日期",
			align : 'center',
			valign : 'middle',
			sortable : true,
			formatter : function(value, row, index) {
				if (value != undefined && value != null) {
					return dateToStr(value).replace(/\s+(\d{2}:){2}\d{2}/g, "");
				}
				return "";			
			}
		}, {
			title : "操作",
			align : 'center',
			valign : 'middle',
			formatter : function(value, row, index) {
				var writer = "";
				writer += "<div>";
				writer += "<button id='saveBtn_" + row.id + "' class='btn btn-xs btn-info' title='修改' ";
				writer += "onclick=\"saveEntity('" + row.id + "', '会员编辑', '/friendMember/')\">";
				writer += "<i class='ace-icon 	fa fa-pencil bigger-120'></i>";
				writer += "</button>";
				writer += "<button  id='delBtn_" + row.id + "' class='btn btn-xs btn-danger' title='刪除' ";
				writer += "onclick=\"deleteEntity('" + row.id + "', '会员删除', '/friendMember/')\">";
				writer += "<i class='ace-icon fa fa-remove bigger-120'></i>";
				writer += "</button>";
				writer += "</div>";
				return writer;
			}
		} ];
	};
	$('#friendMemberTable').bootstrapTable({
		url : "/friendMembers", // 请求后台的URL（*）
		method : "get", // 请求方式（*）
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		toolbar : "#friendMemberToolbar", // 工具按钮用哪个容器
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination : true, // 是否显示分页（*）
		sortable : true, // 是否启用排序
		sortOrder : "asc", // 排序方式
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		queryParamsType : "limit",
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 10, // 每页的记录行数（*）
		pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
		search : true, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
		strictSearch : false,
		showColumns : true, // 是否显示所有的列
		showRefresh : false, // 是否显示刷新按钮
		minimumCountColumns : 2, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		uniqueId : "id", // 每一行的唯一标识，一般为主键列
		showToggle : false, // 是否显示详细视图和列表视图的切换按钮
		cardView : false, // 是否显示详细视图
		detailView : false,
		onLoadSuccess : function() { // 加载成功时执行
			top.hideMask();
		},
		columns : setColumns(),
		queryParams : function(params) {
			return getQueryParams("friendMemberToolbar", params, this);
		} // 传递参数（*）
	});
}

function initZTree() {
	var path = "/friendMemberTypes/department/true/115";
	initSimpleZTree("typeId", "id", "parentId", "text", path, "friendMemberTable", "部门");
}
