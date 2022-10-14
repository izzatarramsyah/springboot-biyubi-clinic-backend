package com.clinic.api.object;

public class InfoChildID {

	private int id;
	private String fullname;

	public InfoChildID(){}
	
	public InfoChildID (int id, String fullname){
		this.id = id;
		this.fullname = fullname;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
}
