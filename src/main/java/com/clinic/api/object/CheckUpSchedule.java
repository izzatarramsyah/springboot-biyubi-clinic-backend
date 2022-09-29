package com.clinic.api.object;

public class CheckUpSchedule {
	
	private String code;
	private String actName;
	private String description;
	private int batch;
	private int userId;
	private int childId;
	private String checkUpDate;
	private int nextCheckUpDays;
	private String scheduleDate;
	private double weight;
	private int length;
	private double headDiameter;
	private String notes;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getBatch() {
		return batch;
	}
	public void setBatch(int batch) {
		this.batch = batch;
	}
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
	public String getCheckUpDate() {
		return checkUpDate;
	}
	public void setCheckUpDate(String checkUpDate) {
		this.checkUpDate = checkUpDate;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getNextCheckUpDays() {
		return nextCheckUpDays;
	}
	public void setNextCheckUpDays(int nextCheckUpDays) {
		this.nextCheckUpDays = nextCheckUpDays;
	}
	
}
