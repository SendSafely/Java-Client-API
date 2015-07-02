package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;

public class UpdatePackageLifeRequest extends BaseRequest 
{	
	
	private static HTTPMethod method = HTTPMethod.POST;
	private static String path = "/package/" + GetParam.PACKAGE_ID + "/";
	
	public UpdatePackageLifeRequest() {
		super(method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setPackageLife(int life)
	{
		super.setPostParam("life", life);
	}
	
}
