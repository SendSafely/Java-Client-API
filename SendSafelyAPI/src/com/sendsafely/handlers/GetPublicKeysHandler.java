package com.sendsafely.handlers;

import com.sendsafely.Recipient;
import com.sendsafely.dto.PublicKey;
import com.sendsafely.dto.request.GetPublicKeysRequest;
import com.sendsafely.dto.request.GetRecipientRequest;
import com.sendsafely.dto.response.AddRecipientResponse;
import com.sendsafely.dto.response.GetPublicKeysResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.PublicKeysFailedException;
import com.sendsafely.exceptions.RecipientFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

import java.io.IOException;
import java.util.List;

public class GetPublicKeysHandler extends BaseHandler
{

	private GetPublicKeysRequest request;

	public GetPublicKeysHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new GetPublicKeysRequest(uploadManager.getJsonManager());
	}

	public List<PublicKey> makeRequest(String packageId) throws PublicKeysFailedException {
		
		request.setPackageId(packageId);
		GetPublicKeysResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
            return response.getPublicKeys();
		}
		else
		{
			throw new PublicKeysFailedException(response.getMessage());
		}
	}
	
	protected GetPublicKeysResponse send() throws PublicKeysFailedException
	{
		try {
			return send(request, new GetPublicKeysResponse());
		} catch (SendFailedException e) {
			throw new PublicKeysFailedException(e);
		} catch (IOException e) {
			throw new PublicKeysFailedException(e);
		}
	}
	
}
