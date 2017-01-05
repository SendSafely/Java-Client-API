package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class AddTopicRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.PUT;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/topic/";
	
	public AddTopicRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setName(String name)
	{
		super.setPostParam("name", name);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
