package com.clinic.api.response;

import com.clinic.api.object.HeaderResponse;

public class APIResponse<T> {
	
	private HeaderResponse header;
	private T payload;
	
	public HeaderResponse getHeader() {
		return header;
	}
	public void setHeader(HeaderResponse header) {
		this.header = header;
	}
	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "APIRequest [header=" + header + ", payload=" + payload + "]";
	}


}
