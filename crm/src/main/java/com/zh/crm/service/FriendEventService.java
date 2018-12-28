package com.zh.crm.service;

import java.util.List;
import java.util.Map;

import com.zh.crm.entity.FriendEvent;

public interface FriendEventService {

	public FriendEvent find(Integer id);

	public List<FriendEvent> findAll(Map<String, Object> map);

	public int insert(Map<String, Object> map);

	public int update(Map<String, Object> map);

	public int delete(Integer id);

}
