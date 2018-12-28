package com.zh.crm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zh.crm.entity.Shift;
import com.zh.crm.mapper.ShiftMapper;
import com.zh.crm.service.ShiftService;

@Service
@Transactional(readOnly = true)
public class ShiftServiceImpl implements ShiftService {

	@Autowired
	ShiftMapper shiftMapper;

	@Override
	public Shift selectByPrimaryKey(Integer id) {
		return shiftMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Shift> findAllShift(Map<String, Object> map) {
		return shiftMapper.findAllShift(map);
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int save(Shift shift) {
		if (shift != null) {
			if (shift.getId() != null) {
				return shiftMapper.updateByPrimaryKeySelective(shift);
			}
			return shiftMapper.insertSelective(shift);
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int deleteByPrimaryKeyLogic(Integer id) {
		if (id != null) {
			Shift shift = selectByPrimaryKey(id);
			if (shift != null) {
				shift.setStatus(1);
				return shiftMapper.updateByPrimaryKeySelective(shift);
			}
		}
		return 0;
	}

}
