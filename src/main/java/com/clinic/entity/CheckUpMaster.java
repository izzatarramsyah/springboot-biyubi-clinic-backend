package com.clinic.entity;

import java.util.Date;

public class CheckUpMaster {

	private String code;
	private String actName;
	private String description;
	private int batch;
	private int nextCheckUpDays;
	private String status;
	private Date createdDtm;
	private String createdBy;
	private Date lastUpdDtm;
	private String lastUpdBy;
	
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
	public int getNextCheckUpDays() {
		return nextCheckUpDays;
	}
	public void setNextCheckUpDays(int nextCheckUpDays) {
		this.nextCheckUpDays = nextCheckUpDays;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedDtm() {
		return createdDtm;
	}
	public void setCreatedDtm(Date createdDtm) {
		this.createdDtm = createdDtm;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastUpdDtm() {
		return lastUpdDtm;
	}
	public void setLastUpdDtm(Date lastUpdDtm) {
		this.lastUpdDtm = lastUpdDtm;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	
}
