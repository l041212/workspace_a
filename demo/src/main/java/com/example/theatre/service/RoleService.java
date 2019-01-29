package com.example.theatre.service;

import java.util.List;

import com.example.theatre.entity.Role;

public interface RoleService extends BaseService<Role> {

	public List<Role> findItemsByUserId(Integer id);

}