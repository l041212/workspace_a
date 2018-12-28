package com.zh.crm.service;

import com.zh.crm.entity.Deparment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DepService {
    public int deleteByPrimaryKey(Integer depId);

    public int insert(Deparment record);

    public int insertSelective(Deparment record);

    public Deparment selectByPrimaryKey(Integer depId);

    public int updateByPrimaryKeySelective(Deparment record);

    public int updateByPrimaryKey(Deparment record);

    public List<Deparment> findDepByParentId(Map<String,Object> map) ;

    public List<Deparment> findAllDep();

    public Integer findDepByCode( String depCode);

    public List<Deparment> findDepNamesByIds(String ids);
}
