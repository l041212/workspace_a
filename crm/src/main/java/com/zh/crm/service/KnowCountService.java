package com.zh.crm.service;

import com.zh.crm.entity.KnowCount;

import java.util.Map;

public interface KnowCountService {
    int deleteByPrimaryKey(Integer id);

    int insert(KnowCount record);

    int insertSelective(KnowCount record);

    KnowCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KnowCount record);

    int updateByPrimaryKey(KnowCount record);

    String findTopOneReadDate(Map<String,Object> map);

    Integer findKnowReadCount (Integer knowId);

    Integer findGoodCountByUser (Map<String,Object> map);

    Integer findBadCountByUser (Map<String,Object> map);

    Integer findKnowGoodCount(Integer knowId);

    Integer findKnowBadCount( Integer knowId);
}
