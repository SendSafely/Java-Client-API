package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.VerifyCredentialsRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.InvalidCredentialsException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class VerifyCredentialsHandler extends BaseHandler 
{	
	
	private VerifyCredentialsRequest request = new VerifyCredentialsRequest();
	
	public VerifyCredentialsHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public String verify() throws InvalidCredentialsException {
		BaseResponse response = send();
		
		if (response.getResponse() == APIResponse.SUCCESS) 
		{
			return response.getMessage();
		}
		
		throw new InvalidCredentialsException(response.getMessage());
	}
	
	protected BaseResponse send() throws InvalidCredentialsException {
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new InvalidCredentialsException(e);
		} catch (IOException e) {
			throw new InvalidCredentialsException(e);
		}
	}
	
}
