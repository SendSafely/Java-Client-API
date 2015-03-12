package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;

public class UserInformationRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.GET;
	private static String path = "/user/";
	
	public UserInformationRequest() {
		super(method, path);
	}
	
}
