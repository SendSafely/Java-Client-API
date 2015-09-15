package com.sendsafely.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.sendsafely.Package;
import com.sendsafely.dto.PackageURL;
import com.sendsafely.dto.request.FinalizePackageRequest;
import com.sendsafely.dto.response.CreatePackageResponse;
import com.sendsafely.dto.response.FinalizePackageResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.Endpoint;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.ApproverRequiredException;
import com.sendsafely.exceptions.FinalizePackageFailedException;
import com.sendsafely.exceptions.LimitExceededException;
import com.sendsafely.exceptions.PackageInformationFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;
import com.sendsafely.utils.CryptoUtil;

public class FinalizePackageHandler extends BaseHandler 
{	
	
	private FinalizePackageRequest request = new FinalizePackageRequest();
	
	public FinalizePackageHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public PackageURL makeRequest(String packageId, String keyCode) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		request.setPackageId(packageId);
		
		Package info;
		try {
			info = ((PackageInformationHandler)(HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION))).makeRequest(packageId);
		} catch (PackageInformationFailedException e) {
			throw new FinalizePackageFailedException(e);
		}
		
		request.setChecksum(CryptoUtil.createChecksum(keyCode, info.getPackageCode()));
		FinalizePackageResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS || response.getResponse() == APIResponse.PACKAGE_NEEDS_APPROVAL) 
		{
			return convert(response, keyCode);
		}
		else if(response.getResponse() == APIResponse.LIMIT_EXCEEDED)
		{
			throw new LimitExceededException(response.getMessage());
		}
		else if(response.getResponse() == APIResponse.APPROVER_REQUIRED)
		{
			throw new ApproverRequiredException();
		}
		else
		{
			throw new FinalizePackageFailedException(response.getMessage(), response.getErrors());
		}
	}
	
	public PackageURL makeRequest(String packageId, String keyCode, boolean undisclosedRecipients, String password) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		
		if(password != null) {
			request.setPassword(password);
		}
		request.setUndisclosedRecipients(undisclosedRecipients);
		
		return makeRequest(packageId, keyCode);
	}
	
	public PackageURL makeRequest(String packageId, String keyCode, boolean undisclosedRecipients) throws LimitExceededException, FinalizePackageFailedException, ApproverRequiredException {
		
		request.setUndisclosedRecipients(undisclosedRecipients);
		
		return makeRequest(packageId, keyCode);
	}
	
	protected PackageURL convert(FinalizePackageResponse response, String keyCode) throws FinalizePackageFailedException
	{
		try {
			PackageURL pUrl = new PackageURL();
			pUrl.setSecureLink(new URL(response.getMessage() + "#keyCode=" + keyCode));
			pUrl.setKeycode(keyCode);
			pUrl.setNeedsApproval(response.getResponse() == APIResponse.PACKAGE_NEEDS_APPROVAL);
			return pUrl;
		} catch (MalformedURLException e) {
			throw new FinalizePackageFailedException(e);
		}
	}
	
	protected FinalizePackageResponse send() throws FinalizePackageFailedException
	{
		try {
			return send(request, new FinalizePackageResponse());
		} catch (SendFailedException e) {
			throw new FinalizePackageFailedException(e);
		} catch (IOException e) {
			throw new FinalizePackageFailedException(e);
		}
	}
	
	protected Package convert(CreatePackageResponse obj)
	{
		Package info = new Package();
		info.setPackageCode(obj.getPackageCode());
		info.setPackageId(obj.getPackageId());
		info.setServerSecret(obj.getServerSecret());
		return info;
	}
	
}
