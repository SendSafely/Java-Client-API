package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.Recipient;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadKeycodeException;
import com.sendsafely.upload.UploadManager;

public class AddRecipientHandler extends BaseHandler 
{	
	
	private AddRecipientRequest request;
	private String keyCode;
	
	public AddRecipientHandler(UploadManager uploadManager, AddRecipientRequest request) {
		super(uploadManager);
		
		this.request = request;
	}
	
	public void setKeyCode(String keyCode) {
		if(keyCode == null || keyCode.isEmpty()) {
			throw new IllegalArgumentException("keyCode is required. Use addRecipient(packageId, email) to add a recipient without syncing Trusted Browser keys.");
		}
		
		this.keyCode = keyCode;
	}

	public Recipient addRecipient(String packageId) throws LimitExceededException, RecipientFailedException {
		request.setPackageId(packageId);
		AddRecipientResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			Recipient recipient = convert(response);
			syncRecipientKeycode(packageId, response.getRecipientId(), response.getCheckForPublicKeys());
			return recipient;
		}
		else if(response.getResponse() == APIResponse.LIMIT_EXCEEDED)
		{
			throw new LimitExceededException(response.getMessage());
		}
		else
		{
			syncExistingRecipientKeycode(packageId, response.getRecipientId());
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
	
	protected void syncRecipientKeycode(String packageId, String recipientId, Boolean checkForPublicKeys)
			throws RecipientFailedException
	{
		if(!KeycodeSyncHandler.shouldSyncRecipient(recipientId, checkForPublicKeys))
		{
			return;
		}
		
		uploadKeycode(packageId, recipientId);
	}
	
	protected void syncExistingRecipientKeycode(String packageId, String recipientId)
			throws RecipientFailedException
	{
		if(!KeycodeSyncHandler.shouldSyncExistingRecipient(recipientId))
		{
			return;
		}
		
		uploadKeycode(packageId, recipientId);
	}
	
	private void uploadKeycode(String packageId, String recipientId) throws RecipientFailedException
	{
		if(this.keyCode == null)
		{
			return;
		}
		
		try {
			new KeycodeSyncHandler(uploadManager).syncRecipientKeycode(packageId, recipientId, this.keyCode);
		} catch (UploadKeycodeException e) {
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
