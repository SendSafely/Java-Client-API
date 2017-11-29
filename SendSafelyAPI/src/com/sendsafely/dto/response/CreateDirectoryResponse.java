package com.sendsafely.dto.response;

public class CreateDirectoryResponse extends BaseResponse {
	private String directoryId;
	private String directoryName;
	
	public String getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}
	public String getDirectoryName() {
		return directoryName;
	}
	public void setContactGroupName(String directoryName) {
		this.directoryName = directoryName;
	}	
}
