package com.example.theatre.service.impl;

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

import com.example.theatre.entity.Role;
import com.example.theatre.mapper.RoleMapper;
import com.example.theatre.service.RoleService;

@Service
@CacheConfig(cacheNames = "c_role")
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	@Cacheable(key = "#id")
	public Role getItem(Integer id) {
		return roleMapper.getItemById(id);
	}

	@Override
	public List<Role> findItems(Map<String, Object> item) {
		return roleMapper.findItems(item);
	}

	@Override
	public List<Role> findItemsByUserId(Integer id) {
		return roleMapper.findItemsByUserId(id);
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Role insertItem(Role item) {
		roleMapper.insertItem(item);
		return getItem(item.getId());
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public Role updateItem(Role item) {
		roleMapper.updateItem(item);
		return getItem(item.getId());
	}

	@Override
	@CacheEvict(key = "#id")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void deleteItem(Integer id) {
		roleMapper.deleteItem(id);
	}

}
