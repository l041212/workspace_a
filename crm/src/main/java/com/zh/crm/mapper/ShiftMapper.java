package com.zh.crm.mapper;

import com.zh.crm.entity.Shift;

import java.util.List;
import java.util.Map;

public interface ShiftMapper {

    public int deleteByPrimaryKey(Integer id);

    public int insert(Shift shift);

    public int insertSelective(Shift shift);

    public Shift selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(Shift shift);

    public int updateByPrimaryKey(Shift shift);

    public List<Shift> findAllShift(Map<String, Object> map);
}