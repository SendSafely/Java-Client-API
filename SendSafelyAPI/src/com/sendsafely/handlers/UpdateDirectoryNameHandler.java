package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.UpdateDirectoryNameRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DirectoryOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class UpdateDirectoryNameHandler extends BaseHandler {
	private UpdateDirectoryNameRequest request;

	protected UpdateDirectoryNameHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new UpdateDirectoryNameRequest(this.uploadManager.getJsonManager());
	}

	public String makeRequest(String packageId, String directoryId, String directoryName) throws DirectoryOperationFailedException {
		request.setPackageId(packageId);
		request.setDirectoryId(directoryId);
		request.setDirectoryName(directoryName);
		
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new DirectoryOperationFailedException(response.getMessage());
		}
		return response.getMessage();	
	}

	protected BaseResponse send() throws DirectoryOperationFailedException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (IOException | SendFailedException e) {
			throw new DirectoryOperationFailedException(e);
		}
	}
}
