package com.zh.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zh.crm.entity.Schedule;
import com.zh.crm.mapper.ScheduleMapper;
import com.zh.crm.service.ScheduleService;

@Service
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleMapper scheduleMapper;

	@Override
	public Schedule selectByPrimaryKey(Integer id) {
		return scheduleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Schedule> findAllSchedule(Schedule schedule) {
		if (schedule.getDate() != null) {
			System.out.println(schedule.getDate());
		}
		return scheduleMapper.findAllSchedule(schedule);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public int saveAll(List<Schedule> list) {
		for (Schedule schedule : list) {
			save(schedule);
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int save(Schedule schedule) {
		if (schedule != null) {
			if (schedule.getId() != null) {
				return scheduleMapper.updateByPrimaryKeySelective(schedule);
			}
			return scheduleMapper.insertSelective(schedule);
		}
		return 0;
	}

}
