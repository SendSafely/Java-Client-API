package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class VerifyCredentialsRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.GET;
	private static String path = "/config/verify-credentials/";
	
	public VerifyCredentialsRequest() {
		super(method, path);
	}
	
	public void setVersion(double version)
	{
		setGetParam(GetParam.VERSION_NO, version);
	}
	
	
	
}
