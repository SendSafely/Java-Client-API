package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.Recipient;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.AddRecipientFailedException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class AddRecipientHandler extends BaseHandler 
{	
	
	private AddRecipientRequest request = new AddRecipientRequest();
	
	public AddRecipientHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public Recipient makeRequest(String packageId, String email) throws LimitExceededException, AddRecipientFailedException {
		request.setEmail(email);
		request.setPackageId(packageId);
		AddRecipientResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return convert(response);
		}
		else if(response.getResponse() == APIResponse.LIMIT_EXCEEDED)
		{
			throw new LimitExceededException(response.getMessage());
		}
		else
		{
			throw new AddRecipientFailedException(response.getMessage());
		}
	}
	
	protected AddRecipientResponse send() throws AddRecipientFailedException 
	{
		try {
			return send(request, new AddRecipientResponse());
		} catch (SendFailedException e) {
			throw new AddRecipientFailedException(e);
		} catch (IOException e) {
			throw new AddRecipientFailedException(e);
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
