package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class AddMessageRequest extends BaseRequest {

	protected HTTPMethod method = HTTPMethod.PUT;
	protected String path = "/package/" + GetParam.PACKAGE_ID + "/message/";
	
	public AddMessageRequest() {
		initialize(method, path);
	}
	
	public AddMessageRequest(HTTPMethod method, String path) {
		this.method = method;
		this.path = path;
		initialize(method, path);
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
	
	@Override
	public String getPath()
	{
		return super.getPath();
	}
	
}
