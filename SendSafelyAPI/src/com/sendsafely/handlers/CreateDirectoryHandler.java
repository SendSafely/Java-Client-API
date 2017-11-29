package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.CreateDirectoryRequest;
import com.sendsafely.dto.response.CreateDirectoryResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DirectoryOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class CreateDirectoryHandler extends BaseHandler {

	private CreateDirectoryRequest request;

	protected CreateDirectoryHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new CreateDirectoryRequest(this.uploadManager.getJsonManager());
	}

	public String makeRequest(String packageId, String parentDirectoryId, String directoryName) throws DirectoryOperationFailedException {
		request.setPackageId(packageId);
		request.setParentDirectoryId(parentDirectoryId);
		request.setDirectoryName(directoryName);
		CreateDirectoryResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new DirectoryOperationFailedException(response.getMessage());
		}
		return response.getMessage(); //returns directoryId. 
	}
	

	protected CreateDirectoryResponse send() throws DirectoryOperationFailedException 
	{
		try {
			return send(request, new CreateDirectoryResponse());
		} catch (IOException | SendFailedException e) {
			throw new DirectoryOperationFailedException(e.getMessage());
		}
	}
}
