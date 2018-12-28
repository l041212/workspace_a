package com.example.theatre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.annotations.TargetDataSource;
import com.example.theatre.entity.City;
import com.example.theatre.mapper.CityMapper;
import com.example.theatre.service.CityService;

@Service
@Transactional(readOnly = true)
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;

	@Override
	@TargetDataSource(label = "library1")
	public City findCity(Integer id) {
		return cityMapper.findListById(id);
	}

}
