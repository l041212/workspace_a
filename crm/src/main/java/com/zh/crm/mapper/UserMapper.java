package com.zh.crm.mapper;

import com.zh.crm.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findUserByName(@Param("number") String number, @Param("password") String password);

    List<User> findAllUsers(Map<String,Object> map);

    Integer findUserExist(@Param("username") String username);

    User findUserInfoById(@Param("userId") Integer userId);

    int deleteUserRole(@Param("userId") Integer userId);

    int insertUserRolesBatch( Map<String,Object> map) ;

    int insertUserDep(@Param("userId")Integer userId,@Param("depId") Integer depId);

    int findUserNumberExist(@Param("number")Integer number);

    int deleteUserDep(@Param("userId") Integer userId);

    Set<String > findRolesByUserId(@Param("userId") Integer userId);

    Set<String> findPersByUserId(@Param("userId") Integer userId);

    Integer findMaxNumber();
}