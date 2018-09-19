package com.sendsafely.handlers;

import java.io.IOException;
import java.util.List;

import com.sendsafely.dto.RecipientHistory;
import com.sendsafely.dto.request.GetRecipientHistoryRequest;
import com.sendsafely.dto.response.RecipientHistoryResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetRecipientHistoryHandler extends BaseHandler {

	private GetRecipientHistoryRequest request;
	
	protected GetRecipientHistoryHandler(UploadManager uploadManager){
		super(uploadManager);
		request = new GetRecipientHistoryRequest(uploadManager.getJsonManager());
	}
	
	public List<RecipientHistory> makeRequest(String recipientEmail) throws RecipientFailedException{
		request.setRecipientEmail(recipientEmail);
		RecipientHistoryResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return response.getPackages();
		}
		else
		{
			throw new RecipientFailedException(response.getMessage());
		}
	}
	
	protected RecipientHistoryResponse send() throws RecipientFailedException 
	{
		try {
			return send(request, new RecipientHistoryResponse());
		} catch (SendFailedException e) {
			throw new RecipientFailedException(e);
		} catch (IOException e) {
			throw new RecipientFailedException(e);
		}
	}
}
