package com.example.theatre.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

	public T getItemById(Integer id);

	public List<T> findItems(Map<String, Object> item);

	public void insertItem(T item);

	public void updateItem(T item);

	public void deleteItem(Integer id);

}
