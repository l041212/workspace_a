package com.zh.crm.entity;

import java.util.List;

public class Comment {
    private Integer id;

    private String creater;

    private String createDate;

    private String content;

    private Integer knowId;

    private Integer replyId;

    private Integer agreeCount;

    private Integer opposeCount;

    private List<Comment> sonComments;

    private boolean hasSon;




    public List<Comment> getSonComments() {
        return sonComments;
    }

    public void setSonComments(List<Comment> sonComments) {
        this.sonComments = sonComments;
    }

    public boolean isHasSon() {
        return hasSon;
    }

    public void setHasSon(boolean hasSon) {
        this.hasSon = hasSon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getKnowId() {
        return knowId;
    }

    public void setKnowId(Integer knowId) {
        this.knowId = knowId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(Integer agreeCount) {
        this.agreeCount = agreeCount;
    }

    public Integer getOpposeCount() {
        return opposeCount;
    }

    public void setOpposeCount(Integer opposeCount) {
        this.opposeCount = opposeCount;
    }
}