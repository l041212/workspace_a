package com.zh.crm.service.impl;

import com.zh.crm.config.CacheExpire;
import com.zh.crm.entity.Deparment;
import com.zh.crm.mapper.DeparmentMapper;
import com.zh.crm.service.DepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/*@CacheConfig(cacheNames="dep")*/
@Transactional
@Service
public class DepServiceImpl implements DepService {
    @Autowired
    DeparmentMapper deparmentMapper;
    @Override
    /*@CacheEvict(value = {"dep"},key = "#depId")
    @CacheExpire(expire = 360)*/
    public int deleteByPrimaryKey(Integer depId) {
        return deparmentMapper.deleteByPrimaryKey(depId);
    }

    @Override
   /* @CachePut(value = {"dep"},key = "#record.depId")
    @CacheExpire(expire = 360)*/
    public int insert(Deparment record) {
        return deparmentMapper.insert(record);
    }

    @Override
    /*@CachePut(value = {"dep"},key = "#record.depId")
    @CacheExpire(expire = 360)*/
    public int insertSelective(Deparment record) {
        return deparmentMapper.insertSelective(record);
    }

    @Override
    /*@Cacheable(value = {"dep"},key = "#depId",unless = "#result==null")*/
    public Deparment selectByPrimaryKey(Integer depId) {
        return deparmentMapper.selectByPrimaryKey(depId);
    }

    @Override
    /*@CachePut(value = {"dep"},key = "#record.depId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKeySelective(Deparment record) {
        return deparmentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    /*@CachePut(value = {"dep"},key = "#record.depId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKey(Deparment record) {
        return deparmentMapper.updateByPrimaryKey(record);
    }

    @Override
/*    @Cacheable(value = {"depsp"},keyGenerator = "keyGenerator",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public List<Deparment> findDepByParentId(Map<String,Object> map) {
        return deparmentMapper.findDepByParentId(map);
    }

    @Override
    /*@Cacheable(value = {"deps"},keyGenerator = "keyGenerator",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public List<Deparment> findAllDep() {
        return deparmentMapper.findAllDep();
    }

    @Override
    /*@Cacheable(value = {"dep"},key = "#depCode")*/
    public Integer findDepByCode(String depCode) {
        return deparmentMapper.findDepByCode(depCode);
    }

    @Override
    public List<Deparment> findDepNamesByIds(String ids) {
        return deparmentMapper.findDepNamesByIds(ids);
    }
}
