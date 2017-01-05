package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class EnterpriseInfoRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/enterprise/";
	
	public EnterpriseInfoRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setVersion(double version)
	{
		setGetParam(GetParam.VERSION_NO, version);
	}
	
	
	
}
