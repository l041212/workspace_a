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

import com.example.theatre.entity.User;
import com.example.theatre.mapper.UserMapper;
import com.example.theatre.service.UserService;

@Service
@CacheConfig(cacheNames = "c_user")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService 
{

	@Autowired
	private UserMapper userMapper;

	@Override
	@Cacheable(key = "#id")
	public User getItem(Integer id) {
		return userMapper.getItemById(id);
	}

	@Override
	@Cacheable(key = "#username")
	public User getItem(String username) {
		return userMapper.getItemByUsername(username);
	}

	@Override
	public List<User> findItems(Map<String, Object> item) {
		return userMapper.findItems(item);
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public User insertItem(User item) {
		userMapper.insertItem(item);
		return getItem(item.getId());
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public User updateItem(User item) {
		userMapper.updateItem(item);
		return getItem(item.getId());
	}

	@Override
	@CacheEvict(key = "#id")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void deleteItem(Integer id) {
		userMapper.deleteItem(id);
	}

}
