package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.UpdatePackageDescriptorRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UpdatePackageDescriptorFailedException;
import com.sendsafely.upload.UploadManager;

public class UpdatePackageDescriptorHandler extends BaseHandler {

	private UpdatePackageDescriptorRequest request;

	protected UpdatePackageDescriptorHandler(UploadManager uploadManager) {
		super(uploadManager);
		request = new UpdatePackageDescriptorRequest(this.uploadManager.getJsonManager());
	}

	public String makeRequest(String packageId, String packageDescriptor) throws UpdatePackageDescriptorFailedException {
		request.setPackageId(packageId);
		request.setPackageDescriptor(packageDescriptor);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new UpdatePackageDescriptorFailedException(response.getMessage());
		}
		return response.getMessage();
	}

	protected BaseResponse send() throws UpdatePackageDescriptorFailedException 
	{
		try {
			return send(request, new BaseResponse());
		} catch (IOException | SendFailedException e) {
			throw new UpdatePackageDescriptorFailedException(e.getMessage());
		}
	}
}
