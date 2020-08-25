package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class FinalizePackageRequest extends BaseRequest 
{	
	
	private HTTPMethod method = HTTPMethod.POST;
	private String path = "/package/" + GetParam.PACKAGE_ID + "/finalize/";
	
	public FinalizePackageRequest(JsonManager jsonManager) {
		initialize(jsonManager, method, path);
	}
	
	public void setPackageId(String packageId)
	{
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public void setPassword(String password) {
		super.setPostParam("password", password);
	}
	
	public void setChecksum(String checksum) {
		super.setPostParam("checksum", checksum);
	}
	
	public void setUndisclosedRecipients(boolean undisclosedRecipients) {
		super.setPostParam("undisclosedRecipients", undisclosedRecipients);
	}
	
	public void setAllowReplyAll(boolean allowReplyAll) {
		super.setPostParam("allowReplyAll", allowReplyAll);
	}
	
}
