package com.clinic.api.object;

import java.util.List;
import java.util.Objects;

import com.clinic.entity.Child;
import com.clinic.util.Util;

public class ChildData {
	
	private int id;
	private String fullname;
	private String birthDate;
	private long age;
	private String gender;
	private String weightCategory;
	private String weightNotes;
	private String lengthCategory;
	private String lengthNotes;
	private String headDiameterCategory;
	private String headDiameterNotes;
	private List < GrowthDtlRs> growthDetail;
	
	public ChildData () {
		
	}
	
	public ChildData setAttribute (Child child) {
		
		this.id = child.getId();
		this.fullname = child.getFullname();
		if (!Objects.isNull( child.getBirthDate() )) {
			this.birthDate = Util.formatDate( child.getBirthDate() );
		}
		if (child.getGender().equals("P")) {
			this.gender = "Perempuan";
		} else {
			this.gender = "Laki - Laki";
		}
		return this;
		
	}
	
	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
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
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWeightCategory() {
		return weightCategory;
	}

	public void setWeightCategory(String weightCategory) {
		this.weightCategory = weightCategory;
	}

	public String getLengthCategory() {
		return lengthCategory;
	}

	public void setLengthCategory(String lengthCategory) {
		this.lengthCategory = lengthCategory;
	}

	public String getWeightNotes() {
		return weightNotes;
	}

	public void setWeightNotes(String weightNotes) {
		this.weightNotes = weightNotes;
	}

	public String getLengthNotes() {
		return lengthNotes;
	}

	public void setLengthNotes(String lengthNotes) {
		this.lengthNotes = lengthNotes;
	}

	public String getHeadDiameterCategory() {
		return headDiameterCategory;
	}

	public void setHeadDiameterCategory(String headDiameterCategory) {
		this.headDiameterCategory = headDiameterCategory;
	}

	public String getHeadDiameterNotes() {
		return headDiameterNotes;
	}

	public void setHeadDiameterNotes(String headDiameterNotes) {
		this.headDiameterNotes = headDiameterNotes;
	}

	public List<GrowthDtlRs> getGrowthDetail() {
		return growthDetail;
	}

	public void setGrowthDetail(List<GrowthDtlRs> growthDetail) {
		this.growthDetail = growthDetail;
	}	
	
	
}
