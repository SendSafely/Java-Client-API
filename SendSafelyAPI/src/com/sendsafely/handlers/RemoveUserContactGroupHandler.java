package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.RemoveUserFromContactGroupRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RemoveEmailContactGroupFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class RemoveUserContactGroupHandler extends BaseHandler{

	private RemoveUserFromContactGroupRequest request;

	public RemoveUserContactGroupHandler(UploadManager uploadManager, RemoveUserFromContactGroupRequest removeUserFromContactGroupRequest) {
		super(uploadManager);
		
		this.request = removeUserFromContactGroupRequest;
	}

	public boolean makeRequest() throws RemoveEmailContactGroupFailedException {
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new RemoveEmailContactGroupFailedException(response.getMessage());
		}
		return true;
	}
	
	protected BaseResponse send() throws RemoveEmailContactGroupFailedException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (IOException | SendFailedException e) {
			throw new RemoveEmailContactGroupFailedException(e);
		}
	}
}
