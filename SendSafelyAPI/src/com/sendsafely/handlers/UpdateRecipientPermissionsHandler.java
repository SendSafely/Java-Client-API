package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.Recipient;
import com.sendsafely.dto.request.UpdateRecipientPermissionsRequest;
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

public class UpdateRecipientPermissionsHandler extends BaseHandler 
{	
	
	private UpdateRecipientPermissionsRequest request;
	
	public UpdateRecipientPermissionsHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new UpdateRecipientPermissionsRequest(uploadManager.getJsonManager());
	}

	public Recipient makeRequest(String packageId, String recipientId, boolean canAddMessages, boolean canAddFiles, boolean canAddRecipients) throws UpdateRecipientFailedException {
		request.setPackageId(packageId);
		request.setRecipientId(recipientId);
		request.setCanAddFile(canAddFiles);
		request.setCanAddMessage(canAddMessages);
		request.setCanAddRecipient(canAddRecipients);
		
		AddRecipientResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new UpdateRecipientFailedException(response.getMessage());
		}
		
		return convert(response);
	}
	
	protected AddRecipientResponse send() throws UpdateRecipientFailedException
	{
		try {
			return send(request, new AddRecipientResponse());
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
		recipient.setNeedsApproval(obj.getApprovalRequired());
		recipient.setRecipientId(obj.getRecipientId());
		return recipient;
	}
	
}
