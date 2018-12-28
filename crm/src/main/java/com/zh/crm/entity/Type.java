package com.zh.crm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Type implements Serializable {
    private Integer id;

    private String text;

    private String value;

    private Integer parentId;

    private String parentName;

    private String title;

    private Integer status;

    private List<Type> sonTypes;

    private boolean hasMenu = false;

    private Integer typeCount = 0 ;

    public Integer getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Integer typeCount) {
        this.typeCount = typeCount;
    }

    public List<Type> getSonTypes() {
        return sonTypes;
    }

    public void setSonTypes(List<Type> sonTypes) {
        this.sonTypes = sonTypes;
    }

    public boolean isHasMenu() {
        return hasMenu;
    }

    public void setHasMenu(boolean hasMenu) {
        this.hasMenu = hasMenu;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}