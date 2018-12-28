package com.zh.crm.service;

import com.zh.crm.entity.KnowFile;

public interface FileService {
    int deleteByPrimaryKey(Integer fileId);

    int insert(KnowFile record);

    int insertSelective(KnowFile record);

    KnowFile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(KnowFile record);

    int updateByPrimaryKey(KnowFile record);
}
