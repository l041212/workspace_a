package com.zh.crm.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zh.crm.config.StringToDateConverter;
import com.zh.crm.entity.Schedule;
import com.zh.crm.entity.Shift;
import com.zh.crm.entity.User;
import com.zh.crm.service.ScheduleService;
import com.zh.crm.service.UserService;

@CrossOrigin
@Controller
public class ScheduleController {

	@Autowired
	UserService userService;
	@Autowired
	private ScheduleService scheduleService;

	@GetMapping(value = { "/toSchedule" })
	public String toSchedulePage() {
		return "view/schedule/schedule_list";
	}

	@ResponseBody
	@GetMapping(value = { "/schedules" })
	public List<Schedule> getSchedule(@RequestParam Map<String, String> map)
			throws InstantiationException, IllegalAccessException {
		Schedule schedule = new Schedule();
		if (map.containsKey("dataFrom") && map.containsKey("dataTo")) {
			schedule.setDateFrom(StringToDateConverter.class.newInstance().convert(map.get("dataFrom")));
			schedule.setDateTo(StringToDateConverter.class.newInstance().convert(map.get("dataTo")));
		}
		List<Schedule> list = scheduleService.findAllSchedule(schedule);
		return list;
	}

	@ResponseBody
	@PostMapping(value = { "/schedule" })
	public String saveSchedule(@RequestBody List<Schedule> list) {
		scheduleService.saveAll(list);
		return "true";
	}

	@ResponseBody
	@GetMapping(value = "/schedule/export")
	public void exportSchedule(HttpServletResponse response, @RequestParam Map<String, String> map)
			throws InstantiationException, IllegalAccessException {
		Map<String, Object> umap = new HashMap<String, Object>();
		umap.put("pageNumber", 1);
		umap.put("pageSize", 100);
		umap.put("sort", "su.userId");
		umap.put("order", "asc");
		List<Schedule> schedules = getSchedule(map);
		List<User> users = userService.findAllUsers(umap);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(StringToDateConverter.class.newInstance().convert(map.get("dataFrom")));
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		week = (week < 0) ? 0 : week;
		String yearMonth = map.get("dataTo").split("-")[0] + "-" + String.valueOf(map.get("dataTo").split("-")[1]);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(yearMonth);
		HSSFRow row0 = sheet.createRow(0);
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setAlignment(HorizontalAlignment.CENTER);
		int to = Integer.parseInt(map.get("dataTo").split("-")[2]);
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Map<Integer, HSSFRow> rows = new HashMap<Integer, HSSFRow>();
		int rc = 1;
		List<Shift> shifts = new ArrayList<Shift>();
		for (int i = 0; i <= to; i++) {
			String title = (i == 0) ? "名字" : (String.valueOf(i / 10) + String.valueOf(i % 10) + "-" + weeks[week++]);
			HSSFCell cell = row0.createCell(i);
			cell.setCellValue(title);
			cell.setCellStyle(style1);
			week = week > 6 ? 0 : week;
		}
		for (int i = 0; i < users.size(); i++, rc++) {
			HSSFRow row = sheet.createRow(i + 1);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(users.get(i).getName());
			cell.setCellStyle(style1);
			rows.put(users.get(i).getUserId(), row);
		}
		for (int i = 0; i < schedules.size(); i++) {
			if (StringUtils.isNotBlank(schedules.get(i).getShiftId())) {
				calendar.setTime(schedules.get(i).getDate());
				HSSFRow row = rows.get(Integer.valueOf(schedules.get(i).getStaffId()));
				HSSFCell cell = row.createCell(calendar.get(Calendar.DATE));
				cell.setCellValue(schedules.get(i).getShift().getName());
				cell.setCellStyle(style1);
				if (shifts.isEmpty() || !shifts.contains(schedules.get(i).getShift())) {
					shifts.add(schedules.get(i).getShift());
				}
			}
		}
		HSSFRow rowym = sheet.createRow(++rc);
		HSSFCell cellym = rowym.createCell(0);
		cellym.setCellValue(yearMonth);
		for (int i = 0; i < shifts.size(); i++) {
			HSSFRow row = sheet.createRow(++rc);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(shifts.get(i).getName());
			cell.setCellStyle(style1);
			cell = row.createCell(1);
			cell.setCellValue(shifts.get(i).getPeriod());
			cell.setCellStyle(style1);
		}
		try {
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(String.valueOf("export_" + yearMonth + ".xls").getBytes(), "ISO-8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			workbook.close();
			if (outputStream != null)
				outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("test");
	}

}
