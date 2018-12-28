package com.zh.crm.service;

import java.util.List;

import com.zh.crm.entity.Schedule;

public interface ScheduleService {

	public Schedule selectByPrimaryKey(Integer id);
	
	public List<Schedule> findAllSchedule(Schedule schedule);
	
	public int saveAll(List<Schedule> list);
	
	public int save(Schedule schedule);

}
