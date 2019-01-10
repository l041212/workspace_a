package com.example.theatre.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {

	public T getItem(Integer id);

	public List<T> findItems(Map<String, Object> item);

	public T insertItem(T item);

	public T updateItem(T item);

	public void deleteItem(Integer id);

}
