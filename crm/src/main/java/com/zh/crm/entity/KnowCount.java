package com.zh.crm.entity;

public class KnowCount {
    private Integer id;

    private Integer knowId;//关联的知识id

    private Integer goodCount;//好评

    private Integer opposeCount;//差评

    private String creater;//评价人

    private String createDate;//动作时间

    private Integer readCount;//阅读量


    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKnowId() {
        return knowId;
    }

    public void setKnowId(Integer knowId) {
        this.knowId = knowId;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getOpposeCount() {
        return opposeCount;
    }

    public void setOpposeCount(Integer opposeCount) {
        this.opposeCount = opposeCount;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

}