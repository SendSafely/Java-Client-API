package com.sendsafely;

import java.util.Date;

/**
 * A Java Bean containing information about a file. 
 * Only the Getters should be used from this object, since the server will populate the object. 
 * Updating the setters will not change any state on the server and should be avoided.
 *
 */
public class File {

	private String fileId;
	private String fileName;
	private long fileSize;
	//private String createdBy;
	private int parts;
	private Date fileUploaded;
	
	public File(String fileId, String fileName, long fileSize, int parts){
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.parts = parts;
	}
	
	public File() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @description Get the unique file ID associated with the file.
	 * @returnType String
	 * @return A string representing File Id.
	 */
	public String getFileId() {
		return fileId;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param fileId
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	/**
	 * @description Get the filename associated with the file.
	 * @returnType String
	 * @return A string representing File Name.
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @description Get the file size for the unencrypted file.
	 * @returnType long
	 * @return A long representing File Size.
	 */
	public long getFileSize() {
		return fileSize;
	}
	
	/**
	 * @description Set internally by the API.
	 * @param fileSize
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/*
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	/**
	 * @description Returns the number of parts this file is internally split up into when stored on the SendSafely servers.
	 * @return
	 * @deprecated
	 */
	public int getParts() {
		return parts;
	}

	/**
	 * @description Set internally by the API.
	 * @param parts
	 */
	public void setParts(int parts) {
		this.parts = parts;
	}

	/**
	 * @description Returns the Date object representing when the file was uploaded
	 * @returnType Date
	 * @return A Date representing when the file was upload
	 */
	public Date getFileUploaded() {
		return fileUploaded;
	}

	/**
	 * @description Set internally by the API.
	 * @param fileUploaded
	 */
	public void setFileUploaded(Date fileUploaded) {
		this.fileUploaded = fileUploaded;
	}

	
	
}
