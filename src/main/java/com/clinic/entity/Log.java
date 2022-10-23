package com.clinic.entity;

import java.util.Date;

public class Log {
	
	private final String ACTIVITY_KEY = "USERNAME";
	
	private final String ACTIVITY_SYSTEM = "SYSTEM";
	
	private final String STATUS = "SUCCESS";
	
	long id;
	String activity;
	String key;
	String value1;
	String value2;
	String status;
	Date createdDtm;
	String createdBy;
	
	public Log(){
		
	}
	
	public Log(String activity, String value1, String value2){
		this.activity = activity;
		this.key = ACTIVITY_KEY;
		this.value1 = value1;
		this.value2 = value2;
		this.status = STATUS;
		this.createdDtm = new Date();
		this.createdBy = ACTIVITY_SYSTEM;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
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

	public String getACTIVITY_KEY() {
		return ACTIVITY_KEY;
	}

	public String getACTIVITY_SYSTEM() {
		return ACTIVITY_SYSTEM;
	}

}
