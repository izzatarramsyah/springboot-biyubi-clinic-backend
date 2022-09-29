package com.clinic.api.object;

import java.util.Date;

public class CheckUpRequest {
	
	private int userId;
	private int childId;
	private String fullname;
	private Date birthDate;
	private String gender;
	private String mstCode;
	private double weight;
	private int length;
	private double headDiameter;
	private int batch;
	private String notes;
	private Date checkUpDate;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getChildId() {
		return childId;
	}
	public void setChildId(int childId) {
		this.childId = childId;
	}
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public double getHeadDiameter() {
		return headDiameter;
	}
	public void setHeadDiameter(double headDiameter) {
		this.headDiameter = headDiameter;
	}
	public Date getCheckUpDate() {
		return checkUpDate;
	}
	public void setCheckUpDate(Date checkUpDate) {
		this.checkUpDate = checkUpDate;
	}
	

}
