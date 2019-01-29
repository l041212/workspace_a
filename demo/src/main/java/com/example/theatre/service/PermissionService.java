package com.example.theatre.service;

import java.util.Collection;
import java.util.List;

import com.example.theatre.entity.Permission;
import com.example.theatre.entity.Role;

public interface PermissionService extends BaseService<Permission> {

	public List<Permission> findItemsByRoleId(Integer id);

	public List<Permission> findItemsByRoleId(Collection<Role> roles);

}