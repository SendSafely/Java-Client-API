package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.DeletePackageRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.DeletePackageException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class DeletePackageHandler extends BaseHandler 
{	
	
	private DeletePackageRequest request;
	
	public DeletePackageHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new DeletePackageRequest(uploadManager.getJsonManager());
	}

	public void makeRequest(String packageId) throws DeletePackageException {
		request.setPackageId(packageId);
		BaseResponse response = send();
		
		if(response.getResponse() != APIResponse.SUCCESS) 
		{
			throw new DeletePackageException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws DeletePackageException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new DeletePackageException(e);
		} catch (IOException e) {
			throw new DeletePackageException(e);
		}
	}
}
