package com.example.theatre.mapper;

import java.util.List;

import com.example.theatre.entity.Role;

public interface RoleMapper extends BaseMapper<Role> {

	public List<Role> findItemsByUserId(Integer id);

}
