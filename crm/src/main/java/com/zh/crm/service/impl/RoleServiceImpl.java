package com.zh.crm.service.impl;

import com.zh.crm.config.CacheExpire;
import com.zh.crm.entity.Role;
import com.zh.crm.mapper.RoleMapper;
import com.zh.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/*@CacheConfig(cacheNames="role")*/
@Transactional
@Service
public class RoleServiceImpl  implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
   /* @CacheEvict(value={"role"},key = "#roleId")
    @CacheExpire(expire = 360)*/
    public int deleteByPrimaryKey(Integer roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    /*@CachePut(value = {"role"},key = "#record.roleId")
    @CacheExpire(expire = 360)*/
    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    @Override
    /*@CachePut(value = {"role"},key = "#record.roleId")
    @CacheExpire(expire = 360)*/
    public int insertSelective(Role record) {
        return roleMapper.insertSelective(record);
    }

    /*@Cacheable(value = {"role"},key = "#roleId",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public Role selectByPrimaryKey(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    /*@CachePut(value = {"role"},key = "#record.roleId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    /*@CachePut(value = {"role"},key = "#record.roleId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKey(Role record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    @Override
    /*@Cacheable(value = {"roles"},key = "#parentId",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public List<Role> findRoleByParentId(Integer parentId) {
        return roleMapper.findRoleByParentId(parentId);
    }

    @Override
    /*@Cacheable(value = {"rolePerms"},key = "#roleId",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public List<Role> findPersByRoleId(Integer roleId) {
        return roleMapper.findPersByRoleId(roleId);
    }

    @Override
   /* @CacheEvict(value={"rolePerms"},key = "#roleId")
    @CacheExpire(expire = 360)*/
    public int deletePermsByRoleId(Integer roleId) {
        return roleMapper.deletePermsByRoleId(roleId);
    }

    @Override
    public int insertPermsBatch(Map<String, Object> map) {
        return roleMapper.insertPermsBatch(map);
    }

    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAllRole();
    }

    @Override
    public List<Role> findRolesByIds(String ids) {
        return roleMapper.findRolesByIds(ids);
    }
}
