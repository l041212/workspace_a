package com.zh.crm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zh.crm.entity.RecordExtend;
import com.zh.crm.mapper.RecordMapper;
import com.zh.crm.service.QualityCheckService;
import com.zh.crm.util.DozerUtil;

@Service
@Transactional(readOnly = true)
public class QualityCheckServiceImpl implements QualityCheckService {

	@Autowired
	DozerUtil dozerUtil;
	@Autowired
	private RecordMapper recordMapper;

	@Override
	public List<RecordExtend> findRecordSelective(Map<String, Object> map) {
		return dozerUtil.convert(recordMapper.findRecordSelective(map), RecordExtend.class);
	}

}
