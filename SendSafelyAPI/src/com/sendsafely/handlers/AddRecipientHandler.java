package com.sendsafely.handlers;

import java.io.IOException;
import java.util.List;

import com.sendsafely.Recipient;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
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
		recipient.setNeedsApproval(obj.getNeedsApproval());
		recipient.setRecipientId(obj.getRecipientId());
		//recipient.setCanAddFiles(obj.getCanAddFiles());
		//recipient.setCanAddMessages(obj.getCanAddMessages());
		//recipient.setCanAddRecipients(obj.getCanAddRecipients());
		return recipient;
	}
	
}
