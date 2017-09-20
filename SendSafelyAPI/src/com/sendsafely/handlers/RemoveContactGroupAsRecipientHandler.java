package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.RemoveContactGroupAsRecipientRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RemoveContactGroupAsRecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class RemoveContactGroupAsRecipientHandler extends BaseHandler{

	private RemoveContactGroupAsRecipientRequest request;

	public RemoveContactGroupAsRecipientHandler(UploadManager uploadManager, RemoveContactGroupAsRecipientRequest removeContactGroupAsRecipientRequest) {
		super(uploadManager);
		
		this.request = removeContactGroupAsRecipientRequest;
	}
 
	public boolean makeRequest() throws RemoveContactGroupAsRecipientFailedException {
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new RemoveContactGroupAsRecipientFailedException(response.getMessage());
		}
		return false;
	}
	
	protected BaseResponse send() throws RemoveContactGroupAsRecipientFailedException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (IOException | SendFailedException e) {
			throw new RemoveContactGroupAsRecipientFailedException(e);
		}
	}

}
