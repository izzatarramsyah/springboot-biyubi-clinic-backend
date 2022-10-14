package com.clinic.api.object;

import java.util.List;

public class InfoUserID {
	
	private int id;
	private String fullname;
	private List < InfoChildID > listChild;

	public InfoUserID (){}

	public InfoUserID (int id, String fullname, List < InfoChildID > listChild){ 
		this.id = id;
		this.fullname = fullname;
		this.listChild = listChild;
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
	public List<InfoChildID> getListChild() {
		return listChild;
	}
	public void setListChild(List<InfoChildID> listChild) {
		this.listChild = listChild;
	}

}
