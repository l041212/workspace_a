package com.zh.crm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zh.crm.entity.FriendMember;
import com.zh.crm.entity.TypeExtend;
import com.zh.crm.mapper.FriendMemberMapper;
import com.zh.crm.service.FriendMemberService;
import com.zh.crm.service.TypeService;

@Service
@Transactional(readOnly = true)
public class FriendMemberServiceImpl implements FriendMemberService {

	
	@Autowired
	private TypeService typeService;
	@Autowired
	private FriendMemberMapper friendMemberMapper;

	@Override
	public FriendMember find(Integer id) {
		if (id != null) {
			return friendMemberMapper.selectByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public List<FriendMember> findAll(Map<String, Object> map) {
		if (map != null) {
			return friendMemberMapper.findAllFriendMember(map);
		}
		return null;
	}

	@Override
	public List<TypeExtend<FriendMember>> findAll(Integer parentId, Boolean flag) {
		return typeService.findAllValidTypeLoop(parentId, flag, this, "findAll", "typeId");
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int insert(Map<String, Object> map) {
		if (map != null) {
			return friendMemberMapper.insertSelective(map);
		}
		return -1;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int update(Map<String, Object> map) {
		if (map != null) {
			if (map.get("id") != null) {
				return friendMemberMapper.updateByPrimaryKeySelective(map);
			}
		}
		return -1;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int delete(Integer id) {
		if (id != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", 1);
			return friendMemberMapper.updateByPrimaryKeySelective(map);
		}
		return -1;
	}

}
