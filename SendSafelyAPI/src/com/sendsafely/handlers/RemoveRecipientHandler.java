package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.RemoveRecipientRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class RemoveRecipientHandler extends BaseHandler 
{	
	
	private RemoveRecipientRequest request;
	
	public RemoveRecipientHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new RemoveRecipientRequest(uploadManager.getJsonManager());
	}

	public void makeRequest(String packageId, String recipientId) throws RecipientFailedException {
		
		request.setPackageId(packageId);
		request.setRecipientId(recipientId);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new RecipientFailedException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws RecipientFailedException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new RecipientFailedException(e);
		} catch (IOException e) {
			throw new RecipientFailedException(e);
		}
	}
	
}
