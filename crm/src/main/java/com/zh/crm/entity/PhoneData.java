package com.zh.crm.entity;

import java.io.Serializable;
import java.util.Date;

public class PhoneData implements Serializable {
    private String ucid;

    private Date IVRanswerTime;

    private Date IVRcallTime;

    private Date IVRhangupTime;

    private String IVRrelay;

    private Date VDNcallTime;

    private Date VDNhangupTime;

    private String VDNrelay;

    private String agentno;

    private Date alertingTime;

    private String appraiseState;

    private Date appraiseTime;

    private String callNo;

    private String calledNo;

    private String callsign;

    private Date connectTime;

    private Date hangupTime;

    private Date notbusyTime;

    private Date queueEndTime;

    private Date queueStartTime;

    private String relay;

    private String skill;

    private Date holdenddate;

    private Date holdstratdate;

    private String rollout;

    private String isqueue;

    private Date operationTime;

    private String dropflag;

    private String extNo;

    private String vdn_num;

    public String getUcid() {
        return ucid;
    }

    public void setUcid(String ucid) {
        this.ucid = ucid == null ? null : ucid.trim();
    }

    public Date getIVRanswerTime() {
        return IVRanswerTime;
    }

    public void setIVRanswerTime(Date IVRanswerTime) {
        this.IVRanswerTime = IVRanswerTime;
    }

    public Date getIVRcallTime() {
        return IVRcallTime;
    }

    public void setIVRcallTime(Date IVRcallTime) {
        this.IVRcallTime = IVRcallTime;
    }

    public Date getIVRhangupTime() {
        return IVRhangupTime;
    }

    public void setIVRhangupTime(Date IVRhangupTime) {
        this.IVRhangupTime = IVRhangupTime;
    }

    public String getIVRrelay() {
        return IVRrelay;
    }

    public void setIVRrelay(String IVRrelay) {
        this.IVRrelay = IVRrelay == null ? null : IVRrelay.trim();
    }

    public Date getVDNcallTime() {
        return VDNcallTime;
    }

    public void setVDNcallTime(Date VDNcallTime) {
        this.VDNcallTime = VDNcallTime;
    }

    public Date getVDNhangupTime() {
        return VDNhangupTime;
    }

    public void setVDNhangupTime(Date VDNhangupTime) {
        this.VDNhangupTime = VDNhangupTime;
    }

    public String getVDNrelay() {
        return VDNrelay;
    }

    public void setVDNrelay(String VDNrelay) {
        this.VDNrelay = VDNrelay == null ? null : VDNrelay.trim();
    }

    public String getAgentno() {
        return agentno;
    }

    public void setAgentno(String agentno) {
        this.agentno = agentno == null ? null : agentno.trim();
    }

    public Date getAlertingTime() {
        return alertingTime;
    }

    public void setAlertingTime(Date alertingTime) {
        this.alertingTime = alertingTime;
    }

    public String getAppraiseState() {
        return appraiseState;
    }

    public void setAppraiseState(String appraiseState) {
        this.appraiseState = appraiseState == null ? null : appraiseState.trim();
    }

    public Date getAppraiseTime() {
        return appraiseTime;
    }

    public void setAppraiseTime(Date appraiseTime) {
        this.appraiseTime = appraiseTime;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo == null ? null : callNo.trim();
    }

    public String getCalledNo() {
        return calledNo;
    }

    public void setCalledNo(String calledNo) {
        this.calledNo = calledNo == null ? null : calledNo.trim();
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign == null ? null : callsign.trim();
    }

    public Date getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Date connectTime) {
        this.connectTime = connectTime;
    }

    public Date getHangupTime() {
        return hangupTime;
    }

    public void setHangupTime(Date hangupTime) {
        this.hangupTime = hangupTime;
    }

    public Date getNotbusyTime() {
        return notbusyTime;
    }

    public void setNotbusyTime(Date notbusyTime) {
        this.notbusyTime = notbusyTime;
    }

    public Date getQueueEndTime() {
        return queueEndTime;
    }

    public void setQueueEndTime(Date queueEndTime) {
        this.queueEndTime = queueEndTime;
    }

    public Date getQueueStartTime() {
        return queueStartTime;
    }

    public void setQueueStartTime(Date queueStartTime) {
        this.queueStartTime = queueStartTime;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay == null ? null : relay.trim();
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill == null ? null : skill.trim();
    }

    public Date getHoldenddate() {
        return holdenddate;
    }

    public void setHoldenddate(Date holdenddate) {
        this.holdenddate = holdenddate;
    }

    public Date getHoldstratdate() {
        return holdstratdate;
    }

    public void setHoldstratdate(Date holdstratdate) {
        this.holdstratdate = holdstratdate;
    }

    public String getRollout() {
        return rollout;
    }

    public void setRollout(String rollout) {
        this.rollout = rollout == null ? null : rollout.trim();
    }

    public String getIsqueue() {
        return isqueue;
    }

    public void setIsqueue(String isqueue) {
        this.isqueue = isqueue == null ? null : isqueue.trim();
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getDropflag() {
        return dropflag;
    }

    public void setDropflag(String dropflag) {
        this.dropflag = dropflag == null ? null : dropflag.trim();
    }

    public String getExtNo() {
        return extNo;
    }

    public void setExtNo(String extNo) {
        this.extNo = extNo == null ? null : extNo.trim();
    }

    public String getVdn_num() {
        return vdn_num;
    }

    public void setVdn_num(String vdn_num) {
        this.vdn_num = vdn_num == null ? null : vdn_num.trim();
    }
}