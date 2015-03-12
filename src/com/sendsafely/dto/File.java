package com.sendsafely.dto;

/**
 * A Java Bean containing information about a file. 
 * Only the Getters should be used from this object, since the server will populate the object. 
 * Updating the setters will not change any state on the server and should be avoided.
 * @author Erik Larsson
 *
 */
public class File {

	private String fileId;
	private String fileName;
	private long fileSize;
	
	/**
	 * @description Get the unique file ID associated with the file.
	 * @return
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
	 * @return
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
	 * @return
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
	
}
