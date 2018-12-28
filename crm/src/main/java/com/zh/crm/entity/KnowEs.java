package com.zh.crm.entity;

import io.searchbox.annotations.JestId;

import java.io.Serializable;

public class KnowEs implements Serializable {
    @JestId
    private Integer id;//知识id
    private String knowTitle;//

    private String knowType;

    private String knowTag;//标签
    private String createDate;//

    private String creater;//创建人

    private String content;//不含html标签

    private Integer resultType;//1知识、2问答

    private Integer readPerm;//阅读权限

    private String  readRoleIds;

    public Integer getReadPerm() {
        return readPerm;
    }

    public void setReadPerm(Integer readPerm) {
        this.readPerm = readPerm;
    }

    public String getReadRoleIds() {
        return readRoleIds;
    }

    public void setReadRoleIds(String readRoleIds) {
        this.readRoleIds = readRoleIds;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKnowTitle() {
        return knowTitle;
    }

    public void setKnowTitle(String knowTitle) {
        this.knowTitle = knowTitle;
    }

    public String getKnowType() {
        return knowType;
    }

    public void setKnowType(String knowType) {
        this.knowType = knowType;
    }

    public String getKnowTag() {
        return knowTag;
    }

    public void setKnowTag(String knowTag) {
        this.knowTag = knowTag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
