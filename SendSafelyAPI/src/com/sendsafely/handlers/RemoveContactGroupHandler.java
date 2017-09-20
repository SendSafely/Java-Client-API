package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.RemoveContactGroupRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RemoveContactGroupFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class RemoveContactGroupHandler  extends BaseHandler{

	private RemoveContactGroupRequest request;

	public RemoveContactGroupHandler(UploadManager uploadManager, RemoveContactGroupRequest removeContactGroupRequest) {
		super(uploadManager);
		
		this.request = removeContactGroupRequest;
	}

	public boolean makeRequest() throws RemoveContactGroupFailedException {
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new RemoveContactGroupFailedException(response.getMessage());
		}
		return true;
	}
	
	protected BaseResponse send() throws RemoveContactGroupFailedException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (IOException | SendFailedException e) {
			throw new RemoveContactGroupFailedException(e);
		}
	}
}
