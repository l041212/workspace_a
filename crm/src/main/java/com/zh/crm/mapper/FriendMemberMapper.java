package com.zh.crm.mapper;

import java.util.List;
import java.util.Map;

import com.zh.crm.entity.FriendMember;

public interface FriendMemberMapper {

	public FriendMember selectByPrimaryKey(Integer id);

	public List<FriendMember> findAllFriendMember(Map<String, Object> map);

	public int insertSelective(Map<String, Object> map);

	public int updateByPrimaryKeySelective(Map<String, Object> map);

	public int deleteByPrimaryKey(Integer id);

}
