package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class VerifyVersionRequest extends BaseRequest
{	
	
	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/config/version/JAVA_API/" + GetParam.VERSION_NO + "/";
	
	public VerifyVersionRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setVersion(double version)
	{
		setGetParam(GetParam.VERSION_NO, version);
	}
	
	
	
}
