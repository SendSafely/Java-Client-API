package com.sendsafely.handlers;

import java.io.IOException;

import com.sendsafely.dto.EnterpriseInfo;
import com.sendsafely.dto.request.EnterpriseInfoRequest;
import com.sendsafely.dto.response.EnterpriseInfoResponse;
import com.sendsafely.enums.APIResponse;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.exceptions.EnterpriseInfoFailedException;
import com.sendsafely.exceptions.SendFailedException;
import com.sendsafely.upload.UploadManager;

public class EnterpriseInfoHandler extends BaseHandler 
{	
	private EnterpriseInfoRequest request = new EnterpriseInfoRequest();
	
	public EnterpriseInfoHandler(UploadManager uploadManager) {
		super(uploadManager);
	}

	public EnterpriseInfo makeRequest() throws EnterpriseInfoFailedException {
		EnterpriseInfoResponse response = send();
		
		if(response.getResponse() == APIResponse.SUCCESS) 
		{
			return convert(response);
		}
		else
		{
			throw new EnterpriseInfoFailedException();
		}
	}
	
	protected EnterpriseInfoResponse send() throws EnterpriseInfoFailedException
	{
		try {
			return send(request, new EnterpriseInfoResponse());
		} catch (SendFailedException e) {
			throw new EnterpriseInfoFailedException(e);
		} catch (IOException e) {
			throw new EnterpriseInfoFailedException(e);
		}
	}
	
	protected EnterpriseInfo convert(EnterpriseInfoResponse obj)
	{
		EnterpriseInfo info = new EnterpriseInfo();
		info.setHost(obj.getHost());
		info.setSystemName(obj.getSystemName());
		info.setAllowUndisclosedRecipients(obj.getAllowUndisclosedRecipients());
		return info;
	}
	
}
