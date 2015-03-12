package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class EnterpriseInfoRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.GET;
	private static String path = "/enterprise/";
	
	public EnterpriseInfoRequest() {
		super(method, path);
	}
	
	public void setVersion(double version)
	{
		setGetParam(GetParam.VERSION_NO, version);
	}
	
	
	
}
