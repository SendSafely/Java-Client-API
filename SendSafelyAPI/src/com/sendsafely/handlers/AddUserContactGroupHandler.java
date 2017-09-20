package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.AddUserToContactGroupRequest;
import com.sendsafely.dto.response.AddGroupResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.AddEmailContactGroupFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class AddUserContactGroupHandler extends BaseHandler{

	private AddUserToContactGroupRequest request;

	public AddUserContactGroupHandler(UploadManager uploadManager, AddUserToContactGroupRequest addEmailToContactGroupRequest) {
		super(uploadManager);
		
		this.request = addEmailToContactGroupRequest;
	}
 
	public String makeRequest() throws AddEmailContactGroupFailedException {
		AddGroupResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new AddEmailContactGroupFailedException(response.getMessage());
		}
		return convert(response);
	}
	
	private String convert(AddGroupResponse response) {
		return response.getUserId();
	}

	protected AddGroupResponse send() throws AddEmailContactGroupFailedException 
	{
		try {
			return send(request, new AddGroupResponse());
		} catch (IOException | SendFailedException e) {
			throw new AddEmailContactGroupFailedException(e);
		}
	}
}
