package com.sendsafely.handlers;

import java.io.IOException;
import java.util.List;

import com.sendsafely.dto.request.GetPackagesRequest;
import com.sendsafely.dto.response.GetPackagesResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.exceptions.GetPackagesException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class GetPackagesHandler extends BaseHandler 
{	
	
	private GetPackagesRequest request;
	
	public GetPackagesHandler(UploadManager uploadManager, GetPackagesRequest request) {
		super(uploadManager);
		
		this.request = request;
	}

	public List<String> makeRequest() throws GetPackagesException {
		GetPackagesResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return response.getPackages();
		}
		else
		{
			throw new GetPackagesException(response.getMessage());
		}
	}
	
	protected GetPackagesResponse send() throws GetPackagesException
	{
		try {
			return send(request, new GetPackagesResponse());
		} catch (SendFailedException e) {
			throw new GetPackagesException(e);
		} catch (IOException e) {
			throw new GetPackagesException(e);
		}
	}
}
