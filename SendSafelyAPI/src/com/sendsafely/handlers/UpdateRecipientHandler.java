package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.UpdateRecipientRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.CountryCode;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UpdateRecipientFailedException;
import com.sendsafely.upload.UploadManager;

public class UpdateRecipientHandler extends BaseHandler 
{	
	
	private UpdateRecipientRequest request;
	
	public UpdateRecipientHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new UpdateRecipientRequest(uploadManager.getJsonManager());
	}

	public void makeRequest(String packageId, String recipientId, String role) throws UpdateRecipientFailedException {
		request.setRecipientRole(role);
		makeRequest(packageId, recipientId, null, null);
		
	}
	
	public void makeRequest(String packageId, String recipientId, String phonenumber, CountryCode countryCode) throws UpdateRecipientFailedException {
		request.setPackageId(packageId);
		request.setRecipientId(recipientId);
		request.addPhonenumber(phonenumber, countryCode);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new UpdateRecipientFailedException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws UpdateRecipientFailedException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new UpdateRecipientFailedException(e);
		} catch (IOException e) {
			throw new UpdateRecipientFailedException(e);
		}
	}
}
