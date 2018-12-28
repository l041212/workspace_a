package com.example.theatre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.theatre.entity.Actor;
import com.example.theatre.mapper.ActorMapper;
import com.example.theatre.service.ActorService;

@Service
@Transactional(readOnly = true)
public class ActorServiceImpl implements ActorService {

	@Autowired
	private ActorMapper actorMapper;

	@Override
	public Actor findActor(Integer actorId) {
		return actorMapper.findListById(actorId);
	}

}
