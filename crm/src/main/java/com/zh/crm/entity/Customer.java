package com.zh.crm.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private Integer id;

    @NotNull
    @Excel(name = "姓名",orderNum = "1" ,width = 10)
    private String name;
    @Excel(name = "公司",orderNum = "2" ,width = 20)
    private String company;
    @Excel(name = "公司类型",orderNum = "3" ,width = 10)
    private String companyType;

    private String country;

    private String province;

    private String city;
    @Excel(name = "手机号码",orderNum = "4" ,width = 15)
    private String telephone;
    @Excel(name = "电话号码",orderNum = "5" ,width = 15)
    private String phone;
    @Excel(name = "信访内容",orderNum = "6" ,width = 30)
    private String content;

    @Excel(name = "线路",orderNum = "7" ,width = 10)
    private String busLine;
    @Excel(name = "车辆编号",orderNum = "8" ,width = 15)
    private String busNumber;
    @Excel(name = "司机",orderNum = "9" ,width = 10)
    private String driver;
    @Excel(name = "乘务员",orderNum = "10" ,width = 10)
    private String steward;
    @Excel(name = "访问时间",orderNum = "11" ,width = 30)
    private String visitTime;
    @Excel(name = "信访类型",orderNum = "12" ,width = 15)
    private String visitType;
    @Excel(name = "是否回复",orderNum = "13" ,width = 10)
    private String isReply;
    @Excel(name = "受理人",orderNum = "14" ,width = 10)
    private String accepter;
    @Excel(name = "客户类型",orderNum = "15" ,width = 15)
    private String customerType;
    @Excel(name = "流水号",orderNum = "16" ,width = 20)
    private String flowNumber;
    @Excel(name = "证件类型",orderNum = "17" ,width = 20)
    private String cardType;
    @Excel(name = "创建时间",orderNum = "18" ,width = 30)
    private String createTime;
    @Excel(name = "地址",orderNum = "19" ,width = 35)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
        this.name = name == null ? null : name.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine == null ? null : busLine.trim();
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber == null ? null : busNumber.trim();
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver == null ? null : driver.trim();
    }

    public String getSteward() {
        return steward;
    }

    public void setSteward(String steward) {
        this.steward = steward == null ? null : steward.trim();
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType == null ? null : visitType.trim();
    }

    public String getIsReply() {
        return isReply;
    }

    public void setIsReply(String isReply) {
        this.isReply = isReply == null ? null : isReply.trim();
    }

    public String getAccepter() {
        return accepter;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter == null ? null : accepter.trim();
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType == null ? null : customerType.trim();
    }

    public String getFlowNumber() {
        return flowNumber;
    }

    public void setFlowNumber(String flowNumber) {
        this.flowNumber = flowNumber == null ? null : flowNumber.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}