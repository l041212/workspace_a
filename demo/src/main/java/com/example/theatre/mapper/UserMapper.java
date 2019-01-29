package com.example.theatre.mapper;

import com.example.theatre.entity.User;

public interface UserMapper extends BaseMapper<User> {

	public User getItemByUsername(String username);

}
