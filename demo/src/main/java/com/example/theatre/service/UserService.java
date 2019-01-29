package com.example.theatre.service;

import com.example.theatre.entity.User;

public interface UserService extends BaseService<User> {

	public User getItem(String username);

}