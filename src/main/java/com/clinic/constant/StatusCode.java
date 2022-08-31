package com.clinic.constant;

public enum StatusCode {
	SUCCESS("00"),GENERIC_ERROR("99"),UNKNOWN_ERROR("98"),BUSINESS_ERROR("97"),INVALID("80");
	
	private StatusCode(String code) {
		this.code=code;
	}
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	
}
