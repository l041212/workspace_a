package com.zh.crm.service;

import com.zh.crm.entity.Type;
import com.zh.crm.entity.TypeExtend;

import java.util.List;
import java.util.Map;

public interface TypeService {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);

    List<Type> findAllType();

    List<Type> findTypeByParentId(Map<String,Object> map);

    Integer findMaxValueByParentId( Integer parentId);

    List<Type> findAllTypeByParentId( Integer parentId);

    Integer findTypeExist(Map<String,Object>map);

    List<Type> findAllKnowValidType(List<Type>types);

    List<Type> findAllValidType(Integer parentId);

    List<Type> findValidType();
    
    List<Type> findAllValidTypeLoop(Integer parentId, Boolean flag);
    
	<T> List<TypeExtend<T>> findAllValidTypeLoop(Integer parentId, Boolean flag, Object service, String find, String typeId);

}
