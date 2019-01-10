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

import com.example.theatre.entity.Actor;
import com.example.theatre.mapper.ActorMapper;
import com.example.theatre.service.ActorService;

@Service
@CacheConfig(cacheNames = "t_actor")
@Transactional(readOnly = true)
public class ActorServiceImpl implements ActorService {

	@Autowired
	private ActorMapper actorMapper;

	@Override
	@Cacheable(key = "#actorId")
	public Actor getItem(Integer actorId) {
		return actorMapper.getItemById(actorId);
	}

	@Override
	public List<Actor> findItems(Map<String, Object> item) {
		return actorMapper.findItems(item);
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public Actor insertItem(Actor item) {
		actorMapper.insertItem(item);
		return getItem(item.getActorId());
	}

	@Override
	@CachePut(key = "#item.id")
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public Actor updateItem(Actor item) {
		actorMapper.updateItem(item);
		return getItem(item.getActorId());
	}

	@Override
	@CacheEvict(key = "#actorId")
	@Transactional(readOnly = false, isolation = Isolation.SERIALIZABLE)
	public void deleteItem(Integer actorId) {
		actorMapper.deleteItem(actorId);
	}

}
