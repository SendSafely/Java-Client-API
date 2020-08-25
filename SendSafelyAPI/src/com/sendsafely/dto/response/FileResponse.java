package com.sendsafely.dto.response;

import java.util.Date;

public class FileResponse {

	private String fileId;
	private String fileName;
	private long fileSize;
	private String createdByEmail;
	private int parts;
	private Date fileUploaded;
	private String fileUploadedStr;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getCreatedByEmail() {
		return createdByEmail;
	}
	public void setCreatedByEmail(String createdByEmail) {
		this.createdByEmail = createdByEmail;
	}
	public int getParts() {
		return parts;
	}
	public void setParts(int parts) {
		this.parts = parts;
	}
	public Date getFileUploaded() {
		return fileUploaded;
	}
	public String getFileUploadedStr() {
		return fileUploadedStr;
	}
}
