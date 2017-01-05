package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class GetPackagesRequest extends BaseRequest 
{	
	public GetPackagesRequest(JsonManager jsonManager, HTTPMethod method, String path) {
		initialize(jsonManager, method, path);
	}

	
}
