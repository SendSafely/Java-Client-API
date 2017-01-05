package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class RemoveRecipientRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.DELETE;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/recipient/" + GetParam.RECIPIENT_ID + "/";
	
	public RemoveRecipientRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setRecipientId(String recipientId)
	{
		super.setGetParam(GetParam.RECIPIENT_ID, recipientId);
	}
}
