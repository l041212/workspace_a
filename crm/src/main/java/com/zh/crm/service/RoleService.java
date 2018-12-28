package com.zh.crm.service;

import com.zh.crm.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    public int deleteByPrimaryKey(Integer roleId);

    public int insert(Role record);

    public int insertSelective(Role record);

    public Role selectByPrimaryKey(Integer roleId);

    public int updateByPrimaryKeySelective(Role record);

    public int updateByPrimaryKey(Role record);

    public List<Role> findRoleByParentId(Integer parentId) ;

    public List<Role> findPersByRoleId( Integer roleId) ;

    public int deletePermsByRoleId( Integer roleId);

    public int insertPermsBatch( Map<String,Object> map) ;

    public List<Role> findAllRole();

    List<Role> findRolesByIds( String ids);
}
