package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.AddDropzoneRecipientRequest;
import com.sendsafely.dto.response.AddDropzoneRecipientResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DropzoneRecipientFailedException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class AddDropzoneRecipientHandler extends BaseHandler {

	private AddDropzoneRecipientRequest request;
	
	public AddDropzoneRecipientHandler(UploadManager uploadManager, AddDropzoneRecipientRequest request) {
		super(uploadManager);
		this.request = request;
	}
	
	public void addDropzoneRecipient(String email) throws DropzoneRecipientFailedException{
		AddDropzoneRecipientResponse response = send();
		
		if(((response.getResponse() != APIResponse.SUCCESS))){
			throw new DropzoneRecipientFailedException(response.getMessage());
		}
	}
	
	protected AddDropzoneRecipientResponse send() throws DropzoneRecipientFailedException{
		try{
			return send(request, new AddDropzoneRecipientResponse());
		} catch (SendFailedException e){
			throw new DropzoneRecipientFailedException(e);
		} catch (IOException e){
			throw new DropzoneRecipientFailedException(e);
		}
	}

}
