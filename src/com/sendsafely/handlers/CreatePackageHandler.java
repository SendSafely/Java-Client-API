package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.PackageInformation;
import com.sendsafely.dto.request.CreatePackageRequest;
import com.sendsafely.dto.response.CreatePackageResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.CreatePackageFailedException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.exceptions.TokenGenerationFailedException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;

public class CreatePackageHandler extends BaseHandler 
{	
	
	private CreatePackageRequest request = new CreatePackageRequest();
	
	public CreatePackageHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public PackageInformation makeRequest() throws CreatePackageFailedException, LimitExceededException {
		CreatePackageResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return convert(response);
		}
		else if(response.getResponse() == APIResponse.LIMIT_EXCEEDED)
		{
			throw new LimitExceededException(response.getMessage());
		}
		else
		{
			throw new CreatePackageFailedException(response.getMessage());
		}
	}
	
	protected CreatePackageResponse send() throws CreatePackageFailedException
	{
		try {
			return send(request, new CreatePackageResponse());
		} catch (SendFailedException e) {
			throw new CreatePackageFailedException(e);
		} catch (IOException e) {
			throw new CreatePackageFailedException(e);
		}
	}
	
	protected PackageInformation convert(CreatePackageResponse obj) throws CreatePackageFailedException
	{
		
		try
		{
			PackageInformation info = getPackageInformation(obj.getPackageId());
			info.setKeyCode(CryptoUtil.GenerateKeyCode());
			return info;
		}
		catch (TokenGenerationFailedException e)
		{
			throw new CreatePackageFailedException(e);
		}
	}
	
	protected PackageInformation getPackageInformation(String packageId) throws CreatePackageFailedException
	{
		PackageInformation info;
		try {
			info = ((PackageInformationHandler)(HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION))).makeRequest(packageId);
		} catch (PackageInformationFailedException e) {
			throw new CreatePackageFailedException(e);
		}
		
		return info;
	}
	
}
