package com.zh.crm.service;

import java.util.List;

import com.zh.crm.entity.Holiday;

public interface HolidayService {
	
	public Holiday selectByPrimaryKey(Integer id);

	public List<Holiday> findAllShift(Holiday holiday);

	public boolean save(Holiday holiday);

	public boolean deleteByPrimaryKeyLogic(Integer id);

}
