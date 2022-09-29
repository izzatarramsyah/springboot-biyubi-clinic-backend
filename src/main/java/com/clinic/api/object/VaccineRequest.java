package com.clinic.api.object;

public class VaccineRequest {

	private String vaccineCode;
	private String vaccineName;
	private String vaccineType;
	private int expDays;
	private String status;
	private String notes;
	private int batch;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getBatch() {
		return batch;
	}
	public void setBatch(int batch) {
		this.batch = batch;
	}
	
}
