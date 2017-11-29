package com.sendsafely.handlers;

import java.io.IOException;
import java.util.List;

import com.sendsafely.ContactGroup;
import com.sendsafely.dto.request.BaseRequest;
import com.sendsafely.dto.request.GetContactGroupsRequest;
import com.sendsafely.dto.request.GetOrganizationContactGroupsRequest;
import com.sendsafely.dto.response.GetUserGroupsResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.GetContactGroupsFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetContactGroupsHandler extends BaseHandler{
	private BaseRequest request;

	public GetContactGroupsHandler(UploadManager uploadManager, GetContactGroupsRequest getUserGroupsRequest) {
		super(uploadManager);
		
		this.request = getUserGroupsRequest;
	}

	public GetContactGroupsHandler(UploadManager uploadManager,
			GetOrganizationContactGroupsRequest getOrganizationContactGroupsRequest) {
		super(uploadManager);
		
		this.request = getOrganizationContactGroupsRequest;
	}

	public List<ContactGroup> makeRequest() throws GetContactGroupsFailedException {
		GetUserGroupsResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new GetContactGroupsFailedException(response.getMessage());
		}
		return convert(response);
	}
	
	private List<ContactGroup> convert(GetUserGroupsResponse response) {
		return response.getContactGroups();
	}

	protected GetUserGroupsResponse send() throws GetContactGroupsFailedException 
	{
		try {
			return send(request, new GetUserGroupsResponse());
		} catch (IOException | SendFailedException e) {
			throw new GetContactGroupsFailedException(e);
		}
	}
}
