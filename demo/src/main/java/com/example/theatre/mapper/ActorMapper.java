package com.example.theatre.mapper;

import java.util.List;
import java.util.Map;

import com.example.theatre.entity.Actor;

public interface ActorMapper {

	public List<Actor> findList(Map<String, Object> item);

	public Actor findListById(Integer actorId);

}
