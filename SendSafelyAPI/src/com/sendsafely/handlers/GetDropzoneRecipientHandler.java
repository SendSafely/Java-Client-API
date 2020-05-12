package com.sendsafely.handlers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.sendsafely.dto.request.GetDropzoneRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.dto.response.GetDropzoneRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.DropzoneRecipientFailedException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetDropzoneRecipientHandler extends BaseHandler {

	private GetDropzoneRecipientRequest request;
	
	public GetDropzoneRecipientHandler(UploadManager uploadManager, GetDropzoneRecipientRequest request) {
		super(uploadManager);
		this.request = request;
	}
	
	public List<String> makeRequest() throws DropzoneRecipientFailedException{
		GetDropzoneRecipientResponse response = send();
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return response.getRecipientEmailAddresses();
		}
		else
		{
			throw new DropzoneRecipientFailedException(response.getMessage());
		}
	}
	
	protected GetDropzoneRecipientResponse send() throws DropzoneRecipientFailedException{
		try {
			return send(request, new GetDropzoneRecipientResponse());
		} catch (SendFailedException e) {
			throw new DropzoneRecipientFailedException(e);
		} catch (IOException e) {
			throw new DropzoneRecipientFailedException(e);
		}
	}
}
