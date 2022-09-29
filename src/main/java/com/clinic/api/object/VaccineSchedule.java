package com.clinic.api.object;

public class VaccineSchedule {
	
	private String vaccineCode;
	private String vaccineName;
	private String vaccineType;
	private int expDays;
	private int batch;
	private int id;
	private int userId;
	private int childId;
	private String scheduleDate;
	private String vaccineDate;
	private String expDate;
	private String notes;
	
	public String getVaccineCode() {
		return vaccineCode;
	}
	public void setVaccineCode(String vaccineCode) {
		this.vaccineCode = vaccineCode;
	}
	public String getVaccineName() {
		return vaccineName;
	}
	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	public String getVaccineType() {
		return vaccineType;
	}
	public void setVaccineType(String vaccineType) {
		this.vaccineType = vaccineType;
	}
	public int getExpDays() {
		return expDays;
	}
	public void setExpDays(int expDays) {
		this.expDays = expDays;
	}
	public int getBatch() {
		return batch;
	}
	public void setBatch(int batch) {
		this.batch = batch;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getVaccineDate() {
		return vaccineDate;
	}
	public void setVaccineDate(String vaccineDate) {
		this.vaccineDate = vaccineDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}


}
