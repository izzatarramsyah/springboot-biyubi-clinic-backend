package com.clinic.api.object;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	private double weight;
	private String weightCategory;
	private String weightNotes;
	private double length;
	private String lengthCategory;
	private String lengthNotes;
	private double headDiameter;
	private String headDiameterCategory;
	private String headDiameterNotes;
	private List < Double > seriesWeight;
	private List < Double > seriesLength;
	private List < Double > seriesHeadDiameter;
	
	public ChildData () { }
	
	public ChildData setAttribute ( int id, String fullname, Date birthDate, String gender,
			double weight, String weightCategory, String weightNotes, 
				double length, String lengthCategory, String lengthNotes, 
					double headDiameter, String headDiameterCategory, String headDiameterNotes, 
					List < Double > seriesWeight, List < Double > seriesLength, List < Double > seriesHeadDiameter ) {
		this.id = id;
		this.fullname = fullname;
		if (!Objects.isNull( birthDate )) {
			this.birthDate = Util.formatDate( birthDate );
		}
		if (gender.equals("P")) {
			this.gender = "Perempuan";
		} else {
			this.gender = "Laki - Laki";
		}
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String currentDt = formatDate.format(new Date());
		String birthDt = formatDate.format(birthDate);
		this.age = Util.calculateMonth(birthDt, currentDt);
		this.weight = weight;
		this.weightCategory = weightCategory;
		this.weightNotes = weightNotes;
		this.length = length;
		this.lengthCategory = lengthCategory;
		this.lengthNotes = lengthNotes;
		this.headDiameter = headDiameter;
		this.headDiameterCategory = headDiameterCategory;
		this.headDiameterNotes = headDiameterNotes;
		this.seriesWeight = seriesWeight;
		this.seriesLength = seriesLength;
		this.seriesHeadDiameter = seriesHeadDiameter;
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

	public List<Double> getSeriesWeight() {
		return seriesWeight;
	}

	public void setSeriesWeight(List<Double> seriesWeight) {
		this.seriesWeight = seriesWeight;
	}

	public List<Double> getSeriesLength() {
		return seriesLength;
	}

	public void setSeriesLength(List<Double> seriesLength) {
		this.seriesLength = seriesLength;
	}

	public List<Double> getSeriesHeadDiameter() {
		return seriesHeadDiameter;
	}

	public void setSeriesHeadDiameter(List<Double> seriesHeadDiameter) {
		this.seriesHeadDiameter = seriesHeadDiameter;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getHeadDiameter() {
		return headDiameter;
	}

	public void setHeadDiameter(double headDiameter) {
		this.headDiameter = headDiameter;
	}	
	
}
