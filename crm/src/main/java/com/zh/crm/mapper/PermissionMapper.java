package com.zh.crm.mapper;

import com.zh.crm.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;


public interface PermissionMapper {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);


    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> findPermsByParentId(@Param("parentId") Integer parentId);

    List<Permission> findAllPerms();

    List<Permission> findPermsByRoleId(@Param("roleId") Integer roleId);

    List<Permission> findMenusByUserId(@Param("userId") Integer userId,@Param("parentId") Integer parentId);
}