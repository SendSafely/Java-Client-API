package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.FileInfo;
import com.sendsafely.dto.request.FileInformationRequest;
import com.sendsafely.dto.response.FileInfoResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.FileOperationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class FileInformationHandler extends BaseHandler {
private FileInformationRequest request;
	
	public FileInformationHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new FileInformationRequest(uploadManager.getJsonManager());
	}

	public FileInfo makeRequest(String packageId, String fileId, String directoryId) throws FileOperationFailedException {
		request.setPackageId(packageId);
		request.setFileId(fileId);
		request.setDirectoryId(directoryId);
		FileInfoResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) {
			throw new FileOperationFailedException(response.getMessage());
		}
		
		return convert(response);
	}
	
	protected FileInfoResponse send() throws FileOperationFailedException
	{
		try {
			return send(request, new FileInfoResponse());
		} catch (SendFailedException | IOException e) {
			throw new FileOperationFailedException(e.getMessage());
		} 
	}
	
	protected FileInfo convert(FileInfoResponse obj)
	{
		FileInfo info = new FileInfo();
		info = obj.getFile();
		return info;
	}
	
}
