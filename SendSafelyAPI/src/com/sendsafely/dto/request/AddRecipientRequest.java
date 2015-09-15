package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class AddRecipientRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/recipient/";
	
	public AddRecipientRequest(String email) {
		initialize(method, path);
	
		super.setPostParam("email", email);
	}
	
	public AddRecipientRequest() {
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
