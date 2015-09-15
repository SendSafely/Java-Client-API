package com.sendsafely.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.utils.Progress;

public interface UploadManager {

	public void send(String path, HTTPMethod method, String data) throws SendFailedException, IOException;
	public String sendFile(String path, File file, String filename, String data, Progress progress) throws SendFailedException, IOException;
	public String getContentType();
	public String getResponse() throws IOException;
	public InputStream getStream();
	
}
