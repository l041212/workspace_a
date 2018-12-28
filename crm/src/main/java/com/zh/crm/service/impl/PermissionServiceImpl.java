package com.zh.crm.service.impl;

import com.zh.crm.config.CacheExpire;
import com.zh.crm.entity.Permission;
import com.zh.crm.mapper.PermissionMapper;
import com.zh.crm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
/*@CacheConfig(cacheNames = "perm")*/
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Override
   /* @CacheEvict(value = {"perm"},key = "#permissionid")
    @CacheExpire(expire = 360)*/
    public int deleteByPrimaryKey(Integer permissionid) {
        return permissionMapper.deleteByPrimaryKey(permissionid);
    }

    @Override
    /*@CachePut(value = {"perm"},key = "#record.permissionId")
    @CacheExpire(expire = 360)*/
    public int insert(Permission record) {
        return permissionMapper.insert(record);
    }

    @Override
    /*@CachePut(value = {"perm"},key = "#record.permissionId")
    @CacheExpire(expire = 360)*/
    public int insertSelective(Permission record) {
        return permissionMapper.insertSelective(record);
    }

    @Override
    /*@Cacheable(value = {"perm"},key = "#permissionid",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public Permission selectByPrimaryKey(Integer permissionid) {
        return permissionMapper.selectByPrimaryKey(permissionid);
    }

    @Override
    /*@CachePut(value = {"perm"},key = "#record.permissionId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKeySelective(Permission record) {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    /*@CachePut(value = {"perm"},key = "#record.permissionId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKey(Permission record) {
        return permissionMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Permission> findPermsByParentId(Integer parentId) {
        return permissionMapper.findPermsByParentId(parentId);
    }

    @Override
    /*@Cacheable(value = {"perms"}, keyGenerator = "keyGenerator",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public List<Permission> findAllPerms() {
        return permissionMapper.findAllPerms();
    }

    @Override
    /*@Cacheable(value = {"rolePerms"}, key = "#roleId",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public List<Permission> findPermsByRoleId(Integer roleId) {
        return permissionMapper.findPermsByRoleId(roleId);
    }

    @Override
    public List<Permission> findMenusByUserId(Integer userId, Integer parentId) {
        return permissionMapper.findMenusByUserId(userId,parentId);
    }


}
