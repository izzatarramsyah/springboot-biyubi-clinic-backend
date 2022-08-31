package com.clinic.api.object;

public class GrowthDtlRs {

	private String mstCode;
	private String description;
	private int batch;
	private int weight;
	private int length;
	private int headDiameter;
	
	public String getMstCode() {
		return mstCode;
	}
	public void setMstCode(String mstCode) {
		this.mstCode = mstCode;
	}
	public int getBatch() {
		return batch;
	}
	public void setBatch(int batch) {
		this.batch = batch;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getHeadDiameter() {
		return headDiameter;
	}
	public void setHeadDiameter(int headDiameter) {
		this.headDiameter = headDiameter;
	}
	
}
