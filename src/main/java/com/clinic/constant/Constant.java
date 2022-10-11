package com.clinic.constant;

public class Constant {
	
	/*APP CONFIG*/
	public static final String APP_NAME = "BiyubiClinicBackend";

	public static final String INSTANCE_NAME = "UserService";
	
    public static final String DEFAULT_PASSWORD = "Password*1";
    
    public static final String CHANNEL_WEB = "WEB";
	
    public static final String CHANNEL_MOBILE = "MOBILE";

	/* ACTIVITY */
	public static final String ACTIVITY_ADMIN_LOGIN = "ADMIN_LOGIN";
	
	public static final String ACTIVITY_ADMIN_LOGOUT = "ADMIN_LOGOUT";
		
	public static final String ACTIVITY_USER_REGISTRATION = "REGISTRASI PENGGUNA BARU";

	public static final String ACTIVITY_USER_UPDATE = "UPDATE DATA PENGGUNA";

	public static final String ACTIVITY_USER_STATUS_UPDATE = "UPDATE STATUS PENGGUNA";

	public static final String ACTIVITY_CHILD_REGISTRATION = "REGISTRASI DATA ANAK";
	
	public static final String ACTIVITY_CHILD_UPDATE = "UPDATE DATA ANAK";

	public static final String ACTIVITY_ADD_MST_VACCINE = "TAMBAH DATA MASTER IMUNISASI";
	
	public static final String ACTIVITY_UPDATE_MST_VACCINE = "UPDATE DATA MASTER IMUNISASI";

	public static final String ACTIVITY_CHANGE_STATUS_MST_VACCINE = "GANTI STATUS AKTIVASI DATA MASTER IMUNISASI";
	
	public static final String ACTIVITY_ADD_MST_CHECK_UP = "TAMBAH DATA MASTER JEJAK MEDIS";
	
	public static final String ACTIVITY_UPDATE_MST_CHECK_UP = "UPDATE DATA MASTER JEJAK MEDIS";
	
	public static final String ACTIVITY_CHANGE_STATUS_MST_CHECK_UP = "GANTI STATUS AKTIVASI DATA MASTER Jejak Medis";

	public static final String ACTIVITY_ADD_VACCINE_RECORD = "TAMBAH DATA JEJAK IMUNISASI";

	public static final String ACTIVITY_UPDATE_VACCINE_RECORD = "UPDATE DATA JEJAK IMUNISASI";

	public static final String ACTIVITY_ADD_CHECK_UP_RECORD = "TAMBAH DATA JEJAK MEDIS";
	
	public static final String ACTIVITY_UPDATE_CHECK_UP_RECORD = "UPDATE DATA JEJAK MEDIS";

	/* TYPE */
	
	public static final String WEIGHT = "WEIGHT";
	
	public static final String LENGTH = "LENGTH"; 
	
	public static final String HEAD_CIRCUMFERENCE = "HEAD CIRCUMFERENCE";
	
	/* CATEGORY */

	public static final String NORMAL = "NORMAL";

	public static final String VERY_UNDERWEIGHT = "VERY UNDERWEIGHT";

	public static final String UNDERWEIGHT = "UNDERWEIGHT";

	public static final String OVERWEIGHT = "OVERWEIGHT";

	public static final String VERY_OVERWEIGHT = "VERY OVERWEIGHT";
	
	public static final String VERY_UNDERLENGTH = "VERY UNDERLENGTH";

	public static final String UNDERLENGTH = "UNDERLENGTH";

	public static final String OVERLENGTH = "OVERLENGTH";

	public static final String VERY_OVERLENGTH = "VERY OVERLENGTH";

	public static final String VERY_MIKROSEFALI = "VERY MIKROSEFALI";

	public static final String MIKROSEFALI = "MIKROSEFALI";

	public static final String MAKROSEFALI = "MAKROSEFALI";

	public static final String VERY_MAKROSEFALI = "VERY MAKROSEFALI";

	/* VALUE LOGS */
	
	public static final String VALUE_INSERT_USER = "Nama : <fullname> | Tanggal Bergabung : <joinDate> ";
	
	public static final String VALUE_UPDATE_USER = "Nama : <fullname> | Email : <email> | No Telepon : <phone_no> | Alamat : <address>";

	public static final String VALUE_CHANGE_STATUS_USER = "Nama : <fullname> | Status : <status> ";

	public static final String VALUE_INSERT_CHILD = "Nama Anak : <childName> | Jenis Kelamin : <gender> | Tanggal Lahir : <birthDate> | Catatan : <notes>";

	public static final String VALUE_UPDATE_CHILD = "Nama Anak : <childName> | Jenis Kelamin : <gender> | Tanggal Lahir : <birthDate> ";

	public static final String VALUE_RECORD_CHECK_UP = "Nama Anak : <childName> | Berat : <weight> | Panjang : <length> | Lingkar Kepala : <headDiameter>";
  
	public static final String VALUE_RECORD_VACCINE = "Nama Anak : <childName> | Nama Imunisasi : <vaccineName> | Bulan Ke : <batch> | Catatan : <notes> ";

	public static final String VALUE_INSERT_MST_VACCINE = "Nama Imunisasi : <vaccineName> | Jenis Imunisasi : <vaccineType> | Hari Kadaluarsa : <expDays> | Bulan Ke : <batch> ";

	public static final String VALUE_UPDATE_MST_VACCINE = "Nama Imunisasi : <vaccineName> | Jenis Imunisasi : <vaccineType> | Hari Kadaluarsa : <expDays> | Catatan : <notes> ";

	public static final String VALUE_CHANGE_STATUS_MST_VACCINE = "Nama Imunisasi : <vaccineName> | Status : <status> ";

	public static final String VALUE_INSERT_CHECKUP = "Nama Kegiatan : <actName> | Bulan Ke : <batch> | Hari Selanjutnya : <nextDays> ";

	public static final String VALUE_UPDATE_CHECKUP = "Nama Kegiatan : <actName> | Bulan Ke : <batch> | Hari Selanjutnya : <nextDays> ";

	public static final String VALUE_CHANGE_STATUS_CHECKUP = "Nama Kegiatan : <actName> | Status : <status> ";

	/*MENU TYPE*/
	public static final String USER_REGISTARTION = "user-registration";
	
	public static final String CHILD_REGISTARTION = "child-registration";

	public static final String VACCINE_RECORD = "vaccine-record";

	public static final String CHECKUP_RECORD = "checkup-record";

	public static final String INFO_USER = "info-user";

	public static final String INFO_ALL_USER = "info-all-user";

	public static final String INFO_SIMPLE_USER = "info-all-simple-user";

	public static final String USER_UPDATE = "user-update";

	public static final String USER_CHANGE_STATUS = "user-change-status";

	public static final String CHILD_UPDATE = "user-update";

	public static final String SCHEDULE_CHECKUP = "schedule-checkup";

	public static final String SCHEDULE_VACCINE = "schedule-vaccine";

	public static final String INFO_LIST_USER = "list-user";

	public static final String INFO_LOGS = "list-logs";
	
	public static final String SAVE_VACCINE_RECORD = "save-vaccine-record";

	public static final String UPDATE_VACCINE_RECORD = "update-vaccine-record";

	public static final String SAVE_CHECKUP_RECORD = "save-checkup-record";

	public static final String UPDATE_CHECKUP_RECORD = "update-checkup-record";
	
	public static final String INFO_LIST_VACCINE = "info-list-vaccine";

	public static final String SAVE_VACCINE = "save-vaccine";

	public static final String UPDATE_VACCINE = "update-vaccine";

	public static final String CHANGE_STATUS_VACCINE = "change-status-vaccine";

	public static final String INFO_LIST_CHECKUP = "info-list-checkup";
	
	public static final String SAVE_CHECKUP = "save-checkup";

	public static final String UPDATE_CHECKUP = "update-checkup";

	public static final String CHANGE_STATUS_CHECKUP = "change-status-checkup";

	/*REGEX*/
    public static final String REGEX_FORMAT_STRING_DD_MM_YYYY = "([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$";

    
}
