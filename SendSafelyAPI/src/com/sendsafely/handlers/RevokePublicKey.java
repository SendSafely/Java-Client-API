package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.RevokePublicKeyRequest;
import com.sendsafely.dto.response.BaseResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.PublicKeysFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class RevokePublicKey extends BaseHandler
{

	private RevokePublicKeyRequest request;

	public RevokePublicKey(UploadManager uploadManager) {
		super(uploadManager);
        request = new RevokePublicKeyRequest(uploadManager.getJsonManager());
	}

	public String makeRequest(String publicKeyId) throws PublicKeysFailedException {
		
		request.setPublicKey(publicKeyId);
		BaseResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
            return response.getMessage();
		}
		else
		{
			throw new PublicKeysFailedException(response.getMessage());
		}
	}
	
	protected BaseResponse send() throws PublicKeysFailedException
	{
		try {
			return send(request, new BaseResponse());
		} catch (SendFailedException e) {
			throw new PublicKeysFailedException(e);
		} catch (IOException e) {
			throw new PublicKeysFailedException(e);
		}
	}	
}
