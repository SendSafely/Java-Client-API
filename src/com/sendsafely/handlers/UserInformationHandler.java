package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.UserInformation;
import com.sendsafely.dto.request.UserInformationRequest;
import com.sendsafely.dto.response.UserInformationResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UserInformationFailedException;
import com.sendsafely.upload.UploadManager;

public class UserInformationHandler extends BaseHandler 
{	
	
	private UserInformationRequest request = new UserInformationRequest();
	
	public UserInformationHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public UserInformation makeRequest() throws UserInformationFailedException {
		UserInformationResponse response = send();
		
		System.out.println("API: " + response.getResponse());
		
		if(response.getResponse() != APIResponse.SUCCESS) {
			throw new UserInformationFailedException();
		}
		
		return convert(response);
	}
	
	protected UserInformationResponse send() throws UserInformationFailedException
	{
		try {
			return send(request, new UserInformationResponse());
		} catch (SendFailedException e) {
			throw new UserInformationFailedException(e);
		} catch (IOException e) {
			throw new UserInformationFailedException(e);
		}
	}
	
	protected UserInformation convert(UserInformationResponse obj)
	{
		UserInformation info = new UserInformation();
		info.setClientKey(obj.getClientKey());
		info.setEmail(obj.getEmail());
		info.setFirstName(obj.getFirstName());
		info.setLastName(obj.getLastName());
		return info;
	}
	
}
