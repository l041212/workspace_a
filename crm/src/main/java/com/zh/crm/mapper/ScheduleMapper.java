package com.zh.crm.mapper;

import java.util.List;

import com.zh.crm.entity.Schedule;

public interface ScheduleMapper {
	
	public Schedule selectByPrimaryKey(Integer id);
	
	public List<Schedule> findAllSchedule(Schedule schedule);
	
	public int insertSelective(Schedule schedule);

    public int updateByPrimaryKeySelective(Schedule schedule);
	
}