package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class VerifyVersionRequest extends BaseRequest
{	
	
	private static HTTPMethod method = HTTPMethod.GET;
	private static String path = "/config/version/JAVA_API/" + GetParam.VERSION_NO + "/";
	
	public VerifyVersionRequest() {
		super(method, path);
	}
	
	public void setVersion(double version)
	{
		setGetParam(GetParam.VERSION_NO, version);
	}
	
	
	
}
