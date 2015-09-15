package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;

public class GetPackagesRequest extends BaseRequest 
{	
	public GetPackagesRequest(HTTPMethod method, String path) {
		initialize(method, path);
	}

	
}
