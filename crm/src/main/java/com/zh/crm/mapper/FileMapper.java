package com.zh.crm.mapper;

import com.zh.crm.entity.KnowFile;

public interface FileMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(KnowFile record);

    int insertSelective(KnowFile record);

    KnowFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(KnowFile record);

    int updateByPrimaryKey(KnowFile record);
}