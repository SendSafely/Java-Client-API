package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.RemoveDropzoneRecipientRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DropzoneRecipientFailedException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class RemoveDropzoneRecipientHandler extends BaseHandler {

	private RemoveDropzoneRecipientRequest request;
	 
	public RemoveDropzoneRecipientHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new RemoveDropzoneRecipientRequest(uploadManager.getJsonManager());
	}
	
	public void makeRequest(String email) throws DropzoneRecipientFailedException{
		request.setEmail(email);
		BaseResponse response = send();
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new DropzoneRecipientFailedException(response.getMessage());
		}
	}

	protected BaseResponse send() throws DropzoneRecipientFailedException{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new DropzoneRecipientFailedException(e);
		} catch (IOException e) {
			throw new DropzoneRecipientFailedException(e);
		}
	}

}
