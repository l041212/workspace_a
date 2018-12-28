package com.zh.crm.service;

import com.zh.crm.entity.Shift;

import java.util.List;
import java.util.Map;

public interface ShiftService {

	public Shift selectByPrimaryKey(Integer id);

	public List<Shift> findAllShift(Map<String, Object> map);

	public int save(Shift shift);

	public int deleteByPrimaryKeyLogic(Integer id);

}
