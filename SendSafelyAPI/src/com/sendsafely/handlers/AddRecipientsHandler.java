package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sendsafely.Recipient;
import com.sendsafely.dto.request.AddRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.dto.response.AddRecipientsResponse;
import com.sendsafely.dto.response.RecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UploadKeycodeException;
import com.sendsafely.upload.UploadManager;

public class AddRecipientsHandler extends BaseHandler 
{	
	
	private AddRecipientRequest request;
	private String keyCode;
	
	public AddRecipientsHandler(UploadManager uploadManager, AddRecipientRequest request) {
		super(uploadManager);
		
		this.request = request;
	}
	
	public void setKeyCode(String keyCode) {
		if(keyCode == null || keyCode.isEmpty()) {
			throw new IllegalArgumentException("keyCode is required. Use addRecipients(packageId, emails) to add recipients without syncing Trusted Browser keys.");
		}
		
		this.keyCode = keyCode;
	}

	public List<Recipient> makeRequest(String packageId) throws LimitExceededException, RecipientFailedException {
		request.setPackageId(packageId);
		AddRecipientsResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			syncRecipientKeycodes(packageId, response);
			
			List<Recipient> recipients = convert(response);
			if(recipients.size() == 0) {
				throw new RecipientFailedException("The Recipients could not be added");
			}
			return recipients;
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
	
	protected AddRecipientsResponse send() throws RecipientFailedException 
	{
		try {
			return send(request, new AddRecipientsResponse());
		} catch (SendFailedException e) {
			throw new RecipientFailedException(e);
		} catch (IOException e) {
			throw new RecipientFailedException(e);
		}
	}
	
	protected void syncRecipientKeycodes(String packageId, AddRecipientsResponse obj) throws RecipientFailedException
	{
		if(this.keyCode == null) {
			return;
		}
		
		KeycodeSyncHandler keycodeSync = new KeycodeSyncHandler(uploadManager);
		List<String> failed = new ArrayList<String>();
		List<UploadKeycodeException> causes = new ArrayList<UploadKeycodeException>();
		
		for(RecipientResponse recipient : obj.getRecipients()) {
			if(!shouldSync(recipient)) {
				continue;
			}
			
			try {
				keycodeSync.syncRecipientKeycode(packageId, recipient.getRecipientId(), this.keyCode);
			} catch (UploadKeycodeException e) {
				failed.add(describe(recipient));
				causes.add(e);
			}
		}
		
		if(!failed.isEmpty()) {
			RecipientFailedException failure = new RecipientFailedException(
					"Trusted Browser keycode sync failed for: " + String.join(", ", failed), causes.get(0));
			for(int i = 1; i < causes.size(); i++) {
				failure.addSuppressed(causes.get(i));
			}
			throw failure;
		}
	}
	
	private static boolean shouldSync(RecipientResponse recipient)
	{
		return recipient.getResponse() == APIResponse.SUCCESS
				? KeycodeSyncHandler.shouldSyncRecipient(recipient.getRecipientId(), recipient.getCheckForPublicKeys())
				: KeycodeSyncHandler.shouldSyncExistingRecipient(recipient.getRecipientId());
	}
	
	private String describe(RecipientResponse recipient)
	{
		String email = recipient.getEmail();
		
		return (email == null || email.isEmpty()) ? recipient.getRecipientId() : email;
	}
	
	protected List<Recipient> convert(AddRecipientsResponse obj)
	{
		List<Recipient> recipients = new ArrayList<Recipient>(obj.getRecipients().size());
		for(RecipientResponse recipient : obj.getRecipients()) {
			if(recipient.getResponse() == APIResponse.SUCCESS) {
				recipients.add(convert(recipient));
			}
		}
		
		return recipients;
	}
	
	protected Recipient convert(RecipientResponse rawRecipient) 
	{
		Recipient recipient = new Recipient();
		recipient.setEmail(rawRecipient.getEmail());
		recipient.setRecipientId(rawRecipient.getRecipientId());
		recipient.setNeedsApproval(rawRecipient.getNeedsApproval());
		
		return recipient;
	}
	
}
