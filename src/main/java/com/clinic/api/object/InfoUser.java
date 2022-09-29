package com.clinic.api.object;

import java.util.List;

public class InfoUser {
	
	private int id;
	private String fullname;
	private List < InfoChild > listChild;
	
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
	public List<InfoChild> getListChild() {
		return listChild;
	}
	public void setListChild(List<InfoChild> listChild) {
		this.listChild = listChild;
	}	

}
