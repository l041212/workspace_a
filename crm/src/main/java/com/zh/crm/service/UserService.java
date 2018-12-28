package com.zh.crm.service;

import com.zh.crm.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    public int deleteByPrimaryKey(Integer userid);

    public int insert(User record);

    public int insertSelective(User record);

    public User selectByPrimaryKey(Integer userid);

    public int updateByPrimaryKeySelective(User record);

    public int updateByPrimaryKey(User record);

    public User findUserByName( String number, String password) ;

    public List<User> findAllUsers(Map<String,Object> map);

    public Integer findUserExist(String username);

    public User findUserInfoById( Integer userId);

    public int deleteUserRole( Integer userId);

    public int insertUserRolesBatch( Map<String,Object> map) ;

    public int insertUserDep(Integer userId,Integer depId);

    public int findUserNumberExist(Integer number);

    public int deleteUserDep( Integer userId);

    public Set<String > findRolesByUserId(Integer userId);

    public Set<String> findPersByUserId( Integer userId);

    public Integer findMaxNumber();
}
