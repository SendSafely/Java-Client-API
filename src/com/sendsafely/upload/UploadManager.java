package com.sendsafely.upload;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.utils.Progress;

public interface UploadManager {

	public String send(String path, HTTPMethod method, String data) throws SendFailedException, IOException;
	public String sendFile(String path, File file, String filename, String data, Progress progress) throws SendFailedException, IOException;
	
}
