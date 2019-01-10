package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.UpdatePackageLifeRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.dto.response.PackageInformationResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.UpdatePackageLifeException;
import com.sendsafely.upload.UploadManager;

public class UpdatePackageLifeHandler extends BaseHandler 
{	
	
	private UpdatePackageLifeRequest request;
	
	public UpdatePackageLifeHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new UpdatePackageLifeRequest(uploadManager.getJsonManager());
	}

	public void makeRequest(String packageId, int life) throws UpdatePackageLifeException {
		request.setPackageId(packageId);
		request.setPackageLife(life);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new UpdatePackageLifeException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws UpdatePackageLifeException
	{
		try {
			return send(request, new PackageInformationResponse());
		} catch (SendFailedException e) {
			throw new UpdatePackageLifeException(e);
		} catch (IOException e) {
			throw new UpdatePackageLifeException(e);
		}
	}
}
