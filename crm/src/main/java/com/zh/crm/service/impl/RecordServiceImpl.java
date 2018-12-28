package com.zh.crm.service.impl;

import com.zh.crm.entity.Record;
import com.zh.crm.mapper.RecordMapper;
import com.zh.crm.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Transactional
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Override
    public int deleteByPrimaryKey(Integer ID) {
        return recordMapper.deleteByPrimaryKey(ID);
    }

    @Override
    public int insert(Record record) {
        return recordMapper.insert(record);
    }

    @Override
    public int insertSelective(Record record) {
        return recordMapper.insertSelective(record);
    }

    @Override
    public Record selectByPrimaryKey(Integer ID) {
        return recordMapper.selectByPrimaryKey(ID);
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
        return recordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Record record) {
        return recordMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Record> findAllRecord(Map<String, Object> map) {
        return recordMapper.findAllRecord(map);
    }
}
