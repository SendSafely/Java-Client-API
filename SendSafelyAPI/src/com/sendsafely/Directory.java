package com.sendsafely;

import java.util.Collection;
import java.util.List;

import com.sendsafely.dto.Subdirectory;


public class Directory {
	private Directory directory;
	private String directoryName;
	private String directoryId;
	private List<File> files;
	private Collection<Subdirectory> subDirectories;
	
	public Directory(String directoryId, String directoryName){
		this.directoryName = directoryName;
		this.directoryId = directoryId;
	}

	public String getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public Collection<Subdirectory> getSubDirectories() {
		return subDirectories;
	}

	public void setSubDirectories(Collection<Subdirectory> subDirectories) {
		this.subDirectories = subDirectories;
	}
	
	
	

}
