package com.zh.crm.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.dozer.Mapping;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class FriendMember implements Serializable {

	private static final long serialVersionUID = 1L;

	@Excel(name = "id", orderNum = "1", width = 15)
	private Integer id;

	@Mapping("parentId")
	@Excel(name = "typeId", orderNum = "2", width = 10)
	private Integer typeId;

	@Mapping("text")
	@Excel(name = "姓名", orderNum = "3", width = 10)
	private String name;

	@Excel(name = "性别", orderNum = "4", width = 10, replace = { "男_1", "女_2" })
	private Integer sex;

	@Excel(name = "身份证", orderNum = "5", width = 20)
	private String identity;

	@Excel(name = "出生日期", orderNum = "6", width = 15, importFormat = "yyyy/MM/dd", exportFormat = "yyyy-MM-dd")
	private Timestamp birthday;

	@Excel(name = "联络电话", orderNum = "7", width = 15)
	private String phone;

	@Excel(name = "邮箱地址", orderNum = "8", width = 15)
	private String email;

	@Excel(name = "住宅地址", orderNum = "9", width = 20)
	private String address;

	@Excel(name = "教育", orderNum = "10", width = 10, replace = { "初中或以下_1", "高中_2", "本科_3", "研究生或以上_4" })
	private Integer education;

	@Excel(name = "职业", orderNum = "11", width = 10, replace = { "公务员_1", "企业_2", "学生_3", "离退休人员_4", "其他_5" })
	private Integer job;

	@Excel(name = "公司", orderNum = "12", width = 15)
	private String company;

	@Excel(name = "职位", orderNum = "13", width = 10)
	private String post;

	@Excel(name = "常用线路", orderNum = "14", width = 10)
	private String line;

	@Excel(name = "申请目的", orderNum = "16", width = 30)
	private String destination;

	@Excel(name = "申请时间", orderNum = "15", width = 15, importFormat = "yyyy/MM/dd", exportFormat = "yyyy-MM-dd")
	private Timestamp applyTime;

	private Integer status;

	private Timestamp birthdayFrom;

	private Timestamp birthdayTo;

	private Timestamp applyTimeFrom;

	private Timestamp applyTimeTo;

	private Type type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getBirthdayFrom() {
		return birthdayFrom;
	}

	public void setBirthdayFrom(Timestamp birthdayFrom) {
		this.birthdayFrom = birthdayFrom;
	}

	public Timestamp getBirthdayTo() {
		return birthdayTo;
	}

	public void setBirthdayTo(Timestamp birthdayTo) {
		this.birthdayTo = birthdayTo;
	}

	public Timestamp getApplyTimeFrom() {
		return applyTimeFrom;
	}

	public void setApplyTimeFrom(Timestamp applyTimeFrom) {
		this.applyTimeFrom = applyTimeFrom;
	}

	public Timestamp getApplyTimeTo() {
		return applyTimeTo;
	}

	public void setApplyTimeTo(Timestamp applyTimeTo) {
		this.applyTimeTo = applyTimeTo;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
