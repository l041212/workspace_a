package com.zh.crm.service.impl;

import com.zh.crm.entity.Collect;
import com.zh.crm.mapper.CollectMapper;
import com.zh.crm.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectMapper collectMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return collectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Collect record) {
        return collectMapper.insert(record);
    }

    @Override
    public int insertSelective(Collect record) {
        return collectMapper.insertSelective(record);
    }

    @Override
    public Collect selectByPrimaryKey(Integer id) {
        return collectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Collect record) {
        return collectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Collect record) {
        return collectMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer findCollectExsit(Collect record) {
        return collectMapper.findCollectExsit(record);
    }
}
