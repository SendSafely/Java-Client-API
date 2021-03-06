package com.sendsafely.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class FileInfo {
	private String fileId;
	private String fileName;
	private String fileSize;
	private String createdByEmail;
	private String createdById;
	private Date uploaded;
	private String uploadedStr;
	private List<FileInfo> oldVersions;
	private Integer fileVersion;
	private int fileParts;
	
	/**
	 * @returnType String
	 * @return
	 */
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	/**
	 * @returnType String
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @returnType String
	 * @return
	 */
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	/**
	 * @returnType String
	 * @return
	 */
	public String getCreatedByEmail() {
		return createdByEmail;
	}
	public void setCreatedByEmail(String createdByEmail) {
		this.createdByEmail = createdByEmail;
	}
	
	/**
	 * @returnType List<FileInfo>
	 * @return
	 */
	public List<FileInfo> getOldVersions() {
		return oldVersions;
	}
	public void setOldVersions(List<FileInfo> versions) {
		this.oldVersions = versions;
	}
	
	/**
	 * @returnType String
	 * @return
	 */
	public String getCreatedById() {
		return createdById;
	}
	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	
	/**
	 * @returnType Date
	 * @return
	 */
	public Date getUploaded() {
		return uploaded;
	}
	public void setUploaded(Date uploaded) {
		this.uploaded = uploaded;
	}
		
	/**
	 * @returnType List<FileInfo>
	 * @param files
	 * @param timeZone
	 * @return
	 */
	private List<FileInfo> createFileVersions(Collection<FileInfo> files, String timeZone)
	{
		List<FileInfo> versions = new ArrayList<FileInfo>();
		//files = sortFiles(new ArrayList(files));
		files = (new ArrayList(files));
		int index = 0;
		for(FileInfo f : files) {
			index++;
			FileInfo dto = new FileInfo();
			//TODO: Create the file versions here. Set setters
			
			dto.setFileVersion(index);
			versions.add(dto);
		}
		
		return versions;
	}

	/**
	 * @returnType int
	 * @return
	 */
	public int getFileParts() {
		return fileParts;
	}

	public void setFileParts(int fileParts) {
		this.fileParts = fileParts;
	}

	/**
	 * @returnType String
	 * @return
	 */
	public String getUploadedStr() {
		return uploadedStr;
	}

	public void setUploadedStr(String uploadedStr) {
		this.uploadedStr = uploadedStr;
	}
	
	/**
	 * @returnType Integer
	 * @return
	 */
	public Integer getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(Integer fileVersion) {
		this.fileVersion = fileVersion;
	}
	
}
