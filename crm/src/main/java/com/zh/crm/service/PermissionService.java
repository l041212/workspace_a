package com.zh.crm.service;

import com.zh.crm.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionService {
    public int deleteByPrimaryKey(Integer permissionid);

    public int insert(Permission record);

    public int insertSelective(Permission record);

    public Permission selectByPrimaryKey(Integer permissionid);

    public int updateByPrimaryKeySelective(Permission record);

    public int updateByPrimaryKey(Permission record);

    public List<Permission> findPermsByParentId( Integer parentId) ;

    public List<Permission> findAllPerms();

    public List<Permission> findPermsByRoleId(Integer roleId);

    public List<Permission> findMenusByUserId(Integer userId, Integer parentId);
}
