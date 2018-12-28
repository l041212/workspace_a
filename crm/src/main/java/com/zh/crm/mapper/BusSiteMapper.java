package com.zh.crm.mapper;

import com.zh.crm.entity.BusSite;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;
import java.util.Map;

public interface BusSiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusSite record);

    int insertSelective(BusSite record);

    BusSite selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusSite record);

    int updateByPrimaryKey(BusSite record);

    List<BusSite> findAllSitePage(Map<String,Object> map);
}