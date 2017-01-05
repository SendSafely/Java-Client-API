package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.request.VerifyVersionRequest;
import com.sendsafely.dto.response.VerifyVersionResponse;
import com.sendsafely.enums.Version;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class VerifyVersionHandler extends BaseHandler 
{
	private VerifyVersionRequest request;
	
	public VerifyVersionHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new VerifyVersionRequest(uploadManager.getJsonManager());
	}

	public Version makeRequest() throws SendFailedException {
		VerifyVersionResponse response = send();
		return response.getVersion();
	}

	protected VerifyVersionResponse send() throws SendFailedException
	{
		try {
			return send(request, new VerifyVersionResponse());
		} catch (IOException e) {
			throw new SendFailedException(e);
		}
	}
	
	public void setVersion(double version) {
		request.setVersion(version);
	}
	
}
