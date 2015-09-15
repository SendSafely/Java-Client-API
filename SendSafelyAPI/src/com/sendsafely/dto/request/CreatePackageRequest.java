package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;

public class CreatePackageRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/";
	
	public CreatePackageRequest() {
		initialize(method, path);
	}
	
	public void setIsVDR(boolean isVDR) {
		this.setPostParam("vdr", isVDR);
	}
	
}
