package com.sendsafely.dto.response;

import com.sendsafely.dto.FileInfo;

public class FileInfoResponse extends BaseResponse{

	private FileInfo file;

	public FileInfo getFile() {
		return file;
	}

	public void setFile(FileInfo file) {
		this.file = file;
	}
	
	
}
