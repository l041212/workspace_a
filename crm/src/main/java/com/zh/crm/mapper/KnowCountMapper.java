package com.zh.crm.mapper;

import com.zh.crm.entity.KnowCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

public interface KnowCountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KnowCount record);

    int insertSelective(KnowCount record);

    KnowCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KnowCount record);

    int updateByPrimaryKey(KnowCount record);

    String findTopOneReadDate(Map<String,Object> map);

    Integer findKnowReadCount (@Param("knowId") Integer knowId);

    Integer findGoodCountByUser (Map<String,Object> map);

    Integer findBadCountByUser (Map<String,Object> map);

    Integer findKnowGoodCount(@Param("knowId") Integer knowId);

    Integer findKnowBadCount(@Param("knowId") Integer knowId);

}