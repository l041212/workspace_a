package com.zh.crm.service;

import com.zh.crm.entity.Record;

import java.util.List;
import java.util.Map;

public interface RecordService {

    public int deleteByPrimaryKey(Integer ID);

    public int insert(Record record);

    public int insertSelective(Record record);

    public Record selectByPrimaryKey(Integer ID);

    public int updateByPrimaryKeySelective(Record record);

    public int updateByPrimaryKey(Record record);

    public List<Record> findAllRecord(Map<String,Object> map);


}
