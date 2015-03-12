package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.Recipient;
import com.sendsafely.dto.request.UpdateRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.CountryCode;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UpdateRecipientFailedException;
import com.sendsafely.upload.UploadManager;

public class UpdateRecipientHandler extends BaseHandler 
{	
	
	private UpdateRecipientRequest request = new UpdateRecipientRequest();
	
	public UpdateRecipientHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public void makeRequest(String packageId, String recipientId, String phonenumber, CountryCode countryCode) throws UpdateRecipientFailedException {
		request.setPackageId(packageId);
		request.setRecipientId(recipientId);
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
	
	protected Recipient convert(AddRecipientResponse obj)
	{
		Recipient recipient = new Recipient();
		recipient.setEmail(obj.getEmail());
		recipient.setNeedsApproval(obj.getNeedsApproval());
		recipient.setRecipientId(obj.getRecipientId());
		return recipient;
	}
	
}
