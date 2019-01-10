package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.AddPublicKeyRequest;
import com.sendsafely.dto.response.AddPublicKeyResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.PublicKeysFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class UploadPublicKey extends BaseHandler
{

	private AddPublicKeyRequest request;

	public UploadPublicKey(UploadManager uploadManager) {
		super(uploadManager);
        request = new AddPublicKeyRequest(uploadManager.getJsonManager());
	}

	public String makeRequest(String publicKey, String description) throws PublicKeysFailedException {
		
		request.setPublicKey(publicKey);
		request.setDescription(description);
		AddPublicKeyResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
            return response.getId();
		}
		else
		{
			throw new PublicKeysFailedException(response.getMessage());
		}
	}
	
	protected AddPublicKeyResponse send() throws PublicKeysFailedException
	{
		try {
			return send(request, new AddPublicKeyResponse());
		} catch (SendFailedException e) {
			throw new PublicKeysFailedException(e);
		} catch (IOException e) {
			throw new PublicKeysFailedException(e);
		}
	}	
}
