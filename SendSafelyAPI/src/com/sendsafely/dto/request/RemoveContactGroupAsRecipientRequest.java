package com.sendsafely.dto.request;

import com.sendsafely.enums.GetParam;
import com.sendsafely.enums.HTTPMethod;
import com.sendsafely.json.JsonManager;

public class RemoveContactGroupAsRecipientRequest extends BaseRequest{
	
	private HTTPMethod method = HTTPMethod.DELETE;
	protected String path = "/package/" + GetParam.PACKAGE_ID + "/group/" + GetParam.GROUP_ID + "/";
	
	public void setGroupId(String groupId) {
		super.setGetParam(GetParam.GROUP_ID, groupId);
	}
	
	public void setPackageId(String packageId) {
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
	}
	
	public RemoveContactGroupAsRecipientRequest(JsonManager jsonManager, String packageId, String groupId){
		initialize(jsonManager, method, path);
		super.setGetParam(GetParam.PACKAGE_ID, packageId);
		super.setGetParam(GetParam.GROUP_ID, groupId);
		
	}
}
