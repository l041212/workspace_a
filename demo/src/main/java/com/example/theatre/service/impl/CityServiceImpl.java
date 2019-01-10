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

import com.example.demo.annotations.TargetDataSource;
import com.example.theatre.entity.City;
import com.example.theatre.mapper.CityMapper;
import com.example.theatre.service.CityService;

@Service
@CacheConfig(cacheNames = "t_city")
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;

	@Override
	@Cacheable(key = "#id")
	@TargetDataSource(label = "library1")
	public City getItem(Integer id) {
		return cityMapper.getItemById(id);
	}

	@Override
	@TargetDataSource(label = "library1")
	public List<City> findItems(Map<String, Object> item) {
		return cityMapper.findItems(item);
	}

	@Override
	@CachePut(key = "#item.id")
	@TargetDataSource(label = "library1")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public City insertItem(City item) {
		cityMapper.insertItem(item);
		return getItem(item.getId());
	}

	@Override
	@CachePut(key = "#item.id")
	@TargetDataSource(label = "library1")
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public City updateItem(City item) {
		cityMapper.updateItem(item);
		return getItem(item.getId());
	}

	@Override
	@CacheEvict(key = "#id")
	@TargetDataSource(label = "library1")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void deleteItem(Integer id) {
		cityMapper.deleteItem(id);
	}

}
