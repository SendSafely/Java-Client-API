package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.DeleteDirectoryRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DirectoryOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class DeleteDirectoryHandler extends BaseHandler {

private DeleteDirectoryRequest request;
	
	public DeleteDirectoryHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new DeleteDirectoryRequest(uploadManager.getJsonManager());
	}

	public void makeRequest(String packageId, String directoryId) throws DirectoryOperationFailedException {
		request.setPackageId(packageId);
		request.setDirectoryId(directoryId);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new DirectoryOperationFailedException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws DirectoryOperationFailedException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException | IOException e) {
			throw new DirectoryOperationFailedException(e.getMessage());
		}
	}
}
