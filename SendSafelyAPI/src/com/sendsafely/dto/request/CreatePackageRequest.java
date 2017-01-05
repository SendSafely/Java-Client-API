package com.sendsafely.dto.request;

import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class CreatePackageRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/";
	
	public CreatePackageRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setIsVDR(boolean isVDR) {
		this.setPostParam("vdr", isVDR);
	}
	
	public void setPackageUserEmail(String email) {
		this.setPostParam("packageUserEmail", email);
	}
	
}
