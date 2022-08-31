package com.clinic.entity;

public class HeadDiameterSeries {
	
	private int month;
	private double diameter;
	private String desc;
	
	public HeadDiameterSeries setAttribute (int month, double diameter, String desc) {
		this.month = month;
		this.diameter = diameter;
		this.desc = desc;
		return this;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
