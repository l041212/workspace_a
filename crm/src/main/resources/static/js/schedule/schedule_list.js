$(function() {
	var shift = getShiftList();
	var staff = getStaffList();
	top.hideMask();
	initDatetimePicker();
	initDataSubmit();
	initScheduleTable(staff, shift);
	initTableScroll();
	initScheduleAutoSort1(shift, staff);
	initExport();
	initShiftDetail(shift);
	initStaffDetail(staff);
});

function initTableScroll(){
	$("#ctx-zone").on("scroll",function(){
		$("#menu-zone")[0].scrollTop = this.scrollTop;
		$("#ctx-title")[0].scrollLeft = this.scrollLeft;
	});
	$("#ctx-zone-mirror").on("scroll",function(){
		$("#menu-zone-mirror")[0].scrollTop = this.scrollTop;
		$("#ctx-title-mirror")[0].scrollLeft = this.scrollLeft;
	});
}

function initDatetimePicker() {
	var datetime = $("#datetimepicker").val();
	var date = new Date();
	if (!(datetime != undefined && datetime != "")) {
		$("#datetimepicker").val(
				date.getFullYear() + "-" + (date.getMonth() + 1));
	}
	$('#datetimepicker').datetimepicker({
		format : 'yyyy-mm',
		startView : 3,
		minView : 3,
		todayBtn : true,
		autoclose : true
	});
	$(".datetimepicker .icon-arrow-left").addClass("fa fa-arrow-left");
	$(".datetimepicker .icon-arrow-right").addClass("fa fa-arrow-right");
}

function initDataSubmit() {
	var flag = null;
	$("#save").on("click", function() {
		saveScheduleData(function(data) {
			top.showMask();
			setTimeout("self.location.reload()", 1000);
			flag = data;
		});
	});
	return flag;
}

function initScheduleAutoSort1(shift, staff) {
	var shiftFirstAndLast = function(dayList) {
		var map = new Object();
		map['f'] = 0;
		map['l'] = 1;
		map['c'] = 0;
		var i = 0;
		var day = null;
		do {
			i++;
			var fcell = getCell(staff[map['f']]['userId'], i);
			var date = fcell.find("[name='date']").val();
			day = new Date(date).getDay();
		} while (day != 1);
		for (; i < dayList.length; i++) {
			shiftFirstAndLastExecute(0, 4, 5, map, i);
		}
	}
	var shiftFirstAndLastExecute = function(first, last, rest, map, i) {
		var fcell = setDayShift(staff[map['f']]['userId'], i, shift[first]['id']);
		var lcell = setDayShift(staff[map['l']]['userId'], i, shift[last]['id']);
		map['c']++;
		if (map['c'] > 2) {
			map['c'] = 0;
			setDayShift('', '', shift[rest]['id'], fcell);
			shiftFLAfter(shift, 'f', map, i, 1, 3, rest);
			setDayShift('', '', shift[rest]['id'], lcell);
			shiftFLAfter(shift, 'l', map, i, 3, 1, rest);
			map['c']++;
			fcell = shiftFirstAndLastPosition(staff, map, 'f', i);
			setDayShift('', '', shift[first]['id'], fcell);
			lcell = shiftFirstAndLastPosition(staff, map, 'l', i);
			setDayShift('', '', shift[last]['id'], lcell);
		}
		if (map['c'] < 2) {
			fcell = getCell(staff[map['f']]['userId'], i);
			var date = fcell.find("[name='date']").val();
			var day = new Date(date).getDay();
			shiftFLBefore(shift, 'f', map, i, 1, 3, rest);
			shiftFLBefore(shift, 'l', map, i, 3, 1, rest);
			if (day == 0) {
				setDayShift(staff[map['f']]['userId'], i-1, shift[rest]['id']);
				setDayShift(staff[map['l']]['userId'], i-1, shift[rest]['id']);
			}
			if (day == 6) {
				setDayShift(staff[map['f']]['userId'], i+7, shift[rest]['id']);
				setDayShift(staff[map['f']]['userId'], i+8, shift[rest]['id']);
				setDayShift(staff[map['l']]['userId'], i+7, shift[rest]['id']);
				setDayShift(staff[map['l']]['userId'], i+8, shift[rest]['id']);
			}
		}
	}
	var setDayShift = function(r, c, val, cell) {
		var cell = (cell != undefined && cell != null) ? cell : getCell(r, c);
		$(cell).find("[name='shiftId']").val(val);
		$(cell).find("[name='shift_']").val(val);
		setSelectorColorAndTitle($(cell).find("[name='shift_']")[0]);
		return cell;
	}
	var shiftFirstAndLastPosition = function(staff, map, t, i) {
		map[t] = (map[t] + 1) > staff.length - 1 ? 0 : (map[t] + 1);
		var cell = getCell(staff[map[t]]['userId'], i);
		if (cell.find("[name='shiftId']").val() != '') {
			return shiftFirstAndLastPosition(staff, map, t, i);
		}
		return cell;
	}
	var shiftFLAfter = function(shift, t, map, i, x1, x2, rest) {
		cell = getCell(staff[map[t]]['userId'], i);
		var date = cell.find("[name='date']").val();
		var day = new Date(date).getDay();
		if (day > 1 && day < 6) {
			do {
				day++;
				setDayShift(staff[map[t]]['userId'], ++i, shift[x2]['id']);
			} while (day < 6);
			setDayShift(staff[map[t]]['userId'], ++i, shift[rest]['id']);
		} else {
			while (day > 0 && day < 5) {
				day++;
				setDayShift(staff[map[t]]['userId'], ++i, shift[x1]['id']);
			}
			if (day == 6) {
				setDayShift(staff[map[t]]['userId'], ++i, shift[x2]['id']);
			}
		}
	}
	var shiftFLBefore = function(shift, t, map, i, x1, x2, rest) {
		cell = getCell(staff[map[t]]['userId'], i);
		var date = cell.find("[name='date']").val();
		var day = new Date(date).getDay();
		var c = 0;
		if (day > 1 && day <= 6) {
			do {
				c++;
				day--;
				if (day < 1) {
					break;
				}
				setDayShift(staff[map[t]]['userId'], --i, shift[x1]['id']);
			} while (c < 2);
			if (day > 1) {
				day--;
				setDayShift(staff[map[t]]['userId'], --i, shift[rest]['id']);
				while (day > 1) {
					day--;
					setDayShift(staff[map[t]]['userId'], --i, shift[x2]['id']);
				}
			}
		} else if (day == 0) {
			--i;
			setDayShift(staff[map[t]]['userId'], --i, shift[x1]['id']);
			setDayShift(staff[map[t]]['userId'], --i, shift[rest]['id']);
			day = 4;
			while (day > 1) {
				setDayShift(staff[map[t]]['userId'], --i, shift[x2]['id']);
				day--;
			}
		}	
	}
	var blindPaddingSort = function(dayList, day2, night2, rest) {
		var fcell = getCell(staff[0]['userId'], 1);
		var fdate = fcell.find("[name='date']").val();
		var fday = new Date(fdate).getDay();
		var rday = fday > 0 && fday < 6 ? fday : 1;
		for (var i = 1; i < dayList.length; i++) {
			for (var j = 0; j < staff.length; j++) {
				var cell = getCell(staff[j]['userId'], i);
				if (cell.find("[name='shiftId']").val() == "") {
					setDayShift("", "", j % 2 > 0 ? day2 : night2, cell);
				}
			}
		}
		for (var j = 0; j < staff.length; j++) {
			var wfday = fday;
			var wp = false;
			var flag = true;
			var flag_ = true;
			for (var i = 1; i < dayList.length; i++) {
				var cell = getCell(staff[j]['userId'], i);
				var date = cell.find("[name='date']").val();
				var day = new Date(date).getDay();
				if (!wp) {
					var k = wfday;
					var l = i;
					flag = true;
					flag_ = true;
					do {				
						var tcell = getCell(staff[j]['userId'], l++);
						if(tcell.find("[name='shiftId']").val()==rest) {
							flag = false;
							flag_ = false;
							break;
						}
						k = k > 5 ? 0 : ++k;
					} while (k > 0);
					wfday = 1;
					wp = true;
				}
				if (flag) {
					if (day > 0 && day < 6 && day == rday) {
						rday = rday >= 5 ? 1 : ++rday;
						setDayShift("", "", rest, cell);
						flag = false;
					}
				}
				if (flag_) {
					if (j % 2 > 0) {
						if (day == 6) {
							setDayShift("", "", rest, cell);
							flag_ = false;
						}

					} else {
						if (day == 0) {
							setDayShift("", "", rest, cell);
							flag_ = false;
						}
					}
				}
				wp = day > 0;
			}
		}
	}
	$("#sort").on("click", function() {
		$("[name='shiftId']").val("");
		$("[name='shift_']").val("");
		$("[name='shift_']").each(function() {
			setSelectorColorAndTitle(this);
		});
		var dayList = getDayList();
		shiftFirstAndLast(dayList);
		blindPaddingSort(dayList, 2, 4, 6);
	});
}

function initExport() {
	$("#export").on("click", function() {
		saveScheduleData(function(data) {
			var dataFrom = $("#datetimepicker").val() + "-" + $("#sch-ctx .day").first().text();
			var dataTo = $("#datetimepicker").val() + "-" + $("#sch-ctx .day").last().text();
			var href = "./schedule/export";
			href += '?dataFrom=' + dataFrom + "&dataTo=" + dataTo;
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.title = "日程导出";
			diag.ShowMaxButton = true;
			diag.ShowMinButton = true;
			diag.URL = href;
			diag.Width = 300;
			diag.Height = 200;
			diag.CancelEvent = function() {
				top.hideMask();
				diag.close();
			};
			diag.AutoClose=2;
			diag.show();
		});
	});
}

function initShiftDetail(shift) {
	for (var i = 0; i < shift.length; i++) {
		var span = document.createElement("span");
		$("#shiftDetail").append(span);
		$(span).css("background-color", shift[i]['color']);
		span.innerHTML = shift[i]['name'] + " " + shift[i]['period'];
	}
}

function initStaffDetail(staff) {
	for (var i = 0; i < staff.length; i++) {
		var span = document.createElement("span");
		$("#staffDetail").append(span);
		span.innerHTML = staff[i]['userId'] + " " + staff[i]['name'];
	}
}

function getDayList() {
	var dayLabels = {
		0 : '星期日',
		1 : '星期一',
		2 : '星期二',
		3 : '星期三',
		4 : '星期四',
		5 : '星期五',
		6 : '星期六'
	};
	var dtmp = $("#datetimepicker").val().split('-');
	var year = parseInt(dtmp[0]);
	var month = Number(dtmp[1]);
	var days = new Date(year, month, 0).getDate();
	var dayList = new Array(null);
	var holidayList = getHolidayList(String(year)
			+ String(String(parseInt(month / 10)) + String(month % 10)));
	for (var i = 1; i <= days; i++) {
		var day = new Object();
		day['day'] = String(parseInt(i / 10)) + String(i % 10);
		day['label'] = dayLabels[new Date(year, month - 1, i).getDay()];
		day['holiday'] = holidayList[day['day']] != undefined ? holidayList[day['day']] : '0';
		dayList.push(day);
	}
	return dayList;
}

function getHolidayList(date) {
	var holidayList = '';
	/*
	$.ajax({
		url : 'http://www.easybots.cn/api/holiday.php?m=' + date,
		async : false,
		dataType : "json",
		type : 'get',
		success : function(data, textStatus, jqXHR) {
			holidayList = data[Number(date)];
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status != '200') {
				console.log('holidayList error');
			}
		}
	});
	*/
	return holidayList;
}

function getShiftList() {
	var list = null;
	$.ajax({
		url : './shifts/',
		async : false,
		data : {
			'pageNumber' : 1,
			'pageSize' : 100
		},
		dataType : "json",
		type : 'get',
		success : function(data, textStatus, jqXHR) {
			list = data['rows'];
			sessionStorage["shift_object"]=JSON.stringify(transArrayToObject(list, "id"));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status != '200') {
				console.log(XMLHttpRequest.status);
			}
		}
	});
	return list;
}

function getStaffList() {
	var list = null;
	$.ajax({
		url : './users/',
		async : false,
		data : {
			'pageNumber' : 1,
			'pageSize' : 100,
			'sort' : 'su.userId',
			'order' : 'asc'
		},
		dataType : "json",
		type : 'post',
		success : function(data, textStatus, jqXHR) {
			list = data['rows'];
			sessionStorage["staff_object"]=JSON.stringify(transArrayToObject(list, "userId"));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status != '200') {
				console.log(XMLHttpRequest.status);
			}
		}
	});
	return list;
}

function getCell(r, c) {
	var ri = $('.staffId').index($("#staffId_" + r));
	var ci = c-1;
	var cell = $('#sch-ctx .row-c:eq(' + ri + ')').find('.sch-ctx-cell:eq(' + ci + ')');
	return cell;
}

function setSelectorColorAndTitle(object) {
	$(object).css("background-color", "#FFFFFF");
	$(object).parent().attr("title", "");
	if(object != undefined) {
		var options = object.getElementsByTagName("option");
		for (var i = 0; i < options.length; i++) {
			if ($(object).val() == options[i].value) {
				$(object).css("background-color", $(options[i]).find("input:eq(0)").val());
				$(object).parent().attr("title", $(options[i]).find("input:eq(1)").val());
			}
		}
		transTableMirrrorText(object);
	}
}

function transTableMirrrorText(object) {	
	var shiftId = $(object).parent().find("[name='shiftId']").val();
	var staffId = $(object).parent().find("[name='staffId']").val();
	var date = $(object).parent().find("[name='date']").val();
	$("#sch-tbl-mirror #ctx-zone-mirror .date-mirror[value='" + date + "']").each(function() {
		var text = $(this).parent().find(".staff-mirror").text();
		text = (text.indexOf(',')!='-1') ? text.replace("," + staffId, "") : text.replace(staffId, "");
		$(this).parent().parent().attr("title", "");
		if ($(this).parent().find(".shiftId-mirror").val() == shiftId) {
			text = (text!="") ? text + "," + staffId : staffId + ",";
			text = (text.charAt(text.length-1) != ',') ? text : text.substr(0,text.length-1);			
		}
		$(this).parent().find(".staff-mirror").text(text);
		var texts = text.split(",");
		var hint = "";
		var staff_object = JSON.parse(sessionStorage["staff_object"]);
		for(var i=0; texts !="" && i < texts.length; i++) {
			hint += texts[i] + " : " + staff_object[texts[i]]["name"] + ", ";
		}
		if(hint.length > 0) {
			$(this).parent().parent().attr("title", hint.substr(0, hint.length-2) + " (" + texts.length + ")");
		}
	});
}

function saveScheduleData(callback) {
	var list=new Array();
	$(".cell-ctx").each(function(){
		var item=new Object();
		item['id']=$(this).find("[name='id']").val();
		item['staffId']=$(this).find("[name='staffId']").val();
		item['shiftId']=$(this).find("[name='shiftId']").val();
		item['date']=$(this).find("[name='date']").val();
		list.push(item);
	});
	console.log(list);
	$.ajax({
		url : './schedule',
		contentType: "application/json;charset=utf-8",
		async : false,
		data : JSON.stringify(list),
		dataType : 'json',
		type : 'post',
		success : function(data, textStatus, jqXHR) {
			callback(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status != '200') {
				console.log(XMLHttpRequest.status);
			}
		}
	});
}

function initScheduleTable(staff, shift) {
	var constructTitle = function(dayList) {
		var mrow = document.createElement("div");
		document.getElementById("menu-title").appendChild(mrow);
		mrow.setAttribute('class', 'row-t');
		var crow = document.createElement("div");
		document.getElementById("ctx-title").appendChild(crow);
		crow.setAttribute('class', 'row-t');
		for (var j = 0; j < dayList.length; j++) {
			var cell = document.createElement("div");
			if (j > 0) {
				crow.appendChild(cell);
				cell.setAttribute('class', 'sch-ctx-cell');
				var day = document.createElement("div");
				cell.appendChild(day);
				day.setAttribute('class', 'day');
				day.innerHTML = dayList[j]['day'];
				var label = document.createElement("div");
				cell.appendChild(label);
				label.innerHTML = dayList[j]['label'];
				var holiday = document.createElement("div");
				cell.appendChild(holiday);
				holiday.innerHTML = dayList[j]['holiday'] != "0" ? '(假日)' : '';
				if(dayList[j]['label'] == "星期日") {
					$(cell).addClass("sunday");
				}
			} else {
				mrow.appendChild(cell);
				cell.setAttribute('class', 'sch-menu-cell');
				cell.innerHTML = "STAFF";
			}
		}
	}
	var constructContext = function(dayList) {
		for (var j = 0; j < dayList.length; j++) {
			if (j > 0) {
				if (j < 2) {
					for (var i = 0; i < staff.length; i++) {
						var crow = document.createElement("div");
						document.getElementById("ctx-zone").appendChild(crow);
						crow.setAttribute('class', 'row-c');
					}
				}
				for (var i = 0; i < staff.length; i++) {
					var cell = document.createElement("div");
					$("#sch-ctx .row-c").eq(i).append(cell);
					cell.setAttribute('class', 'sch-ctx-cell sch-ctx-data-cell');
					if(dayList[j]['label'] == "星期日") {
						$(cell).addClass("sunday");
					}
				}
			} else {
				for (var i = 0; i < staff.length; i++) {
					var mrow = document.createElement("div");
					document.getElementById("menu-zone").appendChild(mrow);
					mrow.setAttribute('class', 'row-c');
					var cell = document.createElement("div");
					mrow.appendChild(cell);
					cell.setAttribute('class', 'sch-menu-cell');
					var id = document.createElement("input");
					cell.appendChild(id);
					id.setAttribute('id', 'staffId_' + staff[i]['userId']);
					id.setAttribute('class', 'staffId');
					id.setAttribute('type', 'hidden');
					id.value = staff[i]['userId']
					var name = document.createElement("div");
					cell.appendChild(name);
					name.innerHTML = staff[i]['name'];
				}
			}
		}
	}
	var initCellFrame = function(dayList) {
		for (var i = 0; i < $("#sch-menu .row-c").size(); i++) {
			for (var j = 0; j < dayList.length - 1; j++) {
				var cell = $("#sch-ctx .row-c").eq(i).find(".sch-ctx-data-cell").eq(j);
				cell.html("");
				var hint = document.createElement("span");
				cell.append(hint);
				hint.setAttribute('class', 'cell-hint');
				hint.innerHTML = dayList[j + 1]['day'] + ' - ' + dayList[j + 1]['label'].substr(2, 2);
				var ctx = document.createElement("div");
				cell.append(ctx);
				ctx.setAttribute('class', 'cell-ctx');
				var id = document.createElement('input');
				ctx.appendChild(id);
				id.setAttribute('name', 'id');
				id.setAttribute('type', 'hidden');
				var staffId = document.createElement('input');
				ctx.appendChild(staffId);
				staffId.setAttribute('name', 'staffId');
				staffId.setAttribute('type', 'hidden');
				staffId.setAttribute('value', $(".staffId").eq(i).val());
				var shiftId = document.createElement('input');
				ctx.appendChild(shiftId);
				shiftId.setAttribute('name', 'shiftId');
				shiftId.setAttribute('type', 'hidden');
				var date = document.createElement('input');
				ctx.appendChild(date);
				date.setAttribute('name', 'date');
				date.setAttribute('type', 'hidden');
				date.setAttribute('value', $("#datetimepicker").val() + '-' + dayList[j + 1]['day']);
				var shift_ = document.createElement("select");
				ctx.appendChild(shift_);
				shift_.setAttribute('class', 'shift_');
				shift_.setAttribute('name', 'shift_');
				var shiftOp_ = document.createElement("option");
				shift_.appendChild(shiftOp_);
				for (var k = 0; k < shift.length; k++) {
					shiftOp_ = document.createElement("option");
					shift_.appendChild(shiftOp_);
					shiftOp_.setAttribute('value', shift[k]['id']);
					shiftOp_.innerHTML = shift[k]['name'];
					shiftOp_.innerHTML += "<input type='hidden' value='" + shift[k]['color'] + "'/>";
					shiftOp_.innerHTML += "<input type='hidden' value='" + shift[k]['period'] + "'>"; 
				}
				$(shift_).on("change", function() {
						$(this).parent().find("[name='shiftId']").val($(this).val());
						setSelectorColorAndTitle(this);
				});
			}
		}
	}
	var execute = function() {
		if ($("#datetimepicker").val() != undefined
				&& $("#datetimepicker").val() != "") {
			var dayList = getDayList();
			document.getElementById("menu-title").innerHTML="";
			document.getElementById("ctx-title").innerHTML="";
			document.getElementById("menu-zone").innerHTML="";
			document.getElementById("ctx-zone").innerHTML="";
			constructTitle(dayList);
			constructContext(dayList);
			initCellFrame(dayList);
			constructTableData(staff, shift);
			initScheduleTableMirror(staff, shift);
		}
	}
	execute();
	$('#datetimepicker').on('change', function() {
		execute();
	});
}

function initScheduleTableMirror(staff, shift) {
	var constructTitle = function(dayList) {
		var mrow = document.createElement("div");
		document.getElementById("menu-title-mirror").appendChild(mrow);
		mrow.setAttribute('class', 'row-t-mirror');
		var crow = document.createElement("div");
		document.getElementById("ctx-title-mirror").appendChild(crow);
		crow.setAttribute('class', 'row-t-mirror');
		for (var j = 0; j < dayList.length; j++) {
			var cell = document.createElement("div");
			if (j > 0) {
				crow.appendChild(cell);
				cell.setAttribute('class', 'sch-ctx-cell-mirror');
				var day = document.createElement("div");
				cell.appendChild(day);
				day.setAttribute('class', 'day');
				day.innerHTML = dayList[j]['day'];
				var label = document.createElement("div");
				cell.appendChild(label);
				label.innerHTML = dayList[j]['label'];
				var holiday = document.createElement("div");
				cell.appendChild(holiday);
				holiday.innerHTML = dayList[j]['holiday'] != "0" ? '(假日)' : '';
				if(dayList[j]['label'] == "星期日") {
					$(cell).addClass("sunday");
				}
			} else {
				mrow.appendChild(cell);
				cell.setAttribute('class', 'sch-menu-cell-mirror');
				cell.innerHTML = "SHIFT";
			}
		}
	}
	var constructContext = function(dayList) {
		for (var j = 0; j < dayList.length; j++) {
			if (j > 0) {
				if (j < 2) {
					for (var i = 0; i < shift.length; i++) {
						var crow = document.createElement("div");
						document.getElementById("ctx-zone-mirror").appendChild(crow);
						crow.setAttribute('class', 'row-c-mirror');
					}
				}
				for (var i = 0; i < shift.length; i++) {
					var cell = document.createElement("div");
					$("#sch-ctx-mirror .row-c-mirror").eq(i).append(cell);
					cell.setAttribute('class', 'sch-ctx-cell-mirror sch-ctx-data-cell-mirror');
					if(dayList[j]['label'] == "星期日") {
						$(cell).addClass("sunday-mirror");
					}
				}
			} else {
				for (var i = 0; i < shift.length; i++) {
					var mrow = document.createElement("div");
					document.getElementById("menu-zone-mirror").appendChild(mrow);
					mrow.setAttribute('class', 'row-c-mirror');
					mrow.setAttribute("title", shift[i]['period']);
					var cell = document.createElement("div");
					mrow.appendChild(cell);
					cell.setAttribute('class', 'sch-menu-cell-mirror');
					var id = document.createElement("input");
					cell.appendChild(id);
					id.setAttribute('id', 'shiftId_' + shift[i]['id']);
					id.setAttribute('class', 'shiftId');
					id.setAttribute('type', 'hidden');
					id.value = shift[i]['id']
					var name = document.createElement("div");
					cell.appendChild(name);
					name.innerHTML = shift[i]['name'];
				}
			}
		}
	}
	var initCellFrame = function(dayList) {
		for (var i = 0; i < $("#sch-menu-mirror .row-c-mirror").size(); i++) {
			for (var j = 0; j < dayList.length - 1; j++) {
				var cell = $("#sch-ctx-mirror .row-c-mirror").eq(i).find(".sch-ctx-data-cell-mirror").eq(j);
				cell.html("");
				var hint = document.createElement("span");
				cell.append(hint);
				hint.setAttribute('class', 'cell-hint-mirror');
				hint.innerHTML = dayList[j + 1]['day'] + ' - ' + dayList[j + 1]['label'].substr(2, 2);
				var ctx = document.createElement("div");
				cell.append(ctx);
				ctx.setAttribute('class', 'cell-ctx-mirror');
				var shiftId = document.createElement('input');
				ctx.appendChild(shiftId);
				shiftId.setAttribute('class', 'shiftId-mirror');
				shiftId.setAttribute('type', 'hidden');
				shiftId.setAttribute('value', $(".shiftId").eq(i).val());
				var date = document.createElement('input');
				ctx.appendChild(date);
				date.setAttribute('class', 'date-mirror');
				date.setAttribute('type', 'hidden');
				date.setAttribute('value', $("#datetimepicker").val() + '-' + dayList[j + 1]['day']);
				var staff = document.createElement('span');
				ctx.appendChild(staff);
				staff.setAttribute('class', 'staff-mirror');
			}
		}
	}
	var execute = function() {
		if ($("#datetimepicker").val() != undefined && $("#datetimepicker").val() != "") {
			var dayList = getDayList();
			document.getElementById("menu-title-mirror").innerHTML = "";
			document.getElementById("ctx-title-mirror").innerHTML = "";
			document.getElementById("menu-zone-mirror").innerHTML = "";
			document.getElementById("ctx-zone-mirror").innerHTML = "";
			constructTitle(dayList);
			constructContext(dayList);
			initCellFrame(dayList);
		}
	}
	execute();
}

function constructTableData(shift, staff) {
	var init = function(list) {
		if (list != null && list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				var cell = getCell(list[i]['staffId'], new Date(list[i]['date']).getDate());
				$(cell).find("[name='id']").val(list[i]['id']);
				$(cell).find("[name='shiftId']").val(list[i]['shiftId']);
				$(cell).find("[name='shift_']").val(list[i]['shiftId']);
				setSelectorColorAndTitle($(cell).find("[name='shift_']")[0]);
			}
		}
	}
	var parameters=new Object();
	parameters['dataFrom']=$("#datetimepicker").val()+"-"+$("#sch-ctx .day").first().text();
	parameters['dataTo']=$("#datetimepicker").val()+"-"+$("#sch-ctx .day").last().text();
	$.ajax({
		url : './schedules',
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		async : false,
		data : parameters,
		dataType : 'json',
		type : 'get',
		success : function(data, textStatus, jqXHR) {
			var list = data;
			init(list);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (XMLHttpRequest.status != '200') {
				console.log(XMLHttpRequest.status);
			}
		}
	});
}

function transArrayToObject(list, pk) {
	var object = new Object();
	for(var i=0; i < list.length; i++) {
		object[list[i][pk]] = list[i];
	}
	return object;
}
