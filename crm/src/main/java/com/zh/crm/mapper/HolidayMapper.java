package com.zh.crm.mapper;

import java.util.List;

import com.zh.crm.entity.Holiday;

public interface HolidayMapper {

	public Holiday selectByPrimaryKey(Integer id);
	
	public List<Holiday> findAllHoliday(Holiday holiday);
	
	public Integer insertSelective(Holiday holiday);
	
	public void updateByPrimaryKeySelective(Holiday holiday);
	
	public void deleteByPrimaryKey(Integer id);
	
}
