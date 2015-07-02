package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class AddMessageRequest extends BaseRequest {

	private static HTTPMethod method = HTTPMethod.PUT;
	private static String path = "/package/" + GetParam.PACKAGE_ID + "/message/";
	
	public AddMessageRequest() {
		super(method, path);
	}
	
	public void setUploadType(String uploadType) 
	{
		super.setPostParam("uploadType", uploadType);
	}
	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	public void setMessage(String message) {
		super.setPostParam("message", message);
	}
	
}
