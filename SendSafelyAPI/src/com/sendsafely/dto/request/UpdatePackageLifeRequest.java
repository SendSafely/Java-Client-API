package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class UpdatePackageLifeRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/";
	
	public UpdatePackageLifeRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
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
