package com.sendsafely.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.sendsafely.dto.request.BaseRequest;
import com.sendsafely.dto.request.UploadFileRequest;
import com.sendsafely.dto.response.ResponseFactory;
import com.sendsafely.dto.response.UploadFileResponse;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class SendUtil {

	private UploadManager connection;
	
	public SendUtil(UploadManager uploadManager)
	{
		this.connection = uploadManager;
	}
	
	public <T> T send(String path, T returnObject, BaseRequest request) throws SendFailedException, IOException 
	{
		String data = (request.hasPostBody()) ? request.getPostBody() : "";
		
		return send(path, request.getMethod(), data, returnObject);
	}
	
	public UploadFileResponse sendFile(String path, UploadFileRequest request, File file, String filename, Progress progress) throws SendFailedException, IOException
	{
		String data = request.getPostBody();
	
		String response = connection.sendFile(path, file, filename, data, progress);
		return ResponseFactory.getInstanceFromString(response, new UploadFileResponse());
	}
	
	protected <T> T send(String path, HTTPMethod method, String data, T clazz) throws IOException, SendFailedException
	{
		String response = connection.send(path, method, data);
		return ResponseFactory.getInstanceFromString(response, clazz);
	}
	
}
