package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.Recipient;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class AddRecipientHandler extends BaseHandler 
{	
	
	private AddRecipientRequest request;
	
	public AddRecipientHandler(UploadManager uploadManager, AddRecipientRequest request) {
		super(uploadManager);
		
		this.request = request;
	}

	public Recipient addRecipient(String packageId) throws LimitExceededException, RecipientFailedException {
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
			throw new RecipientFailedException(response.getMessage());
		}
	}
	
	protected AddRecipientResponse send() throws RecipientFailedException 
	{
		try {
			return send(request, new AddRecipientResponse());
		} catch (SendFailedException e) {
			throw new RecipientFailedException(e);
		} catch (IOException e) {
			throw new RecipientFailedException(e);
		}
	}
	
	protected Recipient convert(AddRecipientResponse obj)
	{
		Recipient recipient = new Recipient();
		recipient.setEmail(obj.getEmail());
		recipient.setNeedsApproval(obj.getApprovalRequired());
		recipient.setRecipientId(obj.getRecipientId());
		recipient.setPhonenumbers(obj.getPhonenumbers());
		recipient.setApprovers(obj.getApprovers());
		
		return recipient;
	}
	
}
