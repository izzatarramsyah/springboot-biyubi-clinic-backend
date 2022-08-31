package com.clinic.api.object;

import java.util.List;

import com.clinic.entity.User;

public class UserData {

	private int id;
	private String fullname;
	private List < ChildData > childData;
	
	public UserData () {}
	
	public UserData setAttribute (User user) {
		this.id = user.getId();
		this.fullname = user.getFullname();
		return this;
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

	public List<ChildData> getChildData() {
		return childData;
	}
	public void setChildData(List<ChildData> childData) {
		this.childData = childData;
	}

}
