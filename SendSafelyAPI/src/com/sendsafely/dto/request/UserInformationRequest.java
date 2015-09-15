package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;

public class UserInformationRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.GET;
	private String path = "/user/";
	
	public UserInformationRequest() {
		initialize(method, path);
	}
	
}
