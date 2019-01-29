package com.example.theatre.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.theatre.entity.Permission;
import com.example.theatre.entity.Role;
import com.example.theatre.mapper.PermissionMapper;
import com.example.theatre.service.PermissionService;

@Service
@CacheConfig(cacheNames = "c_permission")
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	@Cacheable(key = "#id")
	public Permission getItem(Integer id) {
		return permissionMapper.getItemById(id);
	}

	@Override
	public List<Permission> findItems(Map<String, Object> item) {
		return permissionMapper.findItems(item);
	}

	@Override
	public List<Permission> findItemsByRoleId(Integer id) {
		return permissionMapper.findItemsByRoleId(id);
	}

	@Override
	public List<Permission> findItemsByRoleId(Collection<Role> roles) {
		return roles.stream().map(role -> findItemsByRoleId(role.getId())).collect(() -> new ArrayList<Permission>(),
				(list, permissions) -> list.addAll(permissions), (list1, list2) -> list1.addAll(list2));
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Permission insertItem(Permission item) {
		permissionMapper.insertItem(item);
		return getItem(item.getId());
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public Permission updateItem(Permission item) {
		permissionMapper.updateItem(item);
		return getItem(item.getId());
	}

	@Override
	@CacheEvict(key = "#id")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void deleteItem(Integer id) {
		permissionMapper.deleteItem(id);
	}

}
