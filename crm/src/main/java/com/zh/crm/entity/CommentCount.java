package com.zh.crm.entity;

public class CommentCount {
    private Integer id;

    private Integer commentId;

    private Integer agreeCount;

    private Integer opposeCount;

    private String creater;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }
}