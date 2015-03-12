package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;

public class CreatePackageRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.PUT;
	private static String path = "/package/";
	
	public CreatePackageRequest() {
		super(method, path);
	}
	
}
