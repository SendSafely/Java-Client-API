package com.sendsafely.dto.response;

import java.util.Date;

public class ConfirmationResponse {

	private String ipAddress;
	private Date timestamp;
	private FileResponse file;
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public FileResponse getFile() {
		return file;
	}
	
	public void setFile(FileResponse file) {
		this.file = file;
	}
	
	
	
}
