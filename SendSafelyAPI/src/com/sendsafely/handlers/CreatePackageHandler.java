package com.sendsafely.handlers;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.util.encoders.Base64Encoder;
import org.bouncycastle.util.encoders.UrlBase64;
import org.bouncycastle.util.encoders.UrlBase64Encoder;

import com.sendsafely.File;
import com.sendsafely.Package;
import com.sendsafely.Recipient;
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
	
	private CreatePackageRequest request;
	private boolean isVDR = false;
	private String packageUserEmail = null;
	
	public CreatePackageHandler(UploadManager uploadManager) {
		super(uploadManager);
        request = new CreatePackageRequest(uploadManager.getJsonManager());
	}
	
	public void setVDR(boolean isVDR) {
		this.isVDR = isVDR;
	}
	
	public Package makeRequest(String email) throws CreatePackageFailedException, LimitExceededException {
		this.packageUserEmail = email;
		return makeRequest();
	}
	
	public Package makeRequest() throws CreatePackageFailedException, LimitExceededException {
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
		this.request.setIsVDR(this.isVDR);
		this.request.setPackageUserEmail(this.packageUserEmail);

		try {
			return send(request, new CreatePackageResponse());
		} catch (SendFailedException e) {
			throw new CreatePackageFailedException(e);
		} catch (IOException e) {
			throw new CreatePackageFailedException(e);
		}
	}
	
	protected Package convert(CreatePackageResponse obj) throws CreatePackageFailedException
	{
		try
		{
			Package info = getPackageInformation(obj);
			info.setKeyCode(CryptoUtil.GenerateKeyCode());
			return info;
		}
		catch (TokenGenerationFailedException e)
		{
			throw new CreatePackageFailedException(e);
		}
	}
	
	protected Package getPackageInformation(CreatePackageResponse obj) throws CreatePackageFailedException
	{	
		Package info = new Package();
		try {
			info = ((PackageInformationHandler)(HandlerFactory.getInstance(uploadManager, Endpoint.PACKAGE_INFORMATION))).makeRequest(obj.getPackageId());
		} catch (PackageInformationFailedException e) {
			info.setPackageId(obj.getPackageId());
			info.setPackageCode(obj.getPackageCode());
			info.setServerSecret(obj.getServerSecret());
			info.setFiles(new ArrayList<File>());
			info.setRecipients(new ArrayList<Recipient>());
		}
		
		return info;
	}
	
}
