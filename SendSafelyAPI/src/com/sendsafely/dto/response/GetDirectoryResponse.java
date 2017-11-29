package com.sendsafely.dto.response;

import java.util.Collection;
import java.util.List;

import com.sendsafely.Directory;


public class GetDirectoryResponse extends BaseResponse {

	private Directory directory;
	private String directoryName;
	private String directoryId;
	private List<FileResponse> files;
	private Collection<DirectoryResponse> subDirectories;
	
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
	public Directory getDirectory() {
		return directory;
	}
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	public List<FileResponse> getFiles() {
		return files;
	}
	public void setFiles(List<FileResponse> files) {
		this.files = files;
	}
	public Collection<DirectoryResponse> getSubDirectories() {
		return subDirectories;
	}
	public void setSubDirectories(Collection<DirectoryResponse> subDirectories) {
		this.subDirectories = subDirectories;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	
}
