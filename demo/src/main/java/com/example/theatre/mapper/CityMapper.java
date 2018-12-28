package com.example.theatre.mapper;

import java.util.List;
import java.util.Map;

import com.example.theatre.entity.City;

public interface CityMapper {

	public List<City> findList(Map<String, Object> item);

	public City findListById(Integer id);

}
