package com.sendsafely.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sendsafely.dto.request.BaseRequest;
import com.sendsafely.dto.request.UploadFileRequest;
import com.sendsafely.dto.response.DownloadFileResponse;
import com.sendsafely.dto.response.ResponseFactory;
import com.sendsafely.dto.response.UploadFileResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.file.FileManager;
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
	
	public UploadFileResponse sendFile(String path, UploadFileRequest request, FileManager file, String filename, Progress progress) throws SendFailedException, IOException
	{
		String data = request.getPostBody();
	
		String response = connection.sendFile(path, file, filename, data, progress);
		return ResponseFactory.getInstanceFromString(response, new UploadFileResponse(), connection.getJsonManager());
	}
	
	
	protected <T> T send(String path, HTTPMethod method, String data, T clazz) throws IOException, SendFailedException
	{
		connection.send(path, method, data);
		return handleResponse(clazz);
	}
	
	protected <T> T handleResponse(T clazz) throws IOException, SendFailedException
	{
		if(connection.getContentType() != null && (connection.getContentType().equals("application/octet-stream")|| connection.getContentType().equals("binary/octet-stream"))) {
			return handleFileDownload(clazz);
		} else {
			String response = connection.getResponse();

			try{
				if("".equals(response)){
					response = null;
					throw new SendFailedException("Empty Response");
				}
				return ResponseFactory.getInstanceFromString(response, clazz, connection.getJsonManager());
			}catch(Exception e){
				HashMap<String,String> hashMap = new HashMap<String,String>();
				hashMap.put("server", connection.getServer());
				hashMap.put("date", connection.getDate());
				int responseCode = connection.getResponseCode();
				hashMap.put("responseCode", Integer.toString(responseCode));
				hashMap.put("responseMessage", connection.getResponseMessage());
				hashMap.put("responseBody", response);
				Gson gson = new GsonBuilder().serializeNulls().create();
				String jsonString = gson.toJson(hashMap);
				throw new SendFailedException(jsonString);
			}
		}
	}
	
	protected <T> T handleFileDownload(T clazz) throws SendFailedException
	{
		InputStream stream = connection.getStream();
		
		if(!(clazz instanceof DownloadFileResponse))
		{
			throw new SendFailedException("File Download Responses must inherit from DownloadFileResponse");
		}
		
		DownloadFileResponse response = (DownloadFileResponse)clazz;
		response.setResponse(APIResponse.SUCCESS);
		response.setFileStream(stream);
		
		return clazz;
	}
	
}
