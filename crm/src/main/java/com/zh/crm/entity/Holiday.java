package com.zh.crm.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Holiday implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date date;
	private Integer count;
	private Integer status;
	private String description;
	private List<Date> dates;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public List<Date> getDates() {
		if (dates == null || dates.isEmpty()) {
			this.setDates();
		}
		return dates;
	}

	private void setDates() {
		if (this.date != null && this.count > 0) {
			this.dates = new ArrayList<Date>();
			for (int i = 0; i < this.count; i++) {
				this.dates.add(new Date(this.date.getTime() + i * 3600 * 24));
			}
		}
	}

}
