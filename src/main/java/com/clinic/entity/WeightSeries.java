package com.clinic.entity;

public class WeightSeries {
	
	private int month;
	private double weight;
	private String desc;
	
	public WeightSeries setAttribute (int month, double weight, String desc) {
		this.month = month;
		this.weight = weight;
		this.desc = desc;
		return this;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
