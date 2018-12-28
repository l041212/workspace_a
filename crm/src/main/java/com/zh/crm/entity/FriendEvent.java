package com.zh.crm.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class FriendEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Excel(name = "id", orderNum = "1", width = 15)
	private Integer id;

	@Excel(name = "标题", orderNum = "2", width = 15)
	private String title;

	@Excel(name = "开始时间", orderNum = "3", width = 15, importFormat = "yyyy/MM/dd", exportFormat = "yyyy-MM-dd")
	private Timestamp timeFrom;

	@Excel(name = "结束时间", orderNum = "4", width = 15, importFormat = "yyyy/MM/dd", exportFormat = "yyyy-MM-dd")
	private Timestamp timeTo;

	@Excel(name = "地点", orderNum = "5", width = 20)
	private String location;

	private String content;

	private String photo;

	@Excel(name = "简述", orderNum = "6", width = 20)
	private String summary;

	private Integer status;

	private List<FriendMember> members;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Timestamp timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Timestamp getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Timestamp timeTo) {
		this.timeTo = timeTo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<FriendMember> getMembers() {
		return members;
	}

	public void setMembers(List<FriendMember> members) {
		this.members = members;
	}

}
