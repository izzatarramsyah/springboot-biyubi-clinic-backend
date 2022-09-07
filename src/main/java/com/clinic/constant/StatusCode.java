package com.clinic.constant;

public enum StatusCode {
	SUCCESS("00","Success"),
	USER_NOT_FOUND("01","Data not found"),
	USER_NOT_VALID("02","User is not active"),
	GENERIC_ERROR("99","The system has encountered an error. Please contact your administrator");
	
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
