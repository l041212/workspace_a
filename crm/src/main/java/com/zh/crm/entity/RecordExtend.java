package com.zh.crm.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

public class RecordExtend extends Record implements Serializable {

	private static final long serialVersionUID = 1L;

	private String reStartDate;
	private String reEndDate;
	private String reStartHour;
	private String reEndHour;
	private String symbol;
	private String period;

	public String getReStartDate() {
		if (StringUtils.isBlank(this.reStartDate)) {
			this.setReStartDate();
		}
		return this.reStartDate;
	}

	private void setReStartDate() {
		if (super.getStart_time() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.reStartDate = format.format(super.getStart_time());
		}
	}

	public String getReEndDate() {
		if (StringUtils.isBlank(this.reEndDate)) {
			this.setReEndDate();
		}
		return this.reEndDate;
	}

	private void setReEndDate() {
		if (super.getEnd_time() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			this.reEndDate = format.format(super.getEnd_time());
		}
	}

	public String getReStartHour() {
		if (StringUtils.isBlank(this.reStartHour)) {
			this.setReStartHour();
		}
		return this.reStartHour;
	}

	private void setReStartHour() {
		if (super.getStart_time() != null) {
			SimpleDateFormat format = new SimpleDateFormat("HH");
			this.reStartHour = format.format(super.getStart_time());
		}
	}

	public String getReEndHour() {
		if (StringUtils.isBlank(this.reEndHour)) {
			this.setReEndHour();
		}
		return this.reEndHour;
	}

	private void setReEndHour() {
		if (super.getEnd_time() != null) {
			SimpleDateFormat format = new SimpleDateFormat("HH");
			this.reEndHour = format.format(super.getEnd_time());
		}
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}
