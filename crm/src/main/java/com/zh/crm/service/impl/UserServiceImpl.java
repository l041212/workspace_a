package com.zh.crm.service.impl;

import com.zh.crm.config.CacheExpire;
import com.zh.crm.entity.User;
import com.zh.crm.mapper.UserMapper;
import com.zh.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    /*@CacheEvict(value = {"user"},key = "#userid")
    @CacheExpire(expire = 360)*/
    public int deleteByPrimaryKey(Integer userid) {
        return userMapper.deleteByPrimaryKey(userid);
    }

    @Override
    /*@CachePut(value = {"user"},key = "#record.userId")
    @CacheExpire(expire = 360)*/
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    /*@CachePut(value = {"user"},key = "#record.userId")
    @CacheExpire(expire = 360)*/
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    /*@Cacheable(value = {"user"},key = "#userid",unless = "#result==null")
    @CacheExpire(expire = 360)*/
    public User selectByPrimaryKey(Integer userid) {
        return userMapper.selectByPrimaryKey(userid);
    }

    @Override
    /*@CachePut(value = {"user"},key = "#record.userId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    /*@CachePut(value = {"user"},key = "#record.userId")
    @CacheExpire(expire = 360)*/
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User findUserByName(String number, String password) {
        return userMapper.findUserByName(number,password);
    }

    @Override
    public List<User> findAllUsers(Map<String,Object> map) {
        return userMapper.findAllUsers(map);
    }

    @Override
    public Integer findUserExist(String username) {
        return userMapper.findUserExist(username);
    }

    @Override
    public User findUserInfoById(Integer userId) {
        return userMapper.findUserInfoById(userId);
    }

    @Override
    public int deleteUserRole(Integer userId) {
        return userMapper.deleteUserRole(userId);
    }

    @Override
    public int insertUserRolesBatch(Map<String, Object> map) {
        return userMapper.insertUserRolesBatch(map);
    }

    @Override
    public int insertUserDep(Integer userId, Integer depId) {
        return userMapper.insertUserDep(userId,depId);
    }

    @Override
    public int findUserNumberExist(Integer number) {
        return userMapper.findUserNumberExist(number);
    }

    @Override
    public int deleteUserDep(Integer userId) {
        return userMapper.deleteUserDep(userId);
    }

    @Override
    public Set<String> findRolesByUserId(Integer userId) {
        return userMapper.findRolesByUserId(userId);
    }

    @Override
    public Set<String> findPersByUserId(Integer userId) {
        return userMapper.findPersByUserId(userId);
    }

    @Override
    public Integer findMaxNumber() {
        return userMapper.findMaxNumber();
    }
}
