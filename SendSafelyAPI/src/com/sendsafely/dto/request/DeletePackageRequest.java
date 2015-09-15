package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class DeletePackageRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.DELETE;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/";
	
	public DeletePackageRequest() {
		initialize(method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
}
