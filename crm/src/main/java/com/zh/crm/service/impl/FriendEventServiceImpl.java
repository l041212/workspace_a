package com.zh.crm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zh.crm.entity.FriendEvent;
import com.zh.crm.mapper.FriendEventMapper;
import com.zh.crm.service.FriendEventService;

@Service
@Transactional(readOnly = true)
public class FriendEventServiceImpl implements FriendEventService {

	@Autowired
	private FriendEventMapper friendEventMapper;

	@Override
	public FriendEvent find(Integer id) {
		if (id != null) {
			return friendEventMapper.selectByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public List<FriendEvent> findAll(Map<String, Object> map) {
		if (map != null) {
			return friendEventMapper.findAllFriendEvent(map);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int insert(Map<String, Object> map) {
		if (map != null) {		
			friendEventMapper.insertSelective(map);
			if (map.get("members") != null) {
				Lists.newArrayList(map.get("members").toString().split(",")).stream()
						.forEach(memberId -> friendEventMapper.insertMemberEvent(Integer.valueOf(memberId), Integer.valueOf(map.get("id").toString())));
			}
			return 1;
		}
		return -1;
	}

	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public int update(Map<String, Object> map) {
		if (map != null) {
			if (map.get("id") != null) {
				if (map.get("members") != null) {
					friendEventMapper.deleteMemberEvent(Integer.valueOf(map.get("id").toString()));
					Lists.newArrayList(map.get("members").toString().split(",")).stream()
							.forEach(memberId -> friendEventMapper.insertMemberEvent(Integer.valueOf(memberId), Integer.valueOf(map.get("id").toString())));
				}
				return friendEventMapper.updateByPrimaryKeySelective(map);
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
			return friendEventMapper.updateByPrimaryKeySelective(map);
		}
		return -1;
	}

}
