$(function() {
	top.hideMask();
	$('#shiftTable')
			.bootstrapTable(
					{
						url : "/shifts", //请求后台的URL（*）
						method : "get", //请求方式（*）
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						toolbar : "#shiftToolbar", //工具按钮用哪个容器
						striped : true, //是否显示行间隔色
						cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
						pagination : true, //是否显示分页（*）
						sortable : true, //是否启用排序
						sortOrder : "asc", //排序方式
						sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
						queryParamsType : "limit",
						pageNumber : 1, //初始化加载第一页，默认第一页
						pageSize : 10, //每页的记录行数（*）
						pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
						search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
						strictSearch : false,
						showColumns : true, //是否显示所有的列
						showRefresh : false, //是否显示刷新按钮
						minimumCountColumns : 2, //最少允许的列数
						clickToSelect : true, //是否启用点击选中行
						uniqueId : "id", //每一行的唯一标识，一般为主键列
						showToggle : false, //是否显示详细视图和列表视图的切换按钮
						cardView : false, //是否显示详细视图
						detailView : false,
						queryParams : function(params) {
							return getQueryParams("shiftToolbar", params, this);
						}, //传递参数（*）
						columns : [
								{
									field : 'name',
									title : "名称",
									align : 'center',
									valign : 'middle',
									cellStyle : function(value, row, index) {
										return {
											css: {
												'background': row.color
											}
										};
									}
								},
								{
									field : 'period',
									title : "时段",
									align : 'center',
									valign : 'middle',
									sortable : true
								},
								{
									field : 'description',
									title : "描述",
									align : 'center',
									valign : 'middle',
									sortable : true
								},
								{
									title : "操作",
									align : 'center',
									valign : 'middle',
									formatter : function(value, row, index) {
										return '<div class=" btn-group">'
												+ '<button class="btn btn-xs btn-info" onclick="saveEntity(\'' + row.id + '\', \'班次编辑\', \'./shift/\')">'
												+ '<i class="ace-icon fa fa-pencil bigger-120"></i>'
												+ '</button>'
												+ '<button class="btn btn-xs btn-danger" onclick="deleteShift(&apos;'
												+ row.id
												+ '&apos;,&apos;'
												+ row.name
												+ '&apos;)" >'
												+ '<i class="ace-icon fa fa-trash-o bigger-120"></i>'
												+ '</button>' + '</div>';
									}
								} ],
						onLoadSuccess : function() { //加载成功时执行
							top.hideMask();
						}
					});
})

function deleteShift(id, name) {
	bootbox.confirm("确定要删除[" + name + "]吗?", function(result) {
		if (result) {
			$.ajax({
				url : './shift/' + id,
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