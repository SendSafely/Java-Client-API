package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.MoveDirectoryRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DirectoryOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class MoveDirectoryHandler extends BaseHandler {

	private MoveDirectoryRequest request;

	protected MoveDirectoryHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new MoveDirectoryRequest(this.uploadManager.getJsonManager());
	}

	public BaseResponse makeRequest(String packageId, String sourceDirectoryId, String targetDirectoryId) throws DirectoryOperationFailedException {
		request.setPackageId(packageId);
		request.setSourceDirectoryId(sourceDirectoryId);
		request.setTargetDirectoryId(targetDirectoryId);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new DirectoryOperationFailedException(response.getMessage());
		}
		return response;
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
