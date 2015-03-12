package com.sendsafely.utils;

import com.sendsafely.upload.UploadManager;

public class SendSafelyConfig {

	private String host;
	private UploadManager uploadManager;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public UploadManager getUploadManager() {
		return uploadManager;
	}
	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}
	
	
	
	
}
