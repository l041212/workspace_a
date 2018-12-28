package com.zh.crm.service;

import com.zh.crm.entity.BusLine;

import java.util.List;
import java.util.Map;

public interface BusLineService {
    int deleteByPrimaryKey(Integer id);

    int insert(BusLine record);

    int insertSelective(BusLine record);

    BusLine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusLine record);

    int updateByPrimaryKey(BusLine record);

    List<BusLine> findBusLinePaging(Map<String,Object> map);
}
