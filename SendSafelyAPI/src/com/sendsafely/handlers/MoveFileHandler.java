package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.MoveFileRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.FileOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class MoveFileHandler extends BaseHandler {

	private MoveFileRequest request;

	protected MoveFileHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new MoveFileRequest(this.uploadManager.getJsonManager());
		
	}

	public BaseResponse makeRequest(String packageId, String fileId, String targetDirectoryId) throws FileOperationFailedException {
		request.setPackageId(packageId);
		request.setFileId(fileId);
		request.setTargetDirectoryId(targetDirectoryId);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new FileOperationFailedException(response.getMessage());
		}
		return response;
	}
	


	protected BaseResponse send() throws FileOperationFailedException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (IOException | SendFailedException e) {
			throw new FileOperationFailedException(e);
		}
	}
}
