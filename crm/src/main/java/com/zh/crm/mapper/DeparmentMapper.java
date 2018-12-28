package com.zh.crm.mapper;

import com.zh.crm.entity.Deparment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeparmentMapper {

    int deleteByPrimaryKey(Integer depId);

    int insert(Deparment record);

    int insertSelective(Deparment record);

    Deparment selectByPrimaryKey(Integer depId);

    int updateByPrimaryKeySelective(Deparment record);

    int updateByPrimaryKey(Deparment record);

    List<Deparment> findDepByParentId(Map<String,Object> map) ;

    List<Deparment> findAllDep();

    Integer findDepByCode(@Param("depCode") String depCode);

    List<Deparment> findDepNamesByIds(@Param("ids") String ids);
}