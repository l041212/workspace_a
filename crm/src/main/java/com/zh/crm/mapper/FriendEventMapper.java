package com.zh.crm.mapper;

import java.util.List;
import java.util.Map;

import com.zh.crm.entity.FriendEvent;

public interface FriendEventMapper {

	public FriendEvent selectByPrimaryKey(Integer id);

	public List<FriendEvent> findAllFriendEvent(Map<String, Object> map);

	public int insertSelective(Map<String, Object> map);
	
	public int insertMemberEvent(Integer memberId, Integer eventId);

	public int updateByPrimaryKeySelective(Map<String, Object> map);

	public int deleteByPrimaryKey(Integer id);
	
	public int deleteMemberEvent(Integer eventId);

}
