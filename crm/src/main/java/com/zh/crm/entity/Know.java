package com.zh.crm.entity;


import io.searchbox.annotations.JestId;

import java.io.Serializable;

public class Know implements Serializable {
    @JestId
    private Integer id;

    private String knowTitle;//标题

    private String knowType;//类型

    private String knowTag;//标记

    private Integer knowRelationId;//关联知识ID

    private Integer knowTypeId;//类型id

    private String knowFileIds;//关联文件id，以/分隔

    private String createDate;//创建时间

    private String creater;//创建人

    private String knowFileNames;//关联文件以/分隔

    private String status;//状态：待审查、待审核、已归档

    private Integer publish;//发布到部门标记：0所有部门，1publishDepIds的部门

    private Integer editPerm;//编辑权限：0所有人、1创建人、2、选定的角色editRoleIds

    private Integer readPerm;//阅读权限：0所有人、1创建人、2、选定的角色readRoleIds

    private String  publishDepIds;//知识发布的部门id，以，分隔

    private String  editRoleIds;//知识编辑的角色id，以，分隔

    private String  readRoleIds;//知识阅读的角色id，以，分隔

    private Integer readCount ;

    private Integer goodCount;//好评

    private Integer badCount;//差评

    private Integer resultType;//1知识、2问答

    private Integer pastId ;//最初版的知识id---0为第一版

    private Integer version ;//版本标识----1为最新版0为旧版

    private String remark;//版本更新备注

    private String updateDate ;//更新时间

    private String updater ;//更新人

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Integer getPastId() {
        return pastId;
    }

    public void setPastId(Integer pastId) {
        this.pastId = pastId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public Integer getEditPerm() {
        return editPerm;
    }

    public void setEditPerm(Integer editPerm) {
        this.editPerm = editPerm;
    }

    public Integer getReadPerm() {
        return readPerm;
    }

    public void setReadPerm(Integer readPerm) {
        this.readPerm = readPerm;
    }

    public String getPublishDepIds() {
        return publishDepIds;
    }

    public void setPublishDepIds(String publishDepIds) {
        this.publishDepIds = publishDepIds;
    }

    public String getEditRoleIds() {
        return editRoleIds;
    }

    public void setEditRoleIds(String editRoleIds) {
        this.editRoleIds = editRoleIds;
    }

    public String getReadRoleIds() {
        return readRoleIds;
    }

    public void setReadRoleIds(String readRoleIds) {
        this.readRoleIds = readRoleIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKnowFileNames() {
        return knowFileNames;
    }

    public void setKnowFileNames(String knowFileNames) {
        this.knowFileNames = knowFileNames;
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
        this.knowTitle = knowTitle == null ? null : knowTitle.trim();
    }

    public String getKnowType() {
        return knowType;
    }

    public void setKnowType(String knowType) {
        this.knowType = knowType == null ? null : knowType.trim();
    }

    public String getKnowTag() {
        return knowTag;
    }

    public void setKnowTag(String knowTag) {
        this.knowTag = knowTag == null ? null : knowTag.trim();
    }

    public Integer getKnowRelationId() {
        return knowRelationId;
    }

    public void setKnowRelationId(Integer knowRelationId) {
        this.knowRelationId = knowRelationId;
    }

    public Integer getKnowTypeId() {
        return knowTypeId;
    }

    public void setKnowTypeId(Integer knowTypeId) {
        this.knowTypeId = knowTypeId;
    }

    public String getKnowFileIds() {
        return knowFileIds;
    }

    public void setKnowFileIds(String knowFileIds) {
        this.knowFileIds = knowFileIds == null ? null : knowFileIds.trim();
    }
}