package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class NotifyPackageRecipientsRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/notify/";
	
	public NotifyPackageRecipientsRequest(JsonManager jsonManager, String keyCode) {
		initialize(jsonManager, method, path);
	
		super.setPostParam("keycode", keyCode);
	}
	
	public NotifyPackageRecipientsRequest() {
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
}
