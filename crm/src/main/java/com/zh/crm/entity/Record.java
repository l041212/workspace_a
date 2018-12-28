package com.zh.crm.entity;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
    private Integer ID;

    private String ucid;

    private String callid;

    private String calling_no;

    private String called_no;

    private String ext_no;

    private String agent_no;

    private Date start_time;

    private Date end_time;

    private String file_name;

    private Integer direction;

    private String nucid;

    private Integer type;

    private Long span;

    private String skill_no;

    private String vdn;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getUcid() {
        return ucid;
    }

    public void setUcid(String ucid) {
        this.ucid = ucid == null ? null : ucid.trim();
    }

    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid == null ? null : callid.trim();
    }

    public String getCalling_no() {
        return calling_no;
    }

    public void setCalling_no(String calling_no) {
        this.calling_no = calling_no == null ? null : calling_no.trim();
    }

    public String getCalled_no() {
        return called_no;
    }

    public void setCalled_no(String called_no) {
        this.called_no = called_no == null ? null : called_no.trim();
    }

    public String getExt_no() {
        return ext_no;
    }

    public void setExt_no(String ext_no) {
        this.ext_no = ext_no == null ? null : ext_no.trim();
    }

    public String getAgent_no() {
        return agent_no;
    }

    public void setAgent_no(String agent_no) {
        this.agent_no = agent_no == null ? null : agent_no.trim();
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name == null ? null : file_name.trim();
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getNucid() {
        return nucid;
    }

    public void setNucid(String nucid) {
        this.nucid = nucid == null ? null : nucid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSpan() {
        return span;
    }

    public void setSpan(Long span) {
        this.span = span;
    }

    public String getSkill_no() {
        return skill_no;
    }

    public void setSkill_no(String skill_no) {
        this.skill_no = skill_no == null ? null : skill_no.trim();
    }

    public String getVdn() {
        return vdn;
    }

    public void setVdn(String vdn) {
        this.vdn = vdn == null ? null : vdn.trim();
    }
}