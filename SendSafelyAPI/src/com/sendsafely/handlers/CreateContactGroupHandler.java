package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.CreateContactGroupRequest;
import com.sendsafely.dto.response.CreateContactGroupResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.CreateContactGroupFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class CreateContactGroupHandler extends BaseHandler{

	private CreateContactGroupRequest request;

	public CreateContactGroupHandler(UploadManager uploadManager) {
		super(uploadManager);
		
		this.request = new CreateContactGroupRequest(uploadManager.getJsonManager());
	}
 
	public String makeRequest(String groupName) throws CreateContactGroupFailedException {
		request.setgroupName(groupName);
		CreateContactGroupResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new CreateContactGroupFailedException(response.getMessage());
		}
		return convert(response);
	}
	
	public String makeRequest(String groupName, boolean isEnterpriseGroup) throws CreateContactGroupFailedException {
		request.setgroupName(groupName);
		request.setIsEnterpriseContactGroup(isEnterpriseGroup);
		CreateContactGroupResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new CreateContactGroupFailedException(response.getMessage());
		}
		return convert(response);
	}
	
	private String convert(CreateContactGroupResponse response) {
		return response.getContactGroupId();
	}

	protected CreateContactGroupResponse send() throws CreateContactGroupFailedException 
	{
		try {
			return send(request, new CreateContactGroupResponse());
		} catch (IOException | SendFailedException e) {
			throw new CreateContactGroupFailedException(e);
		}
	}
}
