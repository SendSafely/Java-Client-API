package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class UserInformationRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/user/";
	
	public UserInformationRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
}
