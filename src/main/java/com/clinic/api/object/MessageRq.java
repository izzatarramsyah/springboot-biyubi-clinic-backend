package com.clinic.api.object;

public class MessageRq {

	private String number;
	private String message;
	
	public MessageRq(String number, String message){
		this.number = number + "@c.us";
		this.message = message;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
