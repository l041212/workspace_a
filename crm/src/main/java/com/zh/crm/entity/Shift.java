package com.zh.crm.entity;

import java.io.Serializable;

public class Shift implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String period;
	private String color;
	private Integer status;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object != null) {
			if (object.hashCode() != this.hashCode()) {
				if (object.getClass() == Shift.class) {
					Shift shift = (Shift) object;
					return shift.getName().equals(this.name) && shift.getPeriod().equals(this.period)
							&& shift.getStatus().equals(this.status);
				}
				return false;
			}
			return true;
		}
		return false;
	}

}
