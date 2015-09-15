package com.sendsafely.dto.response;

import java.io.InputStream;

public class DownloadFileResponse extends BaseResponse {

	private InputStream fileStream;

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
	
	
	
}
