package com.zh.crm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zh.crm.annotation.QueryForm;
import com.zh.crm.entity.Result;
import com.zh.crm.service.QualityCheckService;
import com.zh.crm.util.DozerUtil;

@CrossOrigin
@Controller
public class QualityController {

	@Autowired
	private DozerUtil dozerUtil;
	@Autowired
	private QualityCheckService qualityCheckService;

	@GetMapping(value = { "/qualityCheckRecord" })
	public String toQualityCheckPage(Map<String, String> map) {
		return "view/quality/quality_check_list";
	}

	@SuppressWarnings("unchecked")
	@QueryForm
	@ResponseBody
	@PostMapping(value = { "/qualityChecks" })
	public Map<String, Object> getQualityCheckList(@RequestParam Map<String, Object> data) {
		Map<String, Object> result = dozerUtil.convert(new Result(), Map.class);
		data.put("status", 0);
		result.put("list", qualityCheckService.findRecordSelective(data));
		return result;
	}

}
