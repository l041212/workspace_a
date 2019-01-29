package com.example.theatre.mapper;

import java.util.List;

import com.example.theatre.entity.Permission;

public interface PermissionMapper extends BaseMapper<Permission> {

	public List<Permission> findItemsByRoleId(Integer id);

}
