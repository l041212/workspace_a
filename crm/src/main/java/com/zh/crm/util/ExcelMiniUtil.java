package com.zh.crm.util;

//import java.io.IOException;
//import java.io.InputStream;
import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.Matcher;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
//import javax.validation.constraints.Pattern;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import com.zh.crm.exception.ExcelIOException;

import cn.afterturn.easypoi.excel.annotation.Excel;

//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelMiniUtil {

	private static final String SUFFIX[] = { ".xls", ".xlsx" };
	private static Workbook workbook;
	private static Sheet sheet;
	
	public static <T> Workbook reWriteModelWorkbook(HttpServletResponse response, String sheetname, Class<T> type) {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(sheetname);
		Row rowTitle = sheet.createRow(0);
		Row rowLabel = sheet.createRow(1);
		CellStyle styleLabel = workbook.createCellStyle();
		Font font = workbook.createFont();
		int length = 0;
		styleLabel.setAlignment(HorizontalAlignment.CENTER);
		styleLabel.setVerticalAlignment(VerticalAlignment.CENTER);
		styleLabel.setFont(font);
		font.setBold(true);
		for (Field field : type.getDeclaredFields()) {
			if (field.getAnnotation(Excel.class) != null) {
				Excel mirror = field.getAnnotation(Excel.class);
				Cell cell = rowLabel.createCell(Integer.valueOf(mirror.orderNum()) - 1);
				cell.setCellValue(mirror.name());
				cell.setCellStyle(styleLabel);
				sheet.setColumnWidth(cell.getColumnIndex(), (int) mirror.width() * 256);
				length++;
			}
		}
		Cell cell = rowTitle.createCell(0);
		cell.setCellValue(sheetname);
		cell.setCellStyle(styleLabel);
		rowTitle.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, length-1));
		writeOutputStream(response, sheetname);
		return workbook;
	}

	/*
	public static <T> Workbook writeModelWorkbook(HttpServletResponse response, String sheetname, Class<T> type) {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(sheetname);
		Row rowLabel = sheet.createRow(0);
		Row rowSample = sheet.createRow(1);
		CellStyle styleLabel = workbook.createCellStyle();
		styleLabel.setAlignment(HorizontalAlignment.CENTER);
		for (Field field : type.getDeclaredFields()) {
			if (field.getAnnotation(ExcelMirror.class) != null) {
				ExcelMirror mirror = field.getAnnotation(ExcelMirror.class);
				Cell cell = rowLabel.createCell(mirror.column());
				cell.setCellValue(mirror.label());
				cell.setCellStyle(styleLabel);
				cell = rowSample.createCell(mirror.column());
				cell.setCellValue(mirror.sample());
				cell.setCellStyle(styleLabel);
			}
		}
		writeOutputStream(response, sheetname);
		return workbook;
	}

	public static <T> Workbook writeWorkbook(HttpServletResponse response, String sheetname, Class<T> type,
			List<T> list) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(sheetname);
		Row rowLabel = sheet.createRow(0);
		CellStyle styleLabel = workbook.createCellStyle();
		styleLabel.setAlignment(HorizontalAlignment.CENTER);
		for (Field field : type.getDeclaredFields()) {
			if (field.getAnnotation(ExcelMirror.class) != null) {
				ExcelMirror mirror = field.getAnnotation(ExcelMirror.class);
				Cell cell = rowLabel.createCell(mirror.column());
				cell.setCellValue(mirror.label());
				cell.setCellStyle(styleLabel);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);
			for (Field field : type.getDeclaredFields()) {
				String methodName = "get" + String.valueOf(field.getName().charAt(0)).toUpperCase()
						+ field.getName().substring(1);
				Method method = list.get(i).getClass().getDeclaredMethod(methodName);
				if (field.getAnnotation(ExcelMirror.class) != null) {
					Object value = method.invoke(list.get(i));
					ExcelMirror mirror = field.getAnnotation(ExcelMirror.class);
					Cell cell = row.createCell(mirror.column());
					cell.setCellValue(value != null ? String.valueOf(value) : "");
					cell.setCellStyle(styleLabel);
				}
			}
		}
		writeOutputStream(response, sheetname);
		return workbook;
	}

	public static <T> List<T> readWorkbook(InputStream inputStream, String suffix, Class<T> type)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException, ExcelIOException, ParseException {
		List<T> list = new ArrayList<T>();
		if (inputStream != null && StringUtils.isNotBlank(suffix)) {
			try {
				if (SUFFIX[0].equals(suffix.trim().toLowerCase())) {
					workbook = new HSSFWorkbook(inputStream);
				}
				if (SUFFIX[1].equals(suffix.trim().toLowerCase())) {
					workbook = new XSSFWorkbook(inputStream);
				}
				sheet = workbook.getSheetAt(0);
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					T item = type.newInstance();
					for (Field field : type.getDeclaredFields()) {
						if (field.getAnnotation(ExcelMirror.class) != null) {
							ExcelMirror mirror = field.getAnnotation(ExcelMirror.class);
							Pattern pattern = field.getAnnotation(Pattern.class);
							Cell cell = row.getCell(mirror.column());
							if (!setTableValue(cell, mirror, pattern, field, item)) {
								throw new ExcelIOException();
							}
						}
					}
					list.add(item);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	private static <T> boolean setTableValue(Cell cell, ExcelMirror mirror, Pattern pattern, Field field, T item)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ParseException, IllegalStateException {
		String methodName = "set" + String.valueOf(field.getName().charAt(0)).toUpperCase()
				+ field.getName().substring(1);
		Method method = item.getClass().getDeclaredMethod(methodName, field.getType());
		if (cell != null) {
			String value = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			DecimalFormat decimalFormat = new DecimalFormat("#");
			if (cell.getCellType() ==  Cell.CELL_TYPE_STRING) {
				value = cell.getStringCellValue();
			}
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					dateFormat = StringUtils.isNotBlank(mirror.format()) ? new SimpleDateFormat(mirror.format())
							: dateFormat;
					value = dateFormat.format(cell.getDateCellValue());
				} else {
					value = String.valueOf(decimalFormat.format(cell.getNumericCellValue()));
				}
			}
			if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				value = String.valueOf(cell.getBooleanCellValue());
			}
			if (StringUtils.isNotBlank(value)) {
				if (field.getType() == Date.class) {
					dateFormat = StringUtils.isNotBlank(mirror.format()) ? new SimpleDateFormat(mirror.format())
							: dateFormat;
					method.invoke(item, dateFormat.parse(value));
				}
				if (field.getType() == String.class) {
					if (StringUtils.isNotBlank(pattern.regexp())) {
						Matcher matcher = java.util.regex.Pattern.compile(pattern.regexp()).matcher(value.trim());
						if (!matcher.find()) {
							return false;
						}
					}
					method.invoke(item, value.trim());
				}
				if (field.getType() == Integer.class) {
					method.invoke(item, Integer.valueOf(value));
				}
				if (field.getType() == Float.class) {
					method.invoke(item, Float.valueOf(value));
				}
				if (field.getType() == Double.class) {
					method.invoke(item, Double.valueOf(value));
				}
				if (field.getType() == Boolean.class) {
					method.invoke(item, Boolean.valueOf(value));
				}
			}
		}
		return true;
	}
	*/

	private static void writeOutputStream(HttpServletResponse response, String filename) {
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(String.valueOf(filename + SUFFIX[0]).getBytes(), "ISO-8859-1"));
			workbook.write(outputStream);
			workbook.close();
			if (outputStream != null)
				outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
