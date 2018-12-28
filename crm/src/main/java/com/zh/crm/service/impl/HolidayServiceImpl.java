package com.zh.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zh.crm.entity.Holiday;
import com.zh.crm.mapper.HolidayMapper;
import com.zh.crm.service.HolidayService;

@Service
@Transactional(readOnly = true)
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayMapper holidayMapper;
	
	@Override
	public Holiday selectByPrimaryKey(Integer id) {
		return holidayMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Holiday> findAllShift(Holiday holiday) {
		return holidayMapper.findAllHoliday(holiday);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public boolean save(Holiday holiday) {
		if (holiday != null) {
			if (holiday.getId() != null) {
				holidayMapper.updateByPrimaryKeySelective(holiday);
			}
			holidayMapper.insertSelective(holiday);
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public boolean deleteByPrimaryKeyLogic(Integer id) {
		if (id != null) {
			Holiday holiday = selectByPrimaryKey(id);
			if (holiday != null) {
				holiday.setStatus(1);
				holidayMapper.updateByPrimaryKeySelective(holiday);
				return true;
			}
		}
		return false;
	}

}
