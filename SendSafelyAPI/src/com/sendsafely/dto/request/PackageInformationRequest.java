package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class PackageInformationRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.GET;
	private static String path = "/package/" + GetParam.PACKAGE_ID + "/";
	
	public PackageInformationRequest() {
		super(method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
