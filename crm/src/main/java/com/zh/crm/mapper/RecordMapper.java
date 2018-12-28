package com.zh.crm.mapper;

import com.zh.crm.entity.Record;

import java.util.List;
import java.util.Map;

public interface RecordMapper {

    int deleteByPrimaryKey(Integer ID);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer ID);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    List<Record> findAllRecord(Map<String,Object> map);
    
	List<Record> findRecordSelective(Map<String, Object> map);
    
}