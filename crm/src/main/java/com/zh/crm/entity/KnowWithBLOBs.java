package com.zh.crm.entity;

import java.io.Serializable;

public class KnowWithBLOBs extends Know implements Serializable {
    private String konwContent;//内容含html标签

    private String knowImg;//压缩图

    private String knowContentTxt;//内容不含html标签

    public String getKnowContentTxt() {
        return knowContentTxt;
    }

    public void setKonwContentTxt(String knowContentTxt) {
        this.knowContentTxt = knowContentTxt == null ? null : knowContentTxt.trim();
    }

    public String getKonwContent() {
        return konwContent;
    }

    public void setKonwContent(String konwContent) {
        this.konwContent = konwContent == null ? null : konwContent.trim();
    }

    public String getKnowImg() {
        return knowImg;
    }

    public void setKnowImg(String knowImg) {
        this.knowImg = knowImg == null ? null : knowImg.trim();
    }
}