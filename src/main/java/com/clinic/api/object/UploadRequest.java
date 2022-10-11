package com.clinic.api.object;

import java.util.List;

public class UploadRequest {
	
	private String filename;
	private List < UploadData > data;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<UploadData> getData() {
		return data;
	}
	public void setData(List<UploadData> data) {
		this.data = data;
	}
	
}
