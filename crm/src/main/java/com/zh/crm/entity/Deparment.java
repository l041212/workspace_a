package com.zh.crm.entity;

import java.io.Serializable;

public class Deparment implements Serializable {
    private Integer depId;

    private String depName;

    private String depCode;

    private Integer parentId;

    private String remark;

    private  String open = "false" ;

    private String icon = "/img/user.gif" ;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public String getDepCode() {
        return depCode;
    }


    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}