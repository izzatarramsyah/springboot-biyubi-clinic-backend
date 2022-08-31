package com.clinic.util;

import java.util.ArrayList;
import java.util.List;

import com.clinic.entity.HeadDiameterSeries;
import com.clinic.entity.LengthSeries;
import com.clinic.entity.WeightSeries;

public class Graph {

	private static List <WeightSeries> getListWeightSeries (){
		List <WeightSeries> result = new ArrayList <WeightSeries>();
		WeightSeries underWeight = new WeightSeries().setAttribute(0, 2, "Sangat Kurus");
		result.add(underWeight);
		WeightSeries underWeight1 = new WeightSeries().setAttribute(1, 3, "Sangat Kurus");
		result.add(underWeight1);
		WeightSeries underWeight2 = new WeightSeries().setAttribute(2, 3.8, "Sangat Kurus");
		result.add(underWeight2);
		WeightSeries underWeight3 = new WeightSeries().setAttribute(3, 4.4, "Sangat Kurus");
		result.add(underWeight3);
		WeightSeries Normal = new WeightSeries().setAttribute(0, 3.4, "Normal");
		result.add(Normal);
		WeightSeries Normal1 = new WeightSeries().setAttribute(1, 4.4, "Normal");
		result.add(Normal1);
		WeightSeries Normal2 = new WeightSeries().setAttribute(2, 5.6, "Normal");
		result.add(Normal2);
		WeightSeries Normal3 = new WeightSeries().setAttribute(3, 6.4, "Normal");
		result.add(Normal3);
		WeightSeries overWeight = new WeightSeries().setAttribute(0, 5, "Sangat Gemuk");
		result.add(overWeight);
		WeightSeries overWeight1 = new WeightSeries().setAttribute(1, 6.6, "Sangat Gemuk");
		result.add(overWeight1);
		WeightSeries overWeight2 = new WeightSeries().setAttribute(2, 8, "Sangat Gemuk");
		result.add(overWeight2);
		WeightSeries overWeight3 = new WeightSeries().setAttribute(3, 9, "Sangat Gemuk");
		result.add(overWeight3);
		return result;
	}
	
	public static String categorizeWeight(long month, double weight){
		String result = null;
		List <WeightSeries> getWeightByMonth = new ArrayList <WeightSeries>();
		for (WeightSeries s : getListWeightSeries ()) {
			if (s.getMonth() == month) {
				if (s.getWeight() < weight) {
					result = s.getDesc();
				}
				getWeightByMonth.add(s);
			}
		}
		if (result == null ) result = getWeightByMonth.get(0).getDesc();
		return result;
	}
	
	public static String getWeightNotes(String notes){
		if (notes.equals("Sangat Kurus")) {
			return "Berat badan anak tergolong sangat kurus. Periksa segara ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lanjut";
		} else if (notes.equals("Normal")){
			return "Berat badan anak tergolong normal. Tetap perhatikan pertumbuhan anak";
		} else {
			return "Berat badan anak tergolong sangat gemuk. Periksa segara ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lanjut";
		}
	}
	
	private static List <LengthSeries> getListLengthSeries (){
		List <LengthSeries> result = new ArrayList <LengthSeries>();
		LengthSeries underLength = new LengthSeries().setAttribute(0, 44, "Sangat Pendek");
		result.add(underLength);
		LengthSeries underLength1 = new LengthSeries().setAttribute(1, 48, "Sangat Pendek");
		result.add(underLength1);
		LengthSeries underLength2 = new LengthSeries().setAttribute(2, 52, "Sangat Pendek");
		result.add(underLength2);
		LengthSeries underLength3 = new LengthSeries().setAttribute(3, 55, "Sangat Pendek");
		result.add(underLength3);
		LengthSeries normal = new LengthSeries().setAttribute(0, 50, "Normal");
		result.add(normal);
		LengthSeries normal1 = new LengthSeries().setAttribute(1, 55, "Normal");
		result.add(normal1);
		LengthSeries normal2 = new LengthSeries().setAttribute(2, 59, "Normal");
		result.add(normal2);
		LengthSeries normal3 = new LengthSeries().setAttribute(3, 62, "Normal");
		result.add(normal3);
		LengthSeries overLength = new LengthSeries().setAttribute(0, 56, "Sangat Tinggi");
		result.add(overLength);
		LengthSeries overLength1 = new LengthSeries().setAttribute(1, 61, "Sangat Tinggi");
		result.add(overLength1);
		LengthSeries overLength2 = new LengthSeries().setAttribute(2, 65, "Sangat Tinggi");
		result.add(overLength2);
		LengthSeries overLength3 = new LengthSeries().setAttribute(3, 68, "Sangat Tinggi");
		result.add(overLength3);
		return result;
	}
	
	public static String categorizeLength(long month, int length){
		String result = null;
		List <LengthSeries> getLengthByMonth = new ArrayList <LengthSeries>();
		for (LengthSeries s : getListLengthSeries ()) {
			if (s.getMonth() == month) {
				if (s.getLength() < length) {
					result = s.getDesc();
				}
				getLengthByMonth.add(s);
			}
		}
		if (result == null ) result = getLengthByMonth.get(0).getDesc();
		return result;
	}
	
	public static String getLengthNotes(String notes){
		if (notes.equals("Sangat Kurus")) {
			return "Tinggi badan anak tergolong sangat pendek. Jadwalkan kunjungan ke dokter spesialis atau fasilitas kesehatan terdekat untuk pemeriksaan lebih lanjut";
		} else if (notes.equals("Normal")){
			return "Tinggi badan anak tergolong normal. Tetap perhatikan pertumbuhan anak";
		} else {
			return "Tinggi badan anak tergolong sangat tinggi. Jadwalkan kunjungan ke dokter spesialis atau fasilitas kesehatan terdekat untuk pemeriksaan lebih lanjut";
		}
	}
	
	private static List <HeadDiameterSeries> getListHeadDiameterSeries (){
		List <HeadDiameterSeries> result = new ArrayList <HeadDiameterSeries>();
		HeadDiameterSeries mikrosefalus = new HeadDiameterSeries().setAttribute(0, 32, "Mikrosefali");
		result.add(mikrosefalus);
		HeadDiameterSeries mikrosefalus1 = new HeadDiameterSeries().setAttribute(1, 35, "Mikrosefali");
		result.add(mikrosefalus1);
		HeadDiameterSeries mikrosefalus2 = new HeadDiameterSeries().setAttribute(2, 37, "Mikrosefali");
		result.add(mikrosefalus2);
		HeadDiameterSeries mikrosefalus3 = new HeadDiameterSeries().setAttribute(3, 39.5, "Mikrosefali");
		result.add(mikrosefalus3);
		HeadDiameterSeries normal = new HeadDiameterSeries().setAttribute(0, 34.5, "Normal");
		result.add(normal);
		HeadDiameterSeries normal1 = new HeadDiameterSeries().setAttribute(1, 37.5, "Normal");
		result.add(normal1);
		HeadDiameterSeries normal2 = new HeadDiameterSeries().setAttribute(2, 39, "Normal");
		result.add(normal2);
		HeadDiameterSeries normal3 = new HeadDiameterSeries().setAttribute(3, 41.5, "Normal");
		result.add(normal3);
		HeadDiameterSeries makrosefalus = new HeadDiameterSeries().setAttribute(0, 37, "Makrosefali");
		result.add(makrosefalus);
		HeadDiameterSeries makrosefalus1 = new HeadDiameterSeries().setAttribute(1, 39.5, "Makrosefali");
		result.add(makrosefalus1);
		HeadDiameterSeries makrosefalus2 = new HeadDiameterSeries().setAttribute(2, 42.5, "Makrosefali");
		result.add(makrosefalus2);
		HeadDiameterSeries makrosefalus3 = new HeadDiameterSeries().setAttribute(3, 44, "Makrosefali");
		result.add(makrosefalus3);
		return result;
	}
	
	public static String categorizeHeadDiameter(long month, double length){
		String result = null;
		List <HeadDiameterSeries> getHeadDiameterByMonth = new ArrayList <HeadDiameterSeries>();
		for (HeadDiameterSeries s : getListHeadDiameterSeries ()) {
			if (s.getMonth() == month) {
				if (s.getDiameter() < length) {
					result = s.getDesc();
				}
				getHeadDiameterByMonth.add(s);
			}
		}
		if (result == null ) result = getHeadDiameterByMonth.get(0).getDesc();
		return result;
	}
	
	public static String getHeadDiameterNotes(String notes){
		if (notes.equals("Mikrosefali")) {
			return "Lingkar kepala anak tergolong kecil (Mikrosefali). Periksa segera ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lebih lanjut";
		} else if (notes.equals("Normal")){
			return "Lingkar kepala anak tergolong normal. Tetap perhatikan pertumbuhan anak";
		} else {
			return "Lingkar kepala anak tergolong besar (Makrosefali). Periksa segera ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lebih lanjut";
		}
	}
	
}
