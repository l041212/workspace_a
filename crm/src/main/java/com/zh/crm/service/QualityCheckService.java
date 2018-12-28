package com.zh.crm.service;

import java.util.List;
import java.util.Map;

import com.zh.crm.entity.RecordExtend;

public interface QualityCheckService {

	public List<RecordExtend> findRecordSelective(Map<String, Object> map);
	
}
