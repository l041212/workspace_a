package com.zh.crm.service;

import java.util.List;
import java.util.Map;

import com.zh.crm.entity.FriendMember;
import com.zh.crm.entity.TypeExtend;

public interface FriendMemberService {

	public FriendMember find(Integer id);

	public List<FriendMember> findAll(Map<String, Object> map);
	
	public List<TypeExtend<FriendMember>> findAll(Integer parentId, Boolean flag);

	public int insert(Map<String, Object> map);

	public int update(Map<String, Object> map);

	public int delete(Integer id);

}
