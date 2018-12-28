package com.zh.crm.entity;

public class BusLine {
    private Integer id;

    private String number;

    private String company;

    private String fleet;

    private String attribute;

    private String status;

    private String rate;

    private String avgMileage;

    private String openDate;

    private String stopDate;

    private String ticketNorm;

    private String ticketPrice;

    private String isCheck;

    private String historyNumber;

    private String runStatus;

    private String deployStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet == null ? null : fleet.trim();
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public String getAvgMileage() {
        return avgMileage;
    }

    public void setAvgMileage(String avgMileage) {
        this.avgMileage = avgMileage == null ? null : avgMileage.trim();
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate == null ? null : openDate.trim();
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate == null ? null : stopDate.trim();
    }

    public String getTicketNorm() {
        return ticketNorm;
    }

    public void setTicketNorm(String ticketNorm) {
        this.ticketNorm = ticketNorm == null ? null : ticketNorm.trim();
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice == null ? null : ticketPrice.trim();
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck == null ? null : isCheck.trim();
    }

    public String getHistoryNumber() {
        return historyNumber;
    }

    public void setHistoryNumber(String historyNumber) {
        this.historyNumber = historyNumber == null ? null : historyNumber.trim();
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus == null ? null : runStatus.trim();
    }

    public String getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(String deployStatus) {
        this.deployStatus = deployStatus == null ? null : deployStatus.trim();
    }
}