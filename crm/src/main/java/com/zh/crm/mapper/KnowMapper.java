package com.zh.crm.mapper;

import com.zh.crm.entity.Know;
import com.zh.crm.entity.KnowWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface KnowMapper {
    int deleteByPrimaryKey(Integer knowId);

    int insert(KnowWithBLOBs record);

    int insertSelective(KnowWithBLOBs record);

    KnowWithBLOBs selectByPrimaryKey(Integer knowId);

    int updateByPrimaryKeySelective(KnowWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(KnowWithBLOBs record);

    int updateByPrimaryKey(Know record);

    List<KnowWithBLOBs> findKnowTopThree(@Param("number") String number );

    void updateVersionByPastId(@Param("id") Integer id);

    List<Know>findHotKnow();

    List<KnowWithBLOBs> findNewKnow();

    int updateKnowReadPerm(@Param("id") Integer id);

    int findKnowCountByTypeId(@Param("typeId") Integer typeId);

    List<KnowWithBLOBs> findKnowSortDate(Map<String,Object> map);
}