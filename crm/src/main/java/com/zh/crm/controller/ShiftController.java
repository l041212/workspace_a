package com.zh.crm.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.annotation.QueryForm;
import com.zh.crm.entity.Result;
import com.zh.crm.entity.Shift;
import com.zh.crm.service.ShiftService;
import com.zh.crm.util.DozerUtil;

@CrossOrigin
@Controller
public class ShiftController {

	@Autowired
	private DozerUtil dozerUtil;
	@Autowired
	ShiftService shiftService;

	@GetMapping(value = { "/toShift" })
	public String toShiftListPage() {
		return "view/shift/shift_list";
	}

	@GetMapping(value = { "/shift", "/shift/{id}" })
	public String toShiftSave(@PathVariable(name = "id", required = false) Integer id, Map<String, Object> map) {
		Shift shift = (id != null) ? shiftService.selectByPrimaryKey(id) : null;
		map.put("shift", shift);
		return "view/shift/shift_save";
	}

	@SuppressWarnings("unchecked")
	@QueryForm
	@ResponseBody
	@GetMapping(value = { "/shifts" })
	public Map<String, Object> getShiftList(@RequestParam Map<String, Object> data) {
		Map<String, Object> result = dozerUtil.convert(new Result(), Map.class);
		data.put("name", data.get("keywords"));
		data.put("status", "0");
		data.put("sort", "period");
		result.put("list", shiftService.findAllShift(data));
		return result;
	}

	@PostMapping(value = { "/shift" })
	public String saveShift(Shift shift) {
		shiftService.save(shift);
		return "view/common/save_reslut";
	}

	@ResponseBody
	@DeleteMapping(value = { "/shift/{id}" })
	public int deleteShift(@PathVariable(name = "id", required = false) Integer id, Map<String, Object> map) {
		return shiftService.deleteByPrimaryKeyLogic(id);
	}
	
	protected static <T> Map<String, Object> getQueryFormData(Map<String, String> parameters, Class<?> type) {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNum = parameters.containsKey("pageNumber") ? Integer.parseInt(parameters.get("pageNumber")) : 1;
		int pageSize = parameters.containsKey("pageSize") ? Integer.parseInt(parameters.get("pageSize")) : 10;
		map.put("order", parameters.containsKey("order") ? parameters.get("order") : "desc");
		map.put("sort", parameters.containsKey("sort") ? parameters.get("sort") : "start_time");
		map.put("keywords", parameters.containsKey("keywords") ? parameters.get("keywords") : null);
		PageHelper.startPage(pageNum, pageSize);
		do {
			for (Field field : type.getDeclaredFields()) {
				if (parameters.containsKey(field.getName())) {
					map.put(field.getName(), parameters.get(field.getName()));
				}
			}
			type = type.getSuperclass();
		}while(type != null);
		return map;
	}
	
	protected static <T> Result getQueryResult(Map<String, String> parameters, List<T> list) {
		Result result = new Result();		
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		result.setRows(pageInfo.getList());
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	protected static void transAttachment(Map<String, Object> map, String srvPath, String urlPath) {
		for (String fn : map.keySet()) {
			if (fn.startsWith("fn_")) {
				String key = fn.replace("fn_", "");
				if (saveFile(map.get(key), map.get(fn), srvPath)) {
					map.put(key, urlPath + "/" + map.get(fn));
				}
			}
		}
	}

	protected static boolean saveFile(Object f, Object fn, String path) {
		if (f != null && String.valueOf(f).length() > 256) {
			Base64 base64 = new Base64();
			byte[] file = base64.decode(f.toString());
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + fn.toString());
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
				bufferedOutputStream.write(file);
				bufferedOutputStream.flush();
				bufferedOutputStream.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
