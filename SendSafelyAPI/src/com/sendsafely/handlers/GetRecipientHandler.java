package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.Recipient;
import com.sendsafely.dto.request.GetRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetRecipientHandler extends BaseHandler 
{	
	
	private GetRecipientRequest request;
	
	public GetRecipientHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new GetRecipientRequest(uploadManager.getJsonManager());
	}

	public Recipient makeRequest(String packageId, String recipientId) throws RecipientFailedException {
		
		request.setPackageId(packageId);
		request.setRecipientId(recipientId);
		AddRecipientResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return convert(response);
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
		//recipient.setCanAddFiles(obj.getCanAddFiles());
		//recipient.setCanAddMessages(obj.getCanAddMessages());
		//recipient.setCanAddRecipients(obj.getCanAddRecipients());
		return recipient;
	}
	
}
