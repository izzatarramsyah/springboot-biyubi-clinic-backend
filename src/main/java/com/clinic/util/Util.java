package com.clinic.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.clinic.constant.Constant;

public class Util {
	
	public static String formatDateWithTime(Date date){
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		return formatDate.format(date);
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMM-yyyy");
		return formatDate.format(date);
	}
	
	public static Date formatDate(String str) throws Exception{
		Date dateStr = new SimpleDateFormat("dd-MM-yyyy").parse(str); 
		return dateStr;
	}

	public static long calculateMonth(String birthDate, String currentDate) {
    	long  diff = ChronoUnit.MONTHS.between(
	            LocalDate.parse(birthDate).withDayOfMonth(1),
	            LocalDate.parse(currentDate).withDayOfMonth(1));
        return diff;
    }
	
	public static String getWeightNotes (String notes) {
		if (notes.equals(Constant.VERY_UNDERWEIGHT)) {
			return "Berat badan anak tergolong sangat kurus. Periksa segara ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lanjut";
		} else if (notes.equals(Constant.UNDERWEIGHT)) {
			return "Berat badan anak tergolong kurus. Periksa segara ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lanjut";
		} else if (notes.equals(Constant.NORMAL)){
			return "Berat badan anak tergolong normal. Tetap perhatikan pertumbuhan anak";
		} else if (notes.equals(Constant.OVERWEIGHT)) {
			return "Berat badan anak tergolong gemuk. Periksa segara ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lanjut";
		} else if (notes.equals(Constant.VERY_OVERWEIGHT)){
			return "Berat badan anak tergolong sangat gemuk. Periksa segara ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lanjut";
		}
		return null;
	}
	
	public static String getLengthNotes (String notes){
		if (notes.equals(Constant.VERY_UNDERLENGTH)) {
			return "Tinggi badan anak tergolong sangat pendek. Jadwalkan kunjungan ke dokter spesialis atau fasilitas kesehatan terdekat untuk pemeriksaan lebih lanjut";
		} else if (notes.equals(Constant.VERY_UNDERLENGTH)) {
			return "Tinggi badan anak tergolong pendek. Jadwalkan kunjungan ke dokter spesialis atau fasilitas kesehatan terdekat untuk pemeriksaan lebih lanjut";
		} else if (notes.equals(Constant.NORMAL)){
			return "Tinggi badan anak tergolong normal. Tetap perhatikan pertumbuhan anak";
		} else if (notes.equals(Constant.OVERLENGTH)){
			return "Tinggi badan anak tergolong tinggi. Jadwalkan kunjungan ke dokter spesialis atau fasilitas kesehatan terdekat untuk pemeriksaan lebih lanjut";
		} else if (notes.equals(Constant.VERY_OVERLENGTH)){
			return "Tinggi badan anak tergolong sangat tinggi. Jadwalkan kunjungan ke dokter spesialis atau fasilitas kesehatan terdekat untuk pemeriksaan lebih lanjut";
		}
		return null;
	}
	
	public static String getHeadDiameterNotes(String notes){
		if (notes.equals(Constant.VERY_MIKROSEFALI)) {
			return "Lingkar kepala anak tergolong kecil (Mikrosefali). Periksa segera ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lebih lanjut";
		} if (notes.equals(Constant.MIKROSEFALI)) {
			return "Lingkar kepala anak tergolong kecil (Mikrosefali). Periksa segera ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lebih lanjut";
		} else if (notes.equals(Constant.NORMAL)){
			return "Lingkar kepala anak tergolong normal. Tetap perhatikan pertumbuhan anak";
		} else if (notes.equals(Constant.MAKROSEFALI)){
			return "Lingkar kepala anak tergolong besar (Makrosefali). Tetap perhatikan pertumbuhan anak";
		} else if (notes.equals(Constant.VERY_MAKROSEFALI)){
			return "Lingkar kepala anak tergolong besar (Makrosefali). Periksa segera ke dokter spesialis anak atau puskesmas terdekat untuk pemeriksaan dan penanganan lebih lanjut";
		}
		return null;
	}
}
