package com.zh.crm.entity;

import java.io.Serializable;
import java.util.List;

public class TypeExtend<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String parentId;
	private String text;
	private List<TypeExtend<T>> sonTypes;
	private List<T> entityTypes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TypeExtend<T>> getSonTypes() {
		return sonTypes;
	}

	public void setSonTypes(List<TypeExtend<T>> sonTypes) {
		this.sonTypes = sonTypes;
	}

	public List<T> getEntityTypes() {
		return entityTypes;
	}

	public void setEntityTypes(List<T> entityTypes) {
		this.entityTypes = entityTypes;
	}

}
