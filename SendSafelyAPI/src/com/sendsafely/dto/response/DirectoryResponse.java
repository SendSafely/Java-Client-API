package com.sendsafely.dto.response;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class DirectoryResponse extends BaseResponse
{	
	private String directoryId;
	private String name;
	private Date created;
	private Collection<DirectoryResponse> subDirectories = (Collection<DirectoryResponse>) new HashSet<DirectoryResponse>(0);
	
	public String getDirectoryId() {
		return directoryId;
	}
	
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	/*
	public UserDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public Collection<FileDTO> getFiles() {
		return files;
	}

	public void setFiles(Collection<FileDTO> files) {
		this.files = files;
	}*/

	public Collection<DirectoryResponse> getSubDirectories() {
		return subDirectories;
	}

	public void setSubDirectories(Collection<DirectoryResponse> subDirectories) {
		this.subDirectories = subDirectories;
	}

}