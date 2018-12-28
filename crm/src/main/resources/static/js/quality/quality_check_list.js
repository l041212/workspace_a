$(function() {
	top.hideMask();
	initQualityCheckTable();
	initQuerySearchListener();
	initQueryClearListener();
	initDatetimePicker();
	$("[data-toggle='tooltip']").tooltip();
});

function initQualityCheckTable() {
	$('#qualityCheckTable').bootstrapTable(
			{
				url : "/qualityChecks", // 请求后台的URL（*）
				method : "POST", // 请求方式（*）
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				// toolbar : "#usertoolbar" , //工具按钮用哪个容器
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
				search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端
				strictSearch : false,
				showColumns : false, // 是否显示所有的列
				showRefresh : false, // 是否显示刷新按钮
				minimumCountColumns : 2, // 最少允许的列数
				clickToSelect : true, // 是否启用点击选中行
				uniqueId : "ID", // 每一行的唯一标识，一般为主键列
				showToggle : false, // 是否显示详细视图和列表视图的切换按钮
				cardView : false, // 是否显示详细视图
				detailView : false,
				queryParams : function(params) {
					var temp = getQueryParams("qualityCheckButton", params, this);
					temp['reStartHour'] = temp['reStartHour'] != '' ? transNum(temp['reStartHour']) : temp['reStartHour'];
					temp['reEndHour'] = temp['reEndHour'] != '' ? transNum(temp['reEndHour']) : temp['reEndHour'];
					return temp;
				}, // 传递参数（*）
				columns : [
						{
							field : 'ucid',
							title : "电话编号",
							align : 'center',
							valign : 'middle'
						},
						{
							field : 'agent_no',
							title : "工号",
							align : 'center',
							valign : 'middle',
							sortable : true
						},
						{
							field : 'calling_no',
							title : "主叫号码",
							align : 'center',
							valign : 'middle',
							sortable : true
						},
						{
							field : 'called_no',
							title : "被叫号码",
							align : 'center',
							valign : 'middle',
							sortable : true
						},
						{
							field : 'file_name',
							title : "文件名",
							align : 'center',
							valign : 'middle'
						},
						{
							field : 'direction',
							title : "方向",
							align : 'center',
							valign : 'middle',
							sortable : true,
							formatter : function(value, row, index) {
								if (value == 0) {
									return "呼入";
								} else if (value == 1) {
									return "呼出";
								} else {
									return "内线";
								}
							}
						},
						{
							field : 'end_time',
							title : "收藏时间",
							align : 'center',
							valign : 'middle',
							sortable : true,
							formatter : function(value, row, index) {
								return dateToStr(value);
							}
						},
						{
							title : "操作",
							align : 'center',
							valign : 'middle',
							formatter : function(value, row, index) {
								return '<div >' + '<button class="btn btn-xs btn-info" title="下载" onclick="download(\''
										+ row.file_name + '\')">'
										+ '<i class="ace-icon fa fa-cloud-download bigger-120"></i>'
										+ '</button>&nbsp;&nbsp;'
										+ '<button class="btn btn-xs btn-danger" title="播放" onclick="playRecord(&apos;'
										+ row.file_name + '&apos;)" >'
										+ '<i class="ace-icon fa fa-play-circle bigger-120"></i>' + '</button>'
										+ '</div>';
							}
						} ],
				onLoadSuccess : function() { // 加载成功时执行
					top.hideMask();
				}
			});
}

function initQuerySearchListener() {
	$("#querySearch").on("click", function() {
		$('#qualityCheckTable').bootstrapTable('refresh');
	});
}

function initQueryClearListener() {
	$("#queryClear").on("click", function() {
		$('#qualityCheckButton [name]').each(function() {
			$(this).val("");
		});
	});
}

function initDatetimePicker() {
	$("[name='reStartDate']").datetimepicker({format : 'yyyy-mm-dd', startView : 2, minView : 2, todayBtn : true, autoclose : true});
	$("[name='reEndDate']").datetimepicker({format : 'yyyy-mm-dd', startView : 2, minView : 2, todayBtn : true, autoclose : true});
	$(".datetimepicker .icon-arrow-left").addClass("fa fa-arrow-left");
	$(".datetimepicker .icon-arrow-right").addClass("fa fa-arrow-right");
}

function download(fileName) {
	window.location.href = "http://172.17.3.225:8070/MyService/GetMediaStream?name=" + fileName;
}

function playRecord(fileName) {
	$("#recordModal").modal({
		keyboard : false
	});
	$("#jquery_jplayer").jPlayer({
		ready : function() {
			$(this).jPlayer("setMedia", {
				title : "Bubble",
				mp3 : "http://172.17.3.225:8070/MyService/GetMediaStream?name" + fileName
			});
		},
		swfPath : "./plugins/jplayer",
		supplied : "mp3",
		wmode : "window",
		useStateClassSkin : true,
		autoBlur : false,
		smoothPlayBar : true,
		keyEnabled : true,
		remainingDuration : true,
		toggleDuration : true
	});
}