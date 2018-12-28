package com.zh.crm.service;

import com.zh.crm.entity.Collect;

public interface CollectService {
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    Integer findCollectExsit(Collect record);
}
