package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class AddRecipientRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.PUT;
	private static String path = "/package/" + GetParam.PACKAGE_ID + "/recipient/";
	
	public AddRecipientRequest() {
		super(method, path);
	}
	
	public void setEmail(String email)
	{
		super.setPostParam("email", email);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
