package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class AddRecipientRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/recipient/";
	
	public AddRecipientRequest(JsonManager jsonManager, String email) {
		initialize(jsonManager, method, path);
	
		super.setPostParam("email", email);
	}
	
	public AddRecipientRequest() {
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
