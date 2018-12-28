package com.zh.crm.service.impl;

import com.zh.crm.entity.KnowCount;
import com.zh.crm.mapper.KnowCountMapper;
import com.zh.crm.service.KnowCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
@Transactional
@Service
public class KnowCountServiceImpl implements KnowCountService {
    @Autowired
    KnowCountMapper knowCountMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return knowCountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(KnowCount record) {
        return knowCountMapper.insert(record);
    }

    @Override
    public int insertSelective(KnowCount record) {
        return knowCountMapper.insertSelective(record);
    }

    @Override
    public KnowCount selectByPrimaryKey(Integer id) {
        return knowCountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(KnowCount record) {
        return knowCountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(KnowCount record) {
        return knowCountMapper.updateByPrimaryKey(record);
    }

    @Override
    public String findTopOneReadDate(Map<String, Object> map) {
        return knowCountMapper.findTopOneReadDate(map);
    }

    @Override
    public Integer findKnowReadCount(Integer knowId) {
        return knowCountMapper.findKnowReadCount(knowId);
    }

    @Override
    public Integer findGoodCountByUser(Map<String, Object> map) {
        return knowCountMapper.findGoodCountByUser(map);
    }

    @Override
    public Integer findBadCountByUser(Map<String, Object> map) {
        return knowCountMapper.findBadCountByUser(map);
    }

    @Override
    public Integer findKnowGoodCount(Integer knowId) {
        return knowCountMapper.findKnowGoodCount(knowId);
    }

    @Override
    public Integer findKnowBadCount(Integer knowId) {
        return knowCountMapper.findKnowBadCount(knowId);
    }
}
