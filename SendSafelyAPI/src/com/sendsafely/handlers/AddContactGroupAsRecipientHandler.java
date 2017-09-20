package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.AddContactGroupAsRecipientRequest;
import com.sendsafely.dto.response.AddContactGroupAsRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.ContactGroupException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class AddContactGroupAsRecipientHandler extends BaseHandler {

	private AddContactGroupAsRecipientRequest request;

	public AddContactGroupAsRecipientHandler(UploadManager uploadManager, AddContactGroupAsRecipientRequest addContactGroupAsRecipientRequest) {
		super(uploadManager);
		
		this.request = addContactGroupAsRecipientRequest;
	}

	public boolean makeRequest() throws ContactGroupException {
		AddContactGroupAsRecipientResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new ContactGroupException(response.getMessage());
		}
		return true;
	}
	
	protected AddContactGroupAsRecipientResponse send() throws ContactGroupException 
	{
		try {
			return send(request, new AddContactGroupAsRecipientResponse());
		} catch (IOException | SendFailedException e) {
			throw new ContactGroupException(e);
		}
	}
}
