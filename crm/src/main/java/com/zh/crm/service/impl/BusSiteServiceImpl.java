package com.zh.crm.service.impl;

import com.zh.crm.entity.BusSite;
import com.zh.crm.mapper.BusSiteMapper;
import com.zh.crm.service.BusSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class BusSiteServiceImpl implements BusSiteService {
    @Autowired
    BusSiteMapper busSiteMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return busSiteMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(BusSite record) {
        return busSiteMapper.insert(record);
    }

    @Override
    public int insertSelective(BusSite record) {
        return busSiteMapper.insertSelective(record);
    }

    @Override
    public BusSite selectByPrimaryKey(Integer id) {
        return busSiteMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BusSite record) {
        return busSiteMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusSite record) {
        return busSiteMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<BusSite> findAllSitePage(Map<String, Object> map) {
        return busSiteMapper.findAllSitePage(map);
    }
}
