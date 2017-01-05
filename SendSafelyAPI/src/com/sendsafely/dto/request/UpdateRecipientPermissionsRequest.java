package com.sendsafely.dto.request;

import com.sendsafely.enums.CountryCode;
import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class UpdateRecipientPermissionsRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/recipient/" + GetParam.RECIPIENT_ID + "/permissions/";
	
	public UpdateRecipientPermissionsRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setPhonenumber(String email)
	{
		super.setPostParam("email", email);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setRecipientId(String recipientId)
	{
		super.setGetParam(GetParam.RECIPIENT_ID, recipientId);
	}
	
	public void setCanAddMessage(boolean canAddMessage) {
		super.setPostParam("canAddMessage", canAddMessage);
	}
	
	public void setCanAddFile(boolean canAddFile) {
		super.setPostParam("canAddFile", canAddFile);
	}
	
	public void setCanAddRecipient(boolean canAddRecipient) {
		super.setPostParam("canAddRecipient", canAddRecipient);
	}
	
}
