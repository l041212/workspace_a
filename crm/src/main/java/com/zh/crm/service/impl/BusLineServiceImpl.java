package com.zh.crm.service.impl;

import com.zh.crm.entity.BusLine;
import com.zh.crm.mapper.BusLineMapper;
import com.zh.crm.mapper.BusSiteMapper;
import com.zh.crm.service.BusLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class BusLineServiceImpl implements BusLineService {
    @Autowired
    BusLineMapper busLineMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return busLineMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BusLine busLine) {
        return busLineMapper.insert(busLine);
    }

    @Override
    public int insertSelective(BusLine busLine) {
        return busLineMapper.insertSelective(busLine);
    }

    @Override
    public BusLine selectByPrimaryKey(Integer id) {
        return busLineMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BusLine busLine) {
        return busLineMapper.updateByPrimaryKeySelective(busLine);
    }

    @Override
    public int updateByPrimaryKey(BusLine busLine) {
        return busLineMapper.updateByPrimaryKey(busLine);
    }

    @Override
    public List<BusLine> findBusLinePaging(Map<String, Object> map) {
        return busLineMapper.findBusLinePaging(map);
    }

}
