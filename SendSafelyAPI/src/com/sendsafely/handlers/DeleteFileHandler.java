package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.DeleteFileRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.FileOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class DeleteFileHandler extends BaseHandler {
private DeleteFileRequest request;
	
	public DeleteFileHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new DeleteFileRequest(uploadManager.getJsonManager());
	}

	public void makeRequest(String packageId, String directoryId, String fileId) throws FileOperationFailedException {
		request.setPackageId(packageId);
		request.setDirectoryId(directoryId);
		request.setFileId(fileId);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new FileOperationFailedException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws FileOperationFailedException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException | IOException e) {
			throw new FileOperationFailedException(e);
		}
	}
}
