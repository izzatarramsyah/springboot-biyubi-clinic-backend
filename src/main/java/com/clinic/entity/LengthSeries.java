package com.clinic.entity;

public class LengthSeries {

	private int month;
	private double length;
	private String desc;
	
	public LengthSeries setAttribute (int month, double length, String desc) {
		this.month = month;
		this.length = length;
		this.desc = desc;
		return this;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
