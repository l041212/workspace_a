package com.zh.crm.mapper;

import com.zh.crm.entity.Type;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;
import java.util.Map;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);

    List<Type> findAllType();

    List<Type> findTypeByParentId(Map<String,Object> map);

    Integer findMaxValueByParentId(@Param("parentId") Integer parentId);

    List<Type> findAllTypeByParentId(@Param("parentId") Integer parentId);

    Integer findTypeExist(Map<String,Object>map);

    List<Type> findAllValidType(@Param("parentId") Integer parentId);

    List<Type> findValidType();
}