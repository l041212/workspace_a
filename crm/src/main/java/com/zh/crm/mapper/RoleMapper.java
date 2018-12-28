package com.zh.crm.mapper;

import com.zh.crm.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> findRoleByParentId(@Param("parentId") Integer parentId) ;

    List<Role> findPersByRoleId(@Param("roleId") Integer roleId) ;

    int deletePermsByRoleId(@Param("roleId") Integer roleId);

    int insertPermsBatch( Map<String,Object> map) ;

    List<Role> findAllRole();

    List<Role> findRolesByIds(@Param("ids") String ids);
}