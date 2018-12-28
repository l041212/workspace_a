package com.zh.crm.service;

import com.zh.crm.entity.Know;
import com.zh.crm.entity.KnowWithBLOBs;

import java.util.List;
import java.util.Map;

public interface KnowService {
    int deleteByPrimaryKey(Integer knowId);

    int insert(KnowWithBLOBs record);

    int insertSelective(KnowWithBLOBs record);

    KnowWithBLOBs selectByPrimaryKey(Integer knowId);

    int updateByPrimaryKeySelective(KnowWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(KnowWithBLOBs record);

    int updateByPrimaryKey(Know record);

    List<KnowWithBLOBs> findKnowTopThree(String number);

    void updateVersionByPastId( Integer id);

    List<Know>findHotKnow();

    List<KnowWithBLOBs> findNewKnow();

    int updateKnowReadPerm(Integer id);

    int findKnowCountByTypeId(Integer typeId);

    List<KnowWithBLOBs> findKnowSortDate(Map<String,Object> map);

    public List<KnowWithBLOBs> sortKnow(List<KnowWithBLOBs> list,String sort,String order );
}
