package com.sendsafely.utils;

import java.io.IOException;

import com.sendsafely.dto.request.AddMessageRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class MessageUtility 
{
	private final String UPLOAD_TYPE = "JAVA_API";
	
	private UploadManager uploadManager;
	
	public MessageUtility(UploadManager uploadManager)
	{
		this.uploadManager = uploadManager;
	}
	
	public void encryptAndUploadMessage(String message, String encryptionKey, AddMessageRequest request) throws SendFailedException, IOException, MessageException
	{
		CryptoUtil cUtil = new CryptoUtil();
		String encryptedMessage = cUtil.encrypt(message, encryptionKey);
		BaseResponse response = upload(encryptedMessage, request);
	
		if(response.getResponse() != APIResponse.SUCCESS) {
			throw new MessageException();
		}
	}
	
	protected AddMessageRequest populateRequest(AddMessageRequest request, String encryptedMessage)
	{
		request.setUploadType(UPLOAD_TYPE);
		request.setMessage(encryptedMessage);
		return request;
	}
	
	protected BaseResponse upload(String encryptedMessage, AddMessageRequest request) throws SendFailedException, IOException
	{
		BaseResponse returnObject = new BaseResponse();
		request = populateRequest(request, encryptedMessage);
		SendUtil util = new SendUtil(uploadManager);
		return util.send(request.getPath(), returnObject, request);
	}
}
