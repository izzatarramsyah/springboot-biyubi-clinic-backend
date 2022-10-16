package com.clinic.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.clinic.constant.Constant;
import java.util.regex.Pattern;

public class Util {

	private static final Pattern PATTERN_NUM_ONLY = Pattern.compile(".*[^0-9].*");

	private static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
												"[a-zA-Z0-9_+&*-]+)*@" + 
												"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
												"A-Z]{2,7}$"; 
												
	public static final String REGEX_FORMAT_STRING_DD_MM_YYYY = "([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$";

	public static String formatDateWithTime(Date date){
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return formatDate.format(date);
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		return formatDate.format(date);
	}
	
	public static String formatDate1(Date date){
		SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd-yyyy");
		return formatDate.format(date);
	}
	
	public static Date formatDate(String str) throws Exception{
		Date dateStr = new SimpleDateFormat("dd-MM-yyyy").parse(str); 
		return dateStr;
	}

	public static Date formatDate1(String str) throws Exception{
		Date dateStr = new SimpleDateFormat("dd-MM-yyyy").parse(str); 
		return dateStr;
	}
	
	public static Date formatDateWithTime(String str) throws Exception{
		Date dateStr = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(str); 
		return dateStr;
	}


    public static long calculateMonth(String birthDate, String currentDate) {
    	long  diff = ChronoUnit.MONTHS.between(
	            LocalDate.parse(birthDate).withDayOfMonth(1),
	            LocalDate.parse(currentDate).withDayOfMonth(1));
        return diff;
    }
    
	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equals( rsmd.getColumnName( x ) )) {
				return true;
			}
		}
		return false;
	}
	
	public static String formatPhoneNo(String msisdn){
		if(msisdn != null && msisdn.length() <= 2){
			return "";
		}
		if(msisdn == null || "".equalsIgnoreCase(msisdn)
				|| "62".equalsIgnoreCase(msisdn.substring(0, 2))){
			return msisdn;
		}
		if("0".equalsIgnoreCase(msisdn.substring(0, 1))){
			return "62" + msisdn.substring(1);
		}else{
			return "";
		}
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

	public static boolean isValidNumber(String msisdn){
		Boolean result = PATTERN_NUM_ONLY.matcher(msisdn).matches();
		return result;
	}

	public static boolean isValidPrefixNum(String msisdn){
		if( !msisdn.startsWith("08") && ! msisdn.startsWith("62")){ 
			return false;
		}
		return true;
	}

	public static boolean isValidEmail(String email){
        Pattern pat = Pattern.compile(emailRegex); 		 
        return pat.matcher(email).matches();
	}

	public static boolean isValidFormatDate(String date){
		if (!date.matches( REGEX_FORMAT_STRING_DD_MM_YYYY )) {
			return false;
		}
		return true;
	}
}
