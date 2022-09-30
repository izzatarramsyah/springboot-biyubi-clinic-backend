package com.clinic.constant;

public enum StatusCode {
	
	SUCCESS_PROCESS("00","Data berhasil diproses"),
	SUCCESS("00","Success"),
	DATA_NOT_FOUND("01","Data tidak ditemukan"),
	FAILED_PROCESS("02","Data gagal diproses. Silahkan hubungi administrator anda"),
	ALREDY_REGISTERD("03","Data gagal diproses. Data sudah terdaftar"),
	USER_ADMIN_NOT_FOUND("04","User Admin tidak ditemukan"),
	INVALID_SESSION("05","Invalid Session"),
	INVALID("06","Invalid"),
	VALID_SESSION("07","Kami mendeteksi Anda masih aktif di browser lain, Silakan logout dulu dan coba lagi"),
	RECORD_ALREADY_INSERTED("08","Data telah di tambahkan sebelumnya. Silahkan cek di menu jadwal"),
	USER_NOT_VALID("09","User is not active"),
	USER_NOT_FOUND("10","User tidak ditemukan"),
	CHILD_NOT_FOUND("11","Data anak tidak ditemukan"),
	GENERIC_ERROR("99","Sistem mengalami kesalahan. Silahkan hubungi administrator anda");
	
	private String code;
	private String statusDesc;
	
	private StatusCode(String code, String statusDesc) {
		this.code = code;
		this.statusDesc = statusDesc;
	}
	
	public String getCode() {
		return code;
	}
	void setCode(String code) {
		this.code = code;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}	
}
