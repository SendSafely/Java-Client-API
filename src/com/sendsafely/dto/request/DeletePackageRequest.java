package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class DeletePackageRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.DELETE;
	private static String path = "/package/" + GetParam.PACKAGE_ID + "/";
	
	public DeletePackageRequest() {
		super(method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
}
