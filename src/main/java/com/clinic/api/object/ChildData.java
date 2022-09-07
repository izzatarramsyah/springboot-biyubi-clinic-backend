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
	private int length;
	private String lengthCategory;
	private String lengthNotes;
	private double headDiameter;
	private String headDiameterCategory;
	private String headDiameterNotes;
	private List < Double > seriesWeight;
	private List < Integer > seriesLength;
	private List < Double > seriesHeadDiameter;
	
	public ChildData () { }
	
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
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = formatDate.format(new Date());
		String birthDate = formatDate.format(child.getBirthDate());
		this.age = Util.calculateMonth(birthDate, currentDate);
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

	public List<Integer> getSeriesLength() {
		return seriesLength;
	}

	public void setSeriesLength(List<Integer> seriesLength) {
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getHeadDiameter() {
		return headDiameter;
	}

	public void setHeadDiameter(double headDiameter) {
		this.headDiameter = headDiameter;
	}	
	
}
